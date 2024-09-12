package com._hw1.hw1page;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;


@SpringBootApplication
@RestController

public class Hw1pageApplication {

	public static void main(String[] args) {
		SpringApplication.run(Hw1pageApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello() {
		return "Hello world";  //Tester to ensure the connection works
	}

	@GetMapping("/bokoblin")
	public Object getCompendium(@RequestParam(value = "name") String mobName) {  //ADD NAME TO THE PARAMETER FOR THE SEARCH!!
		try {
			String url = "https://botw-compendium.herokuapp.com/api/v3/compendium/entry/" + mobName;  //Appends the given mobname to the url
			RestTemplate restTemplate = new RestTemplate();
			ObjectMapper mapper = new ObjectMapper();

			String jsonListResponse = restTemplate.getForObject(url, String.class);
			JsonNode root = mapper.readTree(jsonListResponse);

			CompendiumObject compendium = new CompendiumObject(root.get("data").get("name").asText(), root.get("data").get("category").asText(), root.get("data").get("description").asText());  //Obtains the information from the given data and converts it into useable information for the object
			return compendium;
		} catch (JsonProcessingException ex) {
			Logger.getLogger(Hw1pageApplication.class.getName()).log(Level.SEVERE, null, ex);
			return "error in /bokoblin";
		}


	}
}
