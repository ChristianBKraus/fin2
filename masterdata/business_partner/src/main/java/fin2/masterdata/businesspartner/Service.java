package fin2.masterdata.businesspartner;

import fin2.masterdata.businesspartner.repo.BusinessPartnerRepo;
import fin2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class Service {
    private static long nextId;
    private String nextId() {
        nextId++;
        return String.format("%d",nextId);
    }

    @Autowired
    BusinessPartnerRepo partnerRepo;


    BusinessPartner post(BusinessPartner input)  {

        BusinessPartner partner = BusinessPartner.builder()
                .id(nextId())
                .name(input.getName())
                .build();
        partner = partnerRepo.save(partner);

        return partner;
    }
}
