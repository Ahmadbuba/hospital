CREATE SCHEMA IF NOT EXISTS hospital;

CREATE TABLE address (
       id BIGSERIAL PRIMARY KEY,
       house_number INTEGER,
       state VARCHAR(255),
       street VARCHAR(255)
);

CREATE TABLE personal_detail (
       id BIGSERIAL PRIMARY KEY,
       blood_group VARCHAR(255),
       genotype VARCHAR(255),
       weight FLOAT(53)
);

CREATE TABLE doctor (
       id BIGSERIAL PRIMARY KEY,
       first_name VARCHAR(255),
       gender VARCHAR(255) NOT NULL,
       last_name VARCHAR(255),
       address_id BIGINT,
       personal_detail_id BIGINT,
       CONSTRAINT fk_doctor_address FOREIGN KEY (address_id) REFERENCES address(id),
       CONSTRAINT fk_doctor_personal_detail FOREIGN KEY (personal_detail_id) REFERENCES personal_detail(id)
);

CREATE TABLE doctor_next_of_kin (
       id BIGSERIAL PRIMARY KEY,
       first_name VARCHAR(255),
       gender VARCHAR(255) NOT NULL,
       last_name VARCHAR(255),
       address_id BIGINT,
       doctor_id BIGINT,
       CONSTRAINT fk_doctor_next_of_kin_address FOREIGN KEY (address_id) REFERENCES address(id),
       CONSTRAINT fk_doctor_next_of_kin_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(id)
);

CREATE TABLE patient (
       id BIGSERIAL PRIMARY KEY,
       first_name VARCHAR(255),
       gender VARCHAR(255) NOT NULL,
       last_name VARCHAR(255),
       address_id BIGINT,
       personal_detail_id BIGINT,
       CONSTRAINT fk_patient_address FOREIGN KEY (address_id) REFERENCES address(id),
       CONSTRAINT fk_patient_personal_detail FOREIGN KEY (personal_detail_id) REFERENCES personal_detail(id)
);

CREATE TABLE health_record (
       id BIGSERIAL PRIMARY KEY,
       note VARCHAR(255) NOT NULL,
       date TIMESTAMP(6) NOT NULL,
       doctor_id BIGINT NOT NULL,
       patient_id BIGINT NOT NULL,
       CONSTRAINT fk_health_record_doctor FOREIGN KEY (doctor_id) REFERENCES doctor(id),
       CONSTRAINT fk_health_record_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);



CREATE TABLE patient_next_of_kin (
       id BIGSERIAL PRIMARY KEY,
       first_name VARCHAR(255),
       gender VARCHAR(255) NOT NULL,
       last_name VARCHAR(255),
       address_id BIGINT,
       patient_id BIGINT,
       CONSTRAINT fk_patient_next_of_kin_address FOREIGN KEY (address_id) REFERENCES address(id),
       CONSTRAINT fk_patient_next_of_kin_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);

