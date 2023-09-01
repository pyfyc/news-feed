--liquibase formatted sql

-- changeset alexeym75:1
CREATE TABLE IF NOT EXISTS categories
(
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR
);

CREATE TABLE IF NOT EXISTS feeds
(
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR,
    description     VARCHAR,
    publish_date    DATE,
    category_id     INT
);
