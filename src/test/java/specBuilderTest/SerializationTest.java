package specBuilderTest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.json.Json;


public class SerializationTest       //Optimising code in request sending side i.e common points
{
	public static void main(String[] args) // main class run here only
	{
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		
		  AddPlace p = new AddPlace(); // here value to be set and then ref var p pass to the body
		  p.setAccuracy(50);
		  p.setName("Frontline house");
		  p.setPhone_number("(+91) 983 893 3937");
		  p.setAddress("29, side layout, cohen 09");
		  p.setLanguage("French-IN");
		  p.setWebsite("http://google.com");
		  
		  List<String> myList = new ArrayList<String>();
		  myList.add("shoe park");
		  myList.add("shop");
		  
		  p.setTypes(myList);  // because its array so created araylist and add the values above 
		  
		  Location l = new Location();
		  l.setLat( -38.383494);
		  l.setLng(33.427362);
		  
		  p.setLocation(l); //Location  is class so created object of Location class and using ref var we add the values in its class above shown
		  
       RequestSpecification req = new RequestSpecBuilder()
                                                         .setBaseUri("https://rahulshettyacademy.com/")
                                                         .addQueryParam("key", "qaclick123")
                                                         .setContentType(ContentType.JSON)  // set key
                                                         .build(); 
       
    ResponseSpecification respspec = new ResponseSpecBuilder()
                                                           .expectStatusCode(200)  // expect key
                                                           .expectContentType(ContentType.JSON)
                                                           .build();
       
		    
		  RequestSpecification res = given()
				               .spec(req)  // all reqest common points store and provided here using spec builder
		    		           .body(p);   // return type RequestSpecification ending here ; onnly
		  
     Response response = res.when()
		    		             .post("/maps/api/place/add/json")
		    		        .then()
		    		             .spec(respspec).extract().response();
		  
		  
		 String responseString = response.asString(); // provided response here instead of res
		 System.out.println(responseString);
		    		           
		
	}

}
