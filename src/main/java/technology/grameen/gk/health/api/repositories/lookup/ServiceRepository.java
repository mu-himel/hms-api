package technology.grameen.gk.health.api.repositories.lookup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import technology.grameen.gk.health.api.entity.ServiceCategory;
import technology.grameen.gk.health.api.projection.ServiceListItem;
import technology.grameen.gk.health.api.entity.Service;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service,Long> {
//   new Service(s.serviceId,sc,ltg,s.name,s.code,s.currentCost,s.currentGbCost,s.description," +
//              "s.isActive,s.isLabTest,s.createdAt,s.lastUpdatedAt)



    @Query(value = "SELECT s FROM Service s JOIN FETCH s.serviceCategory sc" +
            " LEFT JOIN FETCH s.labTestGroup ltg",
    countQuery = "SELECT COUNT(s) FROM Service s JOIN s.serviceCategory sc")
    Page<ServiceListItem> findAllServices(Pageable pageable);

    @Query(value = "SELECT s FROM Service s JOIN FETCH s.serviceCategory sc" +
            " LEFT JOIN FETCH s.labTestGroup ltg WHERE upper(s.name) LIKE upper(concat('%',:serviceName,'%'))",
            countQuery = "SELECT COUNT(s) FROM Service s JOIN s.serviceCategory sc " +
                    " WHERE upper(s.name) LIKE upper(concat('%',:serviceName,'%'))")
    Page<ServiceListItem> findAllServices(@Param("serviceName") String serviceName, Pageable pageable);

    @Query(value = "SELECT s FROM Service s JOIN FETCH s.serviceCategory sc" +
            " LEFT JOIN FETCH s.labTestGroup ltg WHERE upper(s.code) LIKE upper(concat('%',:serviceCode,'%'))" +
            " OR upper(s.serviceCode) LIKE upper(concat('%',:serviceCode,'%'))",
            countQuery = "SELECT COUNT(s) FROM Service s JOIN s.serviceCategory sc " +
                    " WHERE upper(s.code) LIKE upper(concat('%',:serviceCode,'%'))" +
                    " OR upper(s.serviceCode) LIKE upper(concat('%',:serviceCode,'%'))")
    Page<ServiceListItem> findAllByServiceCode(@Param("serviceCode") String serviceCode, Pageable pageable);

    @Query(value = "SELECT s FROM Service s JOIN FETCH s.serviceCategory sc" +
            " LEFT JOIN FETCH s.labTestGroup ltg")
    List<ServiceListItem> findAllServices();

    Optional<Service> findByCode(String code);


    @Override
    @Query("SELECT s FROM Service s JOIN FETCH s.serviceCategory sc " +
            " LEFT JOIN FETCH s.labTestGroup ltg" +
            " LEFT JOIN FETCH s.labTestAttributes lta" +
            " LEFT JOIN FETCH lta.labTestUnit ltu WHERE s.serviceId = :id")
    Optional<Service> findById(@Param("id") Long id);

    @Query("SELECT s FROM Service s JOIN FETCH s.serviceCategory sc" +
            " JOIN FETCH s.labTestGroup ltg WHERE s.isLabTest = :s")
    List<ServiceListItem> findByIsLabTestEquals(@Param("s") Boolean bool);

    @Query("SELECT s FROM Service s JOIN FETCH s.serviceCategory sc " +
            "WHERE s.fieldSaleable = :b")
    List<ServiceListItem> findByFieldSaleableEquals(@Param("b") Boolean b);

    interface IServiceList{
        Long getServiceId();
        String getName();
        String getCode();
    }
    List<IServiceList> findByServiceCategory(ServiceCategory serviceCategory);
}
