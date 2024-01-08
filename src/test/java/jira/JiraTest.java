package jira;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTest {

	public static void main(String[] args)
	{
		RestAssured.baseURI = "http://localhost:8082";
		//Login scenario
SessionFilter session = new SessionFilter(); //direct fetch response values from body or jsonPath()
		
String response = given()
                       .relaxedHTTPSValidation()// no proper certification for authentication it will valiate
		               .log().all()
		               .header("Content-Type","application/json")
		               .body("{\r\n"
		      		          + "    \"username\": \"rajappa.rpea20\",\r\n"
		      		          + "    \"password\": \"Raju@3484\"\r\n"
		      		          + "}")
		               .filter(session)
		          .when()
		               .post("/rest/auth/1/session")
		          .then()
		               .log().all()
		               .extract().response().asString();

String expectedMessage = "Hi How are you?";

	//add comment	
String addCommentResponse = given()
		     .log().all()
		     .header("Content-Type","application/json")
		     .pathParam("id", "10500")
		     .body("{\r\n"
		     		+ "    \"body\": \""+expectedMessage+"\",\r\n"
		     		+ "    \"visibility\": {\r\n"
		     		+ "        \"type\": \"role\",\r\n"
		     		+ "        \"value\": \"Administrators\"\r\n"
		     		+ "    }\r\n"
		     		+ "}")
		     .filter(session)
		.when()
		      .post("/rest/api/2/issue/{id}/comment")
		     
		.then()
		      .log().all()
		      .assertThat().statusCode(201).extract().response().asString();
JsonPath js = new JsonPath(addCommentResponse);
String commentId = js.getString("id");
		
	//add attachment  is not showing in jira 
      /*      given()
		           .pathParam("id", "10500")
		           .header("X-Atlassian-Token","no-check")
		           .header("Content-Type", "multipart/form-data")
		           .filter(session)
		           .multiPart("file", new File("jira.txt"))  //for add attachment
		     .when()
		           .post("/rest/api/2/issue/{id}/attachments")
		     .then()
		           .log().all()
		           .assertThat().statusCode(200);    */
		      
	//Get Issue
String issueDetails=given()
		            .log().all()
		            .filter(session)
		            .pathParam("id", "10500")
		            .queryParam("fields", "comment")
		      .when()
		            .get("/rest/api/2/issue/{id}")
		      .then()
		            .log().all()
		            .assertThat().extract().asString();
System.out.println(issueDetails);
 
     JsonPath js1 = new JsonPath(issueDetails);
    int commentsCounts = js1.get("fields.comment.comments.size()");
    
    for(int i=0; i<commentsCounts; i++)
    {
    	String commentIdIssue= js1.get("fields.comment.comments["+i+"].id").toString();
    	
    	if (commentIdIssue.equalsIgnoreCase(commentId)) 
    	{
    		String message = js1.get("fields.comment.comments["+i+"].body").toString();
    		System.out.println(message);
    		Assert.assertEquals(message, expectedMessage); 
    			
		}
    }
		    
    
	}

}
