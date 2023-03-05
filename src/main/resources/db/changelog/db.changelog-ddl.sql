--liquibase formatted sql
--preconditions onFail:CONTINUE onError:CONTINUE
CREATE TABLE IF NOT EXISTS "USER"
(
    user_id     uuid primary key,
    username   varchar(250) not null,
    "password" varchar(250) not null
);
--preconditions onFail:CONTINUE onError:CONTINUE
CREATE TABLE IF NOT EXISTS "ROLE"
(
    role_id   uuid primary key,
    rolename varchar(250) not null
);
--preconditions onFail:CONTINUE onError:CONTINUE
CREATE TABLE IF NOT EXISTS "USER_ROLE"
(
    user_id uuid not null,
    role_id uuid not null,
    primary key (user_id, role_id),
    foreign key (user_id) references "USER" (user_id),
    foreign key (role_id) references "ROLE" (role_id)
);
--preconditions onFail:CONTINUE onError:CONTINUE
CREATE TABLE IF NOT EXISTS "PERSON"
(
    person_id  uuid primary key,
    firstname varchar(250) not null,
    lastname  varchar(250) not null,
    team      varchar(250),
    "rarity"   varchar(250),
    balance   integer      check(balance >= 0),
    user_id   uuid         not null unique,
    foreign key (user_id) references "USER" (user_id)
);
--preconditions onFail:CONTINUE onError:CONTINUE
CREATE TABLE IF NOT EXISTS "CARD"
(
    card_id     uuid primary key,
    person_id uuid not null unique,
    foreign key (person_id) references "PERSON" (person_id)
);
--preconditions onFail:CONTINUE onError:CONTINUE
CREATE TABLE IF NOT EXISTS "CONTENT"
(
    content_id uuid primary key,
    "name"   varchar(250) not null,
    cost     integer      check(cost >= 1)
);
--preconditions onFail:CONTINUE onError:CONTINUE
CREATE TABLE IF NOT EXISTS "CARD_CONTENT"
(
    card_id    uuid not null,
    content_id uuid not null,
    primary key (card_id, content_id),
    foreign key (card_id) references "CARD" (card_id),
    foreign key (content_id) references "CONTENT" (content_id)
);
--preconditions onFail:CONTINUE onError:CONTINUE
CREATE TABLE IF NOT EXISTS "MARKET"
(
    market_id  uuid primary key,
    country varchar(250) not null,
    city    varchar(250) not null,
    cost    integer      check(cost >= 0)
    );
--preconditions onFail:CONTINUE onError:CONTINUE
CREATE TABLE IF NOT EXISTS "PERSON_MARKET"
(
    person_id uuid not null,
    market_id   uuid not null,
    primary key (person_id, market_id),
    foreign key (person_id) references "PERSON" (person_id),
    foreign key (market_id) references "MARKET" (market_id)
);