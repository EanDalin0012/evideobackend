--sequence_vd_source
CREATE SEQUENCE sequence_vd_source START 1;

SELECT nextval('sequence_vd_source');

INSERT INTO user_
(id, account_expired, account_locked, credentials_expired, enabled, "password", user_name, status, create_date, create_by, modify_date, modify_by, last_login_date, last_login_time, first_name, last_name, gender, date_birth, phone_number, other_phone_number, resource_id, is_online, is_first_login, remark, address, identify_info_id, identify_info_resource_id)
VALUES(1, false, false, false, true, '$2a$10$s6KZ/OmEPClG0dq78GDaO.Fc47yKTaQuYyjSWJziipqd3IzBlOMyK', 'admin@gmail.com', '3', '20210830', NULL, '20200924', 2, '20200924', '110541', 'Company', 'Account', 'm', '20170107', '0966555879', NULL, 21, NULL, false, NULL, 'PP', NULL, NULL);

--1
INSERT INTO authority(id,name)VALUES(1,'User_Read');
INSERT INTO authority(id,name)VALUES(2,'User_Create');
INSERT INTO authority(id,name)VALUES(3,'User_Update');
INSERT INTO authority(id,name)VALUES(4,'User_Delete');
--2
INSERT INTO authority(id,name)VALUES(5,'Movie_Read');
INSERT INTO authority(id,name)VALUES(6,'Movie_Create');
INSERT INTO authority(id,name)VALUES(7,'Movie_Update');
INSERT INTO authority(id,name)VALUES(8,'Movie_Delete');
--3
INSERT INTO authority(id,name)VALUES(9,'Movie_Source_Read');
INSERT INTO authority(id,name)VALUES(10,'Movie_Source_Create');
INSERT INTO authority(id,name)VALUES(11,'Movie_Source_Update');
INSERT INTO authority(id,name)VALUES(12,'Movie_Source_Delete');
--4
INSERT INTO authority(id,name)VALUES(13,'Setting_Movie_Type_Read');
INSERT INTO authority(id,name)VALUES(14,'Setting_Movie_Type_Create');
INSERT INTO authority(id,name)VALUES(15,'Setting_Movie_Type_Update');
INSERT INTO authority(id,name)VALUES(16,'Setting_Movie_Type_Delete');
--5
INSERT INTO authority(id,name)VALUES(17,'Setting_Sub_Movie_Type_Read');
INSERT INTO authority(id,name)VALUES(18,'Setting_Sub_Movie_Type_Create');
INSERT INTO authority(id,name)VALUES(19,'Setting_Sub_Movie_Type_Update');
INSERT INTO authority(id,name)VALUES(20,'Setting_Sub_Movie_Type_Delete');
--6
INSERT INTO authority(id,name)VALUES(21,'Setting_Client_Setting_Read');
INSERT INTO authority(id,name)VALUES(22,'Setting_Client_Setting_Create');
INSERT INTO authority(id,name)VALUES(23,'Setting_Client_Setting_Update');
INSERT INTO authority(id,name)VALUES(24,'Setting_Client_Setting_Delete');

INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 1);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 2);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 3);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 4);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 5);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 6);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 7);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 8);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 9);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 10);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 11);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 12);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 13);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 14);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 14);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 15);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 16);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 17);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 18);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 19);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 20);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 21);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 22);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 23);
INSERT INTO users_authorities(user_id, authority_id)VALUES(1, 24);