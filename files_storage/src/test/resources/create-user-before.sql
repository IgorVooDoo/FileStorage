delete from data_object;
delete from user_role;
delete from usr;

insert into usr(id,active,password,username) values
(1,true,'$2a$08$SV9uTNpYj7v5Pi90jrhVjO.DMjWP79Ppbwg7oNOGDaT84Zhdjy7Ue','user'),
(2,true,'$2a$08$oBCyzPSLy/F5UJkaWcWyLuhnT0Faz9LD4exQxmB30SHlebcF4APfS','admin'),
(3,true,'$2a$08$SV9uTNpYj7v5Pi90jrhVjO.DMjWP79Ppbwg7oNOGDaT84Zhdjy7Ue','userRead'),
(4,true,'$2a$08$SV9uTNpYj7v5Pi90jrhVjO.DMjWP79Ppbwg7oNOGDaT84Zhdjy7Ue','userLoad');

insert into user_role(user_id, roles) VALUES
(1,'USER'),
(2,'ADMIN'),
(3,'USER'),
(4,'USER');

alter sequence hibernate_sequence restart with 10;
