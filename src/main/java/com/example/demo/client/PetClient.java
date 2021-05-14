package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "pet-service-client", url = "${pet.service.prefix.url}")
public interface PetClient {

	@GetMapping("/pets")
	String getPetDetails();

}
