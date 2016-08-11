package com.callistech.analytics.frontend.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.callistech.nap.configuration.NAPConfiguration;
import com.callistech.nap.configuration.collector.ipdr.IPDRServerConfiguration;
import com.callistech.nap.configuration.collector.rdr.RDRServerConfiguration;
import com.callistech.nap.configuration.datarecord.DataRecordConfiguration;
import com.callistech.nap.configuration.datarecord.csv.CSVConfiguration;
import com.callistech.nap.configuration.datarecord.rta.RTAConfiguration;
import com.callistech.nap.configuration.parser.ConfigJSON;
import com.callistech.nap.configuration.parser.DBConfigurationAndDBMongoConfiguration;
import com.callistech.nap.configuration.parser.Others;
import com.callistech.nap.configuration.periodic.PeriodicConfiguration;

@Component
public class RestNap {

	@Autowired
	private SettingsService settingsService;

	public String getNAP_SERVER_IP() {
		return settingsService.getPropValue(SettingsService.IP);
	}

	public String getNAP_SERVER_PORT() {
		return settingsService.getPropValue(SettingsService.PORT);
	}

	/**
	 * @return NAPConfiguration
	 * @throws HttpHostConnectException
	 */
	public NAPConfiguration getNAPConfiguration() throws HttpHostConnectException {
		NAPConfiguration napConfiguration = null;
		try {
			String json = sendGetRequest("http://" + getNAP_SERVER_IP() + ":" + getNAP_SERVER_PORT() + "/getNapConfiguration?");
			napConfiguration = ConfigJSON.parseNAPConfiguration(json);
		} catch (HttpHostConnectException ex) {
			throw ex;
		} catch (Exception ex) {
		}
		return napConfiguration;
	}

	/**
	 *
	 * @param napConfiguration
	 * @throws HttpHostConnectException
	 */
	public void setNAPConfiguration(NAPConfiguration napConfiguration) throws HttpHostConnectException {
		try {
			sendPostRequest("http://" + getNAP_SERVER_IP() + ":" + getNAP_SERVER_PORT() + "/setNapConfiguration?", "config", ConfigJSON.parseNAPConfiguration(napConfiguration));
		} catch (HttpHostConnectException ex) {
			throw ex;
		} catch (Exception ex) {
		}
	}

	/**
	 * @return csvConfiguration
	 * @throws HttpHostConnectException
	 */
	public CSVConfiguration getCSVConfiguration() throws HttpHostConnectException {
		CSVConfiguration csvConfiguration = null;
		try {
			String json = sendGetRequest("http://" + getNAP_SERVER_IP() + ":" + getNAP_SERVER_PORT() + "/getNapConfigurationByType?type=CSV_CONFIG");
			csvConfiguration = ConfigJSON.parseCSVConfiguration(json);
		} catch (HttpHostConnectException ex) {
			throw ex;
		} catch (Exception ex) {
		}
		return csvConfiguration;
	}

	/**
	 * @param csvConfiguration
	 * @return csvConfiguration
	 * @throws HttpHostConnectException
	 */
	public CSVConfiguration saveCSVConfiguration(CSVConfiguration csvConfiguration) throws HttpHostConnectException {
		try {
			sendPostRequest("http://" + getNAP_SERVER_IP() + ":" + getNAP_SERVER_PORT() + "/setNapConfigurationByType?type=CSV_CONFIG", "config", ConfigJSON.parseConfigFromCSVConfiguration(csvConfiguration));
		} catch (HttpHostConnectException ex) {
			throw ex;
		} catch (Exception ex) {
		}
		return csvConfiguration;
	}

	/**
	 * @return dataRecordConfiguration
	 * @throws HttpHostConnectException
	 */
	public DataRecordConfiguration getDataRecordConfiguration() throws HttpHostConnectException {
		DataRecordConfiguration dataRecordConfiguration = null;
		try {
			String json = sendGetRequest("http://" + getNAP_SERVER_IP() + ":" + getNAP_SERVER_PORT() + "/getNapConfigurationByType?type=DATARECORD_CONFIG");
			dataRecordConfiguration = ConfigJSON.parseDataRecordConfiguration(json);
		} catch (HttpHostConnectException ex) {
			throw ex;
		} catch (Exception ex) {
		}
		return dataRecordConfiguration;
	}

	/**
	 * @param dataRecordConfiguration
	 * @return dataRecordConfiguration
	 * @throws HttpHostConnectException
	 */
	public DataRecordConfiguration saveDataRecordConfiguration(DataRecordConfiguration dataRecordConfiguration) throws HttpHostConnectException {
		try {
			sendPostRequest("http://" + getNAP_SERVER_IP() + ":" + getNAP_SERVER_PORT() + "/setNapConfigurationByType?type=DATARECORD_CONFIG", "config", ConfigJSON.parseConfigFromDataRecordConfiguration(dataRecordConfiguration));
		} catch (HttpHostConnectException ex) {
			throw ex;
		} catch (Exception ex) {
		}
		return dataRecordConfiguration;
	}

