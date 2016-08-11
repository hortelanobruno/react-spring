package com.callistech.analytics.frontend.domains.settings.entities;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Document
public class Setting {

	private @Id String id;
	@NotNull(message = "Domain name is mandatory")
	@Indexed(unique = true)
	private String name;
	private String value;
	private @Version @JsonIgnore Long version;

	public Setting() {
	}

}
