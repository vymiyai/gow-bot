--drop table if exists public.discord_resources cascade;
--drop table if exists public.discord_user cascade;
create table if not exists public.discord_resources (id  bigserial not null, cooldown timestamp, gems int4, gold int4, spent_gems int4, spent_gold int4, v2launched int4, primary key (id));
create table if not exists public.discord_user (id  bigserial not null, access_token varchar(255), creation_date timestamp, discord_id int8, discord_username varchar(255), last_change timestamp, discord_resources_fk int8, primary key (id));
create unique index if not exists UK_discord_user_discord_id on public.discord_user (discord_id);
create unique index if not exists UK_discord_user_discord_username on public.discord_user (discord_username);
-- BEFORE CHANGING THE FK_discord_resources_discord_user ALTER TABLE STATEMENT, READ https://stackoverflow.com/questions/6801919/postgres-add-constraint-if-it-doesnt-already-exist
alter table public.discord_user drop constraint IF exists FK_discord_resources_discord_user;
alter table public.discord_user add constraint FK_discord_resources_discord_user foreign key (discord_resources_fk) references public.discord_resources;
