package com.callistech.analytics.frontend.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.callistech.admanager.bean.Ad;
import com.callistech.admanager.bean.AdGroup;
import com.callistech.admanager.bean.StopNotificationVO;
import com.callistech.analytics.frontend.domains.ua.entities.UserAudit;
import com.callistech.analytics.frontend.domains.ua.repository.UserAuditRepository;
import com.callistech.analytics.frontend.util.ActionType;
import com.google.common.collect.Lists;

@Component
public class UserAuditService {

	@Autowired
	private UserAuditRepository userAuditRepository;

	public List<UserAudit> getAllUserAudit() {
		return userAuditRepository.findAll();
	}

	public List<UserAudit> getAllUserAuditDND() {
		return userAuditRepository.findAllDND();
	}

	public long getAllUserAuditCount() {
		return userAuditRepository.count();
	}

	public long getAllUserAuditDNDCount() {
		return userAuditRepository.countAllDND();
	}

	private void createAudit(String username, String userIp, String action, String parameters) {
		UserAudit audit = new UserAudit();
		Date date = new Date();

		if (parameters == null) {
			parameters = "";
		}

		audit.setUsername(username);
		audit.setIp(userIp);
		audit.setTimestamp(date);
		audit.setAction(action);
		audit.setParameters(parameters);

		userAuditRepository.save(audit);
	}

