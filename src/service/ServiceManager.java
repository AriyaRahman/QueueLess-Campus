package service;

import model.Service;
import storage.ServiceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Business logic for creating and maintaining the list of campus services
 * (canteen, library, office counters, etc.) that students can queue for.
 */
public class ServiceManager {

    private final ServiceRepository serviceRepository;

    public ServiceManager(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<Service> listAll() {
        return serviceRepository.findAll();
    }

    public List<Service> listActive() {
        List<Service> active = new ArrayList<>();
        for (Service s : serviceRepository.findAll()) {
            if (s.isActive()) {
                active.add(s);
            }
        }
        return active;
    }

    public Service addService(String name, String department, String staffId, int avgServiceTimeMinutes) {
        String serviceId = "SVC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Service service = new Service(serviceId, name, department, staffId, avgServiceTimeMinutes, true);
        serviceRepository.add(service);
        return service;
    }

    public void updateService(Service service) {
        serviceRepository.update(service);
    }

    public void setActive(String serviceId, boolean active) {
        Optional<Service> serviceOpt = serviceRepository.findById(serviceId);
        if (serviceOpt.isPresent()) {
            Service service = serviceOpt.get();
            service.setActive(active);
            serviceRepository.update(service);
        }
    }

    public Optional<Service> findById(String serviceId) {
        return serviceRepository.findById(serviceId);
    }
}
