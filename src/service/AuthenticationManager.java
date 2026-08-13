package service;

import model.Staff;
import model.Student;
import storage.StaffRepository;
import storage.StudentRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Authenticates students and staff, and handles new student registration.
 */
public class AuthenticationManager {

    private final StudentRepository studentRepository;
    private final StaffRepository staffRepository;

    public AuthenticationManager(StudentRepository studentRepository, StaffRepository staffRepository) {
        this.studentRepository = studentRepository;
        this.staffRepository = staffRepository;
    }

    public Optional<Student> authenticateStudent(String email, String password) {
        Optional<Student> student = studentRepository.findByEmail(email);
        if (student.isPresent() && student.get().getPassword().equals(password)) {
            return student;
        }
        return Optional.empty();
    }

    public Optional<Staff> authenticateStaff(String email, String password) {
        Optional<Staff> staff = staffRepository.findByEmail(email);
        if (staff.isPresent() && staff.get().getPassword().equals(password)) {
            return staff;
        }
        return Optional.empty();
    }

    /**
     * Registers a new student account. Returns empty if the email is already in use.
     */
    public Optional<Student> registerStudent(String name, String email, String password, String department) {
        if (studentRepository.findByEmail(email).isPresent()) {
            return Optional.empty();
        }
        String studentId = "STU-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Student student = new Student(studentId, name, email, password, department);
        studentRepository.add(student);
        return Optional.of(student);
    }
}
