package com.example.distributionLock;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DistributionLockApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributionLockApplication.class, args);
	}

	@Bean
	public Redisson redisson(){
		Config config = new Config();
		config.useSingleServer().setAddress("").setDatabase(0);
		return (Redisson) Redisson.create(config);
	}
}
