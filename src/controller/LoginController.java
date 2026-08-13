package controller;

import model.Staff;
import model.Student;
import service.*;
import storage.*;

import java.util.Optional;

/**
 * Handles login and creates the repository/service objects that get
 * passed to StudentController / AdminController after login.
 */
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final StudentRepository studentRepository;
    private final StaffRepository staffRepository;
    private final ServiceRepository serviceRepository;
    private final TokenRepository tokenRepository;
    private final FeedbackRepository feedbackRepository;
    private final AnnouncementRepository announcementRepository;

    private final QueueManager queueManager;
    private final ServiceManager serviceManager;
    private final ReportManager reportManager;
    private final AnnouncementManager announcementManager;
    private final NotificationManager notificationManager;

    public LoginController() {
        this.studentRepository = new StudentRepository();
        this.staffRepository = new StaffRepository();
        this.serviceRepository = new ServiceRepository();
        this.tokenRepository = new TokenRepository();
        this.feedbackRepository = new FeedbackRepository();
        this.announcementRepository = new AnnouncementRepository();

        this.authenticationManager = new AuthenticationManager(studentRepository, staffRepository);
        this.queueManager = new QueueManager(tokenRepository);
        this.serviceManager = new ServiceManager(serviceRepository);
        this.reportManager = new ReportManager(tokenRepository, serviceRepository);
        this.announcementManager = new AnnouncementManager(announcementRepository);
        this.notificationManager = new NotificationManager();
    }

    public Optional<Student> loginStudent(String email, String password) {
        return authenticationManager.authenticateStudent(email, password);
    }

    public Optional<Staff> loginStaff(String email, String password) {
        return authenticationManager.authenticateStaff(email, password);
    }

    public Optional<Student> registerStudent(String name, String email, String password, String department) {
        return authenticationManager.registerStudent(name, email, password, department);
    }

    public StudentController buildStudentController(Student student) {
        return new StudentController(student, queueManager, serviceManager,
                announcementManager, notificationManager, feedbackRepository);
    }

    public AdminController buildAdminController(Staff staff) {
        return new AdminController(staff, queueManager, serviceManager,
                reportManager, announcementManager, staffRepository);
    }
}
