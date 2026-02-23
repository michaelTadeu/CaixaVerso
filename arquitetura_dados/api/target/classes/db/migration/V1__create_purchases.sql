create table if not exists purchases (
  id varchar(64) primary key,
  customer_id varchar(64) not null,
  merchant varchar(128) not null,
  amount numeric(18,2) not null,
  currency varchar(8) not null,
  ts timestamptz not null
);

create index if not exists idx_purchases_customer on purchases(customer_id);
create index if not exists idx_purchases_merchant on purchases(merchant);
create index if not exists idx_purchases_ts on purchases(ts);
