package com.example.api;

import com.example.shared.CustomerTotal;
import com.example.shared.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, String> {

    @Query("""
        select new com.example.shared.CustomerTotal(p.customerId, sum(p.amount))
        from Purchase p
        group by p.customerId
        order by sum(p.amount) desc
    """)
    List<CustomerTotal> totalsByCustomer();

    @Query("""
        select new com.example.shared.CustomerTotal(p.merchant, sum(p.amount))
        from Purchase p
        group by p.merchant
        order by sum(p.amount) desc
    """)
    List<CustomerTotal> totalsByMerchant();
}
