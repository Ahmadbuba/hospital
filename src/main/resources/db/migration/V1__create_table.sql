CREATE SCHEMA IF NOT EXISTS hospital;

CREATE TABLE IF NOT EXISTS address (
       id bigint not null,
        house_number integer,
        state varchar(255),
        street varchar(255),
        primary key (id)
    );


CREATE TABLE IF NOT EXISTS personal_detail (
       id bigint not null,
        blood_group varchar(255),
        genotype varchar(255),
        weight float(53),
        primary key (id)
    );




CREATE TABLE IF NOT EXISTS patient (
       id bigint not null,
        first_name varchar(255),
        gender varchar(255) not null,
        last_name varchar(255),
        address_id bigint,
        personal_detail_id bigint,
        next_of_kin_id bigint,
        primary key (id),
        FOREIGN KEY(address_id)
            REFERENCES address(id),
        FOREIGN KEY(personal_detail_id)
            REFERENCES personal_detail(id)
    );

CREATE TABLE IF NOT EXISTS doctor (
       id bigint not null,
        first_name varchar(255),
        gender varchar(255) not null,
        last_name varchar(255),
        address_id bigint,
        personal_detail_id bigint,
    primary key (id),
        FOREIGN KEY(address_id)
            REFERENCES address(id),
        FOREIGN KEY(personal_detail_id)
            REFERENCES personal_detail(id)
    );





CREATE TABLE IF NOT EXISTS doctor_next_of_kin (
       id bigint not null,
        first_name varchar(255),
        gender varchar(255) not null,
        last_name varchar(255),
        address_id bigint,
	    doctor_id bigint,
        primary key (id),
        FOREIGN KEY(address_id)
            REFERENCES address(id),
	FOREIGN KEY(doctor_id)
	    REFERENCES doctor(id)
    );


CREATE TABLE IF NOT EXISTS patient_next_of_kin (
       id bigint not null,
        first_name varchar(255),
        gender varchar(255) not null,
        last_name varchar(255),
        address_id bigint,
	    patient_id bigint,
        primary key (id),
        FOREIGN KEY(address_id)
            REFERENCES address(id),
	    FOREIGN KEY(patient_id)
	        REFERENCES patient(id)
    );


