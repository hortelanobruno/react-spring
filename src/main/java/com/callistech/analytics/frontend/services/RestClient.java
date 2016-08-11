package com.callistech.analytics.frontend.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author damian
 */
public class RestClient {

	private DefaultHttpClient httpClient;
	private String url;

	/**
	 * Constructor
	 */
	public RestClient(String ip, String port) {
		this.httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		this.url = "http://" + ip + ":" + port;
	}

	/**
	 * Realiza llamada de un GET
	 *
	 * @param restMethod
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void executeRestGetMethod(String restMethod, HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Crea GET
		// HttpGet httpRequest = new HttpGet(this.url + restMethod + "/" + request.getUserPrincipal().getName());
		HttpGet httpRequest = new HttpGet(this.url + restMethod);
		httpRequest.addHeader("accept", "application/json");
		// Ejecuta GET
		HttpResponse httpResponse = httpClient.execute(httpRequest);
		// Procesa response de GET
		HttpEntity entity = httpResponse.getEntity();
		String jsonString = EntityUtils.toString(entity);
		// Carga repsonse de servlet
		response.setContentType("application/json");
		response.getWriter().write(jsonString);
	}

	/**
	 * Realiza llamada de un GET
	 *
	 * @param restMethod
	 * @throws IOException
	 */
	public String executeGetMethod(String restMethod) throws IOException {
		// Crea GET
		HttpGet httpRequest = new HttpGet(this.url + restMethod);
		httpRequest.addHeader("accept", "application/json");
		// Ejecuta GET
		HttpResponse httpResponse = httpClient.execute(httpRequest);
		// Procesa response de GET
		HttpEntity entity = httpResponse.getEntity();
		String jsonString = EntityUtils.toString(entity);
		return jsonString;
	}

	/**
	 * Realiza llamada de POST
	 *
	 * @param restMethod
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void executeRestPostMethod(String restMethod, HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Crea POST
		HttpPost httpPost = new HttpPost(this.url + restMethod);
		// Carga contenido de POST
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (String key : request.getParameterMap().keySet()) {
			nvps.add(new BasicNameValuePair(key, request.getParameter(key)));
		}
		nvps.add(new BasicNameValuePair("napUser", request.getUserPrincipal().getName()));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		// Ejecuta POST
		HttpResponse httpResponse = httpClient.execute(httpPost);
		// Procesa response de POST
		HttpEntity entity = httpResponse.getEntity();
		String jsonString = EntityUtils.toString(entity);
		// Carga response de servlet
		response.setContentType("application/json");
		response.getWriter().write(jsonString);
	}

	/**
	 * Realiza llamada de POST
	 *
	 * @param restMethod
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public String executePostMethod(String param, String value1) throws IOException, Exception {
		DefaultHttpClient httpClient = null;
		HttpPost httpRequest = null;
		HttpEntity entityResponse = null;
		String response = null;
		try {
			HttpParams my_httpParams = new BasicHttpParams();
			httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(), my_httpParams);
			// Crea request
			httpRequest = new HttpPost(this.url + param);

			StringEntity entity = new StringEntity(value1, ContentType.create("application/json", Consts.UTF_8));
			httpRequest.setEntity(entity);

			// Envia request
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// Procesa response
				entityResponse = httpResponse.getEntity();
				response = EntityUtils.toString(entityResponse);
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			EntityUtils.consume(entityResponse);
		}
		return response;
	}

	public String executeRestPostMethod(String restMethod, HttpServletRequest request) throws IOException {
		// Crea POST
		HttpPost httpPost = new HttpPost(this.url + restMethod);
		// Carga contenido de POST
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (String key : request.getParameterMap().keySet()) {
			nvps.add(new BasicNameValuePair(key, request.getParameter(key)));
		}
		nvps.add(new BasicNameValuePair("napUser", request.getUserPrincipal().getName()));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		// Ejecuta POST
		HttpResponse httpResponse = httpClient.execute(httpPost);
		// Procesa response de POST
		HttpEntity entity = httpResponse.getEntity();
		String jsonString = EntityUtils.toString(entity);
		return jsonString;
	}

	public String executeRestPostMethod(String restMethod, String username, Map<String, String> params) throws IOException {
		// Crea POST
		HttpPost httpPost = new HttpPost(this.url + restMethod);
		// Carga contenido de POST
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}
		nvps.add(new BasicNameValuePair("napUser", username));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		// Ejecuta POST
		HttpResponse httpResponse = httpClient.execute(httpPost);
		// Procesa response de POST
		HttpEntity entity = httpResponse.getEntity();
		String jsonString = EntityUtils.toString(entity);
		return jsonString;
	}

	/**
	 * Realiza llamada de POST
	 *
	 * @param restMethod
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void executeRestPostMethodWithoutUser(String restMethod, HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Crea POST
		HttpPost httpPost = new HttpPost(this.url + restMethod);
		// Carga contenido de POST
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (String key : request.getParameterMap().keySet()) {
			nvps.add(new BasicNameValuePair(key, request.getParameter(key)));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		// Ejecuta POST
		HttpResponse httpResponse = httpClient.execute(httpPost);
		// Procesa response de POST
		HttpEntity entity = httpResponse.getEntity();
		String jsonString = EntityUtils.toString(entity);
		// Carga response de servlet
		response.setContentType("application/json");
		response.getWriter().write(jsonString);
	}
}
