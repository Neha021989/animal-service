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

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;

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

	@Test
	@PactTestFor(pactMethod = "pactUpdatePet")
	void updateAnimal() {
		Pet pet = new Pet("Bruno", "Cat");
		petClient.updatePet("Bruno", pet);
		assertEquals("Bruno", pet.getName());
	}

	@Test
	@PactTestFor(pactMethod = "pactDeletePet")
	void deleteAnimal() {
		petClient.deletePet("Bruno");
	}

	@Pact(provider = "pet-service", consumer = "petClient")
	public RequestResponsePact pactPetExists(PactDslWithProvider builder) {
		Map<String, String> headers = new HashMap();
		headers.put("Content-Type", "application/json");
		return builder.given("Pet exists").uponReceiving("A request to /pets").path("/pets").method("GET")
				.willRespondWith().status(200).headers(headers).body(new PactDslJsonBody()
						.stringMatcher("name", "[A-Za-z]*", "Bruno").stringMatcher("type", "[A-Za-z]*", "Dog"))
				.toPact();
	}

	@Pact(provider = "pet-service", consumer = "petClient")
	public RequestResponsePact pactSavePet(PactDslWithProvider builder) {
		Map<String, String> headers = new HashMap();
		headers.put("Content-Type", "application/json");
		return builder.given("Create a new pet").uponReceiving("A request to /pets").path("/pets").headers(headers)
				.method("POST").body(new PactDslJsonBody().stringMatcher("name", "[A-Za-z]*", "Bruno")
						.stringMatcher("type", "[A-Za-z]*", "Dog"))
				.willRespondWith().status(200).toPact();
	}

	@Pact(provider = "pet-service", consumer = "petClient")
	public RequestResponsePact pactUpdatePet(PactDslWithProvider builder) {
		Map<String, String> headers = new HashMap();
		headers.put("Content-Type", "application/json");
		return builder.given("Update a new pet").uponReceiving("A request to /pets").matchPath("/pets/[A-Za-z]+")
				.headers(headers).method("PUT")
				.body(new PactDslJsonBody().stringMatcher("name", "[A-Za-z]*", "Bruno").stringMatcher("type",
						"[A-Za-z]*", "Dog"))
				.willRespondWith().status(200).headers(headers).body(new PactDslJsonBody()
						.stringMatcher("name", "[A-Za-z]*", "Bruno").stringMatcher("type", "[A-Za-z]*", "Dog"))
				.toPact();
	}

	@Pact(provider = "pet-service", consumer = "petClient")
	public RequestResponsePact pactDeletePet(PactDslWithProvider builder) {
		return builder.given("delete a new pet").uponReceiving("A request to /pets").matchPath("/pets/[A-Za-z]+")
				.method("DELETE").willRespondWith().status(200).toPact();
	}
}
