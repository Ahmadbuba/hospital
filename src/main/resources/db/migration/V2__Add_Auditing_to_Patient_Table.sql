-- Add auditing columns
ALTER TABLE IF EXISTS patient
    ADD COLUMN created_by VARCHAR(255),
    ADD COLUMN created_date TIMESTAMP(6),
    ADD COLUMN last_modified_by VARCHAR(255),
    ADD COLUMN last_modified_date TIMESTAMP(6);

-- Set default values for auditing columns
UPDATE patient
    SET created_date = current_timestamp,
        last_modified_date = current_timestamp,
        created_by = 'system',
        last_modified_by = 'system';