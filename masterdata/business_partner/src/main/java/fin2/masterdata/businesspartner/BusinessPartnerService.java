package fin2.masterdata.businesspartner;

import fin2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class BusinessPartnerService {
    private static long nextId;
    private String nextId() {
        nextId++;
        return String.format("%d",nextId);
    }

    @Autowired
    BusinessPartnerRepo repo;


    BusinessPartner post(BusinessPartner partner)  {

        partner.setId(nextId());
        partner = repo.save(partner);

        return partner;
    }
}
