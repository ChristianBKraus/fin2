package fin2.configuration.organisation;

import fin2.configuration.Loader;
import fin2.model.SalesOrganisation;
import org.springframework.stereotype.Service;


@Service
public class SalesOrganisationLoader extends Loader<SalesOrganisation> {

    public SalesOrganisationLoader(SalesOrganisationService service, SalesOrganisationRepo repo) {
        super("SalesOrganisation", service, repo );
        dependsOn("Company");
        add( SalesOrganisation.builder().name("Org 1").companyCode("0001").build() );
    }

}
