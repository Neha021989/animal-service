package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.PetClient;

@Service
public class AnimalService {

	@Autowired
	private PetClient petClient;

//	@Autowired
//	private WildClient wildClient;

	public String getAnimalDetails() {
		String petAnimal = petClient.getPetDetails();
		// String wildAnimal = wildClient.getWildDetails();
		return "Pet animal is " + petAnimal + " wild animal is Lion";

	}

}
