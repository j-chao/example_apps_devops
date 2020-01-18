-- postgres flyway migration script for demo application

CREATE TABLE patients (
   id SERIAL PRIMARY KEY,
   firstName VARCHAR NOT NULL,
   lastName VARCHAR NOT NULL
);
