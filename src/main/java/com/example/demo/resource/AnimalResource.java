package com.example.demo.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.AnimalService;

@RestController
@RequestMapping("/animals")
public class AnimalResource {

	@Autowired
	private AnimalService animalService;

	@GetMapping
	public String getAnimalDetails() {
		return animalService.getAnimalDetails();
	}

}
