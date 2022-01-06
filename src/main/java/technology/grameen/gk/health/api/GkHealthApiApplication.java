package technology.grameen.gk.health.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.TimeZone;

@SpringBootApplication
public class GkHealthApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(GkHealthApiApplication.class, args);
	}

	@PostConstruct
	public void init(){
		// Setting Spring Boot SetTimeZone
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Dhaka"));
		System.out.println("TIME: "+Calendar.getInstance().getTime());
	}

}
