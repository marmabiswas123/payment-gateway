CREATE DATABASE IF NOT EXISTS gw_demo CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE gw_demo;

CREATE TABLE merchants (
  id VARCHAR(64) PRIMARY KEY,
  name VARCHAR(255),
  api_key VARCHAR(255),
  secret_plain VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cards (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  merchant_id VARCHAR(64),
  token VARCHAR(128) UNIQUE,
  encrypted_pan BLOB,
  iv VARBINARY(32),
  brand VARCHAR(50),
  exp_month TINYINT,
  exp_year SMALLINT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (merchant_id) REFERENCES merchants(id)
);

CREATE TABLE payments (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  payment_id VARCHAR(64) UNIQUE,
  merchant_id VARCHAR(64),
  order_id VARCHAR(128),
  amount BIGINT,
  currency VARCHAR(8),
  status VARCHAR(32),
  card_token VARCHAR(128),
  raw_psp_response JSON,
  idempotency_key VARCHAR(128),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (merchant_id) REFERENCES merchants(id)
);

CREATE TABLE webhook_logs (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  source VARCHAR(64),
  payload JSON,
  signature VARCHAR(255),
  verified BOOLEAN,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
