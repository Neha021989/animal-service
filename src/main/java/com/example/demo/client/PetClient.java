package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.resource.Pet;

@FeignClient(name = "pet-service-client", url = "${pet.service.prefix.url}")
public interface PetClient {

	@GetMapping("/pets")
	Pet getPetDetails();

	@PostMapping("/pets")
	void savePet(@RequestBody Pet pet);

	@PutMapping("/pets/{name}")
	Pet updatePet(@PathVariable String name, @RequestBody Pet pet);

	@DeleteMapping("/pets/{name}")
	void deletePet(@PathVariable String name);

}
