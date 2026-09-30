INSERT INTO customers (id, name, email, phone) VALUES
  (1, 'Asha Rao', 'asha.rao@example.com', '9876543210'),
  (2, 'Vikram Singh', 'vikram.singh@example.com', '9876500000'),
  (3, 'Priya Menon', 'priya.menon@example.com', '9876511111');

INSERT INTO orders (id, customer_id, status, total_amount, created_at, updated_at) VALUES
  (1, 1, 'DELIVERED', 2500.00, '2026-07-01 10:00:00', '2026-07-03 15:00:00'),
  (2, 2, 'DELIVERED', 1500.00, '2026-07-05 11:30:00', '2026-07-07 09:00:00'),
  (3, 1, 'PLACED', 750.00, '2026-08-01 09:15:00', null);

INSERT INTO order_items (id, order_id, product_name, quantity, unit_price) VALUES
  (1, 1, 'Wireless Mouse', 2, 500.00),
  (2, 1, 'Keyboard', 1, 1500.00),
  (3, 2, 'Monitor Stand', 1, 1500.00),
  (4, 3, 'USB-C Cable', 3, 250.00);

INSERT INTO payments (id, order_id, amount, status, transaction_ref, paid_at) VALUES
  (1, 1, 2500.00, 'SUCCESS', 'TXN-1001', '2026-07-01 10:05:00'),
  (2, 2, 1500.00, 'SUCCESS', 'TXN-1002', '2026-07-05 11:35:00');

ALTER TABLE customers ALTER COLUMN id RESTART WITH 4;
ALTER TABLE orders ALTER COLUMN id RESTART WITH 4;
ALTER TABLE order_items ALTER COLUMN id RESTART WITH 5;
ALTER TABLE payments ALTER COLUMN id RESTART WITH 3;
