package com.chathura.lapmart.order_service_api.repo;

import com.chathura.lapmart.order_service_api.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * FROM orders WHERE id LIKE %?1% OR total_amount LIKE %?1% ORDER BY id DESC",
            nativeQuery = true)
    Page<Order> findAllWithSearch(String searchText, Pageable pageable);
}