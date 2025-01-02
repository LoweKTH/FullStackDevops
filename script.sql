create table Images
(
    id          int auto_increment
        primary key,
    userId      int          not null,
    title       varchar(255) not null,
    description varchar(255) not null,
    imagePath   varchar(255) not null,
    uploadedAt  datetime     not null
);

create table conversation
(
    id           bigint auto_increment
        primary key,
    created_at   datetime(6) null,
    recipient_id bigint      null,
    sender_id    bigint      null
);

create table diagnosis
(
    id             bigint auto_increment
        primary key,
    description    varchar(255) null,
    diagnosis_date datetime(6)  null,
    doctorstaff_id bigint       null,
    name           varchar(255) null,
    patient_id     bigint       null
);

create table doctor
(
    id                     bigint auto_increment
        primary key,
    email                  varchar(255) null,
    firstname              varchar(255) null,
    lastname               varchar(255) null,
    phone_number           varchar(255) null,
    social_security_number bigint       null,
    specialty              varchar(255) null,
    user_id                bigint       null
);

create table message
(
    id              bigint auto_increment
        primary key,
    content         varchar(255) not null,
    sender_id       bigint       null,
    timestamp       datetime(6)  null,
    conversation_id bigint       not null,
    constraint FK6yskk3hxw5sklwgi25y6d5u1l
        foreign key (conversation_id) references conversation (id)
);

create table note
(
    id             bigint auto_increment
        primary key,
    content        varchar(255) null,
    created_at     datetime(6)  null,
    doctorstaff_id bigint       null,
    patient_id     bigint       null,
    staff_id       bigint       null
);

create table patient
(
    id                     bigint auto_increment
        primary key,
    address                varchar(255) null,
    date_of_birth          datetime(6)  null,
    email                  varchar(255) null,
    firstname              varchar(255) null,
    gender                 varchar(255) null,
    lastname               varchar(255) null,
    phone_number           varchar(255) null,
    social_security_number bigint       null,
    user_id                bigint       null
);

create table patient_diagnoses
(
    patient_id   bigint not null,
    diagnoses_id bigint not null,
    constraint UKhlgtjlehuce6cft746p4wa5o5
        unique (diagnoses_id),
    constraint FK5dv88ykf93hpwevf6jdquny3r
        foreign key (patient_id) references patient (id),
    constraint FKbo220kgk0tl3d8ors9kxt5jcd
        foreign key (diagnoses_id) references diagnosis (id)
);

create table patient_notes
(
    patient_id bigint not null,
    notes_id   bigint not null,
    constraint UKk10gm6e3n6gku1dpl9pw86mqf
        unique (notes_id),
    constraint FKcemws41oih1bj1aqopcqv52me
        foreign key (patient_id) references patient (id),
    constraint FKik5otqqtypry3g3abbr1oxcrn
        foreign key (notes_id) references note (id)
);

create table staff
(
    id                     bigint auto_increment
        primary key,
    description            varchar(255) null,
    email                  varchar(255) null,
    firstname              varchar(255) null,
    lastname               varchar(255) null,
    phone_number           varchar(255) null,
    social_security_number bigint       null,
    user_id                bigint       null
);

create table user
(
    id       bigint auto_increment
        primary key,
    email    varchar(255) null,
    password varchar(255) null,
    role     varchar(255) null,
    username varchar(255) null,
    constraint UKob8kqyqqgmefl0aco34akdtpe
        unique (email),
    constraint UKsb8bbouer5wak8vyiiy4pf2bx
        unique (username)
);


