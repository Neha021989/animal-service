package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.client.PetClient;
import com.example.demo.resource.Pet;

@Service
public class AnimalService {

	@Autowired
	private PetClient petClient;

//	@Autowired
//	private WildClient wildClient;

	public Pet getAnimalDetails() {
		return petClient.getPetDetails();

	}

	public void saveAnimal(Pet pet) {
		petClient.savePet(pet);
	}

}
