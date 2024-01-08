import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import filesPayload.Payload;
import filesPayload.ReUsableMethods;

public class Basics {

	public static void main(String[] args) 
	{
		//validate if Add Place API is working as expected
		
		//Given - all inputs details
		//When - Submite the API -resources,http method
		//Then - Validate the response
		
		//Add place-> Update Place with New Address  -> Get Place to validate if New adress is present in response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
	  String response = given()
			                 .queryParam("key", "qaclick123")
			                 .header("Content-Type","application/json")
		                     .body(Payload.AddPlace()).log().all()
		
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
	 
	
	 //Update Place
	 String newAddress = "Summer Walk, Africa";
	 
	                   given()
	                        .log().all()
	                        .queryParam("key", "qaclick123")
	                        .header("Content-Type","application/json")
	                        .body("{\r\n"
	 		                       + "\"place_id\":\""+placeId+"\",\r\n"
	 		                       + "\"address\":\""+newAddress+"\",\r\n"
	 		                       + "\"key\":\"qaclick123\"\r\n"
	 		                       + "}")
	                   .when()
	                        .put("maps/api/place/update/json")
	                   .then()
	                        .log().all()
	                        .assertThat().statusCode(200)
	                        .body("msg", equalTo("Address successfully updated"));
	                   
	           //Get Place : header is not there in Get() 
	                   
String getPlaceResponse = given()
	                        .log().all()
	                        .queryParam("key", "qaclick123")
	                        .queryParam("place_id", placeId )
	                     
	                      .when()
	                         .get("maps/api/place/get/json")
	                         
	                      .then()
	                         .log().all()
	                         .assertThat().statusCode(200)
	                         .extract().response().asString();

   JsonPath js1 = ReUsableMethods.rawToJson(getPlaceResponse);
   
   String actualAdress = js1.getString("address");
   System.out.println(actualAdress);
   
   Assert.assertEquals(newAddress, actualAdress);
	                         
	                        
	 
	 
		
		
		
		
	

	}

}
