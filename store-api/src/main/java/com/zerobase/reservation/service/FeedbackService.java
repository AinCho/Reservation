package com.zerobase.reservation.service;

import com.zerobase.reservation.entity.Feedback;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FeedbackService {
    Feedback addFeedback(Feedback feedback, int customerId, String key);
    Feedback updateFeedback(Feedback feedback, String key);
    Feedback viewFeedback(int feedbackId);
    List<Feedback> viewAllFeedback();
}
