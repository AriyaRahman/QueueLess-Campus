package storage;

import model.Announcement;
import util.CSVExportUtil;
import util.Constants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving Announcement records to announcements.csv.
 * Format: announcementId,title,message,postedBy,date
 */
public class AnnouncementRepository {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN);

    public List<Announcement> findAll() {
        List<Announcement> announcements = new ArrayList<>();
        for (String line : CSVExportUtil.readLines(Constants.ANNOUNCEMENTS_FILE)) {
            announcements.add(fromLine(line));
        }
        return announcements;
    }

    public void add(Announcement announcement) {
        CSVExportUtil.appendLine(Constants.ANNOUNCEMENTS_FILE, toLine(announcement));
    }

    private String toLine(Announcement a) {
        return String.join(Constants.CSV_DELIMITER,
                a.getAnnouncementId(),
                CSVExportUtil.escape(a.getTitle()),
                CSVExportUtil.escape(a.getMessage()),
                a.getPostedBy(),
                a.getDate().format(FORMATTER));
    }

    private Announcement fromLine(String line) {
        String[] parts = line.split(Constants.CSV_DELIMITER, -1);
        return new Announcement(parts[0], parts[1], parts[2], parts[3], LocalDateTime.parse(parts[4], FORMATTER));
    }
}
