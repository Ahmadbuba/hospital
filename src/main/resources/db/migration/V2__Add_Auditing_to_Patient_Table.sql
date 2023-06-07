-- V2__Add_Auditing_to_Patient_Table.sql

-- Add auditing columns
alter table if exists patient
    add column created_at timestamp(6),
    add column modified_at timestamp(6),
    add column created_by varchar(255),
    add column modified_by varchar(255);

-- Set default values for auditing columns
update patient
    set created_at = current_timestamp,
        modified_at = current_timestamp,
        created_by = 'system',
        modified_by = 'system';