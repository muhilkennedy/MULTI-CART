package com.platform.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.platform.entity.PlatformTenant;
import com.platform.exception.TenantException;
import com.platform.messages.GenericResponse;

/**
 * @author Muhil
 * Util methods to make Http call using Apache library
 */
public class HttpUtil {

	public static final String HEADER_TENANT = "X-Tenant";

	public static String get(String url) throws IOException, URISyntaxException {
		return get(url, null, null);
	}

	public static String get(String url, List<NameValuePair> params) throws IOException, URISyntaxException {
		return get(url, null, params);
	}

	public static String get(String url, List<Header> headers, List<NameValuePair> params)
			throws IOException, URISyntaxException {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpGet request = httpGet(url, headers, params);
			Log.platform.debug(String.format("GET HTTP call : %s ", request.getURI()));
			return client.execute(request).getEntity().toString();
		}
	}

	public static String post(String url) throws IOException, URISyntaxException {
		return post(url, null, null, null);
	}

	public static String post(String url, List<NameValuePair> params, String jsonBody)
			throws IOException, URISyntaxException {
		return post(url, null, null, null);
	}

	public static String post(String url, List<Header> headers, List<NameValuePair> params)
			throws IOException, URISyntaxException {
		return post(url, null, null, null);
	}

	public static String post(String url, List<Header> headers, List<NameValuePair> params, String jsonBody)
			throws IOException, URISyntaxException {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpPost request = httpPost(url, headers, params, jsonBody);
			Log.platform.debug(String.format("POST HTTP call : %s ", request.getURI()));
			return client.execute(request).getEntity().toString();

		}
	}

	public static String put(String url, List<Header> headers, List<NameValuePair> params, String jsonBody)
			throws IOException, URISyntaxException {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpPut request = httpPut(url, headers, params, jsonBody);
			Log.platform.debug(String.format("PUT HTTP call : %s ", request.getURI()));
			return client.execute(request).getEntity().toString();

		}
	}

	public static String delete(String url, List<Header> headers, List<NameValuePair> params)
			throws IOException, URISyntaxException {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpDelete request = httpDelete(url, headers, params);
			Log.platform.debug(String.format("DELETE HTTP call : %s ", request.getURI()));
			return client.execute(request).getEntity().toString();

		}
	}

	public static HttpGet httpGet(String url, List<Header> headers, List<NameValuePair> params)
			throws URISyntaxException {
		HttpGet request = new HttpGet(url);
		if (headers != null && !headers.isEmpty()) {
			request.setHeaders((Header[]) headers.toArray());
		}
		URI uri = prepareURI(request, params);
		request.setURI(uri);
		return request;
	}

	public static HttpPost httpPost(String url, List<Header> headers, List<NameValuePair> params, String jsonBody)
			throws UnsupportedEncodingException, URISyntaxException {
		HttpPost request = new HttpPost(url);
		StringEntity entity = new StringEntity(jsonBody);
		request.setEntity(entity);
		if (headers != null && !headers.isEmpty()) {
			request.setHeaders((Header[]) headers.toArray());
		}
		URI uri = prepareURI(request, params);
		request.setURI(uri);
		return request;
	}

	public static HttpPut httpPut(String url, List<Header> headers, List<NameValuePair> params, String jsonBody)
			throws UnsupportedEncodingException, URISyntaxException {
		HttpPut request = new HttpPut(url);
		StringEntity entity = new StringEntity(jsonBody);
		request.setEntity(entity);
		if (headers != null && !headers.isEmpty()) {
			request.setHeaders((Header[]) headers.toArray());
		}
		URI uri = prepareURI(request, params);
		request.setURI(uri);
		return request;
	}

	public static HttpDelete httpDelete(String url, List<Header> headers, List<NameValuePair> params)
			throws UnsupportedEncodingException, URISyntaxException {
		HttpDelete request = new HttpDelete(url);
		if (headers != null && !headers.isEmpty()) {
			request.setHeaders((Header[]) headers.toArray());
		}
		URI uri = prepareURI(request, params);
		request.setURI(uri);
		return request;
	}

	public static URI prepareURI(HttpRequestBase request, List<NameValuePair> params) throws URISyntaxException {
		URI uri = null;
		if (params != null && !params.isEmpty()) {
			uri = new URIBuilder(request.getURI()).addParameters(params).build();
		} else {
			uri = new URIBuilder(request.getURI()).build();
		}
		return uri;
	}
	
	public static Object getDataResponse(Class<?> cls, GenericResponse response) {
		Gson gson = new Gson();
		String json = gson.toJson(response.getData());
		return gson.fromJson(json, cls);
	}

	public static List getDataListResponse(Class<?> cls, GenericResponse response) {
		List dataList = new ArrayList();
		if (response.getDataList() != null && !response.getDataList().isEmpty()) {
			Gson gson = new Gson();
			String json = gson.toJson(response.getDataList());
			response.getDataList().parallelStream().forEach(data -> {
				dataList.add(gson.fromJson(json, cls));
			});
		}
		return dataList;
	}

}
