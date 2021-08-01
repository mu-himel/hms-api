package technology.grameen.gk.health.api.services.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import technology.grameen.gk.health.api.entity.Employee;
import technology.grameen.gk.health.api.projection.EmployeeItem;
import technology.grameen.gk.health.api.responses.EmployeeDetail;
import technology.grameen.gk.health.api.responses.IResponse;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee addEmployee(Employee employee);

    JobHistoryService getJobHistoryService();

    Page<EmployeeItem> getAll(Pageable pageable);
    Page<EmployeeItem> getAll(Long centerId,
                              String employeeCode,
                              String fullName,
                              String contactNo,
                              String email,
                              Pageable pageable);

    IResponse getEmployeeByApiEmployeeId(Long id);

    List<EmployeeItem> getEmployeeByDesignation(String designation);

    Optional<EmployeeDetail> getEmployeeById(Long employeeId);
}
