INSERT INTO public.users (user_id, user_active, user_name, user_password) VALUES(1, true, 'admin', '{noop}password');
INSERT INTO public.users (user_id, user_active, user_name, user_password) VALUES(2, true, 'user', '{noop}password');

DROP SEQUENCE public.user_id_sequence;
CREATE SEQUENCE public.user_id_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 3 CACHE 1 NO CYCLE;

INSERT INTO public.chatroom (chatroom_id, chatroom_name) VALUES(1, 'chatroom 1');

DROP SEQUENCE public.chatroom_id_sequence;
CREATE SEQUENCE public.chatroom_id_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 2 CACHE 1 NO CYCLE;
