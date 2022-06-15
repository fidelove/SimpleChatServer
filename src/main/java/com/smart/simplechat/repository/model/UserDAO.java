package com.smart.simplechat.repository.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDAO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_id_sequence")
	@SequenceGenerator(initialValue = 2, name = "user_id_sequence", allocationSize = 1)
	@Column(name = "user_id")
	@Setter(value = AccessLevel.PROTECTED)
	private Long id;

	@Column(name = "user_name", unique = true)
	@NotBlank
	private String userName;

	@Column(name = "user_password")
	@NotBlank
	private String userPassword;

	@Column(name = "user_active")
	private Boolean isActive;

	@OneToMany(mappedBy = "destination")
	private List<PrivateMessageDAO> privateMessages;

	/**
	 * @param userName
	 * @param userPassword
	 * @param isActive
	 * @param privateMessages
	 */
	public UserDAO(@NotBlank String userName, @NotBlank String userPassword, Boolean isActive,
			List<PrivateMessageDAO> privateMessages) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
		this.isActive = isActive;
		this.privateMessages = privateMessages;
	}
}