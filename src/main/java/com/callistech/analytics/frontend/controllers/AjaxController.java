package com.callistech.analytics.frontend.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.conn.HttpHostConnectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.callistech.analytics.frontend.domains.uc.entities.Group;
import com.callistech.analytics.frontend.services.NapFrontendService;
import com.callistech.analytics.frontend.services.RestClient;
import com.callistech.analytics.frontend.services.SettingsService;
import com.callistech.analytics.frontend.services.UserAuditService;
import com.callistech.analytics.frontend.services.UserControlService;
import com.callistech.analytics.frontend.util.ActionType;
import com.callistech.analytics.frontend.util.AjaxResult;
import com.callistech.analytics.frontend.util.DashboardDataManager;
import com.callistech.analytics.frontend.util.TableResult;
import com.callistech.analytics.frontend.util.bean.Site;
import com.callistech.nap.configuration.datarecord.rta.RTAConfiguration;
import com.callistech.nap.configuration.periodic.PeriodicConfiguration;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
public class AjaxController {

	@Autowired
	private UserControlService userControlService;

	@Autowired
	private SettingsService settingsService;

	@Autowired
	private UserAuditService userAuditService;

	@Autowired
	private NapFrontendService napFrontendService;

	private RestClient restClient;
	public static final String REST_AJAX_PROXY = "/ajax-proxy";

	private RestClient getRestClient() {
		if (restClient == null) {
			restClient = new RestClient(settingsService.getPropValue(SettingsService.IP), settingsService.getPropValue(SettingsService.PORT));
		}
		return restClient;
	}

