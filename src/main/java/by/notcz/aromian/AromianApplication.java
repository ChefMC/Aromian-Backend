package by.notcz.aromian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { 
  "by.notcz.aromian.controllers" 
})
public class AromianApplication {

	public static void main(String[] args) {
		SpringApplication.run(AromianApplication.class, args);
	}

}
