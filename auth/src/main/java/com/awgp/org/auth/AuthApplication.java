package com.awgp.org.auth;

import com.awgp.org.auth.cognito.CognitoHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import brave.sampler.Sampler;

@SpringBootApplication
@EnableEurekaClient
public class AuthApplication {

	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	@Bean
	public CognitoHelper getCognitoHelper(){return new CognitoHelper();}

	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
	public static void main(String[] args) {

		SpringApplication.run(AuthApplication.class, args);
	}

}
