package com.platform.email;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.platform.util.Log;

/**
 * @author Muhil
 *
 */
public class EmailTemplatePlaceholderConfiguration {

	private static EmailTemplatePlaceholderConfiguration instance = new EmailTemplatePlaceholderConfiguration();

	private static Map<String, List<String>> templatePlaceholdersMap = new HashMap<>();

	private EmailTemplatePlaceholderConfiguration() {

	}

	public static EmailTemplatePlaceholderConfiguration getInstance() {
		return instance;
	}

	public static Map<String, List<String>> getAllTemplateNamesmap() {
		return templatePlaceholdersMap;
	}

	public static List<String> getTemplateNamesmap(String name) {
		return templatePlaceholdersMap.get(name);
	}
	
	public static boolean isValidTemplateName(String name) {
		return templatePlaceholdersMap.containsKey(name);
	}

	public void loadTemplateNameFile(File jsonFile) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode;
		try {
			jsonNode = mapper.readValue(new FileInputStream(jsonFile), JsonNode.class);
			String jsonString = mapper.writeValueAsString(jsonNode);
			Log.platform.debug(jsonString);
			Map<String, List<String>> map = new Gson().fromJson(jsonString, new TypeToken<Map<String, List<String>>>() {
			}.getType());
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				if (templatePlaceholdersMap.containsKey(entry.getKey())) {
					Log.platform.error(String.format("Duplicate entry for template name %s ", entry.getKey()));
				}
				templatePlaceholdersMap.put(entry.getKey(), entry.getValue());
			}
		} catch (Exception e) {
			Log.platform.error("Exception loading template names file : {}", e);
			throw new RuntimeException(e.getMessage());
		}
	}

}
