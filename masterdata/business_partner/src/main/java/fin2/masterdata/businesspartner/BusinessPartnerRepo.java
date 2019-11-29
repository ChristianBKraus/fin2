package fin2.masterdata.businesspartner;

import fin2.model.*;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface BusinessPartnerRepo extends MongoRepository<BusinessPartner,String> {
}
