insert into oltp.customer(id, name) values
  ('c-001','Ana'),
  ('c-002','Bruno'),
  ('c-003','Carla')
on conflict (id) do nothing;

insert into oltp.product(id, name, category, price) values
  ('p-001','Café','Bebidas', 12.50),
  ('p-002','Chocolate','Doces', 9.90),
  ('p-003','Biscoito','Doces', 7.50)
on conflict (id) do nothing;

insert into oltp.sale(customer_id, occurred_at, channel) values
  ('c-001', now() - interval '2 days', 'web'),
  ('c-002', now() - interval '2 days', 'app'),
  ('c-001', now() - interval '1 day', 'pos'),
  ('c-003', now() - interval '1 day', 'web');

insert into oltp.sale_item(sale_id, product_id, quantity, unit_price) values
  (1,'p-001',2,12.50),
  (1,'p-002',1,9.90),
  (2,'p-003',3,7.50),
  (3,'p-001',1,12.50),
  (4,'p-002',2,9.90)
on conflict do nothing;
