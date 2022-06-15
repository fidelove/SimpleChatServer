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
import javax.validation.constraints.NotNull;

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
public class ChatRoomDAO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "chatroom_id_sequence")
	@SequenceGenerator(initialValue = 1, name = "chatroom_id_sequence", allocationSize = 1)
	@Column(name = "chatroom_id")
	@Setter(value = AccessLevel.PROTECTED)
	private Long id;

	@Column(name = "chatroom_name")
	@NotNull
	private String chatRoomName;

	@OneToMany(mappedBy = "chatRoomId")
	private List<ChatRoomMessageDAO> messages;

}