	/**
	 * @return dataRecordConfiguration
	 * @throws HttpHostConnectException
	 */
	public DBConfigurationAndDBMongoConfiguration getDBConfiguration() throws HttpHostConnectException {
		DBConfigurationAndDBMongoConfiguration dbConfiguration = null;
		try {
			String json = sendGetRequest("http://" + getNAP_SERVER_IP() + ":" + getNAP_SERVER_PORT() + "/getNapConfigurationByType?type=DB_CONFIG");
			dbConfiguration = ConfigJSON.parseDBConfiguration(json);
		} catch (HttpHostConnectException ex) {
			throw ex;
		} catch (Exception ex) {
		}
		return dbConfiguration;
	}

	/**
	 * @param dataRecordConfiguration
	 * @return dataRecordConfiguration
	 * @throws HttpHostConnectException
	 */
	public DBConfigurationAndDBMongoConfiguration saveDBConfiguration(DBConfigurationAndDBMongoConfiguration dbConfiguration) throws HttpHostConnectException {
		try {
			sendPostRequest("http://" + getNAP_SERVER_IP() + ":" + getNAP_SERVER_PORT() + "/setNapConfigurationByType?type=DB_CONFIG", "config", ConfigJSON.parseConfigFromDBConfiguration(dbConfiguration));
		} catch (HttpHostConnectException ex) {
			throw ex;
		} catch (Exception ex) {
		}
		return dbConfiguration;
	}

	/**
	 * @return rdrServerConfiguration
	 * @throws HttpHostConnectException
	 */
	public RDRServerConfiguration getRDRServerConfiguration() throws HttpHostConnectException {
		RDRServerConfiguration rdrServerConfiguration = null;
		try {
			String json = sendGetRequest("http://" + getNAP_SERVER_IP() + ":" + getNAP_SERVER_PORT() + "/getNapConfigurationByType?type=RDR_CONFIG");
			rdrServerConfiguration = ConfigJSON.parseRDRServerConfiguration(json);
		} catch (HttpHostConnectException ex) {
			throw ex;
		} catch (Exception ex) {
		}
		return rdrServerConfiguration;
	}

	/**
	 * @param rdrServerConfiguration
	 * @return rdrServerConfiguration
	 * @throws HttpHostConnectException
	 */
	public RDRServerConfiguration saveRDRServerConfiguration(RDRServerConfiguration rdrServerConfiguration) throws HttpHostConnectException {
		try {
			sendPostRequest("http://" + getNAP_SERVER_IP() + ":" + getNAP_SERVER_PORT() + "/setNapConfigurationByType?type=RDR_CONFIG", "config", ConfigJSON.parseConfigFromRDRServerConfiguration(rdrServerConfiguration));
		} catch (HttpHostConnectException ex) {
			throw ex;
		} catch (Exception ex) {
		}
		return rdrServerConfiguration;
	}

	/**
	 * @return ipdrServerConfiguration
	 * @throws HttpHostConnectException
	 */
	public IPDRServerConfiguration getIPDRServerConfiguration() throws HttpHostConnectException {
		IPDRServerConfiguration ipdrServerConfiguration = null;
		try {
			String json = sendGetRequest("http://" + getNAP_SERVER_IP() + ":" + getNAP_SERVER_PORT() + "/getNapConfigurationByType?type=IPDR_CONFIG");
			ipdrServerConfiguration = ConfigJSON.parseIPDRServerConfiguration(json);
		} catch (HttpHostConnectException ex) {
			throw ex;
		} catch (Exception ex) {
		}
		return ipdrServerConfiguration;
	}

	/**
	 *
	 * @param ipdrServerConfiguration
	 * @return ipdrServerConfiguration
	 * @throws HttpHostConnectException
	 */
	public IPDRServerConfiguration saveIPDRServerConfiguration(IPDRServerConfiguration ipdrServerConfiguration) throws HttpHostConnectException {
		try {
			sendPostRequest("http://" + getNAP_SERVER_IP() + ":" + getNAP_SERVER_PORT() + "/setNapConfigurationByType?type=IPDR_CONFIG", "config", ConfigJSON.parseConfigFromIPDRServerConfiguration(ipdrServerConfiguration));
		} catch (HttpHostConnectException ex) {
			throw ex;
		} catch (Exception ex) {
		}
		return ipdrServerConfiguration;
	}

	/**
	 * @return others
	 * @throws HttpHostConnectException
	 */
	public Others getOthers() throws HttpHostConnectException {
		Others others = null;
		try {
			String json = sendGetRequest("http://" + getNAP_SERVER_IP() + ":" + getNAP_SERVER_PORT() + "/getNapConfigurationByType?type=OTHERS");
			others = ConfigJSON.parseOthers(json);
		} catch (HttpHostConnectException ex) {
			throw ex;
		} catch (Exception ex) {
		}
		return others;
	}

