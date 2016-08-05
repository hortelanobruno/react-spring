package com.brunoli.payroll.analytics.util;

import java.util.List;

import lombok.Data;

@Data
public class Elements {

	private List<String> apGroupIds;
	private List<String> apIds;
	private List<String> ssidIds;
	private List<String> camDomainIds;

	public Elements() {

	}
}
