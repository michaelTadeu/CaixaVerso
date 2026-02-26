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
