package com.zerobase.reservation.user.domain.repository;

import com.zerobase.reservation.user.domain.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByIdAndEmail(Long id, String email);

    Optional<Manager> findByEmailAndPasswordAndVerifyIsTrue(String email, String password);

    Optional<Manager> findByEmail(String email);
}
