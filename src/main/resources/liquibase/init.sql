--liquibase formatted sql

--changeset newStudent:1
CREATE TABLE IF NOT EXISTS notification_tasks
(
    id                     BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id                BIGINT       NOT NULL,
    message                TEXT NOT NULL,
    notification_date_time TIMESTAMP    NOT NULL
);