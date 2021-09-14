package technology.grameen.gk.health.api.services.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import technology.grameen.gk.health.api.entity.Employee;
import technology.grameen.gk.health.api.projection.EmployeeItem;
import technology.grameen.gk.health.api.responses.EmployeeDetailInfo;
import technology.grameen.gk.health.api.responses.IResponse;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee addEmployee(Employee employee);

    JobHistoryService getJobHistoryService();

    EmployeeDetailService getEmployeeDetailService();

    Page<EmployeeItem> getAll(Pageable pageable);
    Page<EmployeeItem> getAll(Long centerId,
                              String employeeCode,
                              String fullName,
                              String contactNo,
                              String email,
                              Pageable pageable);

    IResponse getEmployeeByApiEmployeeId(Long id);

    List<EmployeeItem> getEmployeeByDesignation(String designation);

    Optional<EmployeeDetailInfo> getEmployeeById(Long employeeId);

    List<EmployeeItem> getEmployeeByDesignationAndCenter(String designation, Long centerId);
}
