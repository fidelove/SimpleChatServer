INSERT INTO public.users (user_id, user_active, user_name, user_password) VALUES(1, true, 'user1', 'password');
INSERT INTO public.users (user_id, user_active, user_name, user_password) VALUES(2, true, 'user2', 'password');
INSERT INTO public.users (user_id, user_active, user_name, user_password) VALUES(3, true, 'user3', 'password');
INSERT INTO public.users (user_id, user_active, user_name, user_password) VALUES(4, true, 'user4', 'password');
INSERT INTO public.users (user_id, user_active, user_name, user_password) VALUES(5, true, 'user5', 'password');

DROP SEQUENCE public.user_id_sequence;
CREATE SEQUENCE public.user_id_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 6 CACHE 1 NO CYCLE;

INSERT INTO public.chatroom (chatroom_id, chatroom_name) VALUES(1, 'chatroom 1');
INSERT INTO public.chatroom (chatroom_id, chatroom_name) VALUES(2, 'chatroom 2');
INSERT INTO public.chatroom (chatroom_id, chatroom_name) VALUES(3, 'chatroom 3');
INSERT INTO public.chatroom (chatroom_id, chatroom_name) VALUES(4, 'chatroom 4');
INSERT INTO public.chatroom (chatroom_id, chatroom_name) VALUES(5, 'chatroom 5');
INSERT INTO public.chatroom (chatroom_id, chatroom_name) VALUES(6, 'chatroom 6');
INSERT INTO public.chatroom (chatroom_id, chatroom_name) VALUES(7, 'chatroom 7');
INSERT INTO public.chatroom (chatroom_id, chatroom_name) VALUES(8, 'chatroom 8');
INSERT INTO public.chatroom (chatroom_id, chatroom_name) VALUES(9, 'chatroom 9');
INSERT INTO public.chatroom (chatroom_id, chatroom_name) VALUES(10, 'chatroom 10');

DROP SEQUENCE public.chatroom_id_sequence;
CREATE SEQUENCE public.chatroom_id_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 11 CACHE 1 NO CYCLE;

INSERT INTO public.chatroom_messages (message_id, created, message, chatroom_id, creator_id) VALUES(1, '2022-06-09 23:34:15.842', 'message 1', 1, 1);
INSERT INTO public.chatroom_messages (message_id, created, message, chatroom_id, creator_id) VALUES(2, '2022-06-09 23:34:15.842', 'message 2', 1, 1);
INSERT INTO public.chatroom_messages (message_id, created, message, chatroom_id, creator_id) VALUES(3, '2022-06-09 23:34:15.842', 'message 3', 1, 2);
INSERT INTO public.chatroom_messages (message_id, created, message, chatroom_id, creator_id) VALUES(4, '2022-06-09 23:34:15.842', 'message 4', 1, 2);
INSERT INTO public.chatroom_messages (message_id, created, message, chatroom_id, creator_id) VALUES(5, '2022-06-09 23:34:15.842', 'message 5', 1, 3);
INSERT INTO public.chatroom_messages (message_id, created, message, chatroom_id, creator_id) VALUES(6, '2022-06-09 23:34:15.842', 'message 6', 1, 3);
INSERT INTO public.chatroom_messages (message_id, created, message, chatroom_id, creator_id) VALUES(7, '2022-06-09 23:34:15.842', 'message 7', 1, 4);
INSERT INTO public.chatroom_messages (message_id, created, message, chatroom_id, creator_id) VALUES(8, '2022-06-09 23:34:15.842', 'message 8', 1, 4);
INSERT INTO public.chatroom_messages (message_id, created, message, chatroom_id, creator_id) VALUES(9, '2022-06-09 23:34:15.842', 'message 9', 1, 5);
INSERT INTO public.chatroom_messages (message_id, created, message, chatroom_id, creator_id) VALUES(10, '2022-06-09 23:34:15.842', 'message 10', 1, 5);

DROP SEQUENCE public.chat_message_id_sequence;
CREATE SEQUENCE public.chat_message_id_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 11 CACHE 1 NO CYCLE;

INSERT INTO public.private_messages (message_id, created, message, creator_id, destination_id) VALUES(1, '2022-06-09 23:36:51.981', 'message 1', 2, 1);
INSERT INTO public.private_messages (message_id, created, message, creator_id, destination_id) VALUES(2, '2022-06-09 23:36:51.981', 'message 1', 3, 1);
INSERT INTO public.private_messages (message_id, created, message, creator_id, destination_id) VALUES(3, '2022-06-09 23:36:51.981', 'message 1', 4, 1);
INSERT INTO public.private_messages (message_id, created, message, creator_id, destination_id) VALUES(4, '2022-06-09 23:36:51.981', 'message 1', 5, 1);

DROP SEQUENCE public.private_message_id_sequence;
CREATE SEQUENCE public.private_message_id_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 5 CACHE 1 NO CYCLE;