package fin2.configuration.organisation.repo;

import fin2.model.SalesOrganisation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SalesOrganisationRepo extends MongoRepository<SalesOrganisation,String> {
    SalesOrganisation findByName(String name);
}
