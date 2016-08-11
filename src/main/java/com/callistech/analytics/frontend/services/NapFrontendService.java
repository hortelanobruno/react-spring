package com.callistech.analytics.frontend.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.conn.HttpHostConnectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.callistech.nap.bean.DetailRecordType;
import com.callistech.nap.bean.util.SCABBVersion;
import com.callistech.nap.configuration.NAPConfiguration;
import com.callistech.nap.configuration.collector.ipdr.CMTSConfig;
import com.callistech.nap.configuration.collector.ipdr.IPDRServerConfiguration;
import com.callistech.nap.configuration.collector.ipdr.VendorConfig;
import com.callistech.nap.configuration.collector.rdr.RDRServerConfiguration;
import com.callistech.nap.configuration.datarecord.DataRecordConfiguration;
import com.callistech.nap.configuration.datarecord.csv.CSVAdapterConfiguration;
import com.callistech.nap.configuration.datarecord.csv.CSVConfiguration;
import com.callistech.nap.configuration.datarecord.csv.util.IPAddressFormat;
import com.callistech.nap.configuration.datarecord.csv.util.TimestampFormat;
import com.callistech.nap.configuration.datarecord.db.DBConfiguration;
import com.callistech.nap.configuration.datarecord.db.DBMongoConfiguration;
import com.callistech.nap.configuration.datarecord.rta.CategoryConfiguration;
import com.callistech.nap.configuration.datarecord.rta.GroupURLHitsConfiguration;
import com.callistech.nap.configuration.datarecord.rta.QualityCondition;
import com.callistech.nap.configuration.datarecord.rta.RTAAdapterCategoryConfiguration;
import com.callistech.nap.configuration.datarecord.rta.RTAAdapterCategoryHitsConfiguration;
import com.callistech.nap.configuration.datarecord.rta.RTAAdapterConfiguration;
import com.callistech.nap.configuration.datarecord.rta.RTAAdapterDefaultConfiguration;
import com.callistech.nap.configuration.datarecord.rta.RTAAdapterDeviceConfiguration;
import com.callistech.nap.configuration.datarecord.rta.RTAAdapterFlavorUpdaterConfiguration;
import com.callistech.nap.configuration.datarecord.rta.RTAAdapterHeavyConsumptionConfiguration;
import com.callistech.nap.configuration.datarecord.rta.RTAAdapterTopperConfiguration;
import com.callistech.nap.configuration.datarecord.rta.RTAAdapterURLCategoryConsumptionConfiguration;
import com.callistech.nap.configuration.datarecord.rta.RTAAdapterURLHitsConfiguration;
import com.callistech.nap.configuration.datarecord.rta.RTAAdapterUserAgentConfiguration;
import com.callistech.nap.configuration.datarecord.rta.RTAConfiguration;
import com.callistech.nap.configuration.datarecord.rta.util.CounterConsumptionConfiguration;
import com.callistech.nap.configuration.datarecord.rta.util.CounterURLCategoryConsumptionConfiguration;
import com.callistech.nap.configuration.datarecord.rta.util.InterestConversionKey;
import com.callistech.nap.configuration.datarecord.rta.util.RTAAdapterConfigurationType;
import com.callistech.nap.configuration.parser.ConfigJSON;
import com.callistech.nap.configuration.parser.DBConfigurationAndDBMongoConfiguration;
import com.callistech.nap.configuration.parser.Others;
import com.callistech.nap.configuration.periodic.PeriodicConfiguration;
import com.callistech.nap.configuration.periodic.datarecord.task.PeriodicAggregationTask;
import com.callistech.nap.configuration.periodic.datarecord.task.PeriodicDataRecordTask;
import com.callistech.nap.configuration.periodic.datarecord.task.PeriodicRTAPersistenceTask;
import com.callistech.nap.configuration.periodic.datarecord.task.PeriodicRemovalTask;
import com.callistech.nap.configuration.periodic.datarecord.util.PeriodicDataRecordAction;
import com.callistech.nap.configuration.periodic.datarecord.util.PeriodicRemovalConfiguration;
import com.callistech.nap.configuration.periodic.util.PeriodicTaskType;
import com.callistech.nap.configuration.rest.RESTConfiguration;
import com.callistech.nap.configuration.rmi.RMIConfiguration;
import com.callistech.nap.configuration.util.CallisProcessConfiguration;

@Component
public class NapFrontendService {

	@Autowired
	private RestNap restNap;

	/**
	 *
	 * @return NAPConfiguration
	 * @throws HttpHostConnectException
	 */
	public NAPConfiguration getNAPConfig() throws HttpHostConnectException {
		return restNap.getNAPConfiguration();
	}

	/**
	 *
	 * @param config
	 * @throws HttpHostConnectException
	 */
	public void setNAPConfig(NAPConfiguration config) throws HttpHostConnectException {
		restNap.setNAPConfiguration(config);
	}

	/**
	 *
	 * @return CSVConfiguration
	 * @throws HttpHostConnectException
	 */
	public CSVConfiguration getCSVConfiguration() throws HttpHostConnectException {
		return restNap.getCSVConfiguration();
	}

	/**
	 *
	 * @return RDRServerConfiguration
	 * @throws HttpHostConnectException
	 */
	public RDRServerConfiguration getRDRServerConfiguration() throws HttpHostConnectException {
		return restNap.getRDRServerConfiguration();
	}

	/**
	 *
	 * @return IPDRServerConfiguration
	 * @throws HttpHostConnectException
	 */
	public IPDRServerConfiguration getIPDRServerConfiguration() throws HttpHostConnectException {
		return restNap.getIPDRServerConfiguration();
	}

	/**
	 *
	 * @return DataRecordConfiguration
	 * @throws HttpHostConnectException
	 */
	public DataRecordConfiguration getDataRecordConfiguration() throws HttpHostConnectException {
		return restNap.getDataRecordConfiguration();
	}

	/**
	 *
	 * @return Others
	 * @throws HttpHostConnectException
	 */
	public Others getOthers() throws HttpHostConnectException {
		return restNap.getOthers();
	}

	/**
	 *
	 * @return RTAConfiguration
	 * @throws HttpHostConnectException
	 */
	public RTAConfiguration getRTAConfiguration() throws HttpHostConnectException {
		return restNap.getRTAConfiguration();
	}

	/**
	 *
	 * @return @throws HttpHostConnectException
	 */
	public PeriodicConfiguration getPeriodicConfiguration() throws HttpHostConnectException {
		return restNap.getPeriodicConfiguration();
	}

	/**
	 *
	 * @param rtaConfiguration
	 * @return RTAConfiguration
	 * @throws HttpHostConnectException
	 */
	public RTAConfiguration saveRTAConfiguration(RTAConfiguration rtaConfiguration) throws HttpHostConnectException {
		return restNap.saveRTAConfiguration(rtaConfiguration);
	}

	/**
	 *
	 * @param periodicConfiguration
	 * @return PeriodicConfiguration
	 * @throws HttpHostConnectException
	 */
	public PeriodicConfiguration savePeriodicConfiguration(PeriodicConfiguration periodicConfiguration) throws HttpHostConnectException {
		return restNap.savePeriodicConfiguration(periodicConfiguration);
	}

	public DBConfigurationAndDBMongoConfiguration getDBConfiguration() throws HttpHostConnectException {
		return restNap.getDBConfiguration();
	}

	public DBConfigurationAndDBMongoConfiguration saveDBConfiguration(DBConfigurationAndDBMongoConfiguration db) throws HttpHostConnectException {
		return restNap.saveDBConfiguration(db);
	}

