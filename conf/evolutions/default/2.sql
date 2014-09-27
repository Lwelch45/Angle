
# --- !Ups

create table data_source (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  score                     double,
  constraint pk_data_source primary key (id))
;

create table entity_analysis (
  id                        bigint auto_increment not null,
  category                  varchar(255),
  theme                     varchar(255),
  sentiment                 integer,
  constraint pk_entity_analysis primary key (id))
;

create table key_entity (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  sentiment                 integer,
  evidence                  integer,
  confidence                double,
  constraint pk_key_entity primary key (id))
;

create table news_article (
  id                        bigint auto_increment not null,
  api                       varchar(255),
  title                     varchar(255),
  desription                varchar(255),
  link                      varchar(255),
  hash                      varchar(255),
  pub_date                  datetime,
  source_id                 bigint,
  constraint pk_news_article primary key (id))
;

create table news_source (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  description               varchar(255),
  feed                      varchar(255),
  last_updated              datetime,
  constraint pk_news_source primary key (id))
;

alter table news_article add constraint fk_news_article_source_1 foreign key (source_id) references news_source (id) on delete restrict on update restrict;
create index ix_news_article_source_1 on news_article (source_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;



SET FOREIGN_KEY_CHECKS=1;

