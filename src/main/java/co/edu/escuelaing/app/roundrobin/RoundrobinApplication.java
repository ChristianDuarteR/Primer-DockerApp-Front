package co.edu.escuelaing.app.roundrobin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class RoundrobinApplication {

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(RoundrobinApplication.class);

		app.setDefaultProperties(Collections.singletonMap("server.port", getPort()));

		app.run(args);

	}

	private static int getPort() {
		if (System.getenv("PORT") != null) {
			return Integer.parseInt(System.getenv("PORT"));
		}
		return 5000;
	}

}
