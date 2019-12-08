package fin2.configuration.organisation;

import fin2.configuration.SubVal;
import fin2.model.SalesOrganisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class SalesOrganisationService implements SubVal<SalesOrganisation> {
    private static long nextId;
    private String nextId() {
        nextId++;
        return String.format("%d",nextId);
    }

    @Autowired SalesOrganisationRepo repo;
    @Autowired CompanyRepo companyRepo;

    @Override
    public SalesOrganisation subVal(SalesOrganisation org, boolean change) throws Exception {

        if (! change ) {
            if (repo.findByName(org.getName()) != null) {
                throw new Exception("SalesOrganisation with name does already exist");
            }
        }
        if (! companyRepo.findById(org.getCompanyCode()).isPresent()) {
            throw new Exception("Company Code does not exist");
        }

        org.setSalesOrganisationId(nextId());

        return org;

    }
}


