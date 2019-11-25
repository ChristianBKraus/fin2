package fin2.masterdata.businesspartner;

import fin2.masterdata.businesspartner.repo.BusinessPartnerRepo;
import fin2.model.BusinessPartner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = Controller.PATH)
@RestController
public class Controller {
    static final String PATH = "/api";

    @Autowired
    Service service;

    // ---- BusinessPartner
    @Autowired
    BusinessPartnerRepo partnerRepo;

    @GetMapping("/business_partner")
    public List<BusinessPartner> getBusinessPartners() {
        return partnerRepo.findAll();
    }

    @PostMapping("/business_partner")
    public BusinessPartner postBusinessPartner(
            @RequestBody BusinessPartner partner )  {
        return service.post(partner);
    }
}
