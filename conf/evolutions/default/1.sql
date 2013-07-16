# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table base_pojo (
  id                        bigint not null,
  constraint pk_base_pojo primary key (id))
;

create table person (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_person primary key (id))
;

create table user (
  id                        bigint not null,
  name                      varchar(255),
  username                  varchar(255),
  constraint pk_user primary key (id))
;

create sequence base_pojo_seq;

create sequence person_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists base_pojo;

drop table if exists person;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists base_pojo_seq;

drop sequence if exists person_seq;

drop sequence if exists user_seq;

