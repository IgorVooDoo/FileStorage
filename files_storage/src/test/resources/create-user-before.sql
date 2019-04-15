delete from access;
delete from data_object;
delete from user_role;
delete from usr;

insert into usr(id,active,password,username) values
(1,true,'$2a$08$SV9uTNpYj7v5Pi90jrhVjO.DMjWP79Ppbwg7oNOGDaT84Zhdjy7Ue','user'),
(2,true,'$2a$08$oBCyzPSLy/F5UJkaWcWyLuhnT0Faz9LD4exQxmB30SHlebcF4APfS','admin'),
(3,true,'$2a$08$SV9uTNpYj7v5Pi90jrhVjO.DMjWP79Ppbwg7oNOGDaT84Zhdjy7Ue','userRead'),
(4,true,'$2a$08$SV9uTNpYj7v5Pi90jrhVjO.DMjWP79Ppbwg7oNOGDaT84Zhdjy7Ue','userLoad'),
(5,true,'$2a$08$oBCyzPSLy/F5UJkaWcWyLuhnT0Faz9LD4exQxmB30SHlebcF4APfS','analyst'),
(6,true,'$2a$08$SV9uTNpYj7v5Pi90jrhVjO.DMjWP79Ppbwg7oNOGDaT84Zhdjy7Ue','memberRead'),
(7,true,'$2a$08$SV9uTNpYj7v5Pi90jrhVjO.DMjWP79Ppbwg7oNOGDaT84Zhdjy7Ue','memberLoad'),
(8,true,'$2a$08$SV9uTNpYj7v5Pi90jrhVjO.DMjWP79Ppbwg7oNOGDaT84Zhdjy7Ue','memberQueryRead'),
(9,true,'$2a$08$SV9uTNpYj7v5Pi90jrhVjO.DMjWP79Ppbwg7oNOGDaT84Zhdjy7Ue','memberQueryLoad');

insert into user_role(user_id, roles) VALUES
(1,'USER'),
(2,'ADMIN'),
(3,'USER'),
(4,'USER'),
(5,'ANALYST'),
(6,'USER'),
(7,'USER'),
(8,'USER'),
(9,'USER');

insert into  access(member_id,user_id,is_query,type_access) values
(1,3,false,0),
(1,4,false,1),
(6,1,false,0),
(7,1,false,1),
(8,1,true,0),
(9,1,true,1);

alter sequence hibernate_sequence restart with 100;
