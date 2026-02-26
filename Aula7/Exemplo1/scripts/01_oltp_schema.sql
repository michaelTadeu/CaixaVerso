create schema if not exists oltp;

create table if not exists oltp.customer (
  id text primary key,
  name text not null,
  created_at timestamptz not null default now()
);

create table if not exists oltp.product (
  id text primary key,
  name text not null,
  category text not null,
  price numeric(12,2) not null
);

create table if not exists oltp.sale (
  id bigserial primary key,
  customer_id text not null references oltp.customer(id),
  occurred_at timestamptz not null,
  channel text not null,
  constraint ck_channel check (channel in ('web','app','pos'))
);

create table if not exists oltp.sale_item (
  sale_id bigint not null references oltp.sale(id),
  product_id text not null references oltp.product(id),
  quantity int not null,
  unit_price numeric(12,2) not null,
  primary key (sale_id, product_id)
);
