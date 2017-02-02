// Nothing

INSERT INTO `service_authentication`.`ref_auth_handler` (`auth_handler_id`, `name`, `type_impl`, `version`) VALUES ('78fd9528-431a-4cfb-adde-552b6c87fba8', 'Database Login', 'com.dsi.authentication.service.impl.DBLoginHandlerImpl', '1');

INSERT INTO `service_authentication`.`dsi_tenant` (`tenant_id`, `is_active`, `name`, `short_name`, `version`, `auth_handler_id`, `secret_key`) VALUES ('cc4e0554-6582-498b-9ae2-ad3c612f8e8e', b'1', 'Dynamic Solution', 'DSI', '1', '78fd9528-431a-4cfb-adde-552b6c87fba8', '87c63aae-917c-42ce-b4c7-8a4847db4133');

INSERT INTO `service_authentication`.`dsi_login` (`login_id`, `created_by`, `created_date`, `email`, `first_name`, `last_name`, `modified_by`, `modified_date`, `password`, `reset_password_token`, `reset_token_expire_time`, `salt`, `user_id`, `version`) VALUES ('f264874e-0331-4fcd-8e92-49513a7724c2', '354fd26f-c642-40ac-b5cf-718c90081598',
'2016-06-15 00:00:00', 'sabbir@gmail.com', 'Sabbir', 'Ahmed', '354fd26f-c642-40ac-b5cf-718c90081598', '2016-06-15 00:00:00', 'password', NULL, NULL, '87c63aae-917c-42ce-b4c7-8a4847db4133', 'f9e9a19f-4859-4e8c-a8f4-dc134629a57b', '1');


INSERT INTO `service_authorization`.`dsi_api` (`api_id`, `is_active`, `url`, `method`, `version`, `system_id`)
VALUES ('2779d34b-73d9-4541-9cb3-081580bea568', b'1', 'v1/login_session/update', 'PUT', '1', '425744ba-6c10-47c0-91cf-5a4c05265b56');

INSERT INTO `service_authorization`.`dsi_default_api` (`default_api_id`, `allow_type`, `version`, `api_id`)
VALUES ('8871c3dc-6331-4ae2-bcdc-5234c75a0b9d', 'System', '1', '2779d34b-73d9-4541-9cb3-081580bea568');