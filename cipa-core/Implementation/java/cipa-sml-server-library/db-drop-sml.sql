ALTER TABLE sml_allowed_wildcard_schemes DROP FOREIGN KEY FK_sml_allowed_wildcard_schemes_username
ALTER TABLE sml_recipient_participant_identifier DROP FOREIGN KEY FK_sml_recipient_participant_identifier_smp_id
ALTER TABLE sml_service_metadata_publisher DROP FOREIGN KEY FK_sml_service_metadata_publisher_username
DROP TABLE sml_allowed_wildcard_schemes
DROP TABLE sml_migrate
DROP TABLE sml_recipient_participant_identifier
DROP TABLE sml_service_metadata_publisher
DROP TABLE sml_user
