package com.platform.messages;

import java.util.stream.Stream;

/**
 * @author Muhil
 */
public enum GoogleOauthConstants {

	CLIENTID, CLIENTSECRET;

	public static Stream<GoogleOauthConstants> stream() {
		return Stream.of(GoogleOauthConstants.values());
	}

}
