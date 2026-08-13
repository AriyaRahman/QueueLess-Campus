package model;

/**
 * Represents a student user of the system.
 */
public class Student {
    private final String studentId;
    private String name;
    private String email;
    private String password;
    private String department;

    public Student(String studentId, String name, String email, String password, String department) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.department = department;
    }

    public String getStudentId() { return studentId; }
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
        return name + " (" + studentId + ")";
    }

    /** equals by studentId only */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        return studentId.equals(((Student) o).studentId);
    }

    @Override
    public int hashCode() {
        return studentId.hashCode();
    }
}
