-- postgres flyway migration script for demo application
CREATE TABLE patients (
   id SERIAL PRIMARY KEY,
   first_name VARCHAR NOT NULL,
   last_name VARCHAR NOT NULL
);

