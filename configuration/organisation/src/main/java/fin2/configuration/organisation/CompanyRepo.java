package fin2.configuration.organisation;

import fin2.model.*;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CompanyRepo extends MongoRepository<Company,String> {
    Company findByCompanyCode(String companyCode);
}
