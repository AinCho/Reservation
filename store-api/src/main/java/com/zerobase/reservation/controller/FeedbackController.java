package com.zerobase.reservation.controller;

import com.zerobase.reservation.entity.Feedback;
import com.zerobase.reservation.exception.FeedBackException;
import com.zerobase.reservation.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class FeedbackController {
    private final FeedbackService feedbackService;

    @PostMapping("/feedback/{customerId}")
    public ResponseEntity<Feedback> registerFeedback(@Valid @RequestBody Feedback feedback,
                                                     @PathVariable("customerId") int customerId,
                                                     @RequestParam(required = false) String key) throws FeedBackException {
        feedback.setFeedbackDateTime(LocalDateTime.now());
        return new ResponseEntity<>(feedbackService.addFeedback(feedback, customerId, key), HttpStatus.CREATED);
    }

    @PutMapping("/feedback")
    public ResponseEntity<Feedback> updateFeedback(@Valid @RequestBody Feedback feedback,
                                                   @RequestParam(required = false) String key ) throws FeedBackException{
        feedback.setFeedbackDateTime(LocalDateTime.now());
        return new ResponseEntity<>(feedbackService.updateFeedback(feedback,key),HttpStatus.OK);
    }

    @GetMapping("/feedback/{id}")
    public ResponseEntity<Feedback> getFeedback(@PathVariable("id") int feedbackId) throws FeedBackException{
        return new ResponseEntity<>(feedbackService.viewFeedback(feedbackId),HttpStatus.OK);
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<List<Feedback>> getAllFeedback() throws FeedBackException{
        List<Feedback> feedbackList = feedbackService.viewAllFeedback();
        return new ResponseEntity<>(feedbackList,HttpStatus.OK);
    }
}
