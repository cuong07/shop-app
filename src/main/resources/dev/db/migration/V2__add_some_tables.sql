CREATE TABLE order_payments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    note VARCHAR(255) DEFAULT '',
    total DECIMAL(16, 2),
    payment_time TIMESTAMP,
    transaction_id VARCHAR(100),
    message VARCHAR(200),
    status TINYINT(1) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
)
