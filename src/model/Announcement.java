package model;

import java.time.LocalDateTime;

/**
 * Represents a campus-wide announcement posted by staff/admin.
 */
public class Announcement {
    private final String announcementId;
    private final String title;
    private final String message;
    private final String postedBy;
    private final LocalDateTime date;

    public Announcement(String announcementId, String title, String message, String postedBy, LocalDateTime date) {
        this.announcementId = announcementId;
        this.title = title;
        this.message = message;
        this.postedBy = postedBy;
        this.date = date;
    }

    public String getAnnouncementId() { return announcementId; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getPostedBy() { return postedBy; }
    public LocalDateTime getDate() { return date; }
}
