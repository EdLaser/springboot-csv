package de.ruben.csvupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
@ComponentScan({"de.ruben.csvupload.repository"})
public class CsvUploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvUploadApplication.class, args);
	}

}
