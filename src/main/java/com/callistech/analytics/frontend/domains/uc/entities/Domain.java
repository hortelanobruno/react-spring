package com.callistech.analytics.frontend.domains.uc.entities;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.callistech.analytics.frontend.domains.uc.util.CamElement;
import com.callistech.analytics.frontend.domains.uc.util.Permission;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Data
@Document
public class Domain {

	private @Id String id;
	@NotNull(message = "Domain name is mandatory")
	@Indexed(unique = true)
	private String name;
	private String description;
	private @Version @JsonIgnore Long version;

	private List<Permission> permissions;
	private List<Integer> templateIds;
	private Boolean enableACLIPs;
	private List<String> aclIPs;
	private List<CamElement> camElements;

	public Domain() {
	}

}
// end::code[]