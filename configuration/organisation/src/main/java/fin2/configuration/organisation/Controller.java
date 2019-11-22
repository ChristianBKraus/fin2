package fin2.configuration.organisation;

import fin2.configuration.organisation.repo.CompanyRepo;
import fin2.configuration.organisation.repo.SalesOrganisationRepo;
import fin2.model.Company;
import fin2.model.SalesOrganisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = Controller.PATH)
@RestController
public class Controller {
    static final String PATH = "/api";

    @Autowired
    Service service;

    // ---- SalesOrganisation
    @Autowired SalesOrganisationRepo orgRepo;

    @GetMapping("/sales_organisation")
    public List<SalesOrganisation> getSalesOrganisations() {
        return orgRepo.findAll();
    }

    @PostMapping("/sales_organisation")
    public SalesOrganisation postSalesOrganisation(
            @RequestBody SalesOrganisation org ) throws Exception {
        return service.post(org);
    }

    // ---- Company
    @Autowired CompanyRepo companyRepo;

    @GetMapping("/company")
    public List<Company> getCompanies() {
        return companyRepo.findAll();
    }

    @PostMapping("/company")
    public Company postCompany(
            @RequestBody Company company ) throws Exception {
        return service.post(company);
    }
}
