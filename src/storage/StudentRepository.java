package storage;

import model.Student;
import util.CSVExportUtil;
import util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Handles loading and saving Student records to students.csv.
 * Format: studentId,name,email,password,department
 */
public class StudentRepository {

    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        for (String line : CSVExportUtil.readLines(Constants.STUDENTS_FILE)) {
            students.add(fromLine(line));
        }
        return students;
    }

    public Optional<Student> findByEmail(String email) {
        for (Student s : findAll()) {
            if (s.getEmail().equalsIgnoreCase(email)) {
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }

    public Optional<Student> findById(String studentId) {
        for (Student s : findAll()) {
            if (s.getStudentId().equals(studentId)) {
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }

    public void add(Student student) {
        CSVExportUtil.appendLine(Constants.STUDENTS_FILE, toLine(student));
    }

    public void saveAll(List<Student> students) {
        List<String> lines = new ArrayList<>();
        for (Student s : students) {
            lines.add(toLine(s));
        }
        CSVExportUtil.writeLines(Constants.STUDENTS_FILE, lines);
    }

    private String toLine(Student s) {
        return String.join(Constants.CSV_DELIMITER,
                s.getStudentId(),
                CSVExportUtil.escape(s.getName()),
                CSVExportUtil.escape(s.getEmail()),
                s.getPassword(),
                CSVExportUtil.escape(s.getDepartment()));
    }

    private Student fromLine(String line) {
        String[] parts = line.split(Constants.CSV_DELIMITER, -1);
        return new Student(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }
}
