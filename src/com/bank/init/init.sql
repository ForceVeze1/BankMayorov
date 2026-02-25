CREATE TABLE accounts (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    account_number CHAR(20) UNIQUE NOT NULL,
    balance DOUBLE PRECISION DEFAULT 0.0,
    bonus_received BOOLEAN DEFAULT FALSE,
    account_type VARCHAR(50) -- BASE, PREMIUM, VIP
);
