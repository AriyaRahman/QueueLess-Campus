import model.Service;
import model.Staff;
import model.Student;
import storage.ServiceRepository;
import storage.StaffRepository;
import storage.StudentRepository;
import util.CSVExportUtil;
import util.Constants;
import util.ThemeUtil;
import view.LoginFrame;

import javax.swing.*;

/**
 Application entry point.
 */
public class Main {

    public static void main(String[] args) {
        initialiseDataFiles();
        seedDefaultDataIfEmpty();

        ThemeUtil.applySystemLookAndFeel();

        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }

    private static void initialiseDataFiles() {
        CSVExportUtil.ensureFileExists(Constants.STUDENTS_FILE);
        CSVExportUtil.ensureFileExists(Constants.STAFF_FILE);
        CSVExportUtil.ensureFileExists(Constants.SERVICES_FILE);
        CSVExportUtil.ensureFileExists(Constants.TOKENS_FILE);
        CSVExportUtil.ensureFileExists(Constants.FEEDBACK_FILE);
        CSVExportUtil.ensureFileExists(Constants.ANNOUNCEMENTS_FILE);
    }

    private static void seedDefaultDataIfEmpty() {
        StaffRepository staffRepository = new StaffRepository();
        if (staffRepository.findAll().isEmpty()) {
            staffRepository.add(new Staff("STF-00000001", "Admin User", "admin@campus.edu", "admin123", "Administration"));
        }

        StudentRepository studentRepository = new StudentRepository();
        if (studentRepository.findAll().isEmpty()) {
            studentRepository.add(new Student("STU-00000001", "Demo Student", "student@campus.edu", "student123", "Computer Science"));
        }

        ServiceRepository serviceRepository = new ServiceRepository();
        if (serviceRepository.findAll().isEmpty()) {
            serviceRepository.add(new Service("SVC-00000001", "Library Help Desk", "Library", "STF-00000001", 5, true));
            serviceRepository.add(new Service("SVC-00000002", "Canteen Counter", "Cafeteria", "STF-00000001", 3, true));
            serviceRepository.add(new Service("SVC-00000003", "Registrar Office", "Administration", "STF-00000001", 10, true));
        }
    }
}
