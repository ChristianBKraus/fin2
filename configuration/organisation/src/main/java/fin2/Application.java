package fin2;

import fin2.configuration.organisation.*;
import fin2.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.function.Supplier;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired CompanyLoader companyLoader;
	@Bean public Supplier<Company> loadCompany() { return () -> companyLoader.next(); }

	@Autowired PlantLoader plantLoader;
	@Bean public Supplier<Plant> loadPlant() { return () -> plantLoader.next(); }

	@Autowired SalesOrganisationLoader salesOrganisationLoader;
	@Bean public Supplier<SalesOrganisation> loadSalesOrganisation() { return () -> salesOrganisationLoader.next(); }
}
