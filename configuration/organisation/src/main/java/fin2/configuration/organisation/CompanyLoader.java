package fin2.configuration.organisation;

import fin2.configuration.Loader;
import fin2.model.Company;
import org.springframework.stereotype.Service;

@Service
public class CompanyLoader extends Loader<Company> {

    public CompanyLoader(CompanyService service, CompanyRepo repo) {
        super("Company",service,repo);
        add( Company.builder().companyCode("0001").name("Company1").companyCodeCurrency("EUR").build() );
        add( Company.builder().companyCode("0002").name("Company2").companyCodeCurrency("EUR").build() );
        add( Company.builder().companyCode("0003").name("Company3").companyCodeCurrency("USD").build() );
    }
}