	@RequestMapping(value = { "/ajax/getSites", "/ajax/getSCEs", "/ajax/getTemplates", "/ajax/getReportTemplates", "/ajax/getReportTemplatesDomains", "/ajax/getCMTSs", "/ajax/getNetworkLevels", "/ajax/getDashboardQoEServices" })
	public void metodosProxy1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String method = request.getServletPath().split("/ajax")[1];
		getRestClient().executeRestGetMethod(method, request, response);
	}

	@RequestMapping(value = { "/ajax/getObjectManager", "/ajax/getNetworkTemplatesByLevel", "/ajax/executeQuery", "/ajax/executeQueryByType", "/ajax/executeQueryBySCE", "/ajax/executeReportTemplate", "/ajax/killReportTemplate", "/ajax/getDashboardCAM", "/ajax/getDashboardQoE", "/ajax/exportData", "/ajax/getDeviceStatistics", "/ajax/addReportTemplateInstance", "/ajax/renameReportTemplateInstance", "/ajax/deleteReportTemplateInstance", "/ajax/duplicateReportTemplateInstance",
			"/ajax/executeNetworkReportTemplate", "/ajax/queryTableTemplate", "/ajax/dashboard/createDashboard", "/ajax/dashboard/deleteDashboard", "/ajax/dashboard/updateDashboard", "/ajax/dashboard/createDashboardComponent", "/ajax/dashboard/deleteDashboardComponent", "/ajax/dashboard/executeDashboardComponent", "/ajax/executeSingleQuery", "/ajax/reloadSepConfiguration", "/ajax/reloadNotificationServerConfiguration", "/ajax/reloadCsmConfiguration" })
	public void metodosProxy2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String restMethod = request.getServletPath().split("/ajax")[1];
		if ("/deleteReportTemplateInstance".equals(restMethod) || "/deleteDashboardComponent/".equals(restMethod) || "/deletePeriodic".equals(restMethod) || "/deleteDashboard".equals(restMethod) || "/deleteRTAAdapters".equals(restMethod) || "/deletePeriodic".equals(restMethod)) {
			userAuditService.audit(getUser(request), getIp(request), ActionType.NAP_DELETE, request);
			getRestClient().executeRestPostMethod(restMethod, request, response);
		} else if ("/renameReportTemplateInstance".equals(restMethod)) {
			userAuditService.audit(getUser(request), getIp(request), ActionType.NAP_EDIT, request);
			getRestClient().executeRestPostMethod(restMethod, request, response);
		} else if ("/duplicateReportTemplateInstance".equals(restMethod)) {
			userAuditService.audit(getUser(request), getIp(request), ActionType.NAP_DUPLICATE, request);
			getRestClient().executeRestPostMethod(restMethod, request, response);
		} else if ("/addReportTemplateInstance".equals(restMethod)) {
			userAuditService.audit(getUser(request), getIp(request), ActionType.NAP_CREATE, request);
			getRestClient().executeRestPostMethod(restMethod, request, response);
		} else if ("/executeReportTemplate".equals(restMethod)) {
			userAuditService.audit(getUser(request), getIp(request), ActionType.NAP_CREATE, request);
			getRestClient().executeRestPostMethod(restMethod, request, response);
		} else if ("/executeReportTemplate".equals(restMethod)) {
			userAuditService.audit(getUser(request), getIp(request), ActionType.NAP_CREATE, request);
			getRestClient().executeRestPostMethod(restMethod, request, response);
		} else if ("/executeSingleQuery".equals(restMethod)) {
			getRestClient().executeRestPostMethodWithoutUser(restMethod, request, response);
		} else {
			getRestClient().executeRestPostMethod(restMethod, request, response);
		}
	}

	@RequestMapping(value = { "/ajax/getReportTemplatesByDomain" })
	public void getReportTemplatesByDomain(HttpServletRequest request, HttpServletResponse response, @RequestParam String domain) throws IOException {
		String restMethod = request.getServletPath().split("/ajax")[1];
		String username = request.getUserPrincipal().getName();
		Map<String, String> params = new HashMap<String, String>();
		params.put("domain", domain);
		String responseJSON = getRestClient().executeRestPostMethod(restMethod, username, params);
		responseJSON = manageTemplates(request, response, responseJSON);
		response.setContentType("application/json");
		response.getWriter().write(responseJSON);
	}

	@RequestMapping(value = { "/ajax/dashboard/getDashboardsInfo" })
	public void getDashboardsInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String restMethod = request.getServletPath().split("/ajax")[1];
		getRestClient().executeRestGetMethod(restMethod + "?napUser=" + request.getUserPrincipal().getName(), request, response);
	}

	@RequestMapping(value = { "/ajax/dashboard/getDashboard" })
	public void getDashboard(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String restMethod = request.getServletPath().split("/ajax")[1];
		getRestClient().executeRestGetMethod(restMethod + "?napUser=" + request.getUserPrincipal().getName() + "&dashboardId=" + request.getParameter("dashboardId"), request, response);
	}

	@RequestMapping(value = { "/ajax/dashboard/getUserAvailableDashboardComponents" })
	public void getUserAvailableDashboardComponents(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String restMethod = request.getServletPath().split("/ajax")[1];
		getRestClient().executeRestGetMethod(restMethod + "?napUser=" + request.getUserPrincipal().getName() + "&dashboardId=" + request.getParameter("dashboardId"), request, response);
	}

	@RequestMapping(value = "/ajax/SetSettings")
	public String SetSettings(HttpServletRequest request, HttpServletResponse response, Model model) {
		String ipNap = request.getParameter("IpNap");
		String portNap = request.getParameter("PortNap");
		String Time_Zone = request.getParameter("Time_Zone");
		String timeout = request.getParameter("timeout");
		if (timeout != null && !timeout.isEmpty() && isNumeric(timeout)) {
			settingsService.setPropValues(SettingsService.TIMEOUT, timeout);
		}
		settingsService.setPropValues(SettingsService.IP, ipNap);
		settingsService.setPropValues(SettingsService.PORT, portNap);
		settingsService.setPropValues(SettingsService.TIME_ZONE, Time_Zone);
		restClient = null;
		return "redirect:/";
	}

	@RequestMapping(value = { "/ajax/getNapConfiguration" })
	public void getNapConfiguration(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String restMethod = request.getServletPath().split("/ajax")[1];
		getRestClient().executeRestGetMethod(restMethod, request, response);
	}

	@RequestMapping(value = { "/ajax/getReportsForUser" })
	public void getReportsForUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String restMethod = request.getServletPath().split("/ajax")[1];
		getRestClient().executeRestGetMethod(restMethod + "/" + request.getUserPrincipal().getName(), request, response);
	}

	@RequestMapping(value = { "/ajax/saveRDRSetting" })
	public void saveRDRSetting(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxResult result = new AjaxResult();
		try {
			Integer RDRPort = Integer.valueOf(request.getParameter("RDRPort"));
			Integer queueDepth = Integer.valueOf(request.getParameter("queueDepth"));
			Integer timeout = Integer.valueOf(request.getParameter("timeout"));
			String[] allowedTypes = request.getParameterValues("allowedTypes[]");
			Boolean enableAggregator = Boolean.valueOf(request.getParameter("enableAggregator"));
			String defaultScabbVersion = request.getParameter("defaultScabbVersion");
			String[] ScabbVersion = request.getParameterValues("ScabbVersion[]");
			napFrontendService.setRDRSetting(RDRPort, queueDepth, timeout, allowedTypes, enableAggregator, defaultScabbVersion, ScabbVersion);
			result.setResult(0);
		} catch (NumberFormatException ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} catch (HttpHostConnectException ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} finally {
			String json = new Gson().toJson(result);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}

	@RequestMapping(value = { "/ajax/saveIPDRSettings" })
	public void saveIPDRSettings(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxResult result = new AjaxResult();
		try {
			String ipIPDR = request.getParameter("ipIPDR");
			Integer portIPDR = Integer.valueOf(request.getParameter("portIPDR"));
			Boolean enableAgregatorIPDR = Boolean.valueOf(request.getParameter("enableAgregatorIPDR"));
			String[] cmtss = request.getParameterValues("cmtss[]");
			napFrontendService.saveIPDRSettings(ipIPDR, portIPDR, enableAgregatorIPDR, cmtss);
			result.setResult(0);
		} catch (NumberFormatException ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} catch (HttpHostConnectException ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} finally {
			String json = new Gson().toJson(result);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}

	@RequestMapping(value = { "/ajax/saveCSVManager" })
	public void saveCSVManager(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxResult result = new AjaxResult();
		try {
			String[] CSVManager = request.getParameterValues("CSVManager[]");
			napFrontendService.saveCSVManager(CSVManager);
			result.setResult(0);
		} catch (Exception ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} finally {
			String json = new Gson().toJson(result);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}

	@RequestMapping(value = { "/ajax/saveDataRecordsConfig" })
	public void saveDataRecordsConfig(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxResult result = new AjaxResult();
		try {
			Boolean enableAggregator = Boolean.valueOf(request.getParameter("enableAggregator"));
			String[] AllowedTagsPYC = request.getParameterValues("AllowedTagsPYC[]");
			Boolean EnableCSV = Boolean.valueOf(request.getParameter("EnableCSV"));
			String[] AllowedTagsCSV = request.getParameterValues("AllowedTagsCSV[]");
			Boolean EnableDBManager = Boolean.valueOf(request.getParameter("EnableDBManager"));
			String[] AllowedTagsDBManager = request.getParameterValues("AllowedTagsDBManager[]");
			Boolean enebleRTA = Boolean.valueOf(request.getParameter("enebleRTA"));
			String[] AllowedTagsRTA = request.getParameterValues("AllowedTagsRTA[]");
			napFrontendService.saveDataRecordsConfig(enableAggregator, AllowedTagsPYC, EnableCSV, AllowedTagsCSV, EnableDBManager, AllowedTagsDBManager, enebleRTA, AllowedTagsRTA);
			result.setResult(0);
		} catch (Exception ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} finally {
			String json = new Gson().toJson(result);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}

	@RequestMapping(value = { "/ajax/saveDBManager" })
	public void saveDBManager(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxResult result = new AjaxResult();
		try {
			// Mysql
			String url = request.getParameter("url");
			String user = request.getParameter("user");
			String password = request.getParameter("password");
			// Mongo Interest
			String DBName = request.getParameter("DBName");
			String tableName = request.getParameter("tableName");
			String SuscriberIdCol = request.getParameter("SuscriberIdCol");
			String InterestIdCol = request.getParameter("InterestIdCol");
			napFrontendService.saveDBManager(url, user, password, DBName, tableName, SuscriberIdCol, InterestIdCol);
			result.setResult(0);
		} catch (Exception ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} finally {
			String json = new Gson().toJson(result);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}

	@RequestMapping(value = { "/ajax/saveOthers" })
	public void saveOthers(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxResult result = new AjaxResult();
		try {

			String RMIIP = request.getParameter("RMIIP");
			Integer RMIPort = Integer.valueOf(request.getParameter("RMIPort"));
			String RMIService = request.getParameter("RMIService");

			String RestIP = request.getParameter("RestIP");
			Integer RestPort = Integer.valueOf(request.getParameter("RestPort"));

			String SepIP = request.getParameter("SepIP");
			Integer SepPort = Integer.valueOf(request.getParameter("SepPort"));

			String SERIP = request.getParameter("SERIP");
			Integer SERPort = Integer.valueOf(request.getParameter("SERPort"));

			String CSMIP = request.getParameter("CSMIP");
			Integer CSMPort = Integer.valueOf(request.getParameter("CSMPort"));

			napFrontendService.saveOthers(RMIIP, RMIPort, RMIService, RestIP, RestPort, SepIP, SepPort, SERIP, SERPort, CSMIP, CSMPort);
			result.setResult(0);
		} catch (NumberFormatException ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} catch (HttpHostConnectException ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} finally {
			String json = new Gson().toJson(result);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}

	@RequestMapping(value = { "/ajax/newRTAAdapter" })
	public void newRTAAdapter(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxResult result = new AjaxResult();
		try {
			String key = request.getParameter("key");
			String value = request.getParameter("value");
			RTAConfiguration rtaRecordConfiguration = napFrontendService.createRTAAdapter(key, value);
			result.setResult(rtaRecordConfiguration);
		} catch (Exception ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} finally {
			String json = new Gson().toJson(result);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}

	@RequestMapping(value = { "/ajax/saveRTAAdapterAllConfiguration" })
	public void saveRTAAdapterAllConfiguration(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxResult result = new AjaxResult();
		try {
			// General Config
			String key = request.getParameter("key");
			String value = request.getParameter("value");
			Boolean enabled = Boolean.valueOf(request.getParameter("enabled"));
			String id = request.getParameter("id");
			String granularity = request.getParameter("granularity");
			String[] allSources = request.getParameterValues("allSources[]");

			// Advanced Config
			if (value.equals("RTAAdapterTopperConfiguration")) {
				Boolean processUniqSubs = Boolean.valueOf(request.getParameter("processUniqSubs"));
				Integer minAllowedHits = Integer.valueOf(request.getParameter("minAllowedHits"));
				RTAConfiguration rtaConfiguration = napFrontendService.saveRTAAdapterTopperConfiguration(key, enabled, id, granularity, allSources, processUniqSubs, minAllowedHits);
				result.setResult(rtaConfiguration);
			} else if (value.equals("RTAAdapterCategoryHitsConfiguration")) {
				Long calculationGranularity = Long.valueOf(request.getParameter("calculationGranularity"));
				String[] allowedCategories = request.getParameterValues("allowedCategories[]");
				String[] interestConversionKey = request.getParameterValues("interestConversionKey[]");
				RTAConfiguration rtaConfiguration = napFrontendService.saveRTAAdapterCategoryHitsConfiguration(key, enabled, id, granularity, allSources, calculationGranularity, allowedCategories, interestConversionKey);
				result.setResult(rtaConfiguration);
			} else if (value.equals("RTAAdapterHeavyConsumptionConfiguration")) {
				String[] allowedPackageIdsConsumptionConfiguration = request.getParameterValues("allowedPackageIdsConsumptionConfiguration[]");
				String[] counterConsumptionConfiguration = request.getParameterValues("counterConsumptionConfiguration[]");
				RTAConfiguration rtaConfiguration = napFrontendService.saveRTAAdapterHeavyConsumptionConfiguration(key, enabled, id, granularity, allSources, allowedPackageIdsConsumptionConfiguration, counterConsumptionConfiguration);
				result.setResult(rtaConfiguration);
			} else if (value.equals("RTAAdapterCategoryConfiguration")) {
				String defaultCategoryId = request.getParameter("defaultCategoryId");
				String minDownstreamAllowed = request.getParameter("minDownstreamAllowed");
				String minDurationAllowed = request.getParameter("minDurationAllowed");
				String[] services = request.getParameterValues("services[]");
				String[] categories = request.getParameterValues("categories[]");
				String qualityConditions = request.getParameter("qualityConditions");
				RTAConfiguration rtaConfiguration = napFrontendService.saveRTAAdapterCategoryConfiguration(key, enabled, id, granularity, allSources, defaultCategoryId, minDownstreamAllowed, minDurationAllowed, services, categories, qualityConditions);
				result.setResult(rtaConfiguration);
			} else if (value.equals("RTAAdapterURLCategoryConsumptionConfiguration")) {
				String[] allowedPackageIds = request.getParameterValues("allowedPackageIds[]");
				String[] counters = request.getParameterValues("counters[]");
				RTAConfiguration rtaConfiguration = napFrontendService.saveRTAAdapterURLCategoryConsumptionConfiguration(key, enabled, id, granularity, allSources, allowedPackageIds, counters);
				result.setResult(rtaConfiguration);
			} else if (value.equals("RTAAdapterURLHitsConfiguration")) {
				String[] allServices = request.getParameterValues("allServices[]");
				String groupsURLHits = request.getParameter("groupsURLHits");
				RTAConfiguration rtaConfiguration = napFrontendService.saveRTAAdapterURLHitsConfiguration(key, enabled, id, granularity, allSources, allServices, groupsURLHits);
				result.setResult(rtaConfiguration);
			} else if (value.equals("RTAAdapterFlavorUpdaterConfiguration")) {
				String sepIpAddress = request.getParameter("sepIpAddress");
				String sepPort = request.getParameter("sepPort");
				String defaultFlavorId = request.getParameter("defaultFlavorId");
				String newFlavorsCountToApplyInSep = request.getParameter("newFlavorsCountToApplyInSep");
				RTAConfiguration rtaConfiguration = napFrontendService.saveRTAAdapterFlavorUpdaterConfiguration(key, enabled, id, granularity, allSources, sepIpAddress, sepPort, defaultFlavorId, newFlavorsCountToApplyInSep);
				result.setResult(rtaConfiguration);
			} else {
				RTAConfiguration rtaConfiguration = napFrontendService.saveRTAAdapterGeneralConfig(key, value, enabled, id, granularity, allSources);
				result.setResult(rtaConfiguration);
			}
		} catch (NumberFormatException ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} catch (HttpHostConnectException ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} finally {
			String json = new Gson().toJson(result);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}

	@RequestMapping(value = { "/ajax/deleteRTAAdapters" })
	public void deleteRTAAdapters(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxResult result = new AjaxResult();
		try {
			String[] keys = request.getParameterValues("keys[]");
			RTAConfiguration rtaRecordConfiguration = napFrontendService.deleteRTAAdapter(keys);
			result.setResult(rtaRecordConfiguration);
			userAuditService.audit(getUser(request), getIp(request), ActionType.NAP_DELETE, request);
		} catch (Exception ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} finally {
			String json = new Gson().toJson(result);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}

	@RequestMapping(value = { "/ajax/saveRTAAdapterGeneralConfig" })
	public void saveRTAAdapterGeneralConfig(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxResult result = new AjaxResult();
		try {
			String keymap = request.getParameter("keymap");
			String value = request.getParameter("value");
			Boolean enabled = Boolean.valueOf(request.getParameter("enabled"));
			String id = request.getParameter("id");
			String granularity = request.getParameter("granularity");
			String[] allSources = request.getParameterValues("allSources[]");

			RTAConfiguration rtaConfiguration = napFrontendService.saveRTAAdapterGeneralConfig(keymap, value, enabled, id, granularity, allSources);
			result.setResult(rtaConfiguration);
		} catch (NumberFormatException ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} catch (HttpHostConnectException ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} finally {
			String json = new Gson().toJson(result);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}

	@RequestMapping(value = { "/ajax/getRTAConfiguration" })
	public void getRTAConfiguration(HttpServletRequest request, HttpServletResponse response) throws IOException {
		getRestClient().executeRestGetMethod("/getNapConfiguration", request, response);
	}

	@RequestMapping(value = { "/ajax/newPeriodic" })
	public void newPeriodic(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxResult result = new AjaxResult();
		try {
			String valueType = request.getParameter("valueType");
			PeriodicConfiguration periodicConfiguration = napFrontendService.createPeriodic(valueType);
			result.setResult(periodicConfiguration);
		} catch (Exception ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} finally {
			String json = new Gson().toJson(result);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}

	@RequestMapping(value = { "/ajax/deletePeriodic" })
	public void deletePeriodic(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxResult result = new AjaxResult();
		try {
			String index = request.getParameter("index");
			PeriodicConfiguration periodicConfiguration = napFrontendService.deletePeriodicConfiguration(index);
			result.setResult(periodicConfiguration);
			userAuditService.audit(getUser(request), getIp(request), ActionType.NAP_DELETE, request);
		} catch (Exception ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} finally {
			String json = new Gson().toJson(result);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}

	@RequestMapping(value = { "/ajax/savePeriodicDataRecordTasks" })
	public void savePeriodicDataRecordTasks(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxResult result = new AjaxResult();
		try {
			String index = request.getParameter("index");
			String type = request.getParameter("type");
			Boolean enabled = Boolean.valueOf(request.getParameter("enabled"));
			String cronPattern = request.getParameter("cronPattern");
			String[] recordTypes = request.getParameterValues("recordTypes[]");
			String days = request.getParameter("days");
			PeriodicConfiguration periodicConfiguration = napFrontendService.savePeriodicDataRecordTasks(index, type, enabled, cronPattern, recordTypes, days);
			result.setResult(periodicConfiguration);
		} catch (NumberFormatException ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} catch (HttpHostConnectException ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} finally {
			String json = new Gson().toJson(result);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}

	@RequestMapping(value = { "/ajax/getDashboardCN" })
	public void getDashboardCN(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TableResult result = new TableResult();
		try {

			if (request.getMethod().equals("GET")) {
				ArrayList<Site> coso = DashboardDataManager.getAll();
				result.setAaData(coso);
			}

			if (request.getMethod().equals("POST")) {
				Map<String, String[]> params = request.getParameterMap();
				// websocketServerSession.propagateMessage();
			}

		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			String json = new Gson().toJson(result);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}

	@RequestMapping(value = { "/ajax/retrievePermissionsTemplateByName" })
	public void retrievePermissionsTemplateByName(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxResult result = new AjaxResult();
		try {
			String name = request.getParameter("name");
			Group group = userControlService.getGroupByName(name);
			result.setResult(group.getTemplateIds());
		} catch (Exception ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} finally {
			String json = new Gson().toJson(result);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}

	@RequestMapping(value = { "/ajax/getReportTemplates" })
	public void getReportTemplates(HttpServletRequest request, HttpServletResponse response) throws IOException {
		getRestClient().executeRestGetMethod("/getReportTemplates", request, response);
	}

	//////////////// COSAS GENERICAS

	public static String getIp(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}

	private String manageTemplates(HttpServletRequest request, HttpServletResponse response, String responseJSON) {
		List<Integer> allowedTemplateIds = userControlService.getTemplate_idsByUsername(getUser(request));

		JsonElement jsonOrigin = new JsonParser().parse(responseJSON);
		JsonArray templateGroupsOrigin = jsonOrigin.getAsJsonArray();
		JsonArray templatesOrigin = null;

		JsonArray jsonResult = new JsonArray();
		JsonObject tgResult = null;

		Integer templateId = null;
		for (JsonElement tgOrigin : templateGroupsOrigin) {
			tgResult = null;
			templatesOrigin = tgOrigin.getAsJsonObject().getAsJsonArray("templateInfos");
			for (JsonElement tOrigin : templatesOrigin) {
				templateId = tOrigin.getAsJsonObject().get("id").getAsInt();
				// TODO ESTO LO COMENTE HASTA QUE ESTEN LOS TEMPLATEIDS DEL UC EN LA DB
				// for (Integer allowedTemplateIds1 : allowedTemplateIds) {
				// if (allowedTemplateIds1.equals(templateId)) {
				// Se asegura que el template group exista en el json result
				if (tgResult == null) {
					tgResult = new JsonObject();
					tgResult.add("name", tgOrigin.getAsJsonObject().get("name"));
					tgResult.add("templateDomain", tgOrigin.getAsJsonObject().get("templateDomain"));
					tgResult.add("templateInfos", new JsonArray());
					jsonResult.add(tgResult);
				}
				// Al tg del result agregarle el tOrigin
				tgResult.get("templateInfos").getAsJsonArray().add(tOrigin);
				// break;
				// }
				// }
			}
		}
		return jsonResult.toString();
	}

	public static String getUser(HttpServletRequest request) {
		return request.getUserPrincipal().getName();
	}

	public static boolean isNumeric(String str) {
		return (str.matches("[+-]?\\d*(\\.\\d+)?") && str.equals("") == false);
	}

}
