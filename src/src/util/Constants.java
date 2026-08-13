package util;

/**
 Central place for file paths and fixed configuration values.
 */
public final class Constants {

    private Constants() { } // prevent instantiation

    public static final String DATA_DIR = "data";

    public static final String STUDENTS_FILE = DATA_DIR + "/students.csv";
    public static final String STAFF_FILE = DATA_DIR + "/staff.csv";
    public static final String SERVICES_FILE = DATA_DIR + "/services.csv";
    public static final String TOKENS_FILE = DATA_DIR + "/tokens.csv";
    public static final String FEEDBACK_FILE = DATA_DIR + "/feedback.csv";
    public static final String ANNOUNCEMENTS_FILE = DATA_DIR + "/announcements.csv";

    public static final String CSV_DELIMITER = ",";

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String APP_TITLE = "QueueLess Campus";
}
