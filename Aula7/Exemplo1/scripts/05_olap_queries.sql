select
  c.customer_name,
  sum(f.gross_amount) as total
from dw.fact_sales f
join dw.dim_customer c on c.customer_key = f.customer_key
group by c.customer_name
order by total desc;

select
  d.date_value,
  f.channel,
  sum(f.gross_amount) as total
from dw.fact_sales f
join dw.dim_date d on d.date_key = f.date_key
group by d.date_value, f.channel
order by d.date_value, f.channel;

select
  p.category,
  sum(f.gross_amount) as total
from dw.fact_sales f
join dw.dim_product p on p.product_key = f.product_key
group by rollup (p.category)
order by p.category;

select
  p.category,
  f.channel,
  sum(f.gross_amount) as total
from dw.fact_sales f
join dw.dim_product p on p.product_key = f.product_key
group by cube (p.category, f.channel)
order by p.category nulls last, f.channel nulls last;
