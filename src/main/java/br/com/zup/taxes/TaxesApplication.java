package br.com.zup.taxes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class TaxesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxesApplication.class, args);
	}

}
