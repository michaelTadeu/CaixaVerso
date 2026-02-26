package com.ada.dw.worker.etl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class EtlService {

  private final JdbcTemplate jdbc;

  public EtlService(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  /**
   * Executa uma carga idempotente (pode rodar várias vezes).
   * Retorna o total de linhas afetadas (didático, não "exato" em todos os bancos).
   */
  public int runOnce() {
    int rows = 0;

    rows += jdbc.update(
      "insert into dw.dim_customer(customer_id, customer_name) " +
      "select c.id, c.name from oltp.customer c " +
      "on conflict (customer_id) do update set customer_name = excluded.customer_name"
    );

    rows += jdbc.update(
      "insert into dw.dim_product(product_id, product_name, category) " +
      "select p.id, p.name, p.category from oltp.product p " +
      "on conflict (product_id) do update set " +
      "product_name = excluded.product_name, category = excluded.category"
    );

    rows += jdbc.update(
      "insert into dw.dim_date(date_key, date_value, year, month, day) " +
      "select distinct " +
      "cast(to_char(s.occurred_at::date, 'YYYYMMDD') as int) as date_key, " +
      "s.occurred_at::date as date_value, " +
      "extract(year from s.occurred_at)::int as year, " +
      "extract(month from s.occurred_at)::int as month, " +
      "extract(day from s.occurred_at)::int as day " +
      "from oltp.sale s " +
      "on conflict (date_key) do nothing"
    );

    rows += jdbc.update(
      "insert into dw.fact_sales(sale_id, date_key, customer_key, product_key, channel, quantity, gross_amount) " +
      "select s.id as sale_id, " +
      "cast(to_char(s.occurred_at::date, 'YYYYMMDD') as int) as date_key, " +
      "dc.customer_key, dp.product_key, s.channel, si.quantity, " +
      "(si.quantity * si.unit_price)::numeric(12,2) as gross_amount " +
      "from oltp.sale s " +
      "join oltp.sale_item si on si.sale_id = s.id " +
      "join dw.dim_customer dc on dc.customer_id = s.customer_id " +
      "join dw.dim_product dp on dp.product_id = si.product_id " +
      "on conflict (sale_id, product_key) do update set " +
      "quantity = excluded.quantity, gross_amount = excluded.gross_amount, " +
      "channel = excluded.channel, date_key = excluded.date_key, customer_key = excluded.customer_key"
    );

    return rows;
  }
}
