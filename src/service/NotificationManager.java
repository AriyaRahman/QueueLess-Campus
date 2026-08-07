package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Holds transient, in-memory notifications for students
 */
public class NotificationManager {

    private final Map<String, List<String>> notificationsByStudent = new HashMap<>();

    public void notify(String studentId, String message) {
        notificationsByStudent.computeIfAbsent(studentId, id -> new ArrayList<>()).add(message);
    }

    public List<String> getNotifications(String studentId) {
        return new ArrayList<>(notificationsByStudent.getOrDefault(studentId, new ArrayList<>()));
    }

    public void clearNotifications(String studentId) {
        notificationsByStudent.remove(studentId);
    }
}
