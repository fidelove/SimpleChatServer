package com.smart.simplechat.model;

import java.util.List;

import org.springframework.lang.NonNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

	@NonNull
	@Setter(value = AccessLevel.PROTECTED)
	private Long id;

	@NonNull
	private String userName;

	@NonNull
	private String userPassword;

	private List<PrivateMessage> privateMessages;

	/**
	 * @param id
	 * @param userName
	 */
	public User(Long id, String userName) {
		super();
		this.id = id;
		this.userName = userName;
	}
}
