package com.base.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

/**
 * @author Muhil
 *
 */
public class EmailUtil {

	public static final int MAX_EMAIL_THREADS = 10;
	public static final String TEMPLATE_EXTENTION = ".ftl";

	public static List<Map> getBasicInlineImages(String tenantId, String tenantName, String tagLine,
			String tenantContact, String tenantEmail, String tenantUniqueName) {
		List<Map> inlineListMap = new ArrayList<Map>();
		Map<String, File> inlineImages = new HashMap<String, File>();
		Map<String, String> valueMap = new HashMap<String, String>();
		try {
			File tenantLogo = File.createTempFile(tenantId + "-", ".png");
			FileUtils.copyFile(ResourceUtils.getFile("classpath:assets/images/logo.png"), tenantLogo);
			File logoIcon = File.createTempFile(tenantId + "-", ".png");
			FileUtils.copyFile(ResourceUtils.getFile("classpath:assets/images/logo-favicon.png"), logoIcon);
			File calendarLogo = File.createTempFile(tenantId + "-", ".png");
			FileUtils.copyFile(ResourceUtils.getFile("classpath:assets/images/calendar.png"), calendarLogo);
			File mobileLogo = File.createTempFile(tenantId + "-", ".png");
			FileUtils.copyFile(ResourceUtils.getFile("classpath:assets/images/mobile.png"), mobileLogo);
			File emailLogo = File.createTempFile(tenantId + "-", ".png");
			FileUtils.copyFile(ResourceUtils.getFile("classpath:assets/images/email.png"), emailLogo);
			File rightTemplate = File.createTempFile(tenantId + "-", ".png");
			FileUtils.copyFile(ResourceUtils.getFile("classpath:assets/images/right_template.png"), rightTemplate);
			File linkLogo = File.createTempFile(tenantId + "-", ".png");
			FileUtils.copyFile(ResourceUtils.getFile("classpath:assets/images/link.png"), linkLogo);
			inlineImages.put(tenantLogo.getName(), tenantLogo);
			inlineImages.put(calendarLogo.getName(), calendarLogo);
			inlineImages.put(emailLogo.getName(), emailLogo);
			inlineImages.put(logoIcon.getName(), logoIcon);
			inlineImages.put(mobileLogo.getName(), mobileLogo);
			inlineImages.put(linkLogo.getName(), linkLogo);
			inlineImages.put(rightTemplate.getName(), rightTemplate);
			valueMap.put("tenantLogo", "\"cid:" + tenantLogo.getName() + "\"");
			valueMap.put("tenantIcon", "\"cid:" + logoIcon.getName() + "\"");
			valueMap.put("mobileLogo", "\"cid:" + mobileLogo.getName() + "\"");
			valueMap.put("emailLogo", "\"cid:" + emailLogo.getName() + "\"");
			valueMap.put("linkLogo", "\"cid:" + linkLogo.getName() + "\"");
			valueMap.put("calendarLogo", "\"cid:" + calendarLogo.getName() + "\"");
			valueMap.put("rightTemplate", "\"cid:" + rightTemplate.getName() + "\"");
			valueMap.put("tenantName", tenantName);
			valueMap.put("tagLine", tagLine);
			valueMap.put("tenantContact", tenantContact);
			valueMap.put("tenantEmail", tenantEmail);
			valueMap.put("tenantId", tenantUniqueName);
			inlineListMap.add(inlineImages);
			inlineListMap.add(valueMap);
		} catch (IOException e) {
			Log.base.error("getBasicInlineImages : {}", e);
		}
		return inlineListMap;
	}

}