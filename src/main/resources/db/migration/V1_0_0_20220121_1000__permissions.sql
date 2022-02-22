CREATE TABLE if not exists permissions(
id bigint(20) NOT NULL AUTO_INCREMENT,
name TEXT not null,
PRIMARY KEY (id),
INDEX `permissions_id` (`id`) USING BTREE
)ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci;