package fin2.configuration.organisation.repo;

import fin2.model.*;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface BusinessPartnerRepo extends MongoRepository<BusinessPartner,String> {
}
