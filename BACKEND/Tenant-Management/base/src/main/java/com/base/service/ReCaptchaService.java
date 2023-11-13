package com.base.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.base.util.Log;
import com.platform.exception.ReCaptchaException;
import com.platform.messages.RecaptchaResponse;

/**
 * @author Muhil
 * Google recaptcha verification service.
 */
@Service
public class ReCaptchaService {
	
	private static final String KEY_SECRET = "secret";
	private static final String KEY_RESPONSE = "response";

	private final RestTemplate restTemplate;

	@Value("${google.recaptcha.secret.key}")
	public String recaptchaSecret;

	@Value("${google.recaptcha.verify.url}")
	public String recaptchaVerifyUrl;

	public ReCaptchaService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public boolean verify(String response) throws ReCaptchaException {
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add(KEY_SECRET, recaptchaSecret);
		param.add(KEY_RESPONSE, response);
		RecaptchaResponse recaptchaResponse = null;
		try {
			recaptchaResponse = this.restTemplate.postForObject(recaptchaVerifyUrl, param, RecaptchaResponse.class);
		} catch (RestClientException e) {
			Log.base.error("Captcha verification exception - {}", e);
			throw new ReCaptchaException(e.getMessage());
		}
		return (recaptchaResponse != null && recaptchaResponse.isSuccess());
	}

}
