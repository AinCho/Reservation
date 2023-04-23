package com.zerobase.reservation.service;

import com.zerobase.reservation.entity.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservationService {
    Reservation addReservation(Reservation reservation, int customerId, String key);
    Reservation viewReservation(int reservationId);
    List<Reservation> viewAllReservation();
    Reservation deleteReservationIdReservation(int reservationId, String key);
    Reservation deleteCustomerIdReservation(int customerId, String key);
    Reservation updateReservation(Reservation reservation, Integer customerId, String key);
}
