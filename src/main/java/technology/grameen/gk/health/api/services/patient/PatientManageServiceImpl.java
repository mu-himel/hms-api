package technology.grameen.gk.health.api.services.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.*;
import technology.grameen.gk.health.api.projection.PatientNumberAutoComplete;
import technology.grameen.gk.health.api.projection.PatientSearchResult;
import technology.grameen.gk.health.api.repositories.*;
import technology.grameen.gk.health.api.repositories.lookup.ServiceRepository;
import technology.grameen.gk.health.api.repositories.patient.PatientDetailRepository;
import technology.grameen.gk.health.api.repositories.patient.PatientRepository;
import technology.grameen.gk.health.api.repositories.patient.PatientServiceRepository;
import technology.grameen.gk.health.api.requests.ExistingPatientRequest;
import technology.grameen.gk.health.api.requests.PatientRequest;
import technology.grameen.gk.health.api.responses.PatientListItem;
import technology.grameen.gk.health.api.services.HealthCenterService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class PatientManageServiceImpl implements PatientManageService {

    private PatientRepository patientRepository;

    private PatientDetailRepository detailRepository;

    private CardRegistrationRepository cardRegistrationRepository;

    private CardMemberRepository cardMemberRepository;

    private HealthCenterService centerService;

    private PatientInvoiceRepository invoiceRepository;
    private PatientServiceRepository patientServiceRepository;

    private ServiceRepository serviceRepository;

    PatientManageServiceImpl(PatientRepository patientRepository,
                             PatientDetailRepository detailRepository,
                             CardRegistrationRepository cardRegistrationRepository,
                             CardMemberRepository cardMemberRepository,
                             HealthCenterService centerService,
                             PatientInvoiceRepository invoiceRepository,
                             PatientServiceRepository patientServiceRepository,
                             ServiceRepository serviceRepository){
        this.patientRepository = patientRepository;
        this.detailRepository = detailRepository;
        this.cardRegistrationRepository = cardRegistrationRepository;
        this.cardMemberRepository = cardMemberRepository;
        this.centerService = centerService;
        this.invoiceRepository = invoiceRepository;
        this.serviceRepository = serviceRepository;
        this.patientServiceRepository = patientServiceRepository;
    }

    public PatientInvoiceRepository getInvoiceRepository() {
        return invoiceRepository;
    }

    @Override
    @Transactional
    public Patient addPatient(PatientRequest req) throws Exception {



        Patient patient = getPatient(req);
        HealthCenter center = patient.getCenter();
        Employee employee = patient.getCreatedBy();

        if(patient.getId()==null) {
            center.addPatient(patient);
            employee.addPatient(patient);
        }


        PatientDetail detail = patient.getDetail();
        //for new detail

        if(detail != null && detail.getId()==null) {
            detail.setPatient(patient);
            detailRepository.save(detail);
        }

        // for existing detail
        if(detail != null && detail.getId()!=null) {
            detail.setPatient(patient);
        }
        patientRepository.save(patient);

        // for existing detail
        if(detail != null && detail.getId()!=null) {
            detail.setPatient(patient);
            detailRepository.save(detail);
        }
        return patient;
    }

    @Override
    public Page<PatientListItem> getPatients(Pageable pageable) {
        return patientRepository.findAllPatients(pageable);
    }

    @Override
    public Optional<PatientSearchResult> getPatientById(Long id) {
        return this.patientRepository.findPatientById(id);
    }

    @Override
    public Optional<PatientSearchResult> getPatientByPId(String pid){
        Optional<PatientSearchResult> patientSearchResultOptional = this.patientRepository.findByPid(pid);

        PatientSearchResult patientSearchResult = null;
        if(patientSearchResultOptional.isPresent()){
            patientSearchResult = patientSearchResultOptional.get();

            PatientSearchResult.CardRegistration  cardRegistration = patientSearchResult.getRegistration();

            if(cardRegistration != null) {
                // Check is Registration expired
                LocalDateTime expiredDate = cardRegistration.getExpiredDate();
                LocalDateTime currentTime = LocalDateTime.now();
                if (currentTime.isAfter(expiredDate)) {
                    patientSearchResult.getRegistration().setActive(false);
                }
            }
        }

        return Optional.ofNullable(patientSearchResult);
    }

    Patient getPatient(PatientRequest req) throws Exception {
        Patient patient = new Patient();
        Optional<HealthCenter> center = this.centerService.findById(req.getCenter().getId());
        if(center.isPresent() == false){
            throw new Exception("Center not found");
        }
        patient.setId(req.getId());
        patient.setCreatedBy(req.getCreatedBy());

        if(patient.getId()==null) {
            patient.setPid(getPid(center.get()));
        }else{
            patient.setPid(req.getPid());
        }

        patient.setCenter(center.get());
        patient.setHome(req.getHome());
        if(req.getFullName()!=null) {
            patient.setFullName(req.getFullName().trim());
        }
        if(req.getGuardianName()!=null) {
            patient.setGuardianName(req.getGuardianName().trim());
        }
        if(req.getSpouseName()!=null){
            patient.setSpouseName(req.getSpouseName());
        }
        if(req.getMotherName() != null) {
            patient.setMotherName(req.getMotherName().trim());
        }
        if(req.getMobileNumber()!=null) {
            patient.setMobileNumber(req.getMobileNumber().trim());
        }
        if(req.getEmail()!=null){
            patient.setEmail(req.getEmail().trim());
        }
        patient.setMaritalStatus(req.getMaritalStatus());
        patient.setGender(req.getGender());
        patient.setAge(String.valueOf(req.getAge()));
        patient.setDob(req.getDob());
        Village village = null;
        if(req.getVillage() != null && req.getVillage().getLgVillageId() != null){
            village = req.getVillage();
        }
        patient.setVillage(village);
        patient.setStreetAddress(req.getStreetAddress());
        patient.setDetail(req.getDetail());
        if(req.getGb()!=null) {
            patient.setGB(req.getGb());
        }else{
            patient.setGB(false);
        }
        patient.setCreatedAt(req.getCreatedAt());
        return patient;
    }

    String getPid(HealthCenter center){
        Calendar calendar = Calendar.getInstance();
        int year = (calendar.get(Calendar.YEAR));
        int month = (calendar.get(Calendar.MONTH));
        int maxId = ((patientRepository.getMaxId()!=null)? (patientRepository.getMaxId()+1) : 1);
        return center.getCenterCode()+"-"+ year + (((month+1)<10)? "0"+(month+1) :
                (month+1)) + ((maxId<10)? "0"+maxId : maxId);
    }

    @Override
    public Integer getMaxCardRegId() {
        return ((patientRepository.getMaxCardRegId() !=null)? (patientRepository.getMaxCardRegId()+1): 1);
    }

    @Override
    public Patient getReference(Long id) {
         Patient patient = patientRepository.getOne(id);
         return patient;
    }

    @Override
    public List<PatientNumberAutoComplete> getPatientIds(String pid) {
        return patientRepository.findByPidContainingIgnoreCase(pid);
    }

    @Override
    public List<PatientNumberAutoComplete> getPatientIds(String type, String pid) {
        if(type.equalsIgnoreCase("pid")){
            return patientRepository.findByPidContainingIgnoreCase(pid);
        }else if(type.equalsIgnoreCase("mobile")){
            return patientRepository.findByMobileNumberContainingIgnoreCase(pid);
        }else if(type.equalsIgnoreCase("name")){
            return patientRepository.findByFullNameContainingIgnoreCase(pid);
        }
        return null;
    }

    @Override
    public List<PatientNumberAutoComplete> getPatientIds(String type, String pid, Long centerId) {
        HealthCenter center = new HealthCenter();
        center.setId(centerId);
        if(type.equalsIgnoreCase("pid")){
            return patientRepository.findByPidContainingIgnoreCaseAndCenter(pid,center);
        }else if(type.equalsIgnoreCase("mobile")){
            return patientRepository.findByMobileNumberContainingIgnoreCaseAndCenter(pid,center);
        }else if(type.equalsIgnoreCase("name")){
            return patientRepository.findByFullNameContainingIgnoreCaseAndCenter(pid,center);
        }else if(type.equalsIgnoreCase("card")){
            List<CardRegistration> cards = cardRegistrationRepository.findByCardNumberContaining(pid);
            List<Long> cardIds = new ArrayList<>();
            cards.forEach(c->{
                cardIds.add(c.getId());
            });
            return patientRepository.findByCenterAndRegistration_idIn(center,cardIds);
        }
        return null;
    }

    @Override
    public Page<PatientListItem> getPatientsBySearch(Long centerId, String field, String value, Pageable pageable) {

        if(centerId != null && field != null && value != null){

            if(field.equalsIgnoreCase("fullName")){
                return patientRepository.findByCenterAndFullName(centerId,value,pageable);
            }
            if(field.equalsIgnoreCase("mobileNumber")){
                return patientRepository.findByCenterAndMobileNo(centerId,value,pageable);
            }
            if(field.equalsIgnoreCase("pid")){
                return patientRepository.findByCenterAndPid(centerId,value,pageable);
            }
            if(field.equalsIgnoreCase("guardianName")){
                return patientRepository.findByCenterAndGuardianName(centerId,value,pageable);
            }
        }

        if(centerId == null && (field != null && value != null)){
            if(field.equalsIgnoreCase("fullName")){
                return patientRepository.findByFullName(value,pageable);
            }
            if(field.equalsIgnoreCase("mobileNumber")){
                return patientRepository.findByMobileNumber(value,pageable);
            }
            if(field.equalsIgnoreCase("pid")){
                return patientRepository.findByPid(value,pageable);
            }
            if(field.equalsIgnoreCase("guardianName")){
                return patientRepository.findByGuardianName(value,pageable);
            }
        }

        if(centerId!=null && (field == null || value==null)) {
            return patientRepository.findByCenter(centerId, pageable);
        }

        return patientRepository.findAllPatients(pageable);

    }

    @Override
    public Integer getAllPatientCount(HealthCenter center, String fromDate, String toDate) {
        Integer officeTypeId = center.getOfficeTypeId();
        if(officeTypeId==1 || officeTypeId==4){
            return patientRepository.getAllPatientStats(fromDate,toDate);
        }
        List<Long> centerIds = new ArrayList<>();
        if(officeTypeId==5){
            centerIds = getCenterIds(center);
            return patientRepository.getAllPatientStatsByCenters(centerIds, fromDate, toDate);
        }

        centerIds.add(center.getId());
        return patientRepository.getAllPatientStatsByCenters(centerIds, fromDate, toDate);

    }

    private List<Long> getCenterIds(HealthCenter center){
        List<HealthCenter> centers = null;
        List<Long> centerIds = new ArrayList<>();
        String centerCode = center.getCenterCode();
        centers = centerService.getCentersByThirdLevel(centerCode);
        centers.stream().forEach( _center->{
            centerIds.add(_center.getId());

        });

        return centerIds;
    }

    @Override
    public Integer getGbPatientCount(HealthCenter center, String fromDate, String toDate) {
        Integer officeTypeId = center.getOfficeTypeId();
        if(officeTypeId==1 || officeTypeId==4){
            return patientRepository.getAllGbPatientStats(fromDate,toDate);
        }
        List<Long> centerIds = new ArrayList<>();
        if(officeTypeId==5){
            centerIds = getCenterIds(center);
            return patientRepository.getAllGbPatientStatsByCenters(centerIds, fromDate, toDate);
        }

        centerIds.add(center.getId());
        return patientRepository.getAllGbPatientStatsByCenters(centerIds, fromDate, toDate);
    }

    @Override
    public Integer getNonGbPatientCount(HealthCenter center, String fromDate, String toDate) {
        Integer officeTypeId = center.getOfficeTypeId();
        if(officeTypeId==1 || officeTypeId==4){
            return patientRepository.getAllNonGbPatientStats(fromDate, toDate);
        }
        List<Long> centerIds = new ArrayList<>();
        if(officeTypeId==5){
            centerIds = getCenterIds(center);
            return patientRepository.getAllNonGbPatientStatsByCenters(centerIds,fromDate,toDate);
        }

        centerIds.add(center.getId());
        return patientRepository.getAllNonGbPatientStatsByCenters(centerIds,fromDate,toDate);
    }

    @Override
    public Optional<BigDecimal> getTotalAmount(HealthCenter center, String fromDate, String toDate) {
        Integer officeTypeId = center.getOfficeTypeId();
        if(officeTypeId==1 || officeTypeId==4){
            return invoiceRepository.getTotalAmount(fromDate,toDate);
        }
        List<Long> centerIds = new ArrayList<>();
        if(officeTypeId==5){
            centerIds = getCenterIds(center);
            return invoiceRepository.getTotalAmountByCenterIds(centerIds, fromDate, toDate);
        }

        centerIds.add(center.getId());
        return invoiceRepository.getTotalAmountByCenterIds(centerIds, fromDate, toDate);
    }

    @Override
    public Optional<BigDecimal> getTotalAmountUptoLastDay(HealthCenter center, String toDate) {
        Integer officeTypeId = center.getOfficeTypeId();
        if(officeTypeId==1 || officeTypeId==4){
            return invoiceRepository.getTotalAmountUptoLastDay(toDate);
        }
        List<Long> centerIds = new ArrayList<>();
        if(officeTypeId==5){
            centerIds = getCenterIds(center);
            return invoiceRepository.getTotalAmountByCenterIdsUpToLastDay(centerIds,toDate);
        }

        centerIds.add(center.getId());
        return invoiceRepository.getTotalAmountByCenterIdsUpToLastDay(centerIds, toDate);
    }

    @Override
    public Integer getAllPatientCountUpToLastDay(HealthCenter center, String toDate) {
        Integer officeTypeId = center.getOfficeTypeId();
        if(officeTypeId==1 || officeTypeId==4){
            return patientRepository.getAllPatientStats(toDate);
        }
        List<Long> centerIds = new ArrayList<>();
        if(officeTypeId==5){
            centerIds = getCenterIds(center);
            return patientRepository.getAllPatientStatsByCenters(centerIds, toDate);
        }

        centerIds.add(center.getId());
        return patientRepository.getAllPatientStatsByCenters(centerIds, toDate);
    }

    @Override
    public Integer getGbPatientCountUpToLastDay(HealthCenter center, String toDate) {
        Integer officeTypeId = center.getOfficeTypeId();
        if(officeTypeId==1 || officeTypeId==4){
            return patientRepository.getAllGbPatientStats(toDate);
        }
        List<Long> centerIds = new ArrayList<>();
        if(officeTypeId==5){
            centerIds = getCenterIds(center);
            return patientRepository.getAllGbPatientStatsByCenters(centerIds,  toDate);
        }

        centerIds.add(center.getId());
        return patientRepository.getAllGbPatientStatsByCenters(centerIds,  toDate);
    }

    @Override
    public Patient addPatient(ExistingPatientRequest exPatient) throws Exception {
        Patient patient = new Patient();
        Optional<HealthCenter> center = this.centerService.findById(exPatient.getCenter().getId());
        if(center.isPresent() == false){
            throw new Exception("Center not found");
        }
        patient.setPid(getPid(center.get()));
        patient.setCenter(center.get());
        patient.setFullName(exPatient.getFullName().trim());
        patient.setGender(exPatient.getGender());
        if(exPatient.getGb() != null) {
            patient.setGB(exPatient.getGb());
        }else{
            patient.setGB(false);
        }
        patient.setAge(String.valueOf(exPatient.getAge()));
        patient.setDob(exPatient.getDob());
        patient.setCreatedBy(exPatient.getCreatedBy());
        patient.setVillage(exPatient.getVillage());
        Patient p = patientRepository.save(patient);

        return p;
    }

    @Override
    public Optional<Integer> hasPatient(String fullName, String motherName, String villageId) {
        Village village = null;
        if(villageId!=null && villageId !=""){
            village = new Village();
            village.setLgVillageId(Long.parseLong(villageId));
        }
        return patientRepository.findByFullNameAndMotherNameVillage(fullName,motherName,village);
    }
}
