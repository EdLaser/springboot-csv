package de.ruben.csvupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@EntityScan("de.ruben.csvupload.model")
@EnableJpaRepositories("de.ruben.csvupload.repository")
public class CsvUploadApplication {


	public static void main(String[] args) {
		SpringApplication.run(CsvUploadApplication.class, args);
	}

}
