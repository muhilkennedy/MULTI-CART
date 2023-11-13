package com.platform.antivirus;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

import com.platform.configuration.PlatformConfiguration;
import com.platform.util.Log;
import com.platform.util.PlatformPropertiesUtil;

/**
 * @author muhil
 */
public class ClamAVService {

	private static final int CHUNK_SIZE = 2048;
	private static final String RESPONSE_OK = "stream: OK";
	private static final String PONG = "PONG";
	private static final String FOUND_SUFFIX = "FOUND";
	private static final String ERROR_SUFFIX = "ERROR";
	private static final String STREAM_PREFIX = "stream:";
	private static final int CONNECTION_TIMEOUT = 2000;
	private static final int READ_TIMEOUT = 20000;

	private static String HOST;
	private static int PORT;
	private static ClamAVService instance;

	private ClamAVService(String host, int port) {
		HOST = host;
		PORT = port;
	}

	public static ClamAVService getInstance() {
		if (instance == null && Boolean.parseBoolean(
				PlatformConfiguration.getAvProperties().getProperty(PlatformPropertiesUtil.KEY_AV_ENABLED))) {
			instance = new ClamAVService(
					PlatformConfiguration.getAvProperties().getProperty(PlatformPropertiesUtil.KEY_AV_HOST),
					Integer.parseInt(
							PlatformConfiguration.getAvProperties().getProperty(PlatformPropertiesUtil.KEY_AV_PORT)));
		}
		return instance;
	}

	public boolean ping() {
		try {
			return processCommand("zPING\0".getBytes()).trim().equalsIgnoreCase(PONG);
		} catch (Exception e) {
			Log.platform.error("Error pinging to ClamAV {}", e);
			return false;
		}
	}

	private String processCommand(final byte[] cmd) throws IOException {
		String response = "";
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(HOST, PORT), CONNECTION_TIMEOUT);
			socket.setSoTimeout(READ_TIMEOUT);
			try (DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
				dos.write(cmd);
				dos.flush();
				InputStream is = socket.getInputStream();
				int read = CHUNK_SIZE;
				byte[] buffer = new byte[CHUNK_SIZE];
				while (read == CHUNK_SIZE) {
					try {
						read = is.read(buffer);
					} catch (IOException e) {
						Log.platform.error("Error reading result from socket : {} ", e);
						break;
					}
					response = new String(buffer, 0, read);
				}
			}
		}
		Log.platform.debug("ClamAV processCommand : {}", response);
		return response;
	}

	public VirusScanResult scan(final InputStream inputStream) throws IOException {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(HOST, PORT), CONNECTION_TIMEOUT);
			socket.setSoTimeout(READ_TIMEOUT);
			try (OutputStream outStream = new BufferedOutputStream(socket.getOutputStream())) {
				outStream.write("zINSTREAM\0".getBytes(StandardCharsets.UTF_8));
				outStream.flush();
				byte[] buffer = new byte[CHUNK_SIZE];
				try (InputStream inStream = socket.getInputStream()) {
					int read = inputStream.read(buffer);
					while (read >= 0) {
						byte[] chunkSize = ByteBuffer.allocate(4).putInt(read).array();
						outStream.write(chunkSize);
						outStream.write(buffer, 0, read);

						if (inStream.available() > 0) {
							byte[] reply = IOUtils.toByteArray(inStream);
							throw new IOException("Reply from server: " + new String(reply, StandardCharsets.UTF_8));
						}
						read = inputStream.read(buffer);
					}
					outStream.write(new byte[] { 0, 0, 0, 0 });
					outStream.flush();
					return populateVirusScanResult(new String(IOUtils.toByteArray(inStream)).trim());
				}
			}
		}
	}

	private VirusScanResult populateVirusScanResult(final String result) {
		VirusScanResult scanResult = new VirusScanResult();
		scanResult.setResult(result);
		if (result == null || result.isEmpty()) {
			scanResult.setStatus(VirusScanStatus.ERROR);
		} else if (RESPONSE_OK.equals(result)) {
			scanResult.setStatus(VirusScanStatus.PASSED);
		} else if (result.endsWith(FOUND_SUFFIX)) {
			scanResult.setStatus(VirusScanStatus.FAILED);
			scanResult.setSignature(
					result.substring(STREAM_PREFIX.length(), result.lastIndexOf(FOUND_SUFFIX) - 1).trim());
		} else if (result.endsWith(ERROR_SUFFIX)) {
			scanResult.setStatus(VirusScanStatus.ERROR);
		}
		Log.platform.info("ClamAV Scan Result : {}", scanResult);
		return scanResult;
	}
}