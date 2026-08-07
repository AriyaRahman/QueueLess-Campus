package storage;

import model.Staff;
import util.CSVExportUtil;
import util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Handles loading and saving Staff records to staff.csv.
 * Format: staffId,name,email,password,department
 */
public class StaffRepository {

    public List<Staff> findAll() {
        List<Staff> staffList = new ArrayList<>();
        for (String line : CSVExportUtil.readLines(Constants.STAFF_FILE)) {
            staffList.add(fromLine(line));
        }
        return staffList;
    }

    public Optional<Staff> findByEmail(String email) {
        for (Staff s : findAll()) {
            if (s.getEmail().equalsIgnoreCase(email)) {
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }

    public Optional<Staff> findById(String staffId) {
        for (Staff s : findAll()) {
            if (s.getStaffId().equals(staffId)) {
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }

    public void add(Staff staff) {
        CSVExportUtil.appendLine(Constants.STAFF_FILE, toLine(staff));
    }

    private String toLine(Staff s) {
        return String.join(Constants.CSV_DELIMITER,
                s.getStaffId(),
                CSVExportUtil.escape(s.getName()),
                CSVExportUtil.escape(s.getEmail()),
                s.getPassword(),
                CSVExportUtil.escape(s.getDepartment()));
    }

    private Staff fromLine(String line) {
        String[] parts = line.split(Constants.CSV_DELIMITER, -1);
        return new Staff(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }
}
