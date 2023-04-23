package com.zerobase.reservation.user.domain.repository;

import com.zerobase.reservation.user.domain.model.CustomerHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface CustomerHistoryRepository extends JpaRepository<CustomerHistory, Long> {
    Optional<CustomerHistory> findFirstByCustomer_IdReservation(@RequestParam("customer_id") Long customerId);
}
