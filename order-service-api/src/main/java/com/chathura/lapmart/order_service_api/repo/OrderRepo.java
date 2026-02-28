package com.chathura.lapmart.order_service_api.repo;

import com.chathura.lapmart.order_service_api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

}