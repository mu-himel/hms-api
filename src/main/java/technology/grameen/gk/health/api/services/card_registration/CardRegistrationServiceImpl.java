package technology.grameen.gk.health.api.services.card_registration;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.entity.CardRegistration;
import technology.grameen.gk.health.api.entity.HealthCenter;
import technology.grameen.gk.health.api.entity.Patient;
import technology.grameen.gk.health.api.exceptions.CustomException;
import technology.grameen.gk.health.api.repositories.CardMemberRepository;
import technology.grameen.gk.health.api.repositories.CardRegistrationRepository;
import technology.grameen.gk.health.api.services.patient.PatientManageService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardRegistrationServiceImpl  implements  CardRegistrationService{

    PatientManageService patientService;
    CardRegistrationRepository cardRegistrationRepository;
    CardMemberRepository cardMemberRepository;

    CardRegistrationServiceImpl(PatientManageService patientService,
                                CardRegistrationRepository cardRegistrationRepository,
                                CardMemberRepository cardMemberRepository){

        this.patientService = patientService;
        this.cardRegistrationRepository = cardRegistrationRepository;
        this.cardMemberRepository = cardMemberRepository;
    }

    @Override
    public CardRegistration getNewCardRegistrationRequest(Patient patient) {
        return (patient.getRegistration().getId()==null)? patient.getRegistration() : null;
    }

    @Override
    @Transactional
    public Boolean register(Patient patient) throws Exception{

        CardRegistration cardRegistration = patient.getRegistration();//getNewCardRegistrationRequest(patient);

        // find patient's expired cardRegistration for renew

        if(cardRegistration != null) {
            Patient _patient = patientService.getReference(patient.getId());


            if (_patient == null) {
                throw new Exception("Patient Not found");
            }


            HealthCenter center = patient.getCenter();
            cardRegistration.setCardNumber(getCardNumber(center));
            cardRegistration.setCreatedAt(LocalDateTime.now());
            cardRegistration.setStartDate(getRegistrationStartDate());
            cardRegistration.setExpiredDate(getRegistrationExpireDate(cardRegistration.getValidityDuration()));
            cardRegistration.setTotalServiceTaken(0);
            patient.addRegistration(cardRegistration);
            cardRegistration.setActive(true);
            cardRegistrationRepository.save(cardRegistration);

            if (cardRegistration.getId() > 0) {
//                cardRegistration.getMembers()
//                        .stream()
//                        .map(cardMember -> {
//                            cardMember.setCardRegistration(cardRegistration);
//
//                            return cardMember;
//                        }).collect(Collectors.toSet());
//                cardMemberRepository.saveAll(cardRegistration.getMembers());
                this.addCardMembers(cardRegistration);
                return true;
            }

        }
        return false;
    }

    @Override
    @Transactional
    public Boolean register(Patient patient, Boolean existingPatient) throws Exception {

        CardRegistration cardRegistration = patient.getRegistration();
        if(cardRegistration != null) {

            List<CardRegistration> cardRegistrations = getCardRegistrationsByNumber(cardRegistration.getCardNumber());

            if(cardRegistrations.size()>0) {
                throw new CustomException("Card number already exist with number "+cardRegistration.getCardNumber());
            }

            Patient _patient = patientService.getReference(patient.getId());


            if (_patient == null) {
                throw new Exception("Patient Not found");
            }

            cardRegistration.setCreatedAt(LocalDateTime.now());
            cardRegistration.setTotalServiceTaken(0);
            patient.addRegistration(cardRegistration);
            cardRegistration.setActive(true);
            cardRegistrationRepository.save(cardRegistration);

            if (cardRegistration.getId() > 0) {
                this.addCardMembers(cardRegistration);
                return true;
            }
        }
        return false;
    }

    String getCardNumber(HealthCenter center){
        Calendar calendar = Calendar.getInstance();
        int year = (calendar.get(Calendar.YEAR));
        int month = (calendar.get(Calendar.MONTH));
        int maxId = patientService.getMaxCardRegId();
        return center.getCenterCode()+"-"+ year + (((month+1)<10)? "0"+(month+1) :
                (month+1)) + ((maxId<10)? "0"+maxId : maxId);
    }

    LocalDateTime getRegistrationStartDate(){

        return LocalDateTime.now();
    }

    LocalDateTime getRegistrationExpireDate(Integer duration){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,duration);
        ZoneId zoneId = calendar.getTimeZone().toZoneId();
        return LocalDateTime.ofInstant(calendar.toInstant(),zoneId);
    }

    @Override
    public Boolean addCardMembers(CardRegistration cardRegistration) {
        cardRegistration.getMembers()
                .stream()
                .map(cardMember -> {
                    cardMember.setCardRegistration(cardRegistration);

                    return cardMember;
                }).collect(Collectors.toSet());
        cardMemberRepository.saveAll(cardRegistration.getMembers());
        return true;
    }

    @Override
    public List<CardRegistration> getCardRegistrationsByNumber(String number) {
        return cardRegistrationRepository.findByCardNumberContaining(number);
    }
}
