package ec.edu.utpl.TrabajoTitulacion;

import ec.edu.utpl.TrabajoTitulacion.storage.StorageProperties;
import ec.edu.utpl.TrabajoTitulacion.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableConfigurationProperties(StorageProperties.class)
public class TrabajoTitulacionApplication {
	public static void main(String[] args) {
		SpringApplication.run(TrabajoTitulacionApplication.class, args);
	}
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			//Eliminar todos los archivos de la carpeta upload-dir
			//storageService.deleteAll();
			storageService.init();
		};
	}
}
