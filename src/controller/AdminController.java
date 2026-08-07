package controller;

import model.*;
import service.AnnouncementManager;
import service.QueueManager;
import service.ReportManager;
import service.ServiceManager;
import storage.StaffRepository;

import java.util.List;
import java.util.Optional;

/**
 Coordinates all admin/staff-facing actions
 */
public class AdminController {

    private final Staff staff;
    private final QueueManager queueManager;
    private final ServiceManager serviceManager;
    private final ReportManager reportManager;
    private final AnnouncementManager announcementManager;
    private final StaffRepository staffRepository;

    public AdminController(Staff staff, QueueManager queueManager, ServiceManager serviceManager,
                            ReportManager reportManager, AnnouncementManager announcementManager,
                            StaffRepository staffRepository) {
        this.staff = staff;
        this.queueManager = queueManager;
        this.serviceManager = serviceManager;
        this.reportManager = reportManager;
        this.announcementManager = announcementManager;
        this.staffRepository = staffRepository;
    }

    public Staff getStaff() { return staff; }

    public List<Service> getAllServices() {
        return serviceManager.listAll();
    }

    public Service addService(String name, String department, int avgServiceTimeMinutes) {
        return serviceManager.addService(name, department, staff.getStaffId(), avgServiceTimeMinutes);
    }

    public void setServiceActive(String serviceId, boolean active) {
        serviceManager.setActive(serviceId, active);
    }

    public List<Token> getQueueSnapshot(String serviceId) {
        return queueManager.getQueueSnapshot(serviceId);
    }

    public Optional<Token> getCurrentlyServing(String serviceId) {
        return queueManager.getCurrentlyServing(serviceId);
    }

    public Optional<Token> callNext(String serviceId) {
        return queueManager.callNext(serviceId);
    }

    public Optional<Token> completeCurrent(String serviceId) {
        return queueManager.completeCurrent(serviceId);
    }

    public List<ReportManager.ServiceReportRow> generateReport() {
        return reportManager.generateReport();
    }

    public void exportReport(String filePath) {
        reportManager.exportReport(filePath);
    }

    public Announcement postAnnouncement(String title, String message) {
        return announcementManager.post(title, message, staff.getName());
    }

    public List<Announcement> getAnnouncements() {
        return announcementManager.getAllNewestFirst();
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }
}
