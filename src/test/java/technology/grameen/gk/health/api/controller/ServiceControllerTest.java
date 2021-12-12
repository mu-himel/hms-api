package technology.grameen.gk.health.api.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import technology.grameen.gk.health.api.entity.LabTestGroup;
import technology.grameen.gk.health.api.projection.ServiceListItem;
import technology.grameen.gk.health.api.resources.ServiceController;
import technology.grameen.gk.health.api.responses.EntityCollectionResponse;
import technology.grameen.gk.health.api.services.HealthServiceInterface;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ServiceControllerTest {

    @Autowired
    private ServiceController serviceController;

    @MockBean
    HealthServiceInterface healthServiceInterface;



    @Test
    public void serviceList() throws Exception {
        List<ServiceListItem> serviceListItems = new ArrayList<>();
        ServiceListItemObj obj = new ServiceListItemObj();
        obj.setServiceId(2L);
        serviceListItems.add(obj);
        when(healthServiceInterface.getAll()).thenReturn(serviceListItems);
        ResponseEntity result = serviceController.list();
        assertEquals(HttpStatus.OK,result.getStatusCode());
        EntityCollectionResponse body = (EntityCollectionResponse) result.getBody();
        assertEquals(1,body.getCollection().size());
    }


    class ServiceListItemObj implements ServiceListItem{

        private Long serviceId;

        public void setServiceId(Long serviceId) {
            this.serviceId = serviceId;
        }

        @Override
        public Long getServiceId() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getCode() {
            return null;
        }

        @Override
        public ServiceCategory getServiceCategory() {
            return null;
        }

        @Override
        public LabTestGroup getLabTestGroup() {
            return null;
        }

        @Override
        public BigDecimal getCurrentGbCost() {
            return null;
        }

        @Override
        public BigDecimal getCurrentCost() {
            return null;
        }

        @Override
        public BigDecimal getFieldCost() {
            return null;
        }

        @Override
        public Boolean getFieldSaleable() {
            return null;
        }

        @Override
        public Boolean getNeedAccessories() {
            return null;
        }

        @Override
        public Boolean getActive() {
            return null;
        }

        @Override
        public Boolean getLabTest() {
            return null;
        }
    }
}
