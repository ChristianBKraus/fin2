package fin2.configuration.organisation;
/*
import fin2.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = CompanyController.PATH)
@RestController
public class CompanyController {
    static final String PATH = "/api/company";

    @Autowired
    CompanyRepo repo;
    @Autowired
    CompanyService service;

    @GetMapping("")
    public List<Company> getAll() {
        return repo.findAll();
    }

    @PostMapping("")
    public Company post(@RequestBody Company company ) throws Exception {
        return repo.save(service.post(company,false));
    }

}
*/