	/**
	 *
	 * @param RDRPort
	 * @param queueDepth
	 * @param timeout
	 * @param allowedTypes
	 * @param enableAggregator
	 * @param defaultScabbVersion
	 * @param ScabbVersion
	 * @return
	 * @throws HttpHostConnectException
	 */
	public RDRServerConfiguration setRDRSetting(Integer RDRPort, Integer queueDepth, Integer timeout, String[] allowedTypes, Boolean enableAggregator, String defaultScabbVersion, String[] ScabbVersion) throws HttpHostConnectException {

		RDRServerConfiguration rdrServerConfiguration = restNap.getRDRServerConfiguration();
		rdrServerConfiguration.setPort(RDRPort);
		rdrServerConfiguration.setQueueDepth(queueDepth);
		rdrServerConfiguration.setTimeout(timeout);
		if (defaultScabbVersion.equals("v36x")) {
			rdrServerConfiguration.setDefaultScabbVersion(SCABBVersion.v36x);
		} else {
			rdrServerConfiguration.setDefaultScabbVersion(SCABBVersion.v41x);
		}
		if (allowedTypes != null) {
			List<DetailRecordType> allowed = getAllowed(allowedTypes);
			rdrServerConfiguration.setAllowedTypes(allowed);
		} else {
			rdrServerConfiguration.setAllowedTypes(null);
		}
		rdrServerConfiguration.setEnableAggregator(enableAggregator);
		if (ScabbVersion != null) {
			if (ScabbVersion.length != 1) {
				Map<String, SCABBVersion> mapscabbVersions = new HashMap<String, SCABBVersion>();
				for (int i = 0; i < ScabbVersion.length; i = i + 2) {
					if (ScabbVersion[i + 1].equals("v36x")) {
						mapscabbVersions.put(ScabbVersion[i], SCABBVersion.v36x);
					} else {
						mapscabbVersions.put(ScabbVersion[i], SCABBVersion.v41x);
					}
				}
				rdrServerConfiguration.setScabbVersions(mapscabbVersions);
			}
		} else {
			rdrServerConfiguration.setScabbVersions(null);
		}
		restNap.saveRDRServerConfiguration(rdrServerConfiguration);
		return rdrServerConfiguration;
	}

	// Obtiene apartir de un vector de string el tipo de enumerado de cada una de las variables
	private List<DetailRecordType> getAllowed(String[] alloweds) {

		List<DetailRecordType> allowed = new LinkedList<DetailRecordType>();
		if (alloweds != null) {
			for (String allowedType : alloweds) {
				DetailRecordType rt = DetailRecordType.valueOf(allowedType);
				if (rt != null) {
					allowed.add(rt);
				}
			}
		}
		return allowed;
	}

	/**
	 *
	 * @param ipIPDR
	 * @param portIPDR
	 * @param enableAgregatorIPDR
	 * @param cmtss
	 * @throws HttpHostConnectException
	 */
	public void saveIPDRSettings(String ipIPDR, Integer portIPDR, Boolean enableAgregatorIPDR, String[] cmtss) throws HttpHostConnectException {

		IPDRServerConfiguration ipdrSettings = getIPDRServerConfiguration();
		ipdrSettings.setIp(ipIPDR);
		ipdrSettings.setPort(portIPDR);
		ipdrSettings.setEnableAggregator(enableAgregatorIPDR);
		List<CMTSConfig> cmtssConfig = new LinkedList<CMTSConfig>();
		if (cmtss != null) {
			for (int i = 0; i < cmtss.length; i = i + 4) {
				CMTSConfig cmts = new CMTSConfig();
				cmts.setIp(cmtss[i]);
				cmts.setPort(Integer.valueOf(cmtss[i + 1]));
				if (cmtss[i + 2].equals("motorola")) {
					cmts.setVendor(VendorConfig.motorola);
				} else if (cmtss[i + 2].equals("arris")) {
					cmts.setVendor(VendorConfig.arris);
				} else if (cmtss[i + 2].equals("cisco")) {
					cmts.setVendor(VendorConfig.cisco);
				} else if (cmtss[i + 2].equals("casa")) {
					cmts.setVendor(VendorConfig.casa);
				}
				cmts.setIpdrEnabled(Boolean.valueOf(cmtss[i + 3]));
				cmtssConfig.add(cmts);
			}
			ipdrSettings.setCmtss(cmtssConfig);
		} else {
			ipdrSettings.setCmtss(null);
		}

		restNap.saveIPDRServerConfiguration(ipdrSettings);
	}

	/**
	 *
	 * @param csv
	 * @throws HttpHostConnectException
	 */
	public void saveCSVManager(String[] csv) throws HttpHostConnectException {

		CSVConfiguration csvManager = getCSVConfiguration();
		CSVAdapterConfiguration defaultconfig = csvManager.getDefaultConfiguration();
		Map<DetailRecordType, CSVAdapterConfiguration> csvAdapterConfigurations = csvManager.getCsvAdapterConfigurations();

		// Utiliza los lugares del vector para obtener cada tipo de variable
		for (int i = 0; i < csv.length; i = i + 6) {
			// El primero siempre es el default
			if (i == 0) {
				defaultconfig.setSeparator(csv[i + 1]);
				defaultconfig.setIncludeRDRHeaderInfo(Boolean.valueOf(csv[i + 2]));
				if (csv[i + 3].equals("string")) {
					defaultconfig.setSourceIpFormat(IPAddressFormat.string);
				} else if (csv[i + 3].equals("number")) {
					defaultconfig.setSourceIpFormat(IPAddressFormat.number);
				}
				if (csv[i + 4].equals("milliseconds")) {
					defaultconfig.setTimestampFormat(TimestampFormat.milliseconds);
				} else if (csv[i + 4].equals("date")) {
					defaultconfig.setTimestampFormat(TimestampFormat.date);
				}
				defaultconfig.setTimestampFormatPattern(csv[i + 5]);
			} else {
				// Si el getDetailRecordType por algun motivo falla y retorna un null no se realiza el put
				DetailRecordType drt = DetailRecordType.valueOf(csv[i]);
				CSVAdapterConfiguration config = new CSVAdapterConfiguration();
				config.setSeparator(csv[i + 1]);
				config.setIncludeRDRHeaderInfo(Boolean.valueOf(csv[i + 2]));
				if (csv[i + 3].equals("string")) {
					config.setSourceIpFormat(IPAddressFormat.string);
				} else if (csv[i + 3].equals("number")) {
					config.setSourceIpFormat(IPAddressFormat.number);
				}
				if (csv[i + 4].equals("milliseconds")) {
					config.setTimestampFormat(TimestampFormat.milliseconds);
				} else if (csv[i + 4].equals("date")) {
					config.setTimestampFormat(TimestampFormat.date);
				}
				config.setTimestampFormatPattern(csv[i + 5]);
				if (drt != null) {
					if (csvAdapterConfigurations.containsKey(drt)) {
						csvAdapterConfigurations.get(drt).setIncludeRDRHeaderInfo(config.getIncludeRDRHeaderInfo());
						csvAdapterConfigurations.get(drt).setSeparator(config.getSeparator());
						csvAdapterConfigurations.get(drt).setSourceIpFormat(config.getSourceIpFormat());
						csvAdapterConfigurations.get(drt).setTimestampFormat(config.getTimestampFormat());
						csvAdapterConfigurations.get(drt).setTimestampFormatPattern(config.getTimestampFormatPattern());
					} else {
						csvAdapterConfigurations.put(drt, config);
					}
				}
			}
		}
		csvManager.setDefaultConfiguration(defaultconfig);
		csvManager.setCsvAdapterConfigurations(csvAdapterConfigurations);
		restNap.saveCSVConfiguration(csvManager);
	}

