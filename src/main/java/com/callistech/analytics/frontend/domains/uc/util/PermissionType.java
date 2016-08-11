package com.callistech.analytics.frontend.domains.uc.util;

public enum PermissionType {

	Settings(0, "Settings"), NETWORK_NAVIGATOR(1, "Network Navigator"), SYSTEM_STATISTICS(2, "System Statistics"), HTTP_QOE_TRANSACTION_ANALYSIS(3, "HTTP QOE Transaction Analysis"), SUBSCRIBER_QOE_USAGE_ANALYSIS(4, "Subscriber QOE Usage Analysis"), VIDEO_QOE_TRANSACTION_ANALYSIS(5, "Video QOE Transaction Analysis"), ACCESS_CONTROL(6, "User Control"), IN_BROWSER_NOTIFICATION(7, "In Browser Notification"), IPDR_MONITORING(8, "IPDR Monitoring"), SUBSCRIBER_MONITORING(9,
			"Subscriber Monitoring"), TRAFFIC_CONGESTION(10, "Traffic Congestion"), TRAFFIC_MONITORING(11,
					"Traffic Monitoring"), URL_ANALYSIS(12, "URL Analysis"), VIDEO_MONITORING(13, "Video Monitoring"), CAM(14, "CAM Reports"), DASHBOARD(15, "Dashboard"), DYNAMIC_SERVICES(16, "Dynamic Services"), NAP_SETTINGS(17, "NAP Settings"), USER_AUDIT(18, "User Audit"), USER_AUDIT_CLEAR(19, "User Audit Clear Logs"), REPORTER_TEMPLATE_ABM(20, "Reporter Template ABM"), CAM_DASHBOARD(21, "Dashboard CAM"), CN_DASHBOARD(22, "Dashboard CN"), VOD_DASHBOARD(23, "Dashboard QoE");

	private Integer id;
	private String name;

	private PermissionType(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public PermissionType getPermissionById(Integer id) {
		if (id != null) {
			for (PermissionType permission : PermissionType.values()) {
				if (permission.getId().equals(id)) {
					return permission;
				}
			}
		}
		return null;
	}

	public PermissionType getPermissionByName(String name) {
		if (id != null) {
			for (PermissionType permission : PermissionType.values()) {
				if (permission.getName().equals(name)) {
					return permission;
				}
			}
		}
		return null;
	}

}
