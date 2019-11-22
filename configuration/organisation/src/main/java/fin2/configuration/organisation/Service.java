package fin2.configuration.organisation;

import fin2.configuration.organisation.repo.*;
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
    SalesOrganisationRepo orgRepo;

    @Autowired
    CompanyRepo companyRepo;

    SalesOrganisation post(SalesOrganisation input) throws Exception {

        if (orgRepo.findByName(input.getName()) != null)  {
            throw new Exception("SalesOrganisation with name does already exist");
        }
        if (! companyRepo.findById(input.getCompanyCode()).isPresent()) {
            throw new Exception("Company Code does not exist");
        }

        SalesOrganisation org = SalesOrganisation.builder()
                .salesOrganisationId(nextId())
                .name(input.getName())
                .companyCode(input.getCompanyCode())
                .build();
        org = orgRepo.save(org);

        return org;

    }

    Company post(Company input) throws Exception {
        if (companyRepo.findById(input.getCompanyCode()).isPresent()) {
            throw new Exception("Company Code already exists");
        }

        Company company = Company.builder()
                .companyCode(input.getCompanyCode())
                .name(input.getName())
                .build();
        company = companyRepo.save(company);

        return company;
    }
}
