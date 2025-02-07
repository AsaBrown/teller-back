package com.browna.teller_back;

import com.browna.teller_back.config.ConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
@EnableJpaRepositories("com.browna.teller_back")
public class TellerBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(TellerBackApplication.class, args);
	}

}
