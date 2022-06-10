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
@Table(name = "chatroom_messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatRoomMessageDAO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "chat_message_id_sequence")
	@SequenceGenerator(initialValue = 1, name = "chat_message_id_sequence", allocationSize = 1)
	@Column(name = "message_id")
	@Setter(value = AccessLevel.PROTECTED)
	private Long id;

	@ManyToOne(targetEntity = ChatRoomDAO.class)
	@JoinColumn(name = "chatroom_id")
	private ChatRoomDAO chatRoomId;

	@ManyToOne(targetEntity = UserDAO.class)
	@JoinColumn(name = "creator_id")
	private UserDAO creator;

	@Column(name = "created")
	private LocalDateTime created;

	@Column(name = "message")
	private String message;
}