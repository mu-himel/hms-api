package technology.grameen.gk.health.api.repositories.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.PatientOperation;

import java.time.LocalDateTime;

@Repository
public interface PatientOperationRepository extends JpaRepository<PatientOperation,Long> {

    interface PatientOperation{
        Long getId();
        String getPatient();
        String getDoctor();
        String getOperationPackage();
        String getCenter();
        String getHeldOnCenter();
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime getOperationDate();
    }


    @Query(value = "SELECT po.id, e.FULL_NAME AS doctor, hc.NAME AS center, po.OPERATION_DATE as operationDate," +
            " op.NAME AS operationPackage ,hc2.NAME AS heldOnCenter," +
            " '('||p.PID || ') ' || p.FULL_NAME AS patient\n" +
            "FROM PATIENT_OPERATIONS po \n" +
            "JOIN EMPLOYEES e ON e.ID  = po.EMPLOYEE_ID \n" +
            "JOIN HEALTH_CENTERS hc ON hc.ID = po.CENTER_ID \n" +
            "JOIN HEALTH_CENTERS hc2 ON hc2.ID = po.HELD_ON_CENTER_ID \n" +
            "JOIN PATIENTS p ON p.ID  = po.PATIENT_ID \n" +
            "JOIN OPERATION_PACKAGES op ON op.ID  = po.OPERATION_PACKAGE_ID",
    countQuery = "SELECT count(*) " +
            "FROM PATIENT_OPERATIONS po \n" +
            "JOIN EMPLOYEES e ON e.ID  = po.EMPLOYEE_ID \n" +
            "JOIN HEALTH_CENTERS hc ON hc.ID = po.CENTER_ID \n" +
            "JOIN HEALTH_CENTERS hc2 ON hc2.ID = po.HELD_ON_CENTER_ID \n" +
            "JOIN PATIENTS p ON p.ID  = po.PATIENT_ID \n" +
            "JOIN OPERATION_PACKAGES op ON op.ID  = po.OPERATION_PACKAGE_ID",nativeQuery = true)
    Page<PatientOperation> findAllOperations(Pageable pageable);
}
