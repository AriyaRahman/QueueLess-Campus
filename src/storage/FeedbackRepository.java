package storage;

import model.Feedback;
import util.CSVExportUtil;
import util.Constants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving Feedback records to feedback.csv.
 * Format: feedbackId,studentId,serviceId,rating,comment,date
 */
public class FeedbackRepository {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(Constants.DATE_TIME_PATTERN);

    public List<Feedback> findAll() {
        List<Feedback> feedbacks = new ArrayList<>();
        for (String line : CSVExportUtil.readLines(Constants.FEEDBACK_FILE)) {
            feedbacks.add(fromLine(line));
        }
        return feedbacks;
    }

    public List<Feedback> findByService(String serviceId) {
        List<Feedback> result = new ArrayList<>();
        for (Feedback f : findAll()) {
            if (f.getServiceId().equals(serviceId)) {
                result.add(f);
            }
        }
        return result;
    }

    public void add(Feedback feedback) {
        CSVExportUtil.appendLine(Constants.FEEDBACK_FILE, toLine(feedback));
    }

    private String toLine(Feedback f) {
        return String.join(Constants.CSV_DELIMITER,
                f.getFeedbackId(),
                f.getStudentId(),
                f.getServiceId(),
                String.valueOf(f.getRating()),
                CSVExportUtil.escape(f.getComment()),
                f.getDate().format(FORMATTER));
    }

    private Feedback fromLine(String line) {
        String[] parts = line.split(Constants.CSV_DELIMITER, -1);
        return new Feedback(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]),
                parts[4], LocalDateTime.parse(parts[5], FORMATTER));
    }
}
