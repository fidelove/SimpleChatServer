INSERT INTO public.users (user_id, user_active, user_name, user_password) VALUES(1, true, 'user 1', 'password');
INSERT INTO public.users (user_id, user_active, user_name, user_password) VALUES(2, true, 'user 2', 'password');
INSERT INTO public.users (user_id, user_active, user_name, user_password) VALUES(3, true, 'user 3', 'password');
INSERT INTO public.users (user_id, user_active, user_name, user_password) VALUES(4, true, 'user 4', 'password');
INSERT INTO public.users (user_id, user_active, user_name, user_password) VALUES(5, true, 'user 5', 'password');

ALTER SEQUENCE public.user_id_sequence RESTART START WITH 6;
--UPDATE SEQUENCE public.chatroom_chatroom_id_seq SET "last_value"=5;
--DROP SEQUENCE user_id_sequence;
--CREATE SEQUENCE user_id_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 6 CACHE 1 NO CYCLE;

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

ALTER SEQUENCE public.chatroom_id_sequence RESTART START WITH 11;
--UPDATE SEQUENCE public.chatroom_chatroom_id_seq SET "last_value"=10;
--DROP SEQUENCE chatroom_chatroom_id_seq;
--CREATE SEQUENCE chatroom_chatroom_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 11 CACHE 1 NO CYCLE;
