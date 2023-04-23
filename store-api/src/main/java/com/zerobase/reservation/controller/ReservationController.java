package com.zerobase.reservation.controller;

import com.zerobase.reservation.entity.Reservation;
import com.zerobase.reservation.exception.ReservationException;
import com.zerobase.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/reservation/{customerId}")
    public ResponseEntity<Reservation> addReservationHandler(@PathVariable("customerId") int customerId,
                                                             @RequestParam(required = false) String key,
                                                             @RequestBody Reservation reservation) throws ReservationException {
        reservation.setReservationTime(LocalTime.now());
        Reservation saveReservation = reservationService.addReservation(reservation, customerId, key);

        return new ResponseEntity<Reservation>(saveReservation, HttpStatus.CREATED);
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<Reservation> getReservationByReservationIdHandler(@PathVariable("reservationId") int reservationId)
            throws ReservationException {
        Reservation reservation = reservationService.viewReservation(reservationId);

        return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> viewAllReservationHandler() throws ReservationException {
        List<Reservation> reservations = reservationService.viewAllReservation();

        return new ResponseEntity<List<Reservation>>(reservations, HttpStatus.OK);
    }

    @DeleteMapping("/reservation/{customerId}")
    public ResponseEntity<Reservation> deleteReservationByCustomerIdRollHandler(@PathVariable("customerId") int customerId,
                                                                            @RequestParam(required = false) String key) throws ReservationException {
        Reservation reservations = reservationService.deleteCustomerIdReservation(customerId, key);

        return new ResponseEntity<Reservation>(reservations, HttpStatus.OK);
    }

    @DeleteMapping("/reservation/{reservationId}")
    public ResponseEntity<Reservation> deleteReservationByRollHandler(@PathVariable("reservationId") int reservationId,
                                                                      @RequestParam(required = false) String key) throws ReservationException {
        Reservation reservation = reservationService.deleteReservationIdReservation(reservationId, key);

        return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
    }

    @PutMapping("/reservation/{customerId}")
    public ResponseEntity<Reservation> updateReservationHandler(@PathVariable("customerId") Integer customerId,
                                                                @RequestParam(required = false) String key,
                                                                @RequestBody Reservation reservation) throws ReservationException {
        reservation.setReservationTime(LocalTime.now());
        Reservation updatedReservation = reservationService.updateReservation(reservation, customerId, key);

            return new ResponseEntity<Reservation>(updatedReservation, HttpStatus.OK);
    }
}
