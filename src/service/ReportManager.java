package service;

import model.Service;
import model.Token;
import model.TokenStatus;
import storage.ServiceRepository;
import storage.TokenRepository;
import util.CSVExportUtil;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Builds summary statistics per service (tokens issued, completed, cancelled,
 * average wait time) and can export them as a CSV report for record keeping.
 */
public class ReportManager {

    private final TokenRepository tokenRepository;
    private final ServiceRepository serviceRepository;

    public ReportManager(TokenRepository tokenRepository, ServiceRepository serviceRepository) {
        this.tokenRepository = tokenRepository;
        this.serviceRepository = serviceRepository;
    }

    /** Simple immutable row of statistics for one service. */
    public static class ServiceReportRow {
        public final String serviceName;
        public final int issued;
        public final int completed;
        public final int cancelled;
        public final long avgWaitMinutes;

        public ServiceReportRow(String serviceName, int issued, int completed, int cancelled, long avgWaitMinutes) {
            this.serviceName = serviceName;
            this.issued = issued;
            this.completed = completed;
            this.cancelled = cancelled;
            this.avgWaitMinutes = avgWaitMinutes;
        }
    }

    public List<ServiceReportRow> generateReport() {
        List<ServiceReportRow> rows = new ArrayList<>();
        for (Service service : serviceRepository.findAll()) {
            List<Token> tokens = tokenRepository.findByService(service.getServiceId());
            int issued = tokens.size();
            int completed = 0;
            int cancelled = 0;
            long totalWaitMinutes = 0;
            int waitSamples = 0;

            for (Token t : tokens) {
                if (t.getStatus() == TokenStatus.COMPLETED) {
                    completed++;
                } else if (t.getStatus() == TokenStatus.CANCELLED) {
                    cancelled++;
                }
                if (t.getCalledTime() != null) {
                    totalWaitMinutes += Duration.between(t.getIssueTime(), t.getCalledTime()).toMinutes();
                    waitSamples++;
                }
            }

            long avgWait = waitSamples == 0 ? 0 : totalWaitMinutes / waitSamples;
            rows.add(new ServiceReportRow(service.getName(), issued, completed, cancelled, avgWait));
        }
        return rows;
    }

    /** Writes the current report to a CSV file, e.g. data/report_2026-08-06.csv */
    public void exportReport(String filePath) {
        List<String> lines = new ArrayList<>();
        lines.add("Service,Issued,Completed,Cancelled,AverageWaitMinutes");
        for (ServiceReportRow row : generateReport()) {
            lines.add(String.join(",",
                    CSVExportUtil.escape(row.serviceName),
                    String.valueOf(row.issued),
                    String.valueOf(row.completed),
                    String.valueOf(row.cancelled),
                    String.valueOf(row.avgWaitMinutes)));
        }
        CSVExportUtil.writeLines(filePath, lines);
    }
}
