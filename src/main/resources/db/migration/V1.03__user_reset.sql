alter table adminlte.user_register change column verify_code code varchar(255);
alter table adminlte.user_register change column verify_count count integer;
alter table adminlte.user_register change column verify_url url varchar(255);
create table user_reset (id bigint not null auto_increment, code varchar(255), count integer, email varchar(255), expires_at bigint, name varchar(255), url varchar(255), primary key (id)) engine=MyISAM;
alter table user_reset add constraint UK_46tw1du5ysyq8t19w4leu5hj1 unique (email);
alter table user_reset add constraint UK_5v2w3frcx30j89cx9rsn3p3u6 unique (name);