package com.service.internal;

import javax.crypto.SecretKey;

import com.base.util.Log;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

public class KeyGeneratorApplication {
	
	public static String generateSHA512SecretKey() {
		SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
		String secretString = Encoders.BASE64.encode(key.getEncoded());
		return secretString;
	}
	
	public static void main(String args[]) {
		System.out.println("key:"+generateSHA512SecretKey());
	}

}
