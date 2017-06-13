package ahuglajbclajep.spring.boot.ajaxqrcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AjaxQrcodeApplication {

	@GetMapping("/")
	public String hello() {
		return "Hello World!";
	}

	public static void main(String[] args) {
		SpringApplication.run(AjaxQrcodeApplication.class, args);
	}
}
