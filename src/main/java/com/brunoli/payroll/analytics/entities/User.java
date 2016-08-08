package com.brunoli.payroll.analytics.entities;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Data
@ToString(exclude = "password")
@Document
public class User {

	public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

	private @Id String id;
	private String fullName;
	private String description;
	@NotNull(message = "User email is mandatory")
	@Indexed(unique = true)
	private String email;
	private @JsonIgnore String password;
	private Long lastSession;
	private Boolean active;
	private Long creationDate;
	private Long lastLogin;
	@NotNull(message = "User domainId is mandatory")
	private String domainId;

	private @Version @JsonIgnore Long version;

	private List<String> groupsIds;

	public User() {
	}

	public void setPassword(String password) {
		this.password = PASSWORD_ENCODER.encode(password);
	}

}
// end::code[]