	/**
	 *
	 * @param enableAggregator
	 * @param AllowedTagsPYC
	 * @param EnableCSV
	 * @param AllowedTagsCSV
	 * @param EnableDBManager
	 * @param AllowedTagsDBManager
	 * @param enebleRTA
	 * @param AllowedTagsRTA
	 * @throws HttpHostConnectException
	 */
	public void saveDataRecordsConfig(Boolean enableAggregator, String[] AllowedTagsPYC, Boolean EnableCSV, String[] AllowedTagsCSV, Boolean EnableDBManager, String[] AllowedTagsDBManager, Boolean enebleRTA, String[] AllowedTagsRTA) throws HttpHostConnectException {

		DataRecordConfiguration dataRecordsConfig = getDataRecordConfiguration();

		dataRecordsConfig.setEnableCSVManager(EnableCSV);
		dataRecordsConfig.setEnableDBManager(EnableDBManager);
		dataRecordsConfig.setEnablePYC(enableAggregator);
		dataRecordsConfig.setEnableRTAManager(enebleRTA);

		if (AllowedTagsPYC != null) {
			List<DetailRecordType> allowed = getAllowed(AllowedTagsPYC);
			dataRecordsConfig.setAllowedTagsForPYC(allowed);
		} else {
			dataRecordsConfig.setAllowedTagsForPYC(null);
		}

		if (AllowedTagsCSV != null) {
			List<DetailRecordType> allowed = getAllowed(AllowedTagsCSV);
			dataRecordsConfig.setAllowedTagsForCSVManager(allowed);
		} else {
			dataRecordsConfig.setAllowedTagsForCSVManager(null);
		}

		if (AllowedTagsDBManager != null) {
			List<DetailRecordType> allowed = getAllowed(AllowedTagsDBManager);
			dataRecordsConfig.setAllowedTagsForDBManager(allowed);
		} else {
			dataRecordsConfig.setAllowedTagsForDBManager(null);
		}

		if (AllowedTagsRTA != null) {
			List<DetailRecordType> allowed = getAllowed(AllowedTagsRTA);
			dataRecordsConfig.setAllowedTagsForRTAManager(allowed);
		} else {
			dataRecordsConfig.setAllowedTagsForRTAManager(null);
		}

		restNap.saveDataRecordConfiguration(dataRecordsConfig);
	}

	/**
	 *
	 * @param url
	 * @param user
	 * @param password
	 * @param DBName
	 * @param tableName
	 * @param SuscriberIdCol
	 * @param InterestIdCol
	 * @throws HttpHostConnectException
	 */
	public void saveDBManager(String url, String user, String password, String DBName, String tableName, String SuscriberIdCol, String InterestIdCol) throws HttpHostConnectException {
		DBConfigurationAndDBMongoConfiguration db = getDBConfiguration();
		DBConfiguration dbConfiguration = db.getDbConfiguration();
		dbConfiguration.setUrl(url);
		dbConfiguration.setUser(user);
		dbConfiguration.setPassword(password);
		DBMongoConfiguration dbMongoConfiguration = db.getDbMongoConfiguration();
		dbMongoConfiguration.setInterestsDBName(DBName);
		dbMongoConfiguration.setInterestsDBTableName(tableName);
		dbMongoConfiguration.setInterestsDBSubscriberIdColumn(SuscriberIdCol);
		dbMongoConfiguration.setInterestsDBInterestsColumn(InterestIdCol);
		db.setDbConfiguration(dbConfiguration);
		db.setDbMongoConfiguration(dbMongoConfiguration);
		restNap.saveDBConfiguration(db);
	}

	/**
	 *
	 * @param RMIIP
	 * @param RMIPort
	 * @param RMIService
	 * @param RestIP
	 * @param RestPort
	 * @param SepIP
	 * @param SepPort
	 * @param SERIP
	 * @param SERPort
	 * @param CSMIP
	 * @param CSMPort
	 * @throws HttpHostConnectException
	 */
	public void saveOthers(String RMIIP, Integer RMIPort, String RMIService, String RestIP, Integer RestPort, String SepIP, Integer SepPort, String SERIP, Integer SERPort, String CSMIP, Integer CSMPort) throws HttpHostConnectException {

		Others others = new Others();
		RMIConfiguration rmiConfiguration = new RMIConfiguration();
		rmiConfiguration.setIpAddress(RMIIP);
		rmiConfiguration.setPort(RMIPort);
		rmiConfiguration.setServiceName(RMIService);
		RESTConfiguration restConfiguration = new RESTConfiguration();
		restConfiguration.setServerIpAddress(RestIP);
		restConfiguration.setServerPort(RestPort);
		CallisProcessConfiguration sepConfiguration = new CallisProcessConfiguration();
		sepConfiguration.setIpAddress(SepIP);
		sepConfiguration.setPort(SepPort);
		CallisProcessConfiguration csmConfiguration = new CallisProcessConfiguration();
		csmConfiguration.setIpAddress(CSMIP);
		csmConfiguration.setPort(CSMPort);
		CallisProcessConfiguration notificationServerConfiguration = new CallisProcessConfiguration();
		notificationServerConfiguration.setIpAddress(SERIP);
		notificationServerConfiguration.setPort(SERPort);
		others.setNotificationServerConfiguration(notificationServerConfiguration);
		others.setRestConfiguration(restConfiguration);
		others.setRmiConfiguration(rmiConfiguration);
		others.setSepConfiguration(sepConfiguration);
		others.setCsmConfiguration(csmConfiguration);
		restNap.saveOthers(others);
	}

