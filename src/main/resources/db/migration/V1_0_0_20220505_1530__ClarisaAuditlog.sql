create table clarisa_auditlog (
    id bigint NOT NULL AUTO_INCREMENT,
    endpoint text,
    http_method text,
    entity_id bigint,
    entity_table text,
    previous_json text,
    updated_json text,
    request_date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    requested_by bigint DEFAULT NULL,
    modification_justification text,
    was_successful tinyint(1) not null default '0',
    PRIMARY KEY (id),
    KEY FK_clarisa_auditlog_requested_by_idx (requested_by) USING BTREE,
    CONSTRAINT `clarisa_auditlog_user_fk` FOREIGN KEY (requested_by) REFERENCES users (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;