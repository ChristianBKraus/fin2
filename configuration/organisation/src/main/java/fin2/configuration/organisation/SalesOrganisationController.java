package fin2.configuration.organisation;

import fin2.model.SalesOrganisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = SalesOrganisationController.PATH)
@RestController
public class SalesOrganisationController {
    static final String PATH = "/api/sales_organisation";

    @Autowired
    SalesOrganisationRepo repo;
    @Autowired
    SalesOrganisationService service;

    @GetMapping("")
    public List<SalesOrganisation> getAll() {
        return repo.findAll();
    }

    @PostMapping("")
    public SalesOrganisation post(@RequestBody SalesOrganisation org ) throws Exception {
        return service.post(org);
    }

}