	/**
	 *
	 * @param key
	 * @param value
	 * @return RTAConfiguration
	 * @throws HttpHostConnectException
	 */
	public RTAConfiguration createRTAAdapter(String key, String value) throws HttpHostConnectException {

		RTAConfiguration rtaRecordConfiguration = getRTAConfiguration();
		Map<DetailRecordType, RTAAdapterConfiguration> adapters = rtaRecordConfiguration.getAdapters();

		Boolean enabled = null;
		RTAAdapterConfigurationType type = RTAAdapterConfigurationType.valueOf(value);
		Integer id = 0;
		long varLong = 0;
		Long granularity = varLong;
		List<DetailRecordType> sources = new ArrayList<DetailRecordType>();
		RTAAdapterConfiguration rtaConfiguration = null;

		switch (type) {

		case RTAAdapterCategoryConfiguration:
			rtaConfiguration = new RTAAdapterCategoryConfiguration();
			((RTAAdapterCategoryConfiguration) rtaConfiguration).setEnabled(enabled);
			((RTAAdapterCategoryConfiguration) rtaConfiguration).setId(id);
			((RTAAdapterCategoryConfiguration) rtaConfiguration).setType(type);
			((RTAAdapterCategoryConfiguration) rtaConfiguration).setGranularity(granularity);
			((RTAAdapterCategoryConfiguration) rtaConfiguration).setSources(sources);
			break;

		case RTAAdapterCategoryHitsConfiguration:
			rtaConfiguration = new RTAAdapterCategoryHitsConfiguration();
			((RTAAdapterCategoryHitsConfiguration) rtaConfiguration).setEnabled(enabled);
			((RTAAdapterCategoryHitsConfiguration) rtaConfiguration).setId(id);
			((RTAAdapterCategoryHitsConfiguration) rtaConfiguration).setType(type);
			((RTAAdapterCategoryHitsConfiguration) rtaConfiguration).setGranularity(granularity);
			((RTAAdapterCategoryHitsConfiguration) rtaConfiguration).setSources(sources);
			break;

		case RTAAdapterDefaultConfiguration:
			rtaConfiguration = new RTAAdapterDefaultConfiguration();
			((RTAAdapterDefaultConfiguration) rtaConfiguration).setEnabled(enabled);
			((RTAAdapterDefaultConfiguration) rtaConfiguration).setId(id);
			((RTAAdapterDefaultConfiguration) rtaConfiguration).setType(type);
			((RTAAdapterDefaultConfiguration) rtaConfiguration).setGranularity(granularity);
			((RTAAdapterDefaultConfiguration) rtaConfiguration).setSources(sources);
			break;

		case RTAAdapterDeviceConfiguration:
			rtaConfiguration = new RTAAdapterDeviceConfiguration();
			((RTAAdapterDeviceConfiguration) rtaConfiguration).setEnabled(enabled);
			((RTAAdapterDeviceConfiguration) rtaConfiguration).setId(id);
			((RTAAdapterDeviceConfiguration) rtaConfiguration).setType(type);
			((RTAAdapterDeviceConfiguration) rtaConfiguration).setGranularity(granularity);
			((RTAAdapterDeviceConfiguration) rtaConfiguration).setSources(sources);
			break;

		case RTAAdapterHeavyConsumptionConfiguration:
			rtaConfiguration = new RTAAdapterHeavyConsumptionConfiguration();
			((RTAAdapterHeavyConsumptionConfiguration) rtaConfiguration).setEnabled(enabled);
			((RTAAdapterHeavyConsumptionConfiguration) rtaConfiguration).setId(id);
			((RTAAdapterHeavyConsumptionConfiguration) rtaConfiguration).setType(type);
			((RTAAdapterHeavyConsumptionConfiguration) rtaConfiguration).setGranularity(granularity);
			((RTAAdapterHeavyConsumptionConfiguration) rtaConfiguration).setSources(sources);
			break;

		case RTAAdapterTopperConfiguration:
			rtaConfiguration = new RTAAdapterTopperConfiguration();
			((RTAAdapterTopperConfiguration) rtaConfiguration).setEnabled(enabled);
			((RTAAdapterTopperConfiguration) rtaConfiguration).setId(id);
			((RTAAdapterTopperConfiguration) rtaConfiguration).setType(type);
			((RTAAdapterTopperConfiguration) rtaConfiguration).setGranularity(granularity);
			((RTAAdapterTopperConfiguration) rtaConfiguration).setSources(sources);
			((RTAAdapterTopperConfiguration) rtaConfiguration).setMinAllowedHits(0);
			((RTAAdapterTopperConfiguration) rtaConfiguration).setProcessUniqSubs(false);
			break;

		case RTAAdapterURLCategoryConsumptionConfiguration:
			rtaConfiguration = new RTAAdapterURLCategoryConsumptionConfiguration();
			((RTAAdapterURLCategoryConsumptionConfiguration) rtaConfiguration).setEnabled(enabled);
			((RTAAdapterURLCategoryConsumptionConfiguration) rtaConfiguration).setId(id);
			((RTAAdapterURLCategoryConsumptionConfiguration) rtaConfiguration).setType(type);
			((RTAAdapterURLCategoryConsumptionConfiguration) rtaConfiguration).setGranularity(granularity);
			((RTAAdapterURLCategoryConsumptionConfiguration) rtaConfiguration).setSources(sources);
			break;

		case RTAAdapterURLHitsConfiguration:
			rtaConfiguration = new RTAAdapterURLHitsConfiguration();
			((RTAAdapterURLHitsConfiguration) rtaConfiguration).setEnabled(enabled);
			((RTAAdapterURLHitsConfiguration) rtaConfiguration).setId(id);
			((RTAAdapterURLHitsConfiguration) rtaConfiguration).setType(type);
			((RTAAdapterURLHitsConfiguration) rtaConfiguration).setGranularity(granularity);
			((RTAAdapterURLHitsConfiguration) rtaConfiguration).setSources(sources);
			break;

		case RTAAdapterUserAgentConfiguration:
			rtaConfiguration = new RTAAdapterUserAgentConfiguration();
			((RTAAdapterUserAgentConfiguration) rtaConfiguration).setEnabled(enabled);
			((RTAAdapterUserAgentConfiguration) rtaConfiguration).setId(id);
			((RTAAdapterUserAgentConfiguration) rtaConfiguration).setType(type);
			((RTAAdapterUserAgentConfiguration) rtaConfiguration).setGranularity(granularity);
			((RTAAdapterUserAgentConfiguration) rtaConfiguration).setSources(sources);
			break;

		case RTAAdapterFlavorUpdaterConfiguration:
			rtaConfiguration = new RTAAdapterFlavorUpdaterConfiguration();
			((RTAAdapterFlavorUpdaterConfiguration) rtaConfiguration).setEnabled(enabled);
			((RTAAdapterFlavorUpdaterConfiguration) rtaConfiguration).setId(id);
			((RTAAdapterFlavorUpdaterConfiguration) rtaConfiguration).setType(type);
			((RTAAdapterFlavorUpdaterConfiguration) rtaConfiguration).setGranularity(granularity);
			((RTAAdapterFlavorUpdaterConfiguration) rtaConfiguration).setSources(sources);
			break;
		}
		DetailRecordType mapKey = DetailRecordType.valueOf(key);
		adapters.put(mapKey, rtaConfiguration);
		rtaRecordConfiguration.setAdapters(adapters);
		saveRTAConfiguration(rtaRecordConfiguration);

		return rtaRecordConfiguration;
	}

	/**
	 *
	 * @param keys
	 * @return RTAConfiguration
	 * @throws HttpHostConnectException
	 */
	public RTAConfiguration deleteRTAAdapter(String[] keys) throws HttpHostConnectException {

		RTAConfiguration rtaRecordConfiguration = getRTAConfiguration();
		Map<DetailRecordType, RTAAdapterConfiguration> adapters = rtaRecordConfiguration.getAdapters();
		if (keys != null) {
			for (String key : keys) {
				DetailRecordType mapKey = DetailRecordType.valueOf(key);
				adapters.remove(mapKey);
			}
		}
		rtaRecordConfiguration.setAdapters(adapters);
		saveRTAConfiguration(rtaRecordConfiguration);
		return rtaRecordConfiguration;
	}

