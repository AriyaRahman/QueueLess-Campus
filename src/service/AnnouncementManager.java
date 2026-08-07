package service;

import model.Announcement;
import storage.AnnouncementRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Business logic for posting and retrieving campus-wide announcements.
 */
public class AnnouncementManager {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementManager(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public Announcement post(String title, String message, String postedBy) {
        String id = "ANN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Announcement announcement = new Announcement(id, title, message, postedBy, LocalDateTime.now());
        announcementRepository.add(announcement);
        return announcement;
    }

    /** Returns announcements newest first. */
    public List<Announcement> getAllNewestFirst() {
        List<Announcement> all = announcementRepository.findAll();
        all.sort(Comparator.comparing(Announcement::getDate).reversed());
        return all;
    }
}
