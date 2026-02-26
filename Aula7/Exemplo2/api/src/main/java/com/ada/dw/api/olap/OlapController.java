package com.ada.dw.api.olap;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OlapController {

  private final JdbcTemplate jdbc;

  public OlapController(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @GetMapping("/olap/sales/by-customer")
  public List<CustomerTotal> byCustomer() {
    String sql =
      "select c.customer_name, sum(f.gross_amount) as total " +
      "from dw.fact_sales f " +
      "join dw.dim_customer c on c.customer_key = f.customer_key " +
      "group by c.customer_name " +
      "order by total desc";
    return jdbc.query(sql, (rs, i) ->
      new CustomerTotal(rs.getString("customer_name"), rs.getBigDecimal("total"))
    );
  }

  @GetMapping("/olap/sales/by-day-channel")
  public List<DayChannelTotal> byDayChannel() {
    String sql =
      "select d.date_value, f.channel, sum(f.gross_amount) as total " +
      "from dw.fact_sales f " +
      "join dw.dim_date d on d.date_key = f.date_key " +
      "group by d.date_value, f.channel " +
      "order by d.date_value, f.channel";
    return jdbc.query(sql, (rs, i) ->
      new DayChannelTotal(rs.getObject("date_value", LocalDate.class),
                          rs.getString("channel"),
                          rs.getBigDecimal("total"))
    );
  }

  @GetMapping("/olap/sales/by-category-rollup")
  public List<CategoryTotal> byCategoryRollup() {
    String sql =
      "select p.category, sum(f.gross_amount) as total " +
      "from dw.fact_sales f " +
      "join dw.dim_product p on p.product_key = f.product_key " +
      "group by rollup (p.category) " +
      "order by p.category";
    return jdbc.query(sql, (rs, i) ->
      new CategoryTotal(rs.getString("category"), rs.getBigDecimal("total"))
    );
  }

  public record CustomerTotal(String customerName, BigDecimal total) {}
  public record DayChannelTotal(LocalDate day, String channel, BigDecimal total) {}
  public record CategoryTotal(String category, BigDecimal total) {}
}
