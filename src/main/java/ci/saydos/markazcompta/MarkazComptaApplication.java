package ci.saydos.markazcompta;

import ci.saydos.markazcompta.business.StatistiqueBusiness;
import ci.saydos.markazcompta.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class MarkazComptaApplication  {

	public static void main(String[] args) {
		SpringApplication.run(MarkazComptaApplication.class, args);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());


		calendar.add(Calendar.DAY_OF_MONTH, 1);


		Date nouvelleDate = calendar.getTime();

		System.out.println(nouvelleDate);

	}

}
