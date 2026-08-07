# QueueLess Campus

## Overview

QueueLess Campus is a Java Swing desktop application developed to reduce physical queues inside a university campus. The system allows students to obtain virtual queue tokens for different university services while enabling administrators to efficiently manage queues, announcements, reports and services.

The project follows the MVC (Model-View-Controller) architecture and uses CSV files instead of MySQL for data storage.

---

# Technologies Used

- Java
- Java Swing
- MVC Architecture
- CSV File Storage
- ArrayList
- LinkedList
- Queue
- HashMap
- Object-Oriented Programming

---

# Features

## Student

- Login
- Join Queue
- View Current Token
- View Token History
- Submit Feedback
- View Announcements

---

## Administrator

- Login
- Queue Monitoring
- Service Management
- Reports
- Announcement Management

---

## System

- Virtual Token Generation
- Queue Management
- Estimated Waiting Time
- Queue Notifications
- CSV Data Storage
- Dashboard Interface

---

# Project Structure

```
src/
│
├── controller/
├── model/
├── service/
├── storage/
├── util/
├── view/
│
└── Main.java
```

---

# Team Members & Contributions

## Adiba Tasnim Chowdhury (251-15-802) – Project Architecture & Authentication

Responsible for:

- Overall project architecture
- Main application entry
- User authentication
- Login system
- Core model classes
- Validation utilities

Implemented Files:

- Main.java
- LoginController.java
- AuthenticationManager.java
- Student.java
- Staff.java
- Service.java
- Token.java
- TokenStatus.java
- Feedback.java
- Announcement.java
- Constants.java
- ValidationUtil.java

---

## Reyana Islam (251-15-274) – Queue Management System

Responsible for:

- Queue algorithms
- Waiting time calculation
- Queue notifications
- Queue status updates
- Service management logic

Implemented Files:

- QueueManager.java
- NotificationManager.java
- ServiceManager.java

---

## Anika Nahar (251-15-423) – Student Module

Responsible for:

- Student dashboard
- Student interface
- Student operations
- Student controller

Implemented Files:

- StudentController.java
- StudentDashboardFrame.java
- JoinQueuePanel.java
- MyTokensPanel.java
- FeedbackPanel.java
- AnnouncementsPanel.java

---

## Ariya Rahman Tuba (251-15-785) – Administrator Module

Responsible for:

- Administrator dashboard
- Queue monitoring
- Reports
- Announcement management
- Admin controller

Implemented Files:

- AdminController.java
- ReportManager.java
- AnnouncementManager.java
- AdminDashboardFrame.java
- QueueMonitorPanel.java
- ServiceManagementPanel.java
- ReportPanel.java
- AnnouncementAdminPanel.java

---

## Nusrat Jiban Mim (251-15-306) – Data Management, UI & Integration

Responsible for:

- CSV repositories
- Shared UI utilities
- Theme management
- CSV export
- Final integration
- Testing
- Documentation

Implemented Files:

- StudentRepository.java
- StaffRepository.java
- ServiceRepository.java
- TokenRepository.java
- FeedbackRepository.java
- AnnouncementRepository.java
- ThemeUtil.java
- CSVExportUtil.java
- TableUtil.java

Additional Responsibilities:

- Project testing
- Bug fixing
- Final integration
- README
- Documentation

---

# Future Improvements

- MySQL Database Support
- Email Notifications
- QR Code Queue System
- Mobile Application
- PDF Report Export
- Real-time Queue Display

---

# Developed For

Object-Oriented Programming (OOP)

Department of Computer Science & Engineering

Daffodil International University