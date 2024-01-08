package filesPayload;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson
{
	@Test(dataProvider = "BooksData") // connect the link bw the dataprovider to test
	public void addBook(String isbn, String aisle) //pass the below array element in this argument
	{
		//1.Dynamically build json payload with external data inputs
		
		RestAssured.baseURI = "http://216.10.245.166";
		
String response = given()
		               .header("Content-Type","application/json") //header compulsory in post()
		               .body(Payload.AddBook(isbn, aisle)).log().all()  //external data inputs
		     
		         .when()
		               .post("/Library/Addbook.php")
		    
		         .then()
		               .log().all()
		               .assertThat().statusCode(200)
		               .extract().response().asString();

     JsonPath js = ReUsableMethods.rawToJson(response);
     String id= js.get("ID");
     System.out.println(id);
     
	}
	
   //2.Parameterize the API Tests with multiple data sets
	
     @DataProvider(name="BooksData")
     public Object[][] getData()
     {
    	 //array = cllection of elements
    	 //multidimensional array = collection of arrays
    	 
    	 return new Object[][] { {"hsgah","9363"}, {"ahsj","4253"}, {"ashgd","533"} };		 
    		 
    	 }
     
    
}
     
     


