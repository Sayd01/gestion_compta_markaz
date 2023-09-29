package ci.saydos.markazcompta;

import ci.saydos.markazcompta.business.StatistiqueBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class MarkazComptaApplication  {

	public static void main(String[] args) {
		SpringApplication.run(MarkazComptaApplication.class, args);
	}

}
