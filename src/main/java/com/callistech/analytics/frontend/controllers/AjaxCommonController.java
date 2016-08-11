package com.callistech.analytics.frontend.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.callistech.analytics.frontend.domains.tacacs.entity.TacacsServer;
import com.callistech.analytics.frontend.domains.tacacs.entity.TacacsServerAuthType;
import com.callistech.analytics.frontend.domains.ua.entities.UserAudit;
import com.callistech.analytics.frontend.services.NapFrontendService;
import com.callistech.analytics.frontend.services.SettingsService;
import com.callistech.analytics.frontend.services.TACACsService;
import com.callistech.analytics.frontend.services.UserAuditService;
import com.callistech.analytics.frontend.services.UserControlService;
import com.callistech.analytics.frontend.util.ActionType;
import com.callistech.analytics.frontend.util.AjaxResult;
import com.callistech.analytics.frontend.util.TableResult;
import com.callistech.commons.util.IPAddressUtils;
import com.google.gson.Gson;

@Controller
public class AjaxCommonController {

	@Autowired
	private UserControlService userControlService;

	@Autowired
	private SettingsService settingsService;

	@Autowired
	private UserAuditService userAuditService;

	@Autowired
	private NapFrontendService napFrontendService;

	@Autowired
	private TACACsService tacacsService;

	@RequestMapping(value = "/ajax/retrieveAudits")
	public void retrieveAudits(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TableResult result = new TableResult();
		if (request.getParameter("advancedSearchFilter") != null) {
			retrieveAuditsAdvanced(request, response);
		} else {
			try {
				int page = Integer.valueOf(request.getParameter("start"));
				int length = Integer.valueOf(request.getParameter("length"));
				List<UserAudit> users = userAuditService.getAuditsPaginated(page, length);
				result.setAaData(users);
				result.setRecordsTotal((int) userAuditService.getAllUserAuditCount());
				result.setRecordsFiltered((int) userAuditService.getAllUserAuditCount());

			} finally {
				String json = new Gson().toJson(result);
				response.setContentType("application/json");
				response.getWriter().write(json);
			}
		}
	}

	public void clearAudits(HttpServletRequest request, HttpServletResponse response) {
		userAuditService.clearAudits();
		userAuditService.audit(getUser(request), getIp(request), ActionType.USER_CLEAR_AUDIT);
	}

	public static String getIp(HttpServletRequest request) {
		// String ipClient = request.getHeader("X-FORWARDED-FOR");
		// if (ipClient == null) {
		// ipClient = request.getRemoteAddr();
		// }
		// return ipClient;
		return request.getRemoteAddr();
	}

	public static String getUser(HttpServletRequest request) {
		return request.getUserPrincipal().getName();
	}

	public void retrieveAuditsAdvanced(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TableResult result = new TableResult();
		try {
			String advancedSearchFilter = String.valueOf(request.getParameter("advancedSearchFilter"));
			int page = Integer.valueOf(request.getParameter("start"));
			int length = Integer.valueOf(request.getParameter("length"));

			List<UserAudit> users = userAuditService.getAuditsPaginatedAdvancedSearch(page, length, advancedSearchFilter);
			result.setAaData(users);
			result.setRecordsTotal(users.size());
			result.setRecordsFiltered(userAuditService.findPaginatedAdvancedSearchCount(page, length, advancedSearchFilter).size());

		} finally {
			String json = new Gson().toJson(result);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}

	public void validateACLIP(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxResult result = new AjaxResult();
		try {
			String ip = request.getParameter("ip");
			boolean validIP = false;

			try {
				validIP = IPAddressUtils.isIPAddressValid(ip, true);
			} catch (Exception ex) {

			}

			result.setResult(validIP);
		} catch (Exception ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} finally {
			String json = new Gson().toJson(result);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}

	}

	public void newTACACSServer(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String ip = request.getParameter("ip");
		String port = request.getParameter("port");
		String secret = request.getParameter("secret");

		tacacsService.createNewServer(name, ip, port, secret);
	}

	public void retrieveTACACSServer(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxResult result = new AjaxResult();
		try {
			Collection<TacacsServer> servers = tacacsService.getTACACsServers();
			result.setResult(servers);
		} catch (Exception ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} finally {
			String json = new Gson().toJson(result);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}

	public void retrieveTACACSServerAuthType(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxResult result = new AjaxResult();
		try {
			TacacsServerAuthType tacacsAuth = tacacsService.getTACACsAuthType();
			result.setResult(tacacsAuth);
		} catch (Exception ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} finally {
			String json = new Gson().toJson(result);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}

	public void retrieveTACACSServerById(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AjaxResult result = new AjaxResult();
		try {
			String id = request.getParameter("id");
			TacacsServer server = tacacsService.getTACACsServerById(id);
			result.setResult(server);
		} catch (Exception ex) {
			result.setErrorCode(1);
			result.setMessage(ex.getMessage());
		} finally {
			String json = new Gson().toJson(result);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
	}

	public void removeTACACSServer(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		tacacsService.removeServer(id);
	}

	public void setAuthTypeTACACS(HttpServletRequest request, HttpServletResponse response) {
		int id = 1;
		String authtype = request.getParameter("authtype");
		boolean fallbackAuth = Boolean.valueOf(request.getParameter("fallbackAuth"));
		tacacsService.setAuthTypeTACACS(authtype, fallbackAuth);
	}
}
