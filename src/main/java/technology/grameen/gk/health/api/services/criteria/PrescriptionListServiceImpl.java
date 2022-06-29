package technology.grameen.gk.health.api.services.criteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Service;
import technology.grameen.gk.health.api.entity.HealthCenter;
import technology.grameen.gk.health.api.entity.Patient;
import technology.grameen.gk.health.api.entity.Prescription;
import technology.grameen.gk.health.api.responses.PrescriptionList;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrescriptionListServiceImpl implements PrescriptionListService {

    EntityManager entityManager;

    public PrescriptionListServiceImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }


    @Override
    public Page<PrescriptionList> getPrescriptions(String regionCode, String centerCode, String pNumber, String fullName, String date, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PrescriptionList> cq = cb.createQuery(PrescriptionList.class);
        Root<Prescription> root = cq.from(Prescription.class);
        Join<Prescription, HealthCenter> hc = root.join("center", JoinType.INNER);
        Join<Prescription, Patient> p = root.join("prescriptionPatient", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();
        if(!regionCode.isEmpty()){
            Expression param = cb.literal("0");
            Predicate predicateRegionCode = cb.equal(cb.function("nvl",Object.class,hc.get("thirdLevel"),param),
                    regionCode);
            predicates.add(predicateRegionCode);
        }
        if(!centerCode.isEmpty()){
            Expression param = cb.literal("0");
            Predicate predicateCenterCode = cb.equal(cb.function("nvl",Object.class,hc.get("centerCode"),param),
                    centerCode);
            predicates.add(predicateCenterCode);
        }
        if(!pNumber.isEmpty()){
            Predicate predicatePNumber = cb.like(root.get("pNumber"), "%"+pNumber+"%");
            predicates.add(predicatePNumber);
        }
        if(!fullName.isEmpty()){
            Predicate predicatePNumber = cb.like(cb.lower(p.get("fullName")), "%"+fullName.toLowerCase()+"%");
            predicates.add(predicatePNumber);
        }

        if(!date.isEmpty()){
            LocalDateTime start = LocalDateTime.parse(date+"T00:00:00");
            LocalDateTime end = LocalDateTime.parse(date+"T23:59:59");
            Predicate predicatePNumber = cb.between(root.get("createdAt"), start,end);
            predicates.add(predicatePNumber);
        }

        cq.select(cb.construct(PrescriptionList.class,p.get("id"),root.get("id"),root.get("pNumber"),
                root.get("lastFreeVisitDate"),root.get("createdAt"),p.get("fullName"),hc.get("centerCode")));
        cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        cq.orderBy(QueryUtils.toOrders(pageable.getSort(),root,cb));
        List<PrescriptionList> result = entityManager.createQuery(cq)
                .setFirstResult((int)pageable.getOffset()).setMaxResults(pageable.getPageSize())
                .getResultList();


        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Prescription> countRoot = countQuery.from(Prescription.class);
        Join<Prescription, HealthCenter> chc = countRoot.join("center", JoinType.INNER);
        Join<Prescription, Patient> cp = countRoot.join("prescriptionPatient", JoinType.INNER);

        List<Predicate> cPredicates = new ArrayList<>();
        if(!regionCode.isEmpty()){
            Expression param = cb.literal("0");
            Predicate predicateRegionCode = cb.equal(cb.function("nvl",Object.class,chc.get("thirdLevel"),param),
                    regionCode);
            cPredicates.add(predicateRegionCode);
        }
        if(!centerCode.isEmpty()){
            Expression param = cb.literal("0");
            Predicate predicateCenterCode = cb.equal(cb.function("nvl",Object.class,chc.get("centerCode"),param),
                    centerCode);
            cPredicates.add(predicateCenterCode);
        }
        if(!pNumber.isEmpty()){
            Predicate predicatePNumber = cb.like(countRoot.get("pNumber"), "%"+pNumber+"%");
            cPredicates.add(predicatePNumber);
        }

        if(!fullName.isEmpty()){
            Predicate predicatePNumber = cb.like(cb.lower(cp.get("fullName")), "%"+fullName.toLowerCase()+"%");
            cPredicates.add(predicatePNumber);
        }

        if(!date.isEmpty()){
            LocalDateTime start = LocalDateTime.parse(date+"T00:00:00");
            LocalDateTime end = LocalDateTime.parse(date+"T23:59:59");
            Predicate predicatePNumber = cb.between(countRoot.get("createdAt"), start,end);
            cPredicates.add(predicatePNumber);
        }

        countQuery.select(cb.count(countRoot.get("id"))).where(cb.and(cPredicates.toArray(new Predicate[cPredicates.size()])));
        Long count = entityManager.createQuery(countQuery).getSingleResult();
        Page<PrescriptionList> page = new PageImpl<>(result,pageable,count);
        return page;
    }
}
