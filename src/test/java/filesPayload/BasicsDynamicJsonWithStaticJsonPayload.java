package filesPayload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import filesPayload.Payload;
import filesPayload.ReUsableMethods;

public class BasicsDynamicJsonWithStaticJsonPayload {

	public static void main(String[] args) throws IOException 
	{
		//3.How to send static json files(Payload) directly into Post Method of Rest Assured.
		
		//content of the file to String -> content of the file can convert into Byte -> Byte data to String
		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
	  String response = given()
			                 .queryParam("key", "qaclick123")
			                 .header("Content-Type","application/json")
		                     .body(new String(Files.readAllBytes(Paths.get("C:\\Users\\Admin\\Desktop\\Udaimy\\API Rest Assured By Rahul Shetty\\AddPlace.json")))).log().all()
		
		               .when()
		                    .post("maps/api/place/add/json")
		                    
		               .then()
		                    .assertThat().statusCode(200)
		                    .body("scope", equalTo("APP"))
		                    .header("server", "Apache/2.4.52 (Ubuntu)")
		                    .extract().response().asString();
	
	 System.out.println(response);
	 
	 JsonPath js = new JsonPath(response);
	 String placeId = js.getString("place_id"); 
	 
	 System.out.println(placeId);
	 
	// here ready or getting the data from the external file
	                        
	 
	 
		
		
		
		
	

	}

}
