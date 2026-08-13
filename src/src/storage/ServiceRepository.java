package storage;

import model.Service;
import util.CSVExportUtil;
import util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Handles loading and saving Service records to services.csv.
 * Format: serviceId,name,department,staffId,avgServiceTimeMinutes,active
 */
public class ServiceRepository {

    public List<Service> findAll() {
        List<Service> services = new ArrayList<>();
        for (String line : CSVExportUtil.readLines(Constants.SERVICES_FILE)) {
            services.add(fromLine(line));
        }
        return services;
    }

    public Optional<Service> findById(String serviceId) {
        for (Service s : findAll()) {
            if (s.getServiceId().equals(serviceId)) {
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }

    public void add(Service service) {
        List<Service> all = findAll();
        all.add(service);
        saveAll(all);
    }

    public void update(Service service) {
        List<Service> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getServiceId().equals(service.getServiceId())) {
                all.set(i, service);
                break;
            }
        }
        saveAll(all);
    }

    public void saveAll(List<Service> services) {
        List<String> lines = new ArrayList<>();
        for (Service s : services) {
            lines.add(toLine(s));
        }
        CSVExportUtil.writeLines(Constants.SERVICES_FILE, lines);
    }

    private String toLine(Service s) {
        return String.join(Constants.CSV_DELIMITER,
                s.getServiceId(),
                CSVExportUtil.escape(s.getName()),
                CSVExportUtil.escape(s.getDepartment()),
                s.getStaffId(),
                String.valueOf(s.getAvgServiceTimeMinutes()),
                String.valueOf(s.isActive()));
    }

    private Service fromLine(String line) {
        String[] parts = line.split(Constants.CSV_DELIMITER, -1);
        return new Service(parts[0], parts[1], parts[2], parts[3],
                Integer.parseInt(parts[4]), Boolean.parseBoolean(parts[5]));
    }
}