	/**
	 *
	 * @param key
	 * @param enabled
	 * @param id
	 * @param granularity
	 * @param allSources
	 * @param defaultCategoryId
	 * @param minDownstreamAllowed
	 * @param minDurationAllowed
	 * @param services
	 * @param categories
	 * @param qualityConditions
	 * @return RTAConfiguration
	 * @throws HttpHostConnectException
	 */
	public RTAConfiguration saveRTAAdapterCategoryConfiguration(String key, Boolean enabled, String id, String granularity, String[] allSources, String defaultCategoryId, String minDownstreamAllowed, String minDurationAllowed, String[] services, String[] categories, String qualityConditions) throws HttpHostConnectException {

		RTAConfiguration rtaRecordConfiguration = getRTAConfiguration();
		Map<DetailRecordType, RTAAdapterConfiguration> adapters = rtaRecordConfiguration.getAdapters();
		DetailRecordType keyMap = DetailRecordType.valueOf(key);
		List<DetailRecordType> sources = getAllowed(allSources);

		((RTAAdapterCategoryConfiguration) adapters.get(keyMap)).setEnabled(enabled);
		if (id != null) {
			((RTAAdapterCategoryConfiguration) adapters.get(keyMap)).setId(Integer.valueOf(id));
		} else {
			((RTAAdapterCategoryConfiguration) adapters.get(keyMap)).setId(null);
		}
		if (granularity != null) {
			((RTAAdapterCategoryConfiguration) adapters.get(keyMap)).setGranularity(Long.valueOf(granularity));
		} else {
			((RTAAdapterCategoryConfiguration) adapters.get(keyMap)).setGranularity(null);
		}
		((RTAAdapterCategoryConfiguration) adapters.get(keyMap)).setSources(sources);
		List<CategoryConfiguration> allCategories = new LinkedList<CategoryConfiguration>();
		if (categories != null) {
			for (int i = 0; i < categories.length; i = i + 5) {
				CategoryConfiguration c = new CategoryConfiguration();
				c.setId(Integer.valueOf(categories[i]));
				c.setName(categories[i + 1]);
				c.setCategoryParentId(Integer.valueOf(categories[i + 2]));
				c.setMinValue(Long.valueOf(categories[i + 3]));
				c.setMaxValue(Long.valueOf(categories[i + 4]));
				allCategories.add(c);
			}
		}
		((RTAAdapterCategoryConfiguration) adapters.get(keyMap)).setCategories(allCategories);
		if (defaultCategoryId != null && !defaultCategoryId.equals("")) {
			((RTAAdapterCategoryConfiguration) adapters.get(keyMap)).setDefaultCategoryId(Integer.valueOf(defaultCategoryId));
		} else {
			((RTAAdapterCategoryConfiguration) adapters.get(keyMap)).setDefaultCategoryId(null);
		}
		if (minDownstreamAllowed != null && !minDownstreamAllowed.equals("")) {
			((RTAAdapterCategoryConfiguration) adapters.get(keyMap)).setMinDownstreamAllowed(Long.valueOf(minDownstreamAllowed));
		} else {
			((RTAAdapterCategoryConfiguration) adapters.get(keyMap)).setMinDownstreamAllowed(null);
		}
		if (minDurationAllowed != null && !minDurationAllowed.equals("")) {
			((RTAAdapterCategoryConfiguration) adapters.get(keyMap)).setMinDurationAllowed(Long.valueOf(minDurationAllowed));
		} else {
			((RTAAdapterCategoryConfiguration) adapters.get(keyMap)).setMinDurationAllowed(null);
		}
		QualityCondition[] allQualityCondition = null;
		List<QualityCondition> allQualityConditions = new LinkedList<QualityCondition>();
		if (qualityConditions != null && !qualityConditions.equals("")) {
			allQualityCondition = ConfigJSON.parseQualityCondition(qualityConditions);
			allQualityConditions = Arrays.asList(allQualityCondition);
		}
		((RTAAdapterCategoryConfiguration) adapters.get(keyMap)).setQualityConditions(allQualityConditions);
		List<Integer> allServices = new LinkedList<Integer>();
		if (services != null) {
			allServices = getIntegers(services);
		}
		((RTAAdapterCategoryConfiguration) adapters.get(keyMap)).setServices(allServices);
		rtaRecordConfiguration.setAdapters(adapters);

		saveRTAConfiguration(rtaRecordConfiguration);

		return rtaRecordConfiguration;
	}

	/**
	 *
	 * @param key
	 * @param enabled
	 * @param id
	 * @param granularity
	 * @param allSources
	 * @param calculationGranularity
	 * @param allowedCategories
	 * @param interestConversionKey
	 * @return RTAConfiguration
	 * @throws HttpHostConnectException
	 */
	public RTAConfiguration saveRTAAdapterCategoryHitsConfiguration(String key, Boolean enabled, String id, String granularity, String[] allSources, Long calculationGranularity, String[] allowedCategories, String[] interestConversionKey) throws HttpHostConnectException {

		RTAConfiguration rtaRecordConfiguration = getRTAConfiguration();
		Map<DetailRecordType, RTAAdapterConfiguration> adapters = rtaRecordConfiguration.getAdapters();
		DetailRecordType keyMap = DetailRecordType.valueOf(key);
		List<DetailRecordType> sources = getAllowed(allSources);
		RTAAdapterConfiguration rtaConfiguration = adapters.get(keyMap);

		((RTAAdapterCategoryHitsConfiguration) rtaConfiguration).setEnabled(enabled);
		if (id != null && !id.equals("")) {
			((RTAAdapterCategoryHitsConfiguration) rtaConfiguration).setId(Integer.valueOf(id));
		} else {
			((RTAAdapterCategoryHitsConfiguration) rtaConfiguration).setId(null);
		}
		if (granularity != null && !granularity.equals("")) {
			((RTAAdapterCategoryHitsConfiguration) rtaConfiguration).setGranularity(Long.valueOf(granularity));
		} else {
			((RTAAdapterCategoryHitsConfiguration) rtaConfiguration).setGranularity(null);
		}
		((RTAAdapterCategoryHitsConfiguration) rtaConfiguration).setSources(sources);
		((RTAAdapterCategoryHitsConfiguration) rtaConfiguration).setCalculationGranularity(calculationGranularity);

		if (allowedCategories != null) {
			List<Integer> alloweds = getIntegers(allowedCategories);
			((RTAAdapterCategoryHitsConfiguration) rtaConfiguration).setAllowedCategories(alloweds);
		}

		List<InterestConversionKey> interestConversionList = new LinkedList<InterestConversionKey>();

		if (interestConversionKey != null) {
			for (int i = 0; i < interestConversionKey.length; i = i + 2) {
				Byte calification = Byte.valueOf(interestConversionKey[i]);
				Double valueD = Double.valueOf(interestConversionKey[i + 1]);
				InterestConversionKey interestConversion;
				interestConversion = new InterestConversionKey(calification, valueD);
				interestConversionList.add(interestConversion);
			}
		}

		((RTAAdapterCategoryHitsConfiguration) rtaConfiguration).setInterestConversionList(interestConversionList);

		rtaRecordConfiguration.setAdapters(adapters);

		saveRTAConfiguration(rtaRecordConfiguration);

		return rtaRecordConfiguration;
	}

