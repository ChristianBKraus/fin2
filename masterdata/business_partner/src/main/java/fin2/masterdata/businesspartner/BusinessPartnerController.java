package fin2.masterdata.businesspartner;

import fin2.model.BusinessPartner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = BusinessPartnerController.PATH)
@RestController
public class BusinessPartnerController {
    static final String PATH = "/api/business_partner";

    @Autowired
    BusinessPartnerRepo repo;
    @Autowired
    BusinessPartnerService service;


    @GetMapping("")
    public List<BusinessPartner> getAll() {
        return repo.findAll();
    }

    @PostMapping("")
    public BusinessPartner post(@RequestBody BusinessPartner partner )  {
        return service.post(partner);
    }
}
