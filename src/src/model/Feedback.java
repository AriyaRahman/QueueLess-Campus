package model;

import java.time.LocalDateTime;

/**
 * Represents feedback submitted by a student about a completed service visit.
 */
public class Feedback {
    private final String feedbackId;
    private final String studentId;
    private final String serviceId;
    private final int rating;
    private final String comment;
    private final LocalDateTime date;

    public Feedback(String feedbackId, String studentId, String serviceId, int rating,
                     String comment, LocalDateTime date) {
        this.feedbackId = feedbackId;
        this.studentId = studentId;
        this.serviceId = serviceId;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public String getFeedbackId() { return feedbackId; }
    public String getStudentId() { return studentId; }
    public String getServiceId() { return serviceId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDateTime getDate() { return date; }
}
