CREATE TABLE role_permissions(
id bigint(20) NOT NULL AUTO_INCREMENT,
role_id bigint(20) NOT NULL,
permission_id bigint(20) NOT NULL,
PRIMARY KEY (id),
CONSTRAINT `roles_fk1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
CONSTRAINT `permissions_fk1` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
)ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci;

insert into permissions (id,name) values (1, '/users/all');
insert into permissions (id,name) values (2, '/users/save');
insert into permissions (id,name) values (3, '/users/delete');
insert into permissions (id,name) values (4, '/users/get');
insert into permissions (id,name) values (5, '/users/update');
insert into permissions (id,name) values (6, '/users/patch');
insert into permissions (id,name) values (7, '/users/exist');
insert into permissions (id,name) values (8, '/users/count');

insert into permissions (id,name) values (9, '/roles/all');
insert into permissions (id,name) values (10, '/roles/save');
insert into permissions (id,name) values (11, '/roles/delete');
insert into permissions (id,name) values (12, '/roles/get');
insert into permissions (id,name) values (13, '/roles/update');
insert into permissions (id,name) values (14, '/roles/patch');
insert into permissions (id,name) values (15, '/roles/exist');
insert into permissions (id,name) values (16, '/roles/count');

insert into permissions (id,name) values (17, '/institutions/all');
insert into permissions (id,name) values (18, '/institutions/save');
insert into permissions (id,name) values (19, '/institutions/delete');
insert into permissions (id,name) values (20, '/institutions/get');
insert into permissions (id,name) values (21, '/institutions/update');
insert into permissions (id,name) values (22, '/institutions/patch');
insert into permissions (id,name) values (23, '/institutions/exist');
insert into permissions (id,name) values (24, '/institutions/count');


