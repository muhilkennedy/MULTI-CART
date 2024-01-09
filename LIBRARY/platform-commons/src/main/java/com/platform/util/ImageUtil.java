package com.platform.util;

import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;

/**
 * @author Muhil
 *
 */
public class ImageUtil {

	public static final int Thumbnail_AspectWidth = 200;
	public static final int Thumbnail_AspectHeight = 200;
	public static final int HD_Thumbnail_AspectWidth = 600;
	public static final int HD_Thumbnail_AspectHeight = 600;
	public static final int Banner_AspectRatio = 1080;
	public static final String Thumbnail_Exension = "png";
	public static final String Thumbnail_Suffix = "_Thumbnail";

	public static final String[] IMAGE_EXTENTIONS = { "png", "jpg", "jpeg" };

	public static byte[] compressImage(byte[] data) {
		return compressImage(data);
	}

	public static byte[] compressImage(byte[] data, boolean useHighSpeedConversion) {
		Deflater deflater = new Deflater();
		deflater.setLevel(useHighSpeedConversion ? Deflater.BEST_SPEED : Deflater.BEST_COMPRESSION);
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] tmp = new byte[4 * 1024];
		while (!deflater.finished()) {
			int size = deflater.deflate(tmp);
			outputStream.write(tmp, 0, size);
		}
		try {
			outputStream.close();
		} catch (Exception ignored) {
		}
		return outputStream.toByteArray();
	}

	public static byte[] decompressImage(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] tmp = new byte[4 * 1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(tmp);
				outputStream.write(tmp, 0, count);
			}
			outputStream.close();
		} catch (Exception ignored) {
		}
		return outputStream.toByteArray();
	}

	/**
	 * @param in           input stream
	 * @param aspectWidth
	 * @param aspectHeight
	 * @return
	 * @throws IllegalArgumentException
	 * @throws ImagingOpException
	 * @throws IOException
	 */
	public static BufferedImage resizeImage(InputStream in, int aspectWidth, int aspectHeight)
			throws IllegalArgumentException, ImagingOpException, IOException {
		return Scalr.resize(ImageIO.read(in), Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, aspectWidth, aspectHeight,
				Scalr.OP_ANTIALIAS);
	}

	public static byte[] getBannerImage(byte[] image, String extension)
			throws IllegalArgumentException, ImagingOpException, IOException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
			InputStream in = new ByteArrayInputStream(image);
			BufferedImage bImage = Scalr.resize(ImageIO.read(in), Scalr.Method.ULTRA_QUALITY, Scalr.Mode.AUTOMATIC,
					Banner_AspectRatio, Scalr.OP_ANTIALIAS);
			ImageIO.write(bImage, extension, baos);
			return baos.toByteArray();
		}
	}

	public static File compressImageForBanner(File file) throws IOException {
		byte[] image = getBannerImage(Files.readAllBytes(file.toPath()),
				FileUtil.getFileExtensionFromName(FileUtil.getFileExtension(file.getName())));
		FileUtils.writeByteArrayToFile(file, image);
		return file;
	}

	public static File getPNGThumbnailImage(File image) throws IOException {
		return getPNGThumbnailImage(image, false);
	}

	public static File getPNGThumbnailImage(File image, boolean hdImage) throws IOException {
		File tempFile = new File(FileUtil.getTempDirectory() + FileUtil.getFileName(image.getName()) + Thumbnail_Suffix
				+ FileUtil.getFileExtension(image.getName()));
		byte[] result = getPNGThumbnailImage(Files.readAllBytes(image.toPath()), hdImage);
		FileUtils.writeByteArrayToFile(tempFile, result);
		return tempFile;
	}

	public static byte[] getPNGThumbnailImage(byte[] image, boolean hdImage) throws IOException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
			InputStream in = new ByteArrayInputStream(image);
			BufferedImage bImage = resizeImage(in, hdImage ? HD_Thumbnail_AspectWidth : Thumbnail_AspectWidth,
					hdImage ? HD_Thumbnail_AspectHeight : Thumbnail_AspectHeight);
			ImageIO.write(bImage, Thumbnail_Exension, baos);
			return baos.toByteArray();
		}
	}

	public static byte[] getThumbnailImage(byte[] image, String extension) throws IOException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
			InputStream in = new ByteArrayInputStream(image);
			BufferedImage bImage = resizeImage(in, Thumbnail_AspectWidth, Thumbnail_AspectHeight);
			ImageIO.write(bImage, extension, baos);
			return baos.toByteArray();
		}
	}

}
