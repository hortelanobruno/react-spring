package com.callistech.analytics.frontend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.callistech.analytics.frontend.domains.settings.entities.Setting;
import com.callistech.analytics.frontend.domains.settings.repository.SettingRepository;

@Component
public class SettingsService {

	@Autowired
	private SettingRepository settingRepository;

	public static final String PORT = "Port";
	public static final String IP = "IP";
	public static final String TIME_ZONE = "Time_Zone";
	public static final String TIME_ZONE_OFFSET = "Time_Zone_Offset";
	public static final String TIMEOUT = "timeout";

	public void init(String contextPath) {
		setInitPropValues(IP, "127.0.0.1");
		setInitPropValues(PORT, "5388");
		setInitPropValues(TIMEOUT, "30");
	}

	public void setInitPropValues(String reqProperty, String reqValue) {
		Setting set = settingRepository.findByName(reqProperty);
		if (set == null) {
			set = new Setting();
			set.setName(reqProperty);
			set.setValue(reqValue);
			settingRepository.save(set);
		}
	}

	public void setPropValues(String reqProperty, String reqValue) {
		Setting set = settingRepository.findByName(reqProperty);
		if (set == null) {
			set = new Setting();
		}
		set.setName(reqProperty);
		set.setValue(reqValue);
		settingRepository.save(set);
	}

	public String getPropValue(String reqProperty) {
		Setting set = settingRepository.findByName(reqProperty);
		if (set != null) {
			return set.getValue();
		}
		return null;
	}
}
