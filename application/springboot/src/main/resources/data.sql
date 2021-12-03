create schema if not exists testdb;

create table person
(
    id       uuid UNIQUE  not null,
    username varchar(255) not null unique,
    password varchar(255) not null,
    active   boolean default false,
    primary key (id)
);

create table role
(
    id   uuid unique        not null,
    name varchar(20) unique not null,
    primary key (id)
);

create table person_role
(
    person_id uuid not null,
    role_id   uuid not null,
    CONSTRAINT FK_person_id
        FOREIGN KEY (person_id)
            REFERENCES person (id)
            ON DELETE CASCADE,
    CONSTRAINT FK_role_id
        FOREIGN KEY (role_id)
            REFERENCES role (id)
            ON DELETE NO ACTION
);

insert into person(id, username, password, active)
values ('37c19cc1-f47f-432b-bdd0-132a7d79c309',
        'person',
        '$2a$12$fCcVordu8zqZAhrcbrQyI.ft4pD3Ho60GHuUP1Uhp/j1.1Ua2QzPi',
        true),
       ('5ccdaba6-2347-4d53-b7e4-2deeba8e2ef5',
        'member',
        '$2a$12$fag58zTapph2fnvT5oTkLOHV1ZXxbcA2GtzODgJAEWlKMldyOihdO',
        true),
       ('3c9cd3cd-fe0c-42a0-a4e8-1a8a3495118e',
        'admin',
        '$2a$12$vayU87MlNBShGBgYuxd3jOu18InGg2Gf9lnzpI2uNOqA5brjyDlGu',
        true),
       ('1e772065-bac1-4272-b7be-219ae0efd711',
        'notActive',
        '$2a$12$HqghAUwADq7VReGck/iHKOwKwT8P3xXxtW0rliCjq/o0hqURObV2K',
        false);

insert into role(id, name)
values ('08fc3bc6-a0be-46e9-bc5c-bbdd0f4034b5', 'ROLE_USER'),
       ('844ac8e8-ecca-48b7-b55a-ba977808380f', 'ROLE_MEMBER'),
       ('0fe7fb44-6175-465e-81dd-d0eff3f1a845', 'ROLE_ADMIN');

insert into person_role(person_id, role_id)
values ('37c19cc1-f47f-432b-bdd0-132a7d79c309', '08fc3bc6-a0be-46e9-bc5c-bbdd0f4034b5'),
       ('5ccdaba6-2347-4d53-b7e4-2deeba8e2ef5', '08fc3bc6-a0be-46e9-bc5c-bbdd0f4034b5'),
       ('5ccdaba6-2347-4d53-b7e4-2deeba8e2ef5', '844ac8e8-ecca-48b7-b55a-ba977808380f'),
       ('3c9cd3cd-fe0c-42a0-a4e8-1a8a3495118e', '08fc3bc6-a0be-46e9-bc5c-bbdd0f4034b5'),
       ('3c9cd3cd-fe0c-42a0-a4e8-1a8a3495118e', '844ac8e8-ecca-48b7-b55a-ba977808380f'),
       ('3c9cd3cd-fe0c-42a0-a4e8-1a8a3495118e', '0fe7fb44-6175-465e-81dd-d0eff3f1a845');
