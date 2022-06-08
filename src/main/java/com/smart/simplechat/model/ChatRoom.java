package com.smart.simplechat.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "chatroom")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "chatroom_id_sequence")
	@SequenceGenerator(initialValue = 1, name = "chatroom_id_sequence", allocationSize = 1)
	@Column(name = "chatroom_id")
	@Setter(value = AccessLevel.PROTECTED)
	private Long id;

	@Column(name = "chatroom_name")
	@NotNull
	@NonNull
	private String chatRoomName;

	@OneToMany(mappedBy = "chatRoomId")
	private List<ChatRoomMessage> messages;

}
