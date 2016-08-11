package com.callistech.analytics.frontend.domains.ua.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class UserAudit {

	private @Id String id;
	private String username;
	private Date timestamp;
	private String action;
	private String parameters;
	private String ip;
}
