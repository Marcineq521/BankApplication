CREATE TABLE users(
     id BIGSERIAL PRIMARY KEY,
     name VARCHAR(50) NOT NULL,
     surname VARCHAR(50) NOT NULL,
     pesel VARCHAR(11) NOT NULL UNIQUE,
     email VARCHAR(100) NOT NULL UNIQUE,
     password VARCHAR(100) NOT NULL,
     created_at TIMESTAMP NOT NULL
 );

 CREATE TABLE accounts(
     id BIGSERIAL PRIMARY KEY,
     account_number VARCHAR(28) NOT NULL UNIQUE,
     balance DECIMAL(19,2) NOT NULL,
     account_type VARCHAR(20) NOT NULL,
     owner_id BIGINT NOT NULL,
     created_at TIMESTAMP NOT NULL,

     CONSTRAINT fk_accounts_owner
     FOREIGN KEY (owner_id)
     REFERENCES users(id)
 );

  CREATE TABLE transactions(
       id BIGSERIAL PRIMARY KEY,
       transaction_type VARCHAR(30) NOT NULL,
       amount DECIMAL(19,2) NOT NULL,
       description VARCHAR(500),
       transaction_date TIMESTAMP NOT NULL,
       account_id BIGINT NOT NULL,

       CONSTRAINT fk_transactions_account
       FOREIGN KEY (account_id)
       REFERENCES accounts(id)
 );