	/**
	 *
	 * @param others
	 * @return others
	 * @throws HttpHostConnectException
	 */
	public Others saveOthers(Others others) throws HttpHostConnectException {
		try {
			sendPostRequest("http://" + getNAP_SERVER_IP() + ":" + getNAP_SERVER_PORT() + "/setNapConfigurationByType?type=OTHERS", "config", ConfigJSON.parseConfigFromOthers(others));
		} catch (HttpHostConnectException ex) {
			throw ex;
		} catch (Exception ex) {
		}
		return others;
	}

	/**
	 * @return rtaConfiguration
	 * @throws HttpHostConnectException
	 */
	public RTAConfiguration getRTAConfiguration() throws HttpHostConnectException {
		RTAConfiguration rtaConfiguration = null;
		try {
			String json = sendGetRequest("http://" + getNAP_SERVER_IP() + ":" + getNAP_SERVER_PORT() + "/getNapConfigurationByType?type=RTA_CONFIG");
			rtaConfiguration = ConfigJSON.parseRTAConfiguration(json);
		} catch (HttpHostConnectException ex) {
			throw ex;
		} catch (Exception ex) {
		}
		return rtaConfiguration;
	}

	/**
	 *
	 * @param rtaConfiguration
	 * @return rtaConfiguration
	 * @throws HttpHostConnectException
	 */
	public RTAConfiguration saveRTAConfiguration(RTAConfiguration rtaConfiguration) throws HttpHostConnectException {
		try {
			sendPostRequest("http://" + getNAP_SERVER_IP() + ":" + getNAP_SERVER_PORT() + "/setNapConfigurationByType?type=RTA_CONFIG", "config", ConfigJSON.parseRTAConfiguration(rtaConfiguration));
		} catch (HttpHostConnectException ex) {
			throw ex;
		} catch (Exception ex) {
		}
		return rtaConfiguration;
	}

	/**
	 * @return rtaConfiguration
	 * @throws HttpHostConnectException
	 */
	public PeriodicConfiguration getPeriodicConfiguration() throws HttpHostConnectException {
		PeriodicConfiguration periodicConfiguration = null;
		try {
			String json = sendGetRequest("http://" + getNAP_SERVER_IP() + ":" + getNAP_SERVER_PORT() + "/getNapConfigurationByType?type=PERIODIC_CONFIG");
			periodicConfiguration = ConfigJSON.parsePeriodicConfiguration(json);
		} catch (HttpHostConnectException ex) {
			throw ex;
		} catch (Exception ex) {
		}
		return periodicConfiguration;
	}

	/**
	 *
	 * @param periodicConfiguration
	 * @return rtaConfiguration
	 * @throws HttpHostConnectException
	 */
	public PeriodicConfiguration savePeriodicConfiguration(PeriodicConfiguration periodicConfiguration) throws HttpHostConnectException {
		try {
			sendPostRequest("http://" + getNAP_SERVER_IP() + ":" + getNAP_SERVER_PORT() + "/setNapConfigurationByType?type=PERIODIC_CONFIG", "config", ConfigJSON.parsePeriodicConfiguration(periodicConfiguration));
		} catch (HttpHostConnectException ex) {
			throw ex;
		} catch (Exception ex) {
		}
		return periodicConfiguration;
	}

	/**
	 * Envia request GET
	 *
	 * @param String
	 *            url
	 * @return response
	 * @throws Exception
	 */
	private String sendGetRequest(String url) throws Exception {
		DefaultHttpClient httpClient = null;
		String response = null;
		HttpGet httpRequest = null;
		HttpEntity entity = null;
		try {
			HttpParams my_httpParams = new BasicHttpParams();
			httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(), my_httpParams);
			// Crea request
			httpRequest = new HttpGet(url);
			httpRequest.addHeader("accept", "application/json");

			// Ejecuta request
			HttpResponse httpResponse = httpClient.execute(httpRequest);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// Procesa response
				entity = httpResponse.getEntity();
				response = EntityUtils.toString(entity);
			}

		} catch (IOException ex) {
			throw ex;
		} catch (ParseException ex) {
			throw ex;
		} finally {
			EntityUtils.consume(entity);
		}
		return response;
	}

	/**
	 * Envia request POST con parametros
	 *
	 * @param String
	 *            url
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private static String sendPostRequest(String url, String param, String value) throws Exception {
		DefaultHttpClient httpClient = null;
		HttpPost httpRequest = null;
		HttpEntity entity = null;
		String response = null;
		try {
			HttpParams my_httpParams = new BasicHttpParams();
			httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(), my_httpParams);
			// Crea request
			httpRequest = new HttpPost(url);
			// Arma content
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair(param, value));
			httpRequest.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			// Envia request
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// Procesa response
				entity = httpResponse.getEntity();
				response = EntityUtils.toString(entity);
			}
		} catch (IOException ex) {
			throw ex;
		} catch (ParseException ex) {
			throw ex;
		} finally {
			EntityUtils.consume(entity);
		}
		return response;
	}
}
