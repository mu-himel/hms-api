package technology.grameen.gk.health.api.services;

import org.springframework.stereotype.Service;
import technology.grameen.gk.health.api.entity.HaPatientService;
import technology.grameen.gk.health.api.entity.PatientInvoice;
import technology.grameen.gk.health.api.repositories.healthassistant.HaPatientServiceRepository;
import technology.grameen.gk.health.api.requests.HaPatientServiceSell;
import technology.grameen.gk.health.api.services.invoice.PatientInvoiceService;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class HaPatientServiceImpl implements IHaPatientService {

    private HaPatientServiceRepository haPatientServiceRepository;
    private PatientInvoiceService patientInvoiceService;

    public HaPatientServiceImpl(HaPatientServiceRepository haPatientServiceRepository,
                                PatientInvoiceService patientInvoiceService) {
        this.haPatientServiceRepository = haPatientServiceRepository;
        this.patientInvoiceService = patientInvoiceService;
    }

    @Override
    @Transactional
    public HaPatientService addHaPatientService(HaPatientServiceSell haPatientService) throws Exception {
        PatientInvoice created = patientInvoiceService.createInvoice(haPatientService.getPatient());
        HaPatientService haPatientService1=null;
        if(created.getId()!=null) {
            haPatientService1= haPatientServiceRepository.save(haPatientService.getHaPatientService());
        }

        return haPatientService1;
    }

    @Override
    public List<?> getPatientServicesByDate(Long patientId, String date) {
        return haPatientServiceRepository.findByPatientAndCreatedAtContaining(patientId,date);
    }
}
