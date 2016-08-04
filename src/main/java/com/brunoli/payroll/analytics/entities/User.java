package com.brunoli.payroll.analytics.entities;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Data
@Document
public class User {

	private @Id String id;
	private String fullName;
	private String description;
	private String email;
	private String password;
	private Long lastSession;
	private Boolean active;
	private Long creationDate;
	private Long lastLogin;

	private @Version @JsonIgnore Long version;

	private @DBRef List<Group> groups;

	private User() {
	}

}
// end::code[]