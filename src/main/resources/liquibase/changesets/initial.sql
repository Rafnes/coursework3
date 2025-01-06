-- liqiubase formatted sql

-- changeset vdineka:1

CREATE TABLE IF NOT EXISTS notification_task (
    id BIGSERIAL PRIMARY KEY,
    chat_id VARCHAR(255) NOT NULL,
    text VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    notification_date TIMESTAMP
);