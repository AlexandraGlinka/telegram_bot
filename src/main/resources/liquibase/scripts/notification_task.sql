CREATE TABLE notification_task (
    id serial PRIMARY KEY,
    userId TEXT NOT NULL,
    messageText TEXT NOT NULL,
    messageDate TIMESTAMP
);