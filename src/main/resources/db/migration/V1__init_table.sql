CREATE SCHEMA IF NOT EXISTS hospital;

create table address (
       id bigint not null,
        house_number integer,
        state varchar(255),
        street varchar(255),
        primary key (id)
);

create table doctor (
       id bigint not null,
       first_name varchar(255),
       gender varchar(255) not null,
       last_name varchar(255),
       address_id bigint,
       personal_detail_id bigint,
       primary key (id)
);

create table doctor_next_of_kin (
       id bigint not null,
       first_name varchar(255),
       gender varchar(255) not null,
       last_name varchar(255),
       address_id bigint,
       doctor_id bigint,
       primary key (id)
);

create table health_record (
       id bigint not null,
       note varchar(255) not null,
       date timestamp(6) not null,
       doctor_id bigint not null,
       patient_id bigint not null,
       primary key (id)
);

create table patient (
       id bigint not null,
       first_name varchar(255),
       gender varchar(255) not null,
       last_name varchar(255),
       address_id bigint,
       personal_detail_id bigint,
       primary key (id)
);

create table patient_next_of_kin (
       id bigint not null,
       first_name varchar(255),
       gender varchar(255) not null,
       last_name varchar(255),
       address_id bigint,
       patient_id bigint,
       primary key (id)
);

create table personal_detail (
       id bigint not null,
       blood_group varchar(255),
       genotype varchar(255),
       weight float(53),
       primary key (id)
);

alter table if exists doctor
    add constraint FK55wcpoxucujk1vqonmjuljb2y
    foreign key (address_id)
references address;

alter table if exists doctor
    add constraint FKkx9kv7cplj5b3krbctorll3ae
    foreign key (personal_detail_id)
references personal_detail;

alter table if exists doctor_next_of_kin
    add constraint FKjvyujb1pxv7j3mxn0qt5ndhko
    foreign key (address_id)
references address;


alter table if exists doctor_next_of_kin
    add constraint FKd1urd7vkbn81no85cxm1a7qqj
    foreign key (doctor_id)
references doctor;

alter table if exists health_record
    add constraint FKqaarpksv2jh0gvb2xaih72ffv
    foreign key (doctor_id)
references doctor;

alter table if exists health_record
    add constraint FK4uewhggve44h1rv02eskd2rt0
    foreign key (patient_id)
references patient;

alter table if exists patient
    add constraint FKlpwpmtdbdvnxm8fxlxpq2fugh
    foreign key (address_id)
references address;

alter table if exists patient
    add constraint FKkytecbf0854qy166nd47a9trb
    foreign key (personal_detail_id)
references personal_detail;


alter table if exists patient_next_of_kin
    add constraint FK7ewbtyf9i7iw6ex2nbmu6yx12
    foreign key (address_id)
references address;

alter table if exists patient_next_of_kin
    add constraint FK3qku406uoev60xs0t78e7k2i7
    foreign key (patient_id)
references patient;
