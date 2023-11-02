create sequence passphrase_seq start with 1 increment by 50;
create sequence user_profile_seq start with 1 increment by 50;
create sequence word_seq start with 1 increment by 50;
create table passphrase
(
    created       timestamp(6) with time zone not null,
    modified      timestamp(6) with time zone not null,
    passphrase_id bigint                      not null,
    user_id       bigint                      not null,
    external_key  UUID                        not null unique,
    name          varchar(255)                not null,
    primary key (passphrase_id),
    constraint UKpbu2ldv6l1eaabxbjpgu2i7ca unique (user_id, name)
);
create table user_profile
(
    created         timestamp(6) with time zone not null,
    modified        timestamp(6) with time zone not null,
    user_profile_id bigint                      not null,
    external_key    UUID                        not null unique,
    oauth_key       varchar(30)                 not null unique,
    display_name    varchar(255),
    primary key (user_profile_id)
);
create table word
(
    position      integer      not null,
    passphrase_id bigint       not null,
    word_id       bigint       not null,
    content       varchar(255) not null,
    primary key (word_id)
);
alter table passphrase
    add constraint FKiwr3cg06n40nkoa4m0kligpnv foreign key (user_id) references user_profile;
alter table word
    add constraint FKpsougjtl2ab3j1new78wig2rv foreign key (passphrase_id) references passphrase;
