package com.callistech.analytics.frontend.domains.tacacs.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class TacacsServer {

	private @Id String id;
	private String name;
	private String ipAddress;
	private Integer port;
	private String secret;
}
