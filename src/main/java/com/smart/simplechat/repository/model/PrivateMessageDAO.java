package com.smart.simplechat.repository.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "private_messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PrivateMessageDAO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "private_message_id_sequence")
	@SequenceGenerator(initialValue = 1, name = "private_message_id_sequence", allocationSize = 1)
	@Column(name = "message_id")
	@Setter(value = AccessLevel.PROTECTED)
	private Long id;

	@ManyToOne(targetEntity = UserDAO.class)
	@JoinColumn(name = "creator_id")
	private UserDAO creator;

	@ManyToOne(targetEntity = UserDAO.class)
	@JoinColumn(name = "destination_id")
	private UserDAO destination;

	@Column(name = "created")
	private LocalDateTime created;

	@Column(name = "message")
	private String message;
}