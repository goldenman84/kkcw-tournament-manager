# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table bracket (
  id                        bigint not null,
  name                      varchar(255),
  round_id                  bigint,
  constraint pk_bracket primary key (id))
;

create table category (
  id                        bigint not null,
  name                      varchar(255),
  mode                      integer,
  tournament_id             bigint,
  constraint ck_category_mode check (mode in (0,1)),
  constraint pk_category primary key (id))
;

create table fight (
  id                        bigint not null,
  bracket_id                bigint,
  result_id                 bigint,
  fightarea_id              bigint,
  state                     integer,
  constraint ck_fight_state check (state in (0,1)),
  constraint pk_fight primary key (id))
;

create table fight_area (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_fight_area primary key (id))
;

create table fighter (
  id                        bigint not null,
  firstname                 varchar(255),
  lastname                  varchar(255),
  age                       integer,
  size                      integer,
  category_id               bigint,
  constraint pk_fighter primary key (id))
;

create table result (
  id                        bigint not null,
  fighter_one_assessment    integer,
  fighter_two_assessment    integer,
  fighter_one_condition     integer,
  fighter_two_condition     integer,
  constraint ck_result_fighter_one_assessment check (fighter_one_assessment in (0,1,2,3,4)),
  constraint ck_result_fighter_two_assessment check (fighter_two_assessment in (0,1,2,3,4)),
  constraint ck_result_fighter_one_condition check (fighter_one_condition in (0,1)),
  constraint ck_result_fighter_two_condition check (fighter_two_condition in (0,1)),
  constraint pk_result primary key (id))
;

create table round (
  id                        bigint not null,
  category_id               bigint,
  constraint pk_round primary key (id))
;

create table tournament (
  id                        bigint not null,
  name                      varchar(255),
  date                      timestamp,
  constraint pk_tournament primary key (id))
;


create table category_fight_area (
  category_id                    bigint not null,
  fight_area_id                  bigint not null,
  constraint pk_category_fight_area primary key (category_id, fight_area_id))
;

create table fight_fighter (
  fight_id                       bigint not null,
  fighter_id                     bigint not null,
  constraint pk_fight_fighter primary key (fight_id, fighter_id))
;
create sequence bracket_seq;

create sequence category_seq;

create sequence fight_seq;

create sequence fight_area_seq;

create sequence fighter_seq;

create sequence result_seq;

create sequence round_seq;

create sequence tournament_seq;

alter table bracket add constraint fk_bracket_round_1 foreign key (round_id) references round (id) on delete restrict on update restrict;
create index ix_bracket_round_1 on bracket (round_id);
alter table category add constraint fk_category_tournament_2 foreign key (tournament_id) references tournament (id) on delete restrict on update restrict;
create index ix_category_tournament_2 on category (tournament_id);
alter table fight add constraint fk_fight_bracket_3 foreign key (bracket_id) references bracket (id) on delete restrict on update restrict;
create index ix_fight_bracket_3 on fight (bracket_id);
alter table fight add constraint fk_fight_result_4 foreign key (result_id) references result (id) on delete restrict on update restrict;
create index ix_fight_result_4 on fight (result_id);
alter table fight add constraint fk_fight_fightarea_5 foreign key (fightarea_id) references fight_area (id) on delete restrict on update restrict;
create index ix_fight_fightarea_5 on fight (fightarea_id);
alter table fighter add constraint fk_fighter_category_6 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_fighter_category_6 on fighter (category_id);
alter table round add constraint fk_round_category_7 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_round_category_7 on round (category_id);



alter table category_fight_area add constraint fk_category_fight_area_catego_01 foreign key (category_id) references category (id) on delete restrict on update restrict;

alter table category_fight_area add constraint fk_category_fight_area_fight__02 foreign key (fight_area_id) references fight_area (id) on delete restrict on update restrict;

alter table fight_fighter add constraint fk_fight_fighter_fight_01 foreign key (fight_id) references fight (id) on delete restrict on update restrict;

alter table fight_fighter add constraint fk_fight_fighter_fighter_02 foreign key (fighter_id) references fighter (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists bracket;

drop table if exists category;

drop table if exists category_fight_area;

drop table if exists fight;

drop table if exists fight_fighter;

drop table if exists fight_area;

drop table if exists fighter;

drop table if exists result;

drop table if exists round;

drop table if exists tournament;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists bracket_seq;

drop sequence if exists category_seq;

drop sequence if exists fight_seq;

drop sequence if exists fight_area_seq;

drop sequence if exists fighter_seq;

drop sequence if exists result_seq;

drop sequence if exists round_seq;

drop sequence if exists tournament_seq;