	/**
	 *
	 * @param key
	 * @param enabled
	 * @param id
	 * @param granularity
	 * @param allSources
	 * @param allowedPackageIdsConsumptionConfiguration
	 * @param counterConsumptionConfiguration
	 * @return RTAConfiguration
	 * @throws HttpHostConnectException
	 */
	public RTAConfiguration saveRTAAdapterHeavyConsumptionConfiguration(String key, Boolean enabled, String id, String granularity, String[] allSources, String[] allowedPackageIdsConsumptionConfiguration, String[] counterConsumptionConfiguration) throws HttpHostConnectException {

		RTAConfiguration rtaRecordConfiguration = getRTAConfiguration();
		Map<DetailRecordType, RTAAdapterConfiguration> adapters = rtaRecordConfiguration.getAdapters();
		DetailRecordType keyMap = DetailRecordType.valueOf(key);
		List<DetailRecordType> sources = getAllowed(allSources);
		((RTAAdapterHeavyConsumptionConfiguration) adapters.get(keyMap)).setEnabled(enabled);
		if (id != null && !id.equals("")) {
			((RTAAdapterHeavyConsumptionConfiguration) adapters.get(keyMap)).setId(Integer.valueOf(id));
		} else {
			((RTAAdapterHeavyConsumptionConfiguration) adapters.get(keyMap)).setId(null);
		}
		if (granularity != null && !granularity.equals("")) {
			((RTAAdapterHeavyConsumptionConfiguration) adapters.get(keyMap)).setGranularity(Long.valueOf(granularity));
		} else {
			((RTAAdapterHeavyConsumptionConfiguration) adapters.get(keyMap)).setGranularity(null);
		}
		((RTAAdapterHeavyConsumptionConfiguration) adapters.get(keyMap)).setSources(sources);

		if (allowedPackageIdsConsumptionConfiguration != null) {
			((RTAAdapterHeavyConsumptionConfiguration) adapters.get(keyMap)).setAllowedPackageIds(getIntegers(allowedPackageIdsConsumptionConfiguration));
		} else {
			((RTAAdapterHeavyConsumptionConfiguration) adapters.get(keyMap)).setAllowedPackageIds(null);
		}
		Map<Integer, CounterConsumptionConfiguration> counters = new LinkedHashMap<Integer, CounterConsumptionConfiguration>();
		if (counterConsumptionConfiguration != null) {

			for (int i = 0; i < counterConsumptionConfiguration.length; i = i + 8) {
				Integer idkeyMap = Integer.valueOf(counterConsumptionConfiguration[i]);
				Integer counterId = Integer.valueOf(counterConsumptionConfiguration[i + 1]);
				List<Integer> serviceIds = new LinkedList<Integer>();
				if (!counterConsumptionConfiguration[i + 2].equals("")) {
					String[] ary = counterConsumptionConfiguration[i + 2].split(",");
					if (ary != null) {
						serviceIds = getIntegers(ary);
					}
				}
				boolean allServices = Boolean.valueOf(counterConsumptionConfiguration[i + 3]);
				boolean filterByDownstream = Boolean.valueOf(counterConsumptionConfiguration[i + 4]);
				Long minDownstreamAllowed = Long.valueOf(counterConsumptionConfiguration[i + 5]);
				boolean filterByUpstream = Boolean.valueOf(counterConsumptionConfiguration[i + 6]);
				Long minUpstreamAllowed = Long.valueOf(counterConsumptionConfiguration[i + 7]);
				CounterConsumptionConfiguration counterConsumptionConfigurationL = new CounterConsumptionConfiguration();
				counterConsumptionConfigurationL.setCounterId(counterId);
				counterConsumptionConfigurationL.setServiceIds(serviceIds);
				counterConsumptionConfigurationL.setAllServices(allServices);
				counterConsumptionConfigurationL.setFilterByDownstream(filterByDownstream);
				counterConsumptionConfigurationL.setMinDownstreamAllowed(minDownstreamAllowed);
				counterConsumptionConfigurationL.setFilterByUpstream(filterByUpstream);
				counterConsumptionConfigurationL.setMinUpstreamAllowed(minUpstreamAllowed);
				counters.put(idkeyMap, counterConsumptionConfigurationL);
			}
			((RTAAdapterHeavyConsumptionConfiguration) adapters.get(keyMap)).setCounters(counters);
		} else {
			((RTAAdapterHeavyConsumptionConfiguration) adapters.get(keyMap)).setCounters(null);
		}

		rtaRecordConfiguration.setAdapters(adapters);

		saveRTAConfiguration(rtaRecordConfiguration);

		return rtaRecordConfiguration;
	}

	/**
	 *
	 * @param key
	 * @param enabled
	 * @param id
	 * @param granularity
	 * @param allSources
	 * @param processUniqSubs
	 * @param minAllowedHits
	 * @return RTAConfiguration
	 * @throws HttpHostConnectException
	 */
	public RTAConfiguration saveRTAAdapterTopperConfiguration(String key, Boolean enabled, String id, String granularity, String[] allSources, Boolean processUniqSubs, Integer minAllowedHits) throws HttpHostConnectException {

		RTAConfiguration rtaRecordConfiguration = getRTAConfiguration();
		Map<DetailRecordType, RTAAdapterConfiguration> adapters = rtaRecordConfiguration.getAdapters();
		DetailRecordType keyMap = DetailRecordType.valueOf(key);
		List<DetailRecordType> sources = getAllowed(allSources);
		RTAAdapterConfiguration rtaConfiguration = adapters.get(keyMap);
		((RTAAdapterTopperConfiguration) rtaConfiguration).setEnabled(enabled);
		if (id != null && !id.equals("")) {
			((RTAAdapterTopperConfiguration) rtaConfiguration).setId(Integer.valueOf(id));
		} else {
			((RTAAdapterTopperConfiguration) rtaConfiguration).setId(null);
		}
		if (granularity != null && !granularity.equals("")) {
			((RTAAdapterTopperConfiguration) rtaConfiguration).setGranularity(Long.valueOf(granularity));
		} else {
			((RTAAdapterTopperConfiguration) rtaConfiguration).setGranularity(null);
		}
		((RTAAdapterTopperConfiguration) rtaConfiguration).setSources(sources);
		if (processUniqSubs != null) {
			((RTAAdapterTopperConfiguration) rtaConfiguration).setProcessUniqSubs(processUniqSubs);
		} else {
			((RTAAdapterTopperConfiguration) rtaConfiguration).setProcessUniqSubs(false);
		}
		if (minAllowedHits != null) {
			((RTAAdapterTopperConfiguration) rtaConfiguration).setMinAllowedHits(minAllowedHits);
		} else {
			((RTAAdapterTopperConfiguration) rtaConfiguration).setMinAllowedHits(0);
		}
		rtaRecordConfiguration.setAdapters(adapters);
		saveRTAConfiguration(rtaRecordConfiguration);

		return rtaRecordConfiguration;
	}

	/**
	 *
	 * @param key
	 * @param enabled
	 * @param id
	 * @param granularity
	 * @param allSources
	 * @param allowedPackageIds
	 * @param counters
	 * @return RTAConfiguration
	 * @throws HttpHostConnectException
	 */
	public RTAConfiguration saveRTAAdapterURLCategoryConsumptionConfiguration(String key, Boolean enabled, String id, String granularity, String[] allSources, String[] allowedPackageIds, String[] counters) throws HttpHostConnectException {

		RTAConfiguration rtaRecordConfiguration = getRTAConfiguration();
		Map<DetailRecordType, RTAAdapterConfiguration> adapters = rtaRecordConfiguration.getAdapters();
		DetailRecordType keyMap = DetailRecordType.valueOf(key);
		List<DetailRecordType> sources = getAllowed(allSources);

		adapters.get(keyMap).setEnabled(enabled);
		if (id != null && !id.equals("")) {
			adapters.get(keyMap).setId(Integer.valueOf(id));
		} else {
			adapters.get(keyMap).setId(null);
		}
		if (granularity != null && !granularity.equals("")) {
			adapters.get(keyMap).setGranularity(Long.valueOf(granularity));
		} else {
			adapters.get(keyMap).setGranularity(null);
		}
		adapters.get(keyMap).setSources(sources);

		if (allowedPackageIds != null) {
			((RTAAdapterURLCategoryConsumptionConfiguration) adapters.get(keyMap)).setAllowedPackageIds(getIntegers(allowedPackageIds));
		} else {
			((RTAAdapterURLCategoryConsumptionConfiguration) adapters.get(keyMap)).setAllowedPackageIds(null);
		}
		Map<Integer, CounterURLCategoryConsumptionConfiguration> counterURLCategoryConsumptionConfigurationMap = new LinkedHashMap<Integer, CounterURLCategoryConsumptionConfiguration>();
		if (counters != null) {
			for (int i = 0; i < counters.length; i = i + 6) {
				Integer idkeyMap = Integer.valueOf(counters[i]);
				Integer counterId = Integer.valueOf(counters[i + 1]);
				List<Integer> categoryIds = new LinkedList<Integer>();
				if (!counters[i + 2].equals("")) {
					String[] ary = counters[i + 2].split(",");
					if (ary != null) {
						categoryIds = getIntegers(ary);
					}
				}
				boolean allCategories = Boolean.valueOf(counters[i + 3]);
				boolean filterByUsage = Boolean.valueOf(counters[i + 4]);
				Long minUsageAllowed = Long.valueOf(counters[i + 5]);

				CounterURLCategoryConsumptionConfiguration counterURLCategoryConsumptionConfiguration = new CounterURLCategoryConsumptionConfiguration();
				counterURLCategoryConsumptionConfiguration.setCounterId(counterId);
				counterURLCategoryConsumptionConfiguration.setAllCategories(allCategories);
				counterURLCategoryConsumptionConfiguration.setCategoryIds(categoryIds);
				counterURLCategoryConsumptionConfiguration.setFilterByUsage(filterByUsage);
				counterURLCategoryConsumptionConfiguration.setMinUsageAllowed(minUsageAllowed);
				counterURLCategoryConsumptionConfigurationMap.put(idkeyMap, counterURLCategoryConsumptionConfiguration);
			}
			((RTAAdapterURLCategoryConsumptionConfiguration) adapters.get(keyMap)).setCounters(counterURLCategoryConsumptionConfigurationMap);
		} else {
			((RTAAdapterURLCategoryConsumptionConfiguration) adapters.get(keyMap)).setCounters(null);
		}

		rtaRecordConfiguration.setAdapters(adapters);
		saveRTAConfiguration(rtaRecordConfiguration);

		return rtaRecordConfiguration;
	}

