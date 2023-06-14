CREATE TABLE client (
  id_custumer BIGINT NOT NULL,
   first_name VARCHAR(255) NOT NULL,
   last_name VARCHAR(255) NOT NULL,
   cpf VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL,
   password VARCHAR(255) NOT NULL,
   zip_code VARCHAR(255) NOT NULL,
   street VARCHAR(255) NOT NULL,
   CONSTRAINT pk_client PRIMARY KEY (id_custumer)
);

ALTER TABLE client ADD CONSTRAINT uc_client_cpf UNIQUE (cpf);

ALTER TABLE client ADD CONSTRAINT uc_client_email UNIQUE (email);