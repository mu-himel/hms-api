package technology.grameen.gk.health.api.services.employee;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.EmployeeDetail;
import technology.grameen.gk.health.api.repositories.employee.EmployeeDetailRepository;

@Service
public class EmployeeDetailServiceImpl implements EmployeeDetailService{

    private EmployeeDetailRepository employeeDetailRepository;

    public EmployeeDetailServiceImpl(EmployeeDetailRepository employeeDetailRepository) {
        this.employeeDetailRepository = employeeDetailRepository;
    }

    @Override
    @Transactional
    public EmployeeDetail save(EmployeeDetail employeeDetail) {
        EmployeeDetail detail = employeeDetailRepository.save(employeeDetail);
        return detail;
    }
}
