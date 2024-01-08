package eCommerceAPI;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import net.bytebuddy.NamingStrategy.Suffixing.BaseNameResolver.ForGivenType;

public class EcommerceAPITest {

	public static void main(String[] args) 
	{
		//login 
		RequestSpecification req = new RequestSpecBuilder()
				                                          .setBaseUri("https://rahulshettyacademy.com")
				                                          .setContentType(ContentType.JSON)
				                                          .build();
		
LoginRequestSerializationPOJO loginRequest = new LoginRequestSerializationPOJO();//in serilization created pojo and declared variable and initilised below values using setters method
loginRequest.setUserEmail("rajummetre2@gmail.com");
loginRequest.setUserPassword("Raju@3484");

    RequestSpecification reqLogin = given()
    	                                  .relaxedHTTPSValidation()// SSL certificate is Bypasses in industries
    		                              .log().all()
		                                  .spec(req)
		                                  .body(loginRequest);
 LoginResponsePayLoadDeseialization loginResopnse = reqLogin.when()
                                                                 .post("/api/ecom/auth/login")
                                                            .then()
                                                                  .log().all()
                                                                  .extract().response().as(LoginResponsePayLoadDeseialization.class);//get Response As POJO in form
		                            
       System.out.println(loginResopnse.getToken());
       String token = loginResopnse.getToken();
       
       System.out.println(loginResopnse.getUserId());
       String userId = loginResopnse.getUserId();
       
     /*  
       //Add or create product  ......its having issue with creating product methods are same in eclipse and postman
       
 RequestSpecification addProductBaseReq = new RequestSpecBuilder()
                                                                .setBaseUri("https://rahulshettyacademy.com")
                                                                .addHeader("authorization", token)
                                                                .addHeader("Content-Type", "multipart/form-data; charset=UTF-8")//iam writing external
                                                                .build();
    RequestSpecification reqAddproduct = given()
    		
                                               .log().all()
                                               .spec(addProductBaseReq)
                                            //   .contentType("multipart/form-data")//iam writing external
                                               .param("productName", "laptop")
                                               .param("productAddedBy", userId)
                                               .param("productCategory", "fashion")
                                               .param("productSubCategory", "shirts")
                                               .param("productPrice", "11500")
                                               .param("productDescription", "Addias Originals")
                                               .param("productFor", "men")
                                               .multiPart("productimage", new File("/Users//Admin/Downloads/pic.png"));// this helps us to file uploading in test
    
                                               
                                          
   String addProductResponse = reqAddproduct.when()
                                                 .post("/api/ecom/product/add-product")
		                                        
                                                  
                                            .then()
                                                  .log().all()
                                                  .extract().response().asString(); //get Response As JsonPath
   
  JsonPath js = new JsonPath(addProductResponse); // not use de-serialization because two variables in response so
  String productId = js.get("productId");    
                                                   
                                                               */
 
  //Create Order
 
  RequestSpecification createOrderBaseReq = new RequestSpecBuilder()
                                                                   .setBaseUri("https://rahulshettyacademy.com")
                                                                   .addHeader("authorization", token)
                                                                   .setContentType(ContentType.JSON)
                                                            //     .addHeader("Content-Type", "application/json; charset=UTF-8") // iam writing external
                                                                   .build();
  
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setCountry("India");
        orderDetails.setProductOrderedId("6262e990e26b7e1a10e89bfa");  
    //  orderDetails.setProductOrderedId(productId);
        
        List<OrderDetails> orderDetailList = new ArrayList<OrderDetails>(); //injected array to orders
        orderDetailList.add(orderDetails);
        
          Orders orders = new Orders(); 
          orders.setOrders(orderDetailList);
          
    RequestSpecification createOrderReq = given()
                                                .log().all()
                                                .spec(createOrderBaseReq)
                                                .body(orders);
  String responseOrderReq = createOrderReq.when()
                                                .post("/api/ecom/order/create-order")
                                           .then()
                                                 .log().all()
                                                 .extract().response().asString();
  System.out.println(responseOrderReq);
  
             //placed the order ok   
  
  
   /*
                     //view product its me own created result not ok confused in id in query param
  
  RequestSpecification viewProductBaseReq = new RequestSpecBuilder()
                                                                   .setBaseUri("https://rahulshettyacademy.com")
                                                                   .addHeader("authorization", token)
                                                            //    .addHeader("Content-Type", "application/json; charset=UTF-8") // iam writing external
                                                                   .build();
       
   RequestSpecification viewOrderReq = given()
	                                        .log().all()
	                                        .spec(viewProductBaseReq)
	                                        .queryParam("id", "6558a3097244490f95eaffe3");
 String ResponseViewReq = viewOrderReq.when()
                                            .post("/api/ecom/order/get-orders-details")
                                      .then()
                                            .log().all()
                                            .extract().response().asString();
          System.out.println(ResponseViewReq);
		
		                                              */
  
  
  
            //Delete Product
  RequestSpecification DeleteProductBaseReq = new RequestSpecBuilder()
                                                                   .setBaseUri("https://rahulshettyacademy.com")
                                                                   .addHeader("authorization", token)
                                                                   .setContentType(ContentType.JSON)
                                                             //    .addHeader("Content-Type", "application/json; charset=UTF-8") // iam writing external
                                                                   .build();
  
  
          RequestSpecification DeleteProductREq = given()
                                                      .log().all()
                                                      .spec(DeleteProductBaseReq)
                                                      .pathParam("productId", "6262e990e26b7e1a10e89bfa");
                                                 //     .pathParam("productId", productId) //insted of this using above
                                                      
  String   DeleteProductResponse = DeleteProductREq.when()
                                                       .delete("/api/ecom/product/delete-product/6262e990e26b7e1a10e89bfa")
                                               //      .delete("/api/ecom/product/delete-product/{productId}")
                                                  .then()
                                                        .log().all()
                                                        .extract().response().asString(); //single variable used as asString(), complex thing use as POJO
                                   System.out.println(DeleteProductResponse);
                                   
     JsonPath js1 = new JsonPath(DeleteProductResponse);
    Assert.assertEquals("Product Deleted Successfully",  js1.get("message"));
                                                       
  
  // problem occurs in creating or Add the product so can't get the product Id.
     
  
	}

}
