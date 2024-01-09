package com.platform.util;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.auth.openidconnect.IdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

/**
 * @author Muhil
 */
public class PlatformOauthUtil {

	public static GoogleIdTokenVerifier getGoogleTokenVerifier(String clientId) {
		NetHttpTransport transport = new NetHttpTransport();
		JsonFactory jsonFactory = new GsonFactory();
		GoogleIdTokenVerifier googleIdTokenVerifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
				.setAudience(Collections.singletonList(clientId)).build();
		return googleIdTokenVerifier;
	}

	public static Payload verifyAndParseGoogleToken(String clientId, String token)
			throws GeneralSecurityException, IOException {
		NetHttpTransport transport = new NetHttpTransport();
		JsonFactory jsonFactory = new GsonFactory();
		GoogleIdTokenVerifier googleIdTokenVerifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
				.setAudience(Collections.singletonList(clientId)).build();
		GoogleIdToken tokenId = googleIdTokenVerifier.verify(token);
		return tokenId.getPayload();
	}

}
