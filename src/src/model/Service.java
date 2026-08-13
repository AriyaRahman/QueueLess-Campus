package model;

/**
 * Represents a campus service that maintains its own virtual queue
 * (e.g. Library Desk, Canteen Counter, Registrar Office).
 */
public class Service {
    private final String serviceId;
    private String name;
    private String department;
    private String staffId;
    private int avgServiceTimeMinutes;
    private boolean active;

    public Service(String serviceId, String name, String department, String staffId,
                    int avgServiceTimeMinutes, boolean active) {
        this.serviceId = serviceId;
        this.name = name;
        this.department = department;
        this.staffId = staffId;
        this.avgServiceTimeMinutes = avgServiceTimeMinutes;
        this.active = active;
    }

    public String getServiceId() { return serviceId; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getStaffId() { return staffId; }
    public int getAvgServiceTimeMinutes() { return avgServiceTimeMinutes; }
    public boolean isActive() { return active; }

    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
    public void setAvgServiceTimeMinutes(int avgServiceTimeMinutes) { this.avgServiceTimeMinutes = avgServiceTimeMinutes; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        return name + " [" + department + "]";
    }

    /** equals by serviceId only */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service)) return false;
        return serviceId.equals(((Service) o).serviceId);
    }

    @Override
    public int hashCode() {
        return serviceId.hashCode();
    }
}
