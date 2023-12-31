CREATE TABLE credit (
  id_credit BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
   credit_code UUID NOT NULL,
   credit_value DECIMAL NOT NULL,
   day_first_installment date NOT NULL,
   number_of_installments INT NOT NULL,
   status INT,
   customer_id_customer BIGINT,
   CONSTRAINT pk_credit PRIMARY KEY (id_credit)
);

ALTER TABLE credit ADD CONSTRAINT uc_credit_creditcode UNIQUE (credit_code);

ALTER TABLE credit ADD CONSTRAINT FK_CREDIT_ON_CUSTOMER_IDCUSTOMER FOREIGN KEY (customer_id_customer) REFERENCES client (id_customer);