	/**
	 *
	 * @param key
	 * @param enabled
	 * @param id
	 * @param granularity
	 * @param allSources
	 * @param allServices
	 * @param groupsURLHits
	 * @return RTAConfiguration
	 * @throws HttpHostConnectException
	 */
	public RTAConfiguration saveRTAAdapterURLHitsConfiguration(String key, Boolean enabled, String id, String granularity, String[] allSources, String[] allServices, String groupsURLHits) throws HttpHostConnectException {

		RTAConfiguration rtaRecordConfiguration = getRTAConfiguration();
		Map<DetailRecordType, RTAAdapterConfiguration> adapters = rtaRecordConfiguration.getAdapters();
		DetailRecordType keyMap = DetailRecordType.valueOf(key);
		List<DetailRecordType> sources = getAllowed(allSources);

		adapters.get(keyMap).setEnabled(enabled);
		if (id != null && !id.equals("")) {
			adapters.get(keyMap).setId(Integer.valueOf(id));
		} else {
			adapters.get(keyMap).setId(null);
		}
		if (granularity != null && !granularity.equals("")) {
			adapters.get(keyMap).setGranularity(Long.valueOf(granularity));
		} else {
			adapters.get(keyMap).setGranularity(null);
		}
		adapters.get(keyMap).setSources(sources);

		if (allServices != null) {
			((RTAAdapterURLHitsConfiguration) adapters.get(keyMap)).setServices(getIntegers(allServices));
		} else {
			((RTAAdapterURLHitsConfiguration) adapters.get(keyMap)).setId(null);
		}

		GroupURLHitsConfiguration[] allGroupURLHitsConfiguration = null;
		List<GroupURLHitsConfiguration> groupURLs = new LinkedList<GroupURLHitsConfiguration>();
		if (groupsURLHits != null && !groupsURLHits.equals("")) {
			allGroupURLHitsConfiguration = ConfigJSON.parseGroupURLHitsConfiguration(groupsURLHits);
			groupURLs = Arrays.asList(allGroupURLHitsConfiguration);
		}
		((RTAAdapterURLHitsConfiguration) adapters.get(keyMap)).setGroupURLs(groupURLs);

		rtaRecordConfiguration.setAdapters(adapters);
		saveRTAConfiguration(rtaRecordConfiguration);

		return rtaRecordConfiguration;
	}

	private List<Integer> getIntegers(String[] allStrings) {
		List<Integer> listIntegers = new LinkedList<Integer>();
		for (String a : allStrings) {
			listIntegers.add(Integer.valueOf(a));
		}
		return listIntegers;
	}

	/**
	 *
	 * @param key
	 * @param enabled
	 * @param id
	 * @param granularity
	 * @param allSources
	 * @param sepIpAddress
	 * @param sepPort
	 * @param defaultFlavorId
	 * @param newFlavorsCountToApplyInSep
	 * @return RTAConfiguration
	 * @throws HttpHostConnectException
	 */
	public RTAConfiguration saveRTAAdapterFlavorUpdaterConfiguration(String key, Boolean enabled, String id, String granularity, String[] allSources, String sepIpAddress, String sepPort, String defaultFlavorId, String newFlavorsCountToApplyInSep) throws HttpHostConnectException {

		RTAConfiguration rtaRecordConfiguration = getRTAConfiguration();
		Map<DetailRecordType, RTAAdapterConfiguration> adapters = rtaRecordConfiguration.getAdapters();
		DetailRecordType keyMap = DetailRecordType.valueOf(key);

		adapters.get(keyMap).setEnabled(enabled);
		if (id != null && !id.equals("")) {
			adapters.get(keyMap).setId(Integer.valueOf(id));
		} else {
			adapters.get(keyMap).setId(null);
		}
		if (granularity != null && !granularity.equals("")) {
			adapters.get(keyMap).setGranularity(Long.valueOf(granularity));
		} else {
			adapters.get(keyMap).setGranularity(null);
		}
		List<DetailRecordType> sources = getAllowed(allSources);
		adapters.get(keyMap).setSources(sources);
		if (sepIpAddress != null && !sepIpAddress.equals("")) {
			((RTAAdapterFlavorUpdaterConfiguration) adapters.get(keyMap)).setSepIpAddress(sepIpAddress);
		}
		if (sepPort != null && !sepPort.equals("")) {
			((RTAAdapterFlavorUpdaterConfiguration) adapters.get(keyMap)).setSepPort(Integer.valueOf(sepPort));
		}
		if (defaultFlavorId != null && !defaultFlavorId.equals("")) {
			((RTAAdapterFlavorUpdaterConfiguration) adapters.get(keyMap)).setDefaultFlavorId(Integer.valueOf(defaultFlavorId));
		}
		if (newFlavorsCountToApplyInSep != null && !newFlavorsCountToApplyInSep.equals("")) {
			((RTAAdapterFlavorUpdaterConfiguration) adapters.get(keyMap)).setNewFlavorsCountToApplyInSep(Integer.valueOf(newFlavorsCountToApplyInSep));
		}

		rtaRecordConfiguration.setAdapters(adapters);
		saveRTAConfiguration(rtaRecordConfiguration);

		return rtaRecordConfiguration;
	}

	/**
	 *
	 * @param key
	 * @param value
	 * @param enabled
	 * @param id
	 * @param granularity
	 * @param allSources
	 * @return RTAConfiguration
	 * @throws HttpHostConnectException
	 */
	public RTAConfiguration saveRTAAdapterGeneralConfig(String key, String value, Boolean enabled, String id, String granularity, String[] allSources) throws HttpHostConnectException {

		RTAConfiguration rtaRecordConfiguration = getRTAConfiguration();
		Map<DetailRecordType, RTAAdapterConfiguration> adapters = rtaRecordConfiguration.getAdapters();
		DetailRecordType keyMap = DetailRecordType.valueOf(key);
		RTAAdapterConfigurationType type = RTAAdapterConfigurationType.valueOf(value);
		List<DetailRecordType> sources = getAllowed(allSources);

		adapters.get(keyMap).setEnabled(enabled);
		if (id != null && !id.equals("")) {
			adapters.get(keyMap).setId(Integer.valueOf(id));
		} else {
			adapters.get(keyMap).setId(null);
		}
		adapters.get(keyMap).setType(type);
		if (granularity != null && !granularity.equals("")) {
			adapters.get(keyMap).setGranularity(Long.valueOf(granularity));
		} else {
			adapters.get(keyMap).setGranularity(null);
		}
		adapters.get(keyMap).setSources(sources);

		rtaRecordConfiguration.setAdapters(adapters);
		saveRTAConfiguration(rtaRecordConfiguration);

		return rtaRecordConfiguration;
	}

