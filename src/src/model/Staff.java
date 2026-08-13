package model;

/**
 * Represents a staff/admin user who manages queues and services.
 */
public class Staff {
    private final String staffId;
    private String name;
    private String email;
    private String password;
    private String department;

    public Staff(String staffId, String name, String email, String password, String department) {
        this.staffId = staffId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.department = department;
    }

    public String getStaffId() { return staffId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getDepartment() { return department; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return name + " (" + staffId + ")";
    }

    /** equals by staffId only */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Staff)) return false;
        return staffId.equals(((Staff) o).staffId);
    }

    @Override
    public int hashCode() {
        return staffId.hashCode();
    }
}
