package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.resource.Pet;

@FeignClient(name = "pet-service-client", url = "${pet.service.prefix.url}")
public interface PetClient {

	@GetMapping("/pets")
	Pet getPetDetails();

	@PostMapping("/pets")
	void savePet(@RequestBody Pet pet);

}
