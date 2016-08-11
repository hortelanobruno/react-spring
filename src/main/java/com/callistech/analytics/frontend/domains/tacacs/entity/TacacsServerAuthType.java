package com.callistech.analytics.frontend.domains.tacacs.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class TacacsServerAuthType {

	private @Id String id;
	private String authType;
	private Boolean fallbackAuthenthication;
}
