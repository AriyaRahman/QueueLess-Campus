package controller;

import model.*;
import service.AnnouncementManager;
import service.NotificationManager;
import service.QueueManager;
import service.ServiceManager;
import storage.FeedbackRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Handles student actions: browsing services, joining/leaving queues,
 * checking tokens, reading announcements and leaving feedback.
 */
public class StudentController {

    private final Student student;
    private final QueueManager queueManager;
    private final ServiceManager serviceManager;
    private final AnnouncementManager announcementManager;
    private final NotificationManager notificationManager;
    private final FeedbackRepository feedbackRepository;

    public StudentController(Student student, QueueManager queueManager, ServiceManager serviceManager,
                              AnnouncementManager announcementManager, NotificationManager notificationManager,
                              FeedbackRepository feedbackRepository) {
        this.student = student;
        this.queueManager = queueManager;
        this.serviceManager = serviceManager;
        this.announcementManager = announcementManager;
        this.notificationManager = notificationManager;
        this.feedbackRepository = feedbackRepository;
    }

    public Student getStudent() { return student; }

    public List<Service> getActiveServices() {
        return serviceManager.listActive();
    }

    public Token joinQueue(String serviceId) {
        Token token = queueManager.joinQueue(student.getStudentId(), serviceId);
        notificationManager.notify(student.getStudentId(),
                "Joined queue for token #" + token.getTokenNumber());
        return token;
    }

    public boolean cancelToken(String tokenId) {
        return queueManager.cancelToken(tokenId);
    }

    public List<Token> getMyTokens() {
        return queueManager.getTokensForStudent(student.getStudentId());
    }

    public int getQueuePosition(String tokenId, String serviceId) {
        return queueManager.getPosition(tokenId, serviceId);
    }

    public List<Announcement> getAnnouncements() {
        return announcementManager.getAllNewestFirst();
    }

    public List<String> getNotifications() {
        return notificationManager.getNotifications(student.getStudentId());
    }

    public void clearNotifications() {
        notificationManager.clearNotifications(student.getStudentId());
    }

    public Feedback submitFeedback(String serviceId, int rating, String comment) {
        String feedbackId = "FB-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Feedback feedback = new Feedback(feedbackId, student.getStudentId(), serviceId,
                rating, comment, LocalDateTime.now());
        feedbackRepository.add(feedback);
        return feedback;
    }

    public Optional<Service> findService(String serviceId) {
        return serviceManager.findById(serviceId);
    }
}
