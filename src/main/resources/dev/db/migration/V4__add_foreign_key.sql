ALTER TABLE orders ADD FOREIGN KEY (payment_id) REFERENCES order_payments(id);
