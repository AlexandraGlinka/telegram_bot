CREATE TABLE IF NOT EXISTS notification_task (
    id serial PRIMARY KEY,
    user_id bigint NOT NULL,
    message character varying(255) NOT NULL,
    message_date TIMESTAMP without time zone
);