package rick.einvioice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"rick.einvioice.repository"})
@EntityScan("rick.einvioice.model")
@SpringBootApplication
public class EinvioiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EinvioiceApplication.class, args);
	}

}
