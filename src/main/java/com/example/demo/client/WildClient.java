package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "wild-service-client", url = "${wild.service.prefix.url}")
public interface WildClient {

	@GetMapping("/wild")
	String getWildDetails();
}
