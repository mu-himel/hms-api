package technology.grameen.gk.health.api.services.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.Employee;
import technology.grameen.gk.health.api.entity.EmployeeDetail;
import technology.grameen.gk.health.api.entity.HealthCenter;
import technology.grameen.gk.health.api.projection.EmployeeItem;
import technology.grameen.gk.health.api.repositories.employee.EmployeeRepository;
import technology.grameen.gk.health.api.responses.EmployeeDetailInfo;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.responses.SimpleResponse;
import technology.grameen.gk.health.api.services.HealthCenterService;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private JobHistoryService jobHistoryService;
    private EmployeeDetailService employeeDetailService;
    private HealthCenterService healthCenterService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               JobHistoryService jobHistoryService,
                               EmployeeDetailService employeeDetailService,
                               HealthCenterService healthCenterService) {
        this.employeeRepository = employeeRepository;
        this.jobHistoryService = jobHistoryService;
        this.employeeDetailService = employeeDetailService;
        this.healthCenterService = healthCenterService;
    }

    @Override
    @Transactional
    public Employee addEmployee(Employee employee) {
        HealthCenter center = employee.getCenter();
        employee.setEmployeeDetail(null);
        if(center != null) {
            center.addEmployee(employee);
            employeeRepository.save(employee);
        }
        return employee;
    }

    @Override
    @Transactional
    public Employee addEmployee(Employee employee, boolean b) {
        HealthCenter center = employee.getCenter();
        if(center != null) {
            EmployeeDetail employeeDetail = employee.getEmployeeDetail();
            center.addEmployee(employee);

            employee.setEmployeeDetail(null);
            employeeRepository.save(employee);

            employeeDetail.setEmployee(employee);
            employeeDetailService.save(employeeDetail);

        }
        return employee;
    }

    public JobHistoryService getJobHistoryService() {
        return jobHistoryService;
    }

    @Override
    public EmployeeDetailService getEmployeeDetailService() {
        return employeeDetailService;
    }

    @Override
    public Page<EmployeeItem> getAll(Pageable pageable) {
        return employeeRepository.findAllEmployee(pageable);
    }

    @Override
    public Page<EmployeeItem> getAll(Long centerId,
                                     String employeeCode,
                                     String fullName,
                                     String contactNo,
                                     String email,
                                     Pageable pageable) {

        if(centerId != null){
            return employeeRepository.findAllByCenterId(centerId,pageable);
        }
        if(!employeeCode.isEmpty() && !fullName.isEmpty() && !contactNo.isEmpty() && !email.isEmpty()){

        }else if(!employeeCode.isEmpty()) {
            return employeeRepository.findAllByEmployeeCodeContaining(employeeCode,pageable);
        }else if(!fullName.isEmpty()){
            return employeeRepository.findAllByFullNameContainingIgnoreCase(fullName,pageable);
        }else if(!contactNo.isEmpty()){
            return employeeRepository.findAllByContactNumberContaining(contactNo,pageable);
        }else if(!email.isEmpty()){
            return employeeRepository.findAllByEmailContainingIgnoreCase(email,pageable);
        }

        return employeeRepository.findAllEmployee(pageable);
    }

    @Override
    public IResponse getEmployeeByApiEmployeeId(Long id) {
        Integer count = employeeRepository.getCount(id);
        if(count>1){
            return new SimpleResponse(HttpStatus.OK.value(), "Sorry! There are several records found with same Api id");
        }
        return new EntityResponse<>(HttpStatus.OK.value(),employeeRepository.findByApiEmployeeId(id));
    }

    @Override
    public List<EmployeeItem> getEmployeeByRole(String role) {
        return employeeRepository.findAllByRole(role);
    }

    @Override
    public List<EmployeeItem> getEmployeeByRoles(List<String> role) {
        return employeeRepository.findAllByRoleIn(role);
    }

    @Override
    public List<EmployeeItem> getEmployeeByRolesAndThirdLevelCode(List<String> role, Optional<String> thirdLevelCode) {
        if(thirdLevelCode.isPresent()) {
            List<Long> centerIds = healthCenterService.getCenterIdByThirdLevel(thirdLevelCode.get());
            return employeeRepository.findAllByRoleInAndCenter_IdIn(role, centerIds);
        }
        return employeeRepository.findAllByRoleIn(role);
    }

    @Override
    public List<EmployeeItem> getEmployeeByDesignationAndCenter(String designation, Long centerId) {
        return employeeRepository.findAllByRoleContainingIgnoreCaseAndCenter(designation,
                                            new HealthCenter(centerId));
    }

    @Override
    public Optional<EmployeeDetailInfo> getEmployeeById(Long employeeId) {
        return employeeRepository.findByEmployeeId(employeeId);
    }
}
