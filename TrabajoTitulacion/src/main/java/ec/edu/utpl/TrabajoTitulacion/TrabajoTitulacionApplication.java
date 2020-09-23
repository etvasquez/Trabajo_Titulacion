package ec.edu.utpl.TrabajoTitulacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class TrabajoTitulacionApplication {
	public static void main(String[] args) {
		SpringApplication.run(TrabajoTitulacionApplication.class, args);
	}

}
