package com.callistech.analytics.frontend.controllers;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TimeZone;
import java.util.jar.Manifest;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.callistech.analytics.frontend.domains.uc.util.Permission;
import com.callistech.analytics.frontend.domains.uc.util.PermissionType;
import com.callistech.analytics.frontend.services.SettingsService;
import com.callistech.analytics.frontend.services.UserControlService;
import com.callistech.analytics.frontend.util.ActionType;
import com.callistech.nap.bean.DetailRecordType;

@Controller
public class HomeController {

	private String swVersion = "N/A";
	@Autowired
	private UserControlService userControlService;

	@Autowired
	private SettingsService settingsService;

	@PostConstruct
	public void init() {
		try {
			// Leer manifest
			this.swVersion = getVersion();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@RequestMapping(value = "/")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		setAttributes(request, response, model);
		return "index";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";// You can redirect wherever you want, but generally it's a good practice to show login screen again.
	}

	@RequestMapping(value = "/networkNavigator", method = RequestMethod.GET)
	public String networkNavigator(HttpServletRequest request, HttpServletResponse response, Model model) {
		setAttributes(request, response, model);
		return "networkNavigator";
	}

	@RequestMapping(value = "/systemStatistics", method = RequestMethod.GET)
	public String systemStatistics(HttpServletRequest request, HttpServletResponse response, Model model) {
		setAttributes(request, response, model);
		return "systemStatistics";
	}

	@RequestMapping(value = "/qoe/htur", method = RequestMethod.GET)
	public String qoeHTUR(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("viewType", "htur");
		setAttributes(request, response, model);
		return "qoe";
	}

	@RequestMapping(value = "/qoe/sur", method = RequestMethod.GET)
	public String qoeSUR(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("viewType", "sur");
		setAttributes(request, response, model);
		return "qoe";
	}

	@RequestMapping(value = "/cdrs", method = RequestMethod.GET)
	public String cdrs(HttpServletRequest request, HttpServletResponse response, Model model) {
		setAttributes(request, response, model);
		return "cdrs";
	}

	@RequestMapping(value = "/qoe/vtur", method = RequestMethod.GET)
	public String qoeVTUR(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("viewType", "vtur");
		setAttributes(request, response, model);
		return "qoe";
	}

	@RequestMapping(value = "/reporter", method = RequestMethod.GET)
	public String reporter(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam String domain) {
		model.addAttribute("domain", domain);
		boolean result = checkAccessPermissionSubdomains(request, response, model);
		if (result) {
			setAttributes(request, response, model);
			return "reporter";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(HttpServletRequest request, HttpServletResponse response, Model model) {
		setAttributes(request, response, model);
		return "dashboard";
	}

	@RequestMapping(value = "/dashVOD", method = RequestMethod.GET)
	public String dashVOD(HttpServletRequest request, HttpServletResponse response, Model model) {
		setAttributes(request, response, model);
		return "dashVOD";
	}

	@RequestMapping(value = "/NAPsettings", method = RequestMethod.GET)
	public String napSettings(HttpServletRequest request, HttpServletResponse response, Model model) {
		boolean result = checkAccessPermission(request, response, model);
		if (result) {
			setAttributes(request, response, model);
			String[] names = new String[DetailRecordType.values().length];
			int i = 0;
			for (DetailRecordType type : DetailRecordType.values()) {
				names[i] = type.name();
				i++;
			}
			model.addAttribute("DetailRecordTypes", names);
			return "NAPsettings";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public String settings(HttpServletRequest request, HttpServletResponse response, Model model) {
		loadSettings(request, response, model);
		return "settings";
	}

	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String about(HttpServletRequest request, HttpServletResponse response, Model model) {
		setAttributes(request, response, model);
		return "about";
	}

	@RequestMapping(value = "/userControl", method = RequestMethod.GET)
	public String userControl(HttpServletRequest request, HttpServletResponse response, Model model) {
		boolean result = checkAccessPermission(request, response, model);
		if (result) {
			setAttributes(request, response, model);
			model.addAttribute("permissionTypes", getPermissionTypesAsString());
			return "userControl";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/userAudit", method = RequestMethod.GET)
	public String userAudit(HttpServletRequest request, HttpServletResponse response, Model model) {
		boolean result = checkAccessPermission(request, response, model);
		if (result) {
			setAttributes(request, response, model);
			model.addAttribute("Actions", ActionType.values());
			model.addAttribute("USER_AUDIT", PermissionType.USER_AUDIT.getId());
			return "userAudit";
		} else {
			return "redirect:/";
		}
	}

	// COSAS INTERNAS

	private List<String> getPermissionTypesAsString() {
		List<String> permissionTypes = new ArrayList<String>();
		for (PermissionType p : PermissionType.values()) {
			permissionTypes.add(p.getName());
		}
		return permissionTypes;
	}

	public String getVersion() throws IOException {
		String impVersion = "N/A";
		String impBuild = "N/A";
		URLClassLoader cl = (URLClassLoader) HomeController.class.getClassLoader();
		try {
			URL url = cl.findResource("META-INF/MANIFEST.MF");
			URL u = null;
			Enumeration<URL> enumURLs = cl.findResources("META-INF/MANIFEST.MF");
			while (enumURLs.hasMoreElements()) {
				u = enumURLs.nextElement();
				if ((u.toString().contains("react-spring")) && (!u.toString().contains("GUI")) && (u.toString().contains("jar"))) {
					url = u;
					break;
				}
			}
			Manifest manifest = new Manifest(url.openStream());
			if (manifest.getMainAttributes().getValue("Implementation-Version") != null) {
				impVersion = manifest.getMainAttributes().getValue("Implementation-Version");
				impVersion = impVersion.replaceAll("-SNAPSHOT", "");
			}
			if (manifest.getMainAttributes().getValue("Implementation-Build") != null) {
				impBuild = manifest.getMainAttributes().getValue("Implementation-Build");
			}
		} catch (Exception e) {
		}
		String version = impVersion + "  (Build " + impBuild + ")";
		return version;
	}

	private void loadSettings(HttpServletRequest request, HttpServletResponse response, Model model) {
		boolean result = checkAccessPermission(request, response, model);
		if (result) {
			model.addAttribute(SettingsService.IP, settingsService.getPropValue(SettingsService.IP));
			model.addAttribute(SettingsService.PORT, settingsService.getPropValue(SettingsService.PORT));
			model.addAttribute(SettingsService.TIME_ZONE, settingsService.getPropValue(SettingsService.TIME_ZONE));
			model.addAttribute(SettingsService.TIMEOUT, settingsService.getPropValue(SettingsService.TIMEOUT));
			model.addAttribute("TimeZoneIds", TimeZone.getAvailableIDs());
			setAttributes(request, response, model);
		}
	}

	private boolean checkAccessPermission(HttpServletRequest request, HttpServletResponse response, Model model) {
		String username = request.getUserPrincipal().getName();
		List<Permission> permissions = userControlService.getPermissionsByUsername(username);
		boolean result = false;
		for (Permission permission : permissions) {
			if (request.getServletPath().equals("/networkNavigator")) {
				if (permission.getPermission() == 2 && permission.getModule() == PermissionType.NETWORK_NAVIGATOR.getId()) {
					result = true;
					break;
				}
			} else if (request.getServletPath().equals("/systemStatistics")) {
				if (permission.getPermission() == 2 && permission.getModule() == PermissionType.SYSTEM_STATISTICS.getId()) {
					result = true;
					break;
				}
			} else if (request.getServletPath().equals("/qoe/htur")) {
				if (permission.getPermission() == 2 && permission.getModule() == PermissionType.HTTP_QOE_TRANSACTION_ANALYSIS.getId()) {
					result = true;
					break;
				}
			} else if (request.getServletPath().equals("/qoe/sur")) {
				if (permission.getPermission() == 2 && permission.getModule() == PermissionType.SUBSCRIBER_QOE_USAGE_ANALYSIS.getId()) {
					result = true;
					break;
				}
			} else if (request.getServletPath().equals("/qoe/vtur")) {
				if (permission.getPermission() == 2 && permission.getModule() == PermissionType.VIDEO_QOE_TRANSACTION_ANALYSIS.getId()) {
					result = true;
					break;
				}
			} else if (request.getServletPath().equals("/dashboard")) {
				if (permission.getPermission() == 2 && permission.getModule() == PermissionType.DASHBOARD.getId()) {
					result = true;
					break;
				}
			} else if (request.getServletPath().equals("/userControl")) {
				if (permission.getPermission() == 2 && permission.getModule() == PermissionType.ACCESS_CONTROL.getId()) {
					result = true;
					break;
				}
			} else if (request.getServletPath().equals("/userAudit")) {
				if (permission.getPermission() == 2 && permission.getModule() == PermissionType.USER_AUDIT.getId()) {
					result = true;
					break;
				}
			} else if (request.getServletPath().equals("/NAPsettings")) {
				if (permission.getPermission() == 2 && permission.getModule() == PermissionType.NAP_SETTINGS.getId()) {
					result = true;
					break;
				}
			} else if (request.getServletPath().equals("/settings")) {
				if (permission.getPermission() == 2 && permission.getModule() == PermissionType.Settings.getId()) {
					result = true;
					break;
				}
			} else if (request.getServletPath().equals("/dashVOD")) {
				if (permission.getPermission() == 2 && permission.getModule() == PermissionType.VOD_DASHBOARD.getId()) {
					result = true;
					break;
				}
			}

		}
		return result;
	}

	private boolean checkAccessPermissionSubdomains(HttpServletRequest request, HttpServletResponse response, Model model) {
		String username = request.getUserPrincipal().getName();
		List<Permission> permissions = userControlService.getPermissionsByUsername(username);
		boolean result = false;
		for (Permission permission : permissions) {
			if (request.getParameter("domain").equals("Dynamic_Services")) {
				if (permission.getPermission() == 2 && permission.getModule() == PermissionType.DYNAMIC_SERVICES.getId()) {
					result = true;
					break;
				}
			} else if (request.getParameter("domain").equals("InBrowser_Notification")) {
				if (permission.getPermission() == 2 && permission.getModule() == PermissionType.IN_BROWSER_NOTIFICATION.getId()) {
					result = true;
					break;
				}
			} else if (request.getParameter("domain").equals("IPDR_Monitoring")) {
				if (permission.getPermission() == 2 && permission.getModule() == PermissionType.IPDR_MONITORING.getId()) {
					result = true;
					break;
				}
			} else if (request.getParameter("domain").equals("Subscriber_Monitoring")) {
				if (permission.getPermission() == 2 && permission.getModule() == PermissionType.SUBSCRIBER_MONITORING.getId()) {
					result = true;
					break;
				}
			} else if (request.getParameter("domain").equals("Traffic_Congestion")) {
				if (permission.getPermission() == 2 && permission.getModule() == PermissionType.TRAFFIC_CONGESTION.getId()) {
					result = true;
					break;
				}
			} else if (request.getParameter("domain").equals("Traffic_Monitoring")) {
				if (permission.getPermission() == 2 && permission.getModule() == PermissionType.TRAFFIC_MONITORING.getId()) {
					result = true;
					break;
				}
			} else if (request.getParameter("domain").equals("URL_Analysis")) {
				if (permission.getPermission() == 2 && permission.getModule() == PermissionType.URL_ANALYSIS.getId()) {
					result = true;
					break;
				}
			} else if (request.getParameter("domain").equals("Video_Monitoring")) {
				if (permission.getPermission() == 2 && permission.getModule() == PermissionType.VIDEO_MONITORING.getId()) {
					result = true;
					break;
				}
			} else if (request.getParameter("domain").equals("CAM")) {
				if (permission.getPermission() == 2 && permission.getModule() == PermissionType.CAM.getId()) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	private void setAttributes(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("swVersion", swVersion);
		String username = request.getUserPrincipal().getName();
		List<Permission> permissions = userControlService.getPermissionsByUsername(username);
		HttpSession session = request.getSession();
		if (settingsService.getPropValue(settingsService.TIMEOUT) != null) {
			session.setMaxInactiveInterval(Integer.valueOf(settingsService.getPropValue(settingsService.TIMEOUT)) * 60);
		} else {
			settingsService.setPropValues(settingsService.TIMEOUT, "30");
			session.setMaxInactiveInterval(30 * 60);
		}
		model.addAttribute("permissions", permissions);
		model.addAttribute(settingsService.TIME_ZONE, settingsService.getPropValue(settingsService.TIME_ZONE));
		if (settingsService.getPropValue(settingsService.TIME_ZONE) != null) {
			model.addAttribute(settingsService.TIME_ZONE_OFFSET, TimeZone.getTimeZone(settingsService.getPropValue(settingsService.TIME_ZONE)).getRawOffset());
		}
	}

}