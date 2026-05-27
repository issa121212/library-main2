CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO users (username, password_hash)
VALUES ('isaac', crypt('12345678', gen_salt('bf')));