	public void audit(String username, String userIp, ActionType action, Object... parameters) {
		try {
			String parametersInString = parseParameters(action, parameters);
			createAudit(username, userIp, action.getName(), parametersInString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private String parseParameters(ActionType action, Object[] parameters) {
		StringBuilder sb;
		switch (action) {
		case CREATE_SUBSCRIBER:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SubscriberId=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("PolicyId=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SubPortDown=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SubPortUp=").append(parameters[3]);
			}
			if (parameters[5] != null) {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Custom Property Keys=").append(Arrays.toString((String[]) parameters[5]));
			}
			if (parameters[6] != null) {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Custom Property Values=").append(Arrays.toString((String[]) parameters[6]));
			}
			return sb.toString();
		case EDIT_SUBSCRIBER:
			sb = new StringBuilder();

			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SubscriberId=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("PolicyId=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SubPortDown=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SubPortUp=").append(parameters[3]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Custom Property Keys=").append(Arrays.toString((String[]) parameters[5]));
			}
			if (parameters[6] != null) {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Custom Property Values=").append(Arrays.toString((String[]) parameters[6]));
			}
			return sb.toString();
		case REMOVE_SUBSCRIBER:
			sb = new StringBuilder();
			if (parameters[0] != null) {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SubscriberId=").append(parameters[0]);
			}
			return sb.toString();
		case USER_CLEAR_AUDIT:
			sb = new StringBuilder();
			sb.append("Logs Cleared!");
			return sb.toString();
		case USER_LOGIN:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("");
			}
			return sb.toString();
		case USER_LOGOUT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("");
			}
			return sb.toString();
		case USER_IMPORT:
			sb = new StringBuilder();
			sb.append("Config=").append(parameters[0]).append(", ");
			return sb.toString();
		case USER_EXPORT:
			sb = new StringBuilder();
			sb.append("Config=").append(parameters[0]).append(", ");
		case CREATE_USER:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Username=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Profile=").append(parameters[1]);
			}
			return sb.toString();
		case REMOVE_USER:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Username=").append(parameters[0]);
			}
			return sb.toString();
		case EDIT_USER:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Username=").append(parameters[0]);
			}
			return sb.toString();
		case EDIT_USER_PROFILE:
			sb = new StringBuilder();
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Username Current=").append(parameters[1]);
			}
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Username New=").append(parameters[0]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Description=").append(parameters[2]);
			}
			return sb.toString();
		case CREATE_NEW_USER_PROFILE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Username=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Description=").append(parameters[1]);
			}
			return sb.toString();
		case REMOVE_USER_PROFILE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Username=").append(parameters[0]);
			}
			return sb.toString();
		case CREATE_AD_CAMPAIGN:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ad Campaign Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ad Campaign Name=").append(parameters[1]);
			}
			return sb.toString();
		case SAVE_AD_CAMPAIGN:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ad Campaign Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ad Campaign Name=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ad Campaign Audiences=").append(Arrays.toString((String[]) parameters[2]));
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ad Groups Ids=").append(Arrays.toString((String[]) parameters[3]));
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ad Campaign Status=").append(parameters[4]);
			}
			if (parameters[5] != null) {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ad Campaing Duration Start=").append(parameters[5]);
			}
			if (parameters[6] != null) {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ad Campaing Duration Status=").append(parameters[6]);
			}
			if (parameters[7] != null) {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ad Campaing Duration End=").append(parameters[7]);
			}
			if (parameters[8] != null) {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ad Campaing Frecuency=").append(parameters[8]);
			}
			if (parameters[11] != null) {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ad Campaing Sleep Time=").append(parameters[11]);
			}
			return sb.toString();
		case DELETE_AD_CAMPAIGN:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ad Campaign Id=").append(parameters[0]);
			}
			return sb.toString();
		case CREATE_AD_VIEW:
			sb = new StringBuilder();
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Browser=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Device=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Device Width=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Device Height=").append(parameters[4]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("OS=").append(parameters[5]);
			}
			if (parameters[6] != null && parameters[6] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Type=").append(parameters[6]);
			}
			if (parameters[7] != null && parameters[7] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("imgURL=").append(parameters[7]);
			}
			if (parameters[8] != null && parameters[8] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Height=").append(parameters[8]);
			}
			if (parameters[9] != null && parameters[9] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Width=").append(parameters[9]);
			}
			if (parameters[10] != null && parameters[10] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Link=").append(parameters[10]);
			}
			if (parameters[11] != null && parameters[11] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Top=").append(parameters[11]);
			}
			if (parameters[12] != null && parameters[12] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Left=").append(parameters[12]);
			}
			if (parameters[13] != null && parameters[13] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auto Close=").append(parameters[13]);
			}
			if (parameters[14] != null && parameters[14] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Cycle=").append(parameters[14]);
			}
			if (parameters[15] != null && parameters[15] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Sleep=").append(parameters[15]);
			}
			if (parameters[16] != null && parameters[16] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("divCustomized=").append(parameters[16]);
			}
			if (parameters[17] != null && parameters[17] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("srcurl=").append(parameters[17]);
			}
			if (parameters[18] != null && parameters[18] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Position=").append(parameters[18]);
			}
			if (parameters[19] != null && parameters[19] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Remove Scroll Body=").append(parameters[19]);
			}
			if (parameters[20] != null && parameters[20] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Background Black=").append(parameters[20]);
			}
			return sb.toString();
		case EDIT_AD_VIEW:
			sb = new StringBuilder();
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Browser=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Device=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Device Width=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Device Height=").append(parameters[4]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("OS=").append(parameters[5]);
			}
			if (parameters[6] != null && parameters[6] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Type=").append(parameters[6]);
			}
			if (parameters[7] != null && parameters[7] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("imgURL=").append(parameters[7]);
			}
			if (parameters[8] != null && parameters[8] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Height=").append(parameters[8]);
			}
			if (parameters[9] != null && parameters[9] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Width=").append(parameters[9]);
			}
			if (parameters[10] != null && parameters[10] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Link=").append(parameters[10]);
			}
			if (parameters[11] != null && parameters[11] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Top=").append(parameters[11]);
			}
			if (parameters[12] != null && parameters[12] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Left=").append(parameters[12]);
			}
			if (parameters[13] != null && parameters[13] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auto Close=").append(parameters[13]);
			}
			if (parameters[14] != null && parameters[14] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Cycle=").append(parameters[14]);
			}
			if (parameters[15] != null && parameters[15] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Sleep=").append(parameters[15]);
			}
			if (parameters[16] != null && parameters[16] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("divCustomized=").append(parameters[16]);
			}
			if (parameters[17] != null && parameters[17] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("src url=").append(parameters[17]);
			}
			if (parameters[18] != null && parameters[18] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Position=").append(parameters[18]);
			}
			if (parameters[19] != null && parameters[19] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Remove Scroll Body=").append(parameters[19]);
			}
			if (parameters[20] != null && parameters[20] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Background Black=").append(parameters[20]);
			}
			return sb.toString();
		case DELETE_AD_VIEW:
			sb = new StringBuilder();
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Browser=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Device=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Device Width=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Device Height=").append(parameters[4]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("OS=").append(parameters[5]);
			}
			return sb.toString();
		case CREATE_AD:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ad Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ad Name=").append(parameters[1]);
			}
			return sb.toString();
		case DELETE_AD:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ad Id=").append(parameters[0]);
			}
			return sb.toString();
		case SAVE_AD:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ad Id=").append(((Ad) parameters[0]).getAdid());
				sb.append(" ,Ad Name=").append(((Ad) parameters[0]).getAdname());
			}
			return sb.toString();
		case DUPLICATE_AD:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ad Id=").append(((Ad) parameters[0]).getAdid());
				sb.append(" ,Ad Name=").append(((Ad) parameters[0]).getAdname());
			}
			return sb.toString();
		case CREATE_ADGROUP:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("AdGroup Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("AdGroup Name=").append(parameters[1]);
			}
			return sb.toString();
		case DELETE_ADGROUP:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("AdGroup Id=").append(parameters[0]);
			}
			return sb.toString();
		case SAVE_ADGROUP:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("AdGroup Id=").append(((AdGroup) parameters[0]).getAdgroupid());
				sb.append(" ,AdGroup Name=").append(((AdGroup) parameters[0]).getAdgroupname());
				sb.append(" ,AdGroup Ids=").append(Arrays.toString(((AdGroup) parameters[0]).getAdids()));
			}
			return sb.toString();
		case REMOVE_AD_COUNTER:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("AdGroup Name=").append(parameters[1]);
			}
			return sb.toString();
		case CREATE_STOP_NOTIFICATION:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Stop Notification Id=").append(parameters[0]);
			}
			return sb.toString();
		case DELETE_STOP_NOTIFICATION:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Stop Notification Id=").append(parameters[0]);
			}
			return sb.toString();
		case UPDATE_STOP_NOTIFICATION:
			sb = new StringBuilder();
			sb.append("Stop Notification Id=").append(((StopNotificationVO) parameters[0]).getAdstopnotificationid());
			sb.append(" ,Stop Notification Name=").append(((StopNotificationVO) parameters[0]).getAdstopnotificationname());
			sb.append(" ,Stop Notification Action=").append(((StopNotificationVO) parameters[0]).getAction());
			sb.append(" ,Stop Notification Match Counter=").append(((StopNotificationVO) parameters[0]).getMatch_counters());
			return sb.toString();
		case CREATE_CATEGORY:
			sb = new StringBuilder();
			sb.append("Category Name=").append(parameters[0]);
			return sb.toString();
		case EDIT_CATEGORY:
			sb = new StringBuilder();
			sb.append("Category Name=").append(parameters[0]);
			return sb.toString();
		case REMOVE_CATEGORY:
			sb = new StringBuilder();
			sb.append("Category Name=").append(parameters[0]);
			return sb.toString();
		case CREATE_CATEGORY_URL:
			sb = new StringBuilder();
			sb.append("Url=").append(parameters[0]);
			return sb.toString();
		case EDIT_CATEGORY_URL:
			sb = new StringBuilder();
			sb.append("Url=").append(parameters[0]);
			return sb.toString();
		case REMOVE_CATEGORY_URL:
			sb = new StringBuilder();
			sb.append("Url Id=").append(parameters[0]);
			return sb.toString();
		case CREATE_SERVICE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Name=").append(parameters[1]);
			}
			return sb.toString();
		case EDIT_SERVICE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Name=").append(parameters[1]);
			}
			return sb.toString();
		case CREATE_POLICY:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Policy Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Policy Name=").append(parameters[1]);
			}
			return sb.toString();
		case DUPLICATE_POLICY:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Policy Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Policy Name=").append(parameters[1]);
			}
			return sb.toString();

		case EDIT_POLICY:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Policy Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Policy Name=").append(parameters[1]);
			}
			return sb.toString();
		case EDIT_RULE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Rule Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Id=").append(parameters[1]);
			}
			return sb.toString();
		case CREATE_SERVICE_POLICY:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Policy Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Id=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Redirection=").append(parameters[2]);
			}
			return sb.toString();
		case CREATE_SERVICE_ITEM:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Category Id=").append(parameters[1]);
			}
			return sb.toString();
		case EDIT_CATEGORY_SEP:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Category=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Location=").append(parameters[2]);
			}
			return sb.toString();
		case EDIT_SERVICE_ELEMENT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Flavor=").append(parameters[1]);
			}
			return sb.toString();
		case CREATE_FLAVOR_ITEM:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Flavor Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Hostname=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Param Prefix=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Path Prefix=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Path Suffix=").append(parameters[4]);
			}
			return sb.toString();
		case EDIT_FLAVOR_ITEM:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Flavor Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Hostname=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Flavor Item Id=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Param Prefix=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Path Prefix=").append(parameters[4]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Path Suffix=").append(parameters[5]);
			}
			return sb.toString();
		case CREATE_FLAVOR:
			sb = new StringBuilder();
			// if (parameters[0] != null && parameters[0] != "") {
			// if (sb.length() != 0) {
			// sb.append(", ");
			// }
			// sb.append("Flavor Id=").append(parameters[0]);
			// }
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Flavor Name=").append(parameters[0]);
			}
			return sb.toString();
		case EDIT_FLAVOR:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Flavor Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Flavor Id=").append(parameters[1]);
			}
			return sb.toString();
		case CREATE_REDIRECT_PROFILE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Redirect Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Http Destination Id=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Response Code=").append(parameters[2]);
			}
			return sb.toString();
		case EDIT_REDIRECT_PROFILE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Redirect Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Http Destination Id=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Response Code=").append(parameters[2]);
			}
			return sb.toString();
		case CREATE_REDIRECT_PROFILE_ALLOWEDURL:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Redirect Profile Allowed URL Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("URL=").append(parameters[1]);
			}
			return sb.toString();
		case EDIT_REDIRECT_PROFILE_ALLOWEDURL:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Redirect Profile Allowed URL Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("URL=").append(parameters[1]);
			}
			return sb.toString();
		case REMOVE_SERVICE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Ids=").append(Arrays.toString((String[]) parameters[0]));
			}
			return sb.toString();
		case REMOVE_POLICY:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Policy Ids=").append(Arrays.toString((String[]) parameters[0]));
			}
			return sb.toString();
		case REMOVE_SERVICE_POLICY:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Policy Id=").append(parameters[0]);
			}
			return sb.toString();
		case REMOVE_SERVICE_ELEMENT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Id=").append(parameters[0]);
			}
			return sb.toString();
		case REMOVE_FLAVOR_ITEM:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Flavor Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Flavor Item Id=").append(parameters[1]);
			}
			return sb.toString();
		case REMOVE_FLAVOR:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Flavor Id=").append(parameters[0]);
			}
			return sb.toString();
		case REMOVE_REDIRECT_PROFILE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Flavor Ids=").append(Arrays.toString((String[]) parameters[0]));
			}
			return sb.toString();
		case REMOVE_REDIRECT_PROFILE_ALLOWEDURL:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Allowed URL Id=").append(parameters[0]);
			}
			return sb.toString();
		case CREATE_USER_AGENT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Items=").append(Arrays.toString((String[]) parameters[2]));
			}
			return sb.toString();
		case EDIT_USER_AGENT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[1]);
			}
			return sb.toString();
		case REMOVE_USER_AGENT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(Arrays.toString((String[]) parameters[0]));
			}
			return sb.toString();
		case CREATE_USER_AGENT_ITEM:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Item=").append(parameters[1]);
			}
			return sb.toString();
		case EDIT_USER_AGENT_ITEM:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Item Id=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Item=").append(parameters[2]);
			}
			return sb.toString();
		case REMOVE_USER_AGENT_ITEM:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Item Id=").append(parameters[1]);
			}
			return sb.toString();
		case CREATE_ZONE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[1]);
			}
			return sb.toString();
		case EDIT_ZONE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[1]);
			}
			return sb.toString();
		case REMOVE_ZONE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ids=").append(Arrays.toString((String[]) parameters[0]));
			}
			return sb.toString();
		case CREATE_ZONE_ITEM:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Item=").append(parameters[1]);
			}
			return sb.toString();
		case EDIT_ZONE_ITEM:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Item=").append(parameters[1]);
			}
			return sb.toString();
		case REMOVE_ZONE_ITEM:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Item Id=").append(parameters[1]);
			}
			return sb.toString();
		case CREATE_PROTOCOL:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[1]);
			}
			return sb.toString();
		case EDIT_PROTOCOL:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[1]);
			}
			return sb.toString();
		case REMOVE_PROTOCOL:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ids=").append(Arrays.toString((String[]) parameters[0]));
			}
			return sb.toString();
		case CREATE_PROTOCOL_ELEMENT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ip Protocol=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Port High=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Port Low=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Signature=").append(parameters[4]);
			}
			return sb.toString();
		case EDIT_PROTOCOL_ELEMENT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ip Protocol=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Port High=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Port Low=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Signature=").append(parameters[4]);
			}
			return sb.toString();
		case REMOVE_PROTOCOL_ELEMENT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Element Id=").append(parameters[1]);
			}
			return sb.toString();
		case CREATE_SUBPORT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ids =").append(Arrays.toString((String[]) parameters[1]));
			}
			return sb.toString();
		case DELETE_SUBPORT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ids =").append(Arrays.toString((String[]) parameters[1]));
			}
			return sb.toString();
		case EDIT_SUBPORT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Rate=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("TrafficClass0=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("TrafficClass1=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("TrafficClass2=").append(parameters[4]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("TrafficClass3=").append(parameters[5]);

			}
			return sb.toString();
		case EDIT_LINKPORT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Rate=").append(parameters[1]);
			}
			return sb.toString();
		case CREATE_RDR_FORMATTER_DESTINATION:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ip=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Port=").append(parameters[2]);
			}
			return sb.toString();
		case REMOVE_RDR_FORMATTER_DESTINATION:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ids=").append(Arrays.toString((String[]) parameters[0]));
			}
			return sb.toString();
		case EDIT_RDR_FORMATTER_DESTINATION:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ip=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Port=").append(parameters[3]);
			}
			return sb.toString();
		case CREATE_TAGS:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Tags=").append(Arrays.toString((String[]) parameters[1]));
			}
			return sb.toString();
		case REMOVE_TAGS:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Tags=").append(Arrays.toString((String[]) parameters[1]));
			}
			return sb.toString();
		case CREATE_ANONYMOUS_GROUP:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Network Id=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template Id=").append(parameters[3]);
			}
			return sb.toString();
		case CREATE_ANONYMOUS_TEMPLATE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Policy Id=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subport Downstream Id=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subport Upstream Id=").append(parameters[3]);
			}
			return sb.toString();
		case DELETE_ANONYMOUS_GROUP:
			sb = new StringBuilder();
			sb.append("Id=").append(parameters[0]);
			return sb.toString();
		case DELETE_ANONYMOUS_TEMPLATE:
			sb = new StringBuilder();
			sb.append("Id=").append(parameters[0]);
			return sb.toString();
		case EDIT_ANONYMOUS_GROUP:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Network Id=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template Id=").append(parameters[3]);
			}
			return sb.toString();
		case EDIT_ANONYMOUS_TEMPLATE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Policy Id=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subport Downstream Id=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subport Upstream Id=").append(parameters[3]);
			}
			return sb.toString();
		case ADD_DND_SUBSCRIBER:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Id=").append(parameters[0]);

			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("IP Address=").append(parameters[1]);

			}
			return sb.toString();
		case REMOVE_DND_SUBSCRIBER:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("IP Address=").append(parameters[1]);
			}
			return sb.toString();
		case EDIT_REDUNDANCY:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Enabled=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Redundancies=").append(parameters[2]);
			}
			return sb.toString();
		case EDIT_VIRTUAL_LINK:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Direction=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Redundant=").append(parameters[4]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("PIR=").append(parameters[5]);
			}
			return sb.toString();
		case ADD_VIRTUAL_LINK:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Direction=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("PIR=").append(parameters[4]);
			}
			return sb.toString();
		case VIRTUAL_LINK_DELETE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[1]);
			}
			return sb.toString();
		case IMPORT_VIRTUAL_LINKS:
			sb = new StringBuilder();
			sb.append("Virtual Links=").append(parameters[0].toString());
			return sb.toString();
		case EXPORT_VIRTUAL_LINKS:
			sb = new StringBuilder();
			sb.append("Virtual Links=").append(parameters[0].toString());
			return sb.toString();
		case SYNCHRONIZATION_VLINK_STANDARD_MODE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE User=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE Password=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE PasswordEn15=").append(parameters[3]);
			}
			return sb.toString();
		case SYNCHRONIZATION_VLINK_FORCE_MODE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE User=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE Password=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE PasswordEn15=").append(parameters[3]);
			}
			return sb.toString();
		case REMOVE_VLINK:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE IP=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE User=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE Password=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE PasswordEn15=").append(parameters[3]);
			}
			return sb.toString();
		case USER_IMPORT_DND:
			sb = new StringBuilder();
			sb.append("Imported successfully:").append(parameters[1]).append(" ");
			if (Integer.valueOf(parameters[0].toString()) > 0) {
				sb.append("Failed:").append(parameters[0]);
			}
			return sb.toString();
		case USER_EXPORT_DND:
			sb = new StringBuilder();
			sb.append("Config=").append(parameters[0]);
			return sb.toString();
		case UNDO_VIRTUAL_LINK:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Direction=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Redundant=").append(parameters[4]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("PIR=").append(parameters[5]);
			}
			return sb.toString();
		case SYNCHRONIZATION_VLINK:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE User=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE Password=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE PasswordEn15=").append(parameters[3]);
			}
			return sb.toString();
		case SYNCHRONIZATION_SCE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE User=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE Password=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SCE PasswordEn15=").append(parameters[3]);
			}
			return sb.toString();
		case NAP_CREATE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				HttpServletRequest req = (HttpServletRequest) parameters[0];

				String id = (req.getParameter("templateId"));
				sb.append("Template Id: ").append(id);
			}
			return sb.toString();
		case SAVE_RDR_SETTING:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				HttpServletRequest req = (HttpServletRequest) parameters[0];

				String id = (req.getParameter("templateId"));
				sb.append("Template Id: ").append(id);
			}
			return sb.toString();

		case UPDATEPERIODICQUOTAREFILLTASK:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append(parameters[0]);
			}
			return sb.toString();
		case UPDATEPERIODICSUBSCRIBERREMOVALTASK:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append(parameters[0]);
			}
			return sb.toString();
		case UPDATEPERIODICAUTOLOGINREMOVALTASK:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append(parameters[0]);
			}
			return sb.toString();
		case UPDATEPERIODICAPSESSIONSAGINGTASK:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append(parameters[0]);
			}
			return sb.toString();
		case UPDATEPERIODICCAMSESSIONSAGINGTASK:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append(parameters[0]);
			}
			return sb.toString();
		case UPDATEDEFAULTCVPLAN:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append(parameters[0]);
			}
			return sb.toString();
		case UPDATEDEFAULTNONCVPLAN:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append(parameters[0]);
			}
			return sb.toString();
		case UPDATEEMAIL:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Title=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Content=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Email From=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Email Server IP=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Email Server Port=").append(parameters[4]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Email User name=").append(parameters[5]);
			}
			if (parameters[6] != null && parameters[6] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Email Password=").append(parameters[6]);
			}
			if (parameters[7] != null && parameters[7] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Welcome Email Title=").append(parameters[7]);
			}
			if (parameters[8] != null && parameters[8] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Welcome Email Content=").append(parameters[8]);
			}
			if (parameters[9] != null && parameters[9] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Welcome Email From=").append(parameters[9]);
			}
			if (parameters[10] != null && parameters[10] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Notification Email Title=").append(parameters[10]);
			}
			if (parameters[11] != null && parameters[11] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Notification Email Content=").append(parameters[11]);
			}
			if (parameters[12] != null && parameters[12] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Notification Email From=").append(parameters[12]);
			}
			return sb.toString();
		case UPDATETEMPORARYACCOUNTCV:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Temporary account=").append(parameters[0]);
			}

			return sb.toString();
		case UPDATETEMPORARYACCOUNTNOCV:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Temporary account=").append(parameters[0]);
			}

			return sb.toString();
		case EXPORTCAMACCOUNTINGCSV:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Search=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Login Time Max=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Login Time Min=").append(parameters[2]);
			}
			return sb.toString();
		case EXPORTISGACCOUNTINGCSV:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Search=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Login Time Max=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Login Time Min=").append(parameters[2]);
			}
			return sb.toString();
		case EXPORTCMACCOUNTINGCSV:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Search=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Login Time Max=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Login Time Min=").append(parameters[2]);
			}
			return sb.toString();
		case EXPORTEXTERNALACCOUNTINGCSV:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Search=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Login Time Max=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Login Time Min=").append(parameters[2]);
			}
			return sb.toString();
		case EXPORTSESSIONSCSV:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Search=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Login Time Max=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Login Time Min=").append(parameters[2]);
			}
			return sb.toString();
		case REFRESHAPS:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Refresh APs result=").append(parameters[0]);
			}

			return sb.toString();
		case CREATESUBSCRIBERPROFILE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Profile Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("COA Plan Name=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("DSD=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Priority=").append(parameters[3]);
			}
			return sb.toString();
		case EDITSUBSCRIBERPROFILE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Profile Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("COA Plan Name=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("DSD=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Priority=").append(parameters[3]);
			}
			return sb.toString();
		case REMOVESUBSCRIBERPROFILE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Profile Id=").append(parameters[0]);
			}
			return sb.toString();
		case CREATENETWORKOPERATOR:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auth Type=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("External AAA=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Profile=").append(parameters[3]);
			}
			return sb.toString();
		case EDITNETWORKOPERATOR:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auth Type=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("External AAA=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Profile=").append(parameters[3]);
			}
			return sb.toString();
		case REMOVENETWORKOPERATOR:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			return sb.toString();
		case CREATEAAA:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("IP=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Secret=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Nas IP Address=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auth Process=").append(parameters[4]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auth Port=").append(parameters[5]);
			}
			if (parameters[6] != null && parameters[6] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auth Proxy To=").append(parameters[6]);
			}
			if (parameters[7] != null && parameters[7] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auth Method=").append(parameters[7]);
			}
			if (parameters[8] != null && parameters[8] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Acct Process=").append(parameters[8]);
			}
			if (parameters[9] != null && parameters[9] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Acct Port=").append(parameters[9]);
			}

			return sb.toString();
		case EDITAAA:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("IP=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Secret=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Nas IP Address=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auth Process=").append(parameters[4]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auth Port=").append(parameters[5]);
			}
			if (parameters[6] != null && parameters[6] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auth Proxy To=").append(parameters[6]);
			}
			if (parameters[7] != null && parameters[7] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auth Method=").append(parameters[7]);
			}
			if (parameters[8] != null && parameters[8] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Acct Process=").append(parameters[8]);
			}
			if (parameters[9] != null && parameters[9] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Acct Port=").append(parameters[9]);
			}

			return sb.toString();
		case REMOVEAAA:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			return sb.toString();
		case CREATEDOMAIN:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Network Operator=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Strip=").append(parameters[2]);
			}
			return sb.toString();
		case EDITDOMAIN:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Network Operator=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Strip=").append(parameters[2]);
			}
			return sb.toString();
		case REMOVEDOMAIN:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			return sb.toString();
		case CREATESUBSCRIBER:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Profile Name=").append(parameters[1]);
			}
			return sb.toString();
		case EDITSUBSCRIBER:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Profile Name=").append(parameters[1]);
			}
			return sb.toString();
		case REMOVESUBSCRIBER:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Id=").append(parameters[0]);
			}

			return sb.toString();
		case REMOVESUBACCOUNT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}

			return sb.toString();
		case CREATESUBACCOUNT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Parent Subscriber Id=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Description=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("User Name=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Password=").append(parameters[4]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Password Confirm=").append(parameters[5]);
			}
			if (parameters[6] != null && parameters[6] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Profile Name=").append(parameters[6]);
			}
			if (parameters[7] != null && parameters[7] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Shared Quota=").append(parameters[7]);
			}
			return sb.toString();
		case EDITSUBACCOUNT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Id=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Description=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("User Name=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Old Password=").append(parameters[4]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Password=").append(parameters[5]);
			}
			if (parameters[6] != null && parameters[6] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Password Confirm=").append(parameters[6]);
			}
			if (parameters[7] != null && parameters[7] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Profile Name=").append(parameters[7]);
			}
			if (parameters[8] != null && parameters[8] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Shared Quota=").append(parameters[8]);
			}
			return sb.toString();
		case CREATEPORTAL:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Description=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Id Format=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auto Login Limited Enabled=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auto Login Enabled=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auto Login Method=").append(parameters[4]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auto Login Valid Time=").append(parameters[5]);
			}
			if (parameters[6] != null && parameters[6] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Quota Recharge Enabled=").append(parameters[6]);
			}
			if (parameters[7] != null && parameters[7] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Users Limit Enabled=").append(parameters[7]);
			}
			if (parameters[8] != null && parameters[8] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Users Limit=").append(parameters[8]);
			}
			if (parameters[9] != null && parameters[9] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Simultaneous Sessions Limit Enabled=").append(parameters[9]);
			}
			if (parameters[10] != null && parameters[10] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Simultaneous Sessions Limit=").append(parameters[10]);
			}
			if (parameters[11] != null && parameters[11] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Facebook App Id=").append(parameters[11]);
			}
			if (parameters[12] != null && parameters[12] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Google App Client Id=").append(parameters[12]);
			}
			if (parameters[13] != null && parameters[13] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Google App Client Secret=").append(parameters[13]);
			}
			return sb.toString();
		case EDITPORTAL:
			sb = new StringBuilder();
			if (parameters[14] != null && parameters[14] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[14]);
			}
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Description=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Id Format=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auto Login Limited Enabled=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auto Login Enabled=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auto Login Method=").append(parameters[4]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auto Login Valid Time=").append(parameters[5]);
			}
			if (parameters[6] != null && parameters[6] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Quota Recharge Enabled=").append(parameters[6]);
			}
			if (parameters[7] != null && parameters[7] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Users Limit Enabled=").append(parameters[7]);
			}
			if (parameters[8] != null && parameters[8] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Users Limit=").append(parameters[8]);
			}
			if (parameters[9] != null && parameters[9] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Simultaneous Sessions Limit Enabled=").append(parameters[9]);
			}
			if (parameters[10] != null && parameters[10] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Simultaneous Sessions Limit=").append(parameters[10]);
			}
			if (parameters[11] != null && parameters[11] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Facebook App Id=").append(parameters[11]);
			}
			if (parameters[12] != null && parameters[12] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Google App Client Id=").append(parameters[12]);
			}
			if (parameters[13] != null && parameters[13] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Google App Client Secret=").append(parameters[13]);
			}
			return sb.toString();
		case REMOVEPORTAL:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			return sb.toString();
		case REMOVEPORTALFROMPORTALDESCRIPTION:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Description=").append(parameters[0]);
			}
			return sb.toString();
		case DUPLICATEPORTAL:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Description=").append(parameters[0]);
			}
			return sb.toString();
		case CREATEPORTALURL:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Description=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Description=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("URL=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("File Path=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Type=").append(parameters[4]);
			}
			return sb.toString();
		case EDITPORTALURL:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Description=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("URL=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("File Path=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Type=").append(parameters[4]);
			}
			return sb.toString();
		case REMOVEPORTALURL:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			return sb.toString();
		case CREATEPORTALLOGINTYPE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Description=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Login Type=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Enable=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auto login=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Profile Name=").append(parameters[4]);
			}
			return sb.toString();
		case EDITPORTALLOGINTYPE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Enable=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Auto login=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Profile Name=").append(parameters[3]);
			}
			return sb.toString();
		case REMOVEPORTALLOGINTYPE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			return sb.toString();
		case CREATEPORTALMAPPING:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Description=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("AP Group Name=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SS Id=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("IP Range=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("OS=").append(parameters[4]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Browser=").append(parameters[5]);
			}
			if (parameters[6] != null && parameters[6] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Device=").append(parameters[6]);
			}
			return sb.toString();
		case EDITPORTALMAPPING:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("AP Group Name=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SS Id=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("IP Range=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("OS=").append(parameters[4]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Browser=").append(parameters[5]);
			}
			if (parameters[6] != null && parameters[6] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Device=").append(parameters[6]);
			}
			return sb.toString();
		case REMOVEPORTALMAPPING:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			return sb.toString();
		case EDITQUOTAVALUE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Quota Value=").append(parameters[1]);
			}
			return sb.toString();
		case CREATEPORTALQUOTAMAPPING:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Description=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Login Type=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Profile Name=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Description=").append(parameters[3]);
			}
			return sb.toString();
		case REMOVEPORTALQUOTAMAPPING:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			return sb.toString();
		case CREATEVOUCHERGROUP:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Description=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Enabled=").append(parameters[2]);
			}

			return sb.toString();
		case EDITVOUCHERGROUP:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Description=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Enabled=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[3]);
			}
			return sb.toString();
		case REMOVEVOUCHERGROUP:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			return sb.toString();
		case REMOVEVOUCHERGROUPFROMVOUCHERGROUPNAME:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[0]);
			}
			return sb.toString();
		case REMOVEVOUCHER:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			return sb.toString();
		case REMOVEPLAN:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Plan Name=").append(parameters[0]);
			}
			return sb.toString();
		case REMOVESERVICEFROMPLAN:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Plan Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Name=").append(parameters[1]);
			}
			return sb.toString();
		case REMOVESERVICE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Name=").append(parameters[0]);
			}
			return sb.toString();
		case REMOVESERVICEELEMENT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Element Attribute=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Element Value=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Element Operator=").append(parameters[3]);
			}

			return sb.toString();
		case REMOVETEMPLATE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template Name=").append(parameters[0]);
			}
			return sb.toString();
		case REMOVETEMPLATEELEMENT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template Element Attribute=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template Element Value=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template Element Operator=").append(parameters[3]);
			}

			return sb.toString();
		case REMOVENETWORKID:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Network Identifier=").append(parameters[0]);
			}
			return sb.toString();
		case REMOVEISG:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			return sb.toString();
		case REMOVEAPGROUP:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("AP Group Name=").append(parameters[0]);
			}
			return sb.toString();
		case REMOVEAP:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			return sb.toString();
		case REMOVELOCATION:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			return sb.toString();
		case REMOVEWLC:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			return sb.toString();
		case CREATEVOUCHERS:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Voucher Group Name=").append(parameters[0]);
			}

			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Cant=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Type=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Reuse=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Limit Uses=").append(parameters[4]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Profile Name=").append(parameters[5]);
			}
			if (parameters[6] != null && parameters[6] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Start Period=").append(parameters[6]);
			}
			if (parameters[7] != null && parameters[7] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("End Period=").append(parameters[7]);
			}
			return sb.toString();
		case ADDVOUCHERMAPPING:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Voucher Group Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal Description=").append(parameters[1]);
			}
			return sb.toString();
		case REMOVEVOUCHERMAPPING:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[0]);
			}
			return sb.toString();
		case CREATEPLAN:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Plan Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Description=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Accounting=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Notification Enabled=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Notification Threshold=").append(parameters[4]);
			}
			return sb.toString();
		case EDITPLAN:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Old Plan Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Plan Name=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Description=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Accounting=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Name=").append(parameters[4]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Volume Quota=").append(parameters[5]);
			}
			if (parameters[6] != null && parameters[6] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Volume Dosage=").append(parameters[6]);
			}
			if (parameters[7] != null && parameters[7] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Time Quota=").append(parameters[7]);
			}
			if (parameters[7] != null && parameters[7] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Time Dosage=").append(parameters[7]);
			}
			if (parameters[8] != null && parameters[8] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Time Dosage=").append(parameters[8]);
			}
			if (parameters[9] != null && parameters[9] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Notification Enabled=").append(parameters[9]);
			}
			if (parameters[10] != null && parameters[10] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Notification Threshold=").append(parameters[10]);
			}
			return sb.toString();
		case ADDSERVICETOPLAN:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Plan Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Name=").append(parameters[1]);
			}
			return sb.toString();
		case CREATESERVICE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Description=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Accounting=").append(parameters[3]);
			}
			return sb.toString();
		case EDITSERVICE:
			sb = new StringBuilder();
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Old Service Name=").append(parameters[4]);
			}
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Description=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Accounting=").append(parameters[3]);
			}
			return sb.toString();
		case ADDSERVICEELEMENT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Element Attribute=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Element Value=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Element Operator=").append(parameters[3]);
			}
			return sb.toString();
		case EDITSERVICEELEMENT:
			sb = new StringBuilder();

			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Name=").append(parameters[0]);
			}
			if (parameters[4] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Old Service Element Attribute=").append(parameters[4]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Element Attribute=").append(parameters[1]);
			}
			if (parameters[4] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Old Service Element Value=").append(parameters[4]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Element Value=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Service Element Operator=").append(parameters[3]);
			}
			return sb.toString();
		case CREATETEMPLATE:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template Description=").append(parameters[1]);
			}
			return sb.toString();
		case EDITTEMPLATE:
			sb = new StringBuilder();
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Old Template Name=").append(parameters[2]);
			}
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template Description=").append(parameters[1]);
			}
			return sb.toString();
		case ADDTEMPLATEELEMENT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template Element Attribute=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template Element Value=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template Element Operator=").append(parameters[3]);
			}
			return sb.toString();
		case EDITTEMPLATEELEMENT:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template Name=").append(parameters[0]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Old Template Element Operator=").append(parameters[4]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template Element Attribute=").append(parameters[1]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Old Template Element Value=").append(parameters[5]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template Element Value=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Template Element Operator=").append(parameters[3]);
			}
			return sb.toString();
		case CREATENETWORKID:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Network Identifier=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Id=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Authval Id=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Expires=").append(parameters[3]);
			}
			return sb.toString();
		case EDITNETWORKID:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Network Identifier=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Subscriber Id=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Authval Id=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Expires=").append(parameters[3]);
			}
			return sb.toString();
		case CREATEISG:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("IP=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Secret=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("COA Port=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Type=").append(parameters[4]);
			}
			return sb.toString();
		case EDITISG:
			sb = new StringBuilder();
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[5]);
			}
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("IP=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Secret=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("COA Port=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Type=").append(parameters[4]);
			}
			return sb.toString();
		case CREATEWLC:
			sb = new StringBuilder();

			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("IP=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Secret=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SNMP Port=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SNMP Community=").append(parameters[4]);
			}

			return sb.toString();
		case EDITWLC:
			sb = new StringBuilder();
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[5]);
			}
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("IP=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Secret=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SNMP Port=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("SNMP Community=").append(parameters[4]);
			}
			return sb.toString();
		case CREATEAPGROUP:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ap Group Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal=").append(parameters[1]);
			}
			return sb.toString();
		case EDITAPGROUP:
			sb = new StringBuilder();
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Old Ap Group Name=").append(parameters[2]);
			}
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ap Group Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Portal=").append(parameters[1]);
			}
			return sb.toString();
		case CREATEAP:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ap Group Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ap Name=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("MAC=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Description=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Type=").append(parameters[4]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Latitude=").append(parameters[5]);
			}
			if (parameters[6] != null && parameters[6] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Longitude=").append(parameters[6]);
			}
			if (parameters[7] != null && parameters[7] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Address=").append(parameters[7]);
			}
			if (parameters[8] != null && parameters[8] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Country=").append(parameters[8]);
			}
			if (parameters[9] != null && parameters[9] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("State=").append(parameters[9]);
			}
			if (parameters[10] != null && parameters[10] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("City=").append(parameters[10]);
			}
			if (parameters[11] != null && parameters[11] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Neighborhood=").append(parameters[11]);
			}
			if (parameters[12] != null && parameters[12] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Zip Code=").append(parameters[12]);
			}
			if (parameters[13] != null && parameters[13] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Enable Users Limit=").append(parameters[13]);
			}
			if (parameters[14] != null && parameters[14] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Users Limit=").append(parameters[14]);
			}
			if (parameters[15] != null && parameters[15] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("WISPR Location Id=").append(parameters[15]);
			}
			if (parameters[16] != null && parameters[16] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("WISPR Location Name=").append(parameters[16]);
			}
			return sb.toString();
		case EDITAP:
			sb = new StringBuilder();
			if (parameters[17] != null && parameters[17] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Id=").append(parameters[17]);
			}
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ap Group Name=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ap Name=").append(parameters[1]);
			}
			if (parameters[2] != null && parameters[2] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("MAC=").append(parameters[2]);
			}
			if (parameters[3] != null && parameters[3] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Description=").append(parameters[3]);
			}
			if (parameters[4] != null && parameters[4] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Type=").append(parameters[4]);
			}
			if (parameters[5] != null && parameters[5] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Latitude=").append(parameters[5]);
			}
			if (parameters[6] != null && parameters[6] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Longitude=").append(parameters[6]);
			}
			if (parameters[7] != null && parameters[7] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Address=").append(parameters[7]);
			}
			if (parameters[8] != null && parameters[8] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Country=").append(parameters[8]);
			}
			if (parameters[9] != null && parameters[9] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("State=").append(parameters[9]);
			}
			if (parameters[10] != null && parameters[10] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("City=").append(parameters[10]);
			}
			if (parameters[11] != null && parameters[11] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Neighborhood=").append(parameters[11]);
			}
			if (parameters[12] != null && parameters[12] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Zip Code=").append(parameters[12]);
			}
			if (parameters[13] != null && parameters[13] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Enable Users Limit=").append(parameters[13]);
			}
			if (parameters[14] != null && parameters[14] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Users Limit=").append(parameters[14]);
			}
			if (parameters[15] != null && parameters[15] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("WISPR Location Id=").append(parameters[15]);
			}
			if (parameters[16] != null && parameters[16] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("WISPR Location Name=").append(parameters[16]);
			}
			return sb.toString();
		case MOVEALLAPS:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ap Group Source=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Ap Group Destination=").append(parameters[1]);
			}
			return sb.toString();
		case TESTMAIL:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Mail=").append(parameters[0]);
			}
			if (parameters[1] != null && parameters[1] != "") {
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append("Type Mail=").append(parameters[1]);
			}
			return sb.toString();
		case EXPORTWLCACCOUNTINGCSV:
			sb = new StringBuilder();
			sb.append("Export Wlc Accounting CSV");
			return sb.toString();
		case EXPORTLOCATIONSCSV:
			sb = new StringBuilder();
			sb.append("Export Locations CSV");
			return sb.toString();
		case SAVESETTINGS:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				Map<String, String[]> map = (Map<String, String[]>) parameters[0];
				for (String key : map.keySet()) {
					if (sb.length() != 0) {
						sb.append(", ");
					}
					sb.append(key).append("= ").append(Arrays.toString(map.get(key)));
				}

			}
			return sb.toString();
		case IMPORTZONEITEMS:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				sb.append(parameters[0]);
			}
			return sb.toString();
		case EXPORTZONEITEMS:
			sb = new StringBuilder();
			if (parameters[0] != null && parameters[0] != "") {
				sb.append(parameters[0]);
			}
			return sb.toString();

		}

		return null;
	}

	public void clearAudits() {
		List<UserAudit> audit = userAuditRepository.findAll();
		for (UserAudit audit1 : audit) {
			userAuditRepository.delete(audit1);
		}
	}

	public List<UserAudit> getAuditsPaginated(int page, int length) {
		Pageable pageable = new PageRequest(page, 5); // get 5 profiles on a page
		Page<UserAudit> result = userAuditRepository.findAll(pageable);
		return Lists.newArrayList(result);
	}

	public List<UserAudit> findAllPaginatedDND(int page, int length) {
		Pageable pageable = new PageRequest(page, 5); // get 5 profiles on a page
		Page<UserAudit> result = userAuditRepository.findAllDND(pageable);
		return Lists.newArrayList(result);
	}

	public List<UserAudit> getAuditsPaginatedAdvancedSearch(int page, int length, String advancedSearchFilter) {
		// return userAuditRepository.findPaginatedAdvancedSearch(page, length, advancedSearchFilter);
		// TODO TERMINAR
		return null;
	}

	public List<UserAudit> findPaginatedAdvancedSearchCount(int page, int length, String advancedSearchFilter) {
		// return userAuditRepository.findPaginatedAdvancedSearchCount(page, length, advancedSearchFilter);
		// TODO TERMINAR
		return null;
	}

	public List<UserAudit> getAuditsPaginatedAdvancedSearchDND(int page, int length, String advancedSearchFilter) {
		// return userAuditRepository.findPaginatedAdvancedSearchDND(page, length, advancedSearchFilter);
		// TODO TERMINAR
		return null;
	}

	public Integer findPaginatedAdvancedSearchCountDND(int page, int length, String advancedSearchFilter) {
		// return auditFacade.findPaginatedAdvancedSearchCountDND(page, length, advancedSearchFilter);
		// TODO TERMINAR
		return null;
	}

}
