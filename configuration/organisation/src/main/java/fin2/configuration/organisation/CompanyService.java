package fin2.configuration.organisation;

import fin2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class CompanyService {
    @Autowired
    CompanyRepo repo;

    Company post(Company company) throws Exception {
        if (repo.findById(company.getCompanyCode()).isPresent()) {
            throw new Exception("Company Code already exists");
        }

        company = repo.save(company);

        return company;
    }
}
