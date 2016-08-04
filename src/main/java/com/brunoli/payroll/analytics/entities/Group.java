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
public class Group {

	private @Id String id;
	private String name;
	private String description;

	private @Version @JsonIgnore Long version;

	 private @DBRef List<User> users;

	private Group() {
	}

}
// end::code[]