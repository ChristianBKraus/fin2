package fin2.configuration.organisation;

import fin2.configuration.SubVal;
import fin2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class CompanyService implements SubVal<Company> {

    @Autowired CompanyRepo companyRepo;

    @Override
    public Company subVal(Company company, boolean change) throws Exception {

        if ( !change ) {
            if (companyRepo.findById(company.getCompanyCode()).isPresent()) {
                throw new Exception("Company Code already exists");
            }
        }

        return company;
    }
}
