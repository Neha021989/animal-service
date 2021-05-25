package com.example.demo.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.client.PetClient;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "pet-service", port = "8084")
@ExtendWith(SpringExtension.class)
@SpringBootTest({ "pet.service.prefix.url: http://localhost:8084" })
class AnimalServiceTest {

	@Autowired
	private PetClient petClient;

	@Test
	@PactTestFor(pactMethod = "pactPetExists")
	void getAnimal() {
		Pet pet = petClient.getPetDetails();
		assertEquals("Bruno", pet.getName());
	}

	@Test
	@PactTestFor(pactMethod = "pactSavePet")
	void saveAnimal() {
		Pet pet = new Pet("Bruno", "Dog");
		petClient.savePet(pet);
	}

	@Pact(provider = "pet-service", consumer = "petClient")
	public RequestResponsePact pactPetExists(PactDslWithProvider builder) {
		Map<String, String> headers = new HashMap();
		headers.put("Content-Type", "application/json");
		return builder.given("Pet exists").uponReceiving("A request to /pets").path("/pets").method("GET")
				.willRespondWith().status(200).headers(headers)
				.body(new PactDslJsonBody().stringType("name", "Bruno").stringType("type", "Dog")).toPact();
	}

	@Pact(provider = "pet-service", consumer = "petClient")
	public RequestResponsePact pactSavePet(PactDslWithProvider builder) {
		Map<String, String> headers = new HashMap();
		headers.put("Content-Type", "application/json");
		return builder.given("Create a new pet").uponReceiving("A request to /pets").path("/pets").headers(headers)
				.method("POST").body(new PactDslJsonBody().stringType("name", "Bruno").stringType("type", "Dog"))
				.willRespondWith().status(200).toPact();
	}
}
