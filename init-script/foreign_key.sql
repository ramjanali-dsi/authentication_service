alter table dsi_login add constraint UK_email  unique (email);

alter table dsi_tenant add constraint FK_authHandlerID foreign key (auth_handler_id) references ref_auth_handler (auth_handler_id);

ALTER TABLE  `dsi_login` ADD  `is_active` BIT( 1 ) NOT NULL AFTER  `user_id` ;