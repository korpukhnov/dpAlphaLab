create table PERSON
(
    ID         serial constraint "PERSON_pk" primary key,
    FIRST_NAME varchar not null,
    LAST_NAME  varchar not null
);

create table DOCUMENT
(
    ID     serial
        constraint "DOCUMENT_pk"
            primary key,
    TYPE   varchar,
    NUMBER varchar
    PERSON_ID integer;
    ACTIVE bool;
    constraint document_person_fk
            foreign key (PERSON_ID) references person;
);