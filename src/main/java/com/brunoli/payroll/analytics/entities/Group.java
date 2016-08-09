package com.brunoli.payroll.analytics.entities;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.brunoli.payroll.analytics.util.Elements;
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
	@NotNull(message = "Group name is mandatory")
	@Indexed(unique = true)
	private String name;
	private String description;
	@NotNull(message = "Group domainId is mandatory")
	private String domainId;
	private Elements elements;
	private @Version @JsonIgnore Long version;
	private Boolean isDefault;

	public Group() {
	}

}
// end::code[]