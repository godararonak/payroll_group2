package com.example.salary;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class SalaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalaryApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STRICT);
//		try {
//			modelMapper.getConfiguration()
//					.setMatchingStrategy(MatchingStrategies.STRICT);
//		}catch (Exception e){
//			logger.error("Failed to set MatchingStrategy for modelMapper : {}", ExceptionUtils.getStackTrace(e));
//		}
		return modelMapper;
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}


}