	/**
	 *
	 * @param valueType
	 * @return PeriodicConfiguration
	 * @throws HttpHostConnectException
	 */
	public PeriodicConfiguration createPeriodic(String valueType) throws HttpHostConnectException {

		PeriodicConfiguration periodicConfiguration = getPeriodicConfiguration();
		Collection<PeriodicDataRecordTask> periodicDataRecordTask = periodicConfiguration.getPeriodicDataRecordTasks();

		Boolean enabled = false;
		PeriodicTaskType type = PeriodicTaskType.valueOf(valueType);
		List<DetailRecordType> recordTypes = new ArrayList<DetailRecordType>();
		PeriodicDataRecordTask periodicDataRecord = null;

		switch (type) {

		case PeriodicAggregationTask:
			periodicDataRecord = new PeriodicAggregationTask();
			((PeriodicAggregationTask) periodicDataRecord).setEnabled(enabled);
			((PeriodicAggregationTask) periodicDataRecord).setType(type);
			((PeriodicAggregationTask) periodicDataRecord).setAction(PeriodicDataRecordAction.aggregation);
			((PeriodicAggregationTask) periodicDataRecord).setRecordTypes(recordTypes);
			break;

		case PeriodicRemovalTask:
			periodicDataRecord = new PeriodicRemovalTask();
			((PeriodicRemovalTask) periodicDataRecord).setEnabled(enabled);
			((PeriodicRemovalTask) periodicDataRecord).setType(type);
			((PeriodicRemovalTask) periodicDataRecord).setAction(PeriodicDataRecordAction.removal);
			((PeriodicRemovalTask) periodicDataRecord).setRecordTypes(recordTypes);
			break;

		case PeriodicRTAPersistenceTask:
			periodicDataRecord = new PeriodicRTAPersistenceTask();
			((PeriodicRTAPersistenceTask) periodicDataRecord).setEnabled(enabled);
			((PeriodicRTAPersistenceTask) periodicDataRecord).setType(type);
			((PeriodicRTAPersistenceTask) periodicDataRecord).setAction(PeriodicDataRecordAction.rtaPersistence);
			((PeriodicRTAPersistenceTask) periodicDataRecord).setRecordTypes(recordTypes);
			break;
		}
		periodicDataRecordTask.add(periodicDataRecord);
		periodicConfiguration.setPeriodicDataRecordTasks(periodicDataRecordTask);
		savePeriodicConfiguration(periodicConfiguration);

		return periodicConfiguration;
	}

	/**
	 * @param index
	 * @return PeriodicConfiguration
	 * @throws HttpHostConnectException
	 */
	public PeriodicConfiguration deletePeriodicConfiguration(String index) throws HttpHostConnectException {

		PeriodicConfiguration periodicConfiguration = getPeriodicConfiguration();
		Collection<PeriodicDataRecordTask> periodicDataRecordTask = periodicConfiguration.getPeriodicDataRecordTasks();
		Collection<PeriodicDataRecordTask> periodicTask = new LinkedList<PeriodicDataRecordTask>();
		if (index != null && !index.equals("")) {
			int delete = Integer.valueOf(index);
			int i = 0;
			for (PeriodicDataRecordTask p : periodicDataRecordTask) {
				if (i != delete) {
					periodicTask.add(p);
				}
				i++;
			}
		}
		periodicConfiguration.setPeriodicDataRecordTasks(periodicTask);
		savePeriodicConfiguration(periodicConfiguration);
		return periodicConfiguration;
	}

	/**
	 *
	 * @param index
	 * @param type
	 * @param enabled
	 * @param cronPattern
	 * @param recordTypes
	 * @param days
	 * @return PeriodicConfiguration
	 * @throws HttpHostConnectException
	 */
	public PeriodicConfiguration savePeriodicDataRecordTasks(String index, String type, boolean enabled, String cronPattern, String[] recordTypes, String days) throws HttpHostConnectException {

		PeriodicConfiguration periodicConfiguration = getPeriodicConfiguration();
		Collection<PeriodicDataRecordTask> periodicDataRecordTask = periodicConfiguration.getPeriodicDataRecordTasks();
		Collection<PeriodicDataRecordTask> periodicTask = new LinkedList<PeriodicDataRecordTask>();
		if (index != null && !index.equals("")) {
			int edit = Integer.valueOf(index);
			int i = 0;
			for (PeriodicDataRecordTask p : periodicDataRecordTask) {
				if (i != edit) {
					periodicTask.add(p);
				} else {
					PeriodicDataRecordTask periodic = editPeriodicDataRecordTask(type, enabled, cronPattern, recordTypes, days);
					periodicTask.add(periodic);
				}
				i++;
			}
		}
		periodicConfiguration.setPeriodicDataRecordTasks(periodicTask);
		savePeriodicConfiguration(periodicConfiguration);
		return periodicConfiguration;
	}

	private PeriodicDataRecordTask editPeriodicDataRecordTask(String valueType, boolean enabled, String cronPattern, String[] recordTypes, String days) throws HttpHostConnectException {

		PeriodicTaskType type = PeriodicTaskType.valueOf(valueType);
		List<DetailRecordType> allrecordTypes = getAllowed(recordTypes);
		PeriodicDataRecordTask periodicDataRecord = null;

		switch (type) {
		case PeriodicAggregationTask:
			periodicDataRecord = new PeriodicAggregationTask();
			((PeriodicAggregationTask) periodicDataRecord).setEnabled(enabled);
			((PeriodicAggregationTask) periodicDataRecord).setType(type);
			((PeriodicAggregationTask) periodicDataRecord).setAction(PeriodicDataRecordAction.aggregation);
			((PeriodicAggregationTask) periodicDataRecord).setRecordTypes(allrecordTypes);
			((PeriodicAggregationTask) periodicDataRecord).setCronPattern(cronPattern);
			break;
		case PeriodicRemovalTask:
			periodicDataRecord = new PeriodicRemovalTask();
			((PeriodicRemovalTask) periodicDataRecord).setEnabled(enabled);
			((PeriodicRemovalTask) periodicDataRecord).setType(type);
			((PeriodicRemovalTask) periodicDataRecord).setAction(PeriodicDataRecordAction.removal);
			((PeriodicRemovalTask) periodicDataRecord).setRecordTypes(allrecordTypes);
			((PeriodicRemovalTask) periodicDataRecord).setCronPattern(cronPattern);
			if (days != null && !days.equals("")) {
				PeriodicRemovalConfiguration removalConfiguration = new PeriodicRemovalConfiguration();
				removalConfiguration.setDays(Integer.valueOf(days));
				((PeriodicRemovalTask) periodicDataRecord).setRemovalConfiguration(removalConfiguration);
			}
			break;
		case PeriodicRTAPersistenceTask:
			periodicDataRecord = new PeriodicRTAPersistenceTask();
			((PeriodicRTAPersistenceTask) periodicDataRecord).setEnabled(enabled);
			((PeriodicRTAPersistenceTask) periodicDataRecord).setType(type);
			((PeriodicRTAPersistenceTask) periodicDataRecord).setAction(PeriodicDataRecordAction.rtaPersistence);
			((PeriodicRTAPersistenceTask) periodicDataRecord).setRecordTypes(allrecordTypes);
			((PeriodicRTAPersistenceTask) periodicDataRecord).setCronPattern(cronPattern);
			break;
		}

		return periodicDataRecord;
	}

}
