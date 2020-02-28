-- postgres flyway migration script for demo application
CREATE TABLE medications (
   id SERIAL PRIMARY KEY,
   medication_name VARCHAR NOT NULL
);
