-- V1__schema.sql
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

create schema if not exists dw;

create table if not exists dw.dim_customer (
  customer_key bigserial primary key,
  customer_id text unique not null,
  customer_name text not null
);

create table if not exists dw.dim_product (
  product_key bigserial primary key,
  product_id text unique not null,
  product_name text not null,
  category text not null
);

create table if not exists dw.dim_date (
  date_key int primary key,
  date_value date not null,
  year int not null,
  month int not null,
  day int not null
);

create table if not exists dw.fact_sales (
  sale_id bigint not null,
  date_key int not null references dw.dim_date(date_key),
  customer_key bigint not null references dw.dim_customer(customer_key),
  product_key bigint not null references dw.dim_product(product_key),
  channel text not null,
  quantity int not null,
  gross_amount numeric(12,2) not null,
  primary key (sale_id, product_key)
);
