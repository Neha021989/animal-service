package com.example.demo.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.client.PetClient;

import au.com.dius.pact.consumer.Pact;
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
		String pet = petClient.getPetDetails();
		assertEquals("dog", pet);
	}

	@Pact(provider = "pet-service", consumer = "petClient")
	public RequestResponsePact pactPetExists(PactDslWithProvider builder) {
		return builder.given("Pet exists").uponReceiving("A request to /pets").path("/pets").method("GET")
				.willRespondWith().status(200).body("dog").toPact();
	}

}
