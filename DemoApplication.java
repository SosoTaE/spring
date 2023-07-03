package com.example.demo;

import java.util.ArrayList;
import org.bson.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;



@RestController
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/quadraticEquations")
	public String EquationsArray() throws JsonProcessingException  {
		ArrayList<QuadraticEquation> array = new ArrayList<>();

		MongoConnection database = new MongoConnection("mongodb://localhost:27017","QuadraticEquation","Parameters");
		
		MongoCursor<Document> cursor = database.collection.find().iterator();

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			while(cursor.hasNext()) {
				Document each = cursor.next();
				double a = (double)each.get("a");
				double b = (double)each.get("b");
				double c = (double)each.get("c");
				array.add(new QuadraticEquation(a,b,c));

			}
		}
		finally {
			cursor.close();
		}


		return objectMapper.writeValueAsString(array);

		
		
	}

	@GetMapping("/quadraticEquation")
	public String func(@RequestParam("a") double a,@RequestParam("b") double b,@RequestParam("c") double c) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();

		if (a == 0) {
			ErrorResponse error = new ErrorResponse("a must not be equal to 0");
			return objectMapper.writeValueAsString(error);
        }

        String result = objectMapper.writeValueAsString(new QuadraticEquation(a,b,c));

		return result;

	}

}

class MongoConnection {
		private String connectionString; // Replace with your MongoDB connection string
		private MongoClientSettings settings;
		private MongoClient client;

		public MongoConnection(String connectionString, String databaseName, String collectionName) {
			 try {
				this.connectionString = connectionString;
				this.settings = MongoClientSettings.builder()
						.applyConnectionString(new ConnectionString(connectionString))
						.build();
				this.client = MongoClients.create(this.settings);
				this.database = client.getDatabase(databaseName);
				this.collection = database.getCollection(collectionName);
        } catch (Exception e) {
            // Handle connection exception
            throw new RuntimeException("Error occurred while connecting to MongoDB.");
        	}
		}
		
		private MongoDatabase database; 
		public MongoCollection<Document> collection;
}

class ErrorResponse {
		private String error;

		public ErrorResponse(String error) {
			this.error = error;
		}

		public String getError() {
			return error;
		}
	}
