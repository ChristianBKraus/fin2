package fin2.configuration.organisation;

import fin2.configuration.Loader;
import fin2.model.Plant;
import org.springframework.stereotype.Service;

@Service
public class PlantLoader extends Loader<Plant> {

    public PlantLoader(PlantService service, PlantRepo repo) {
        super("Plant", service, repo);
        dependsOn("Company");
        add( Plant.builder().name("Plant 1").companyCode("0001").build() );
    }

}
