package pojoDeserialization;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class OAuthTest {

	public static void main(String[] args) throws InterruptedException 
	{
		String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor" }; // its a array
		//google is not supporting automation thats way used the manually enter
		
		//3 getting code and using split method extract code from current url
	/*	WebDriverManager.chromedriver().setup();
		WebDriver driver = new FirefoxDriver();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		driver.manage().window().maximize();
		//driver.findElement(By.xpath("//input[@id='identifierId']")).sendKeys("rajummetre2@gmail.com");
		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("rajummetre2@gmail.com");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[text()='Next']")).click();
		//driver.findElement(By.xpath("//span[text()='Next']")).sendKeys(Keys.ENTER);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Raju@3484");
		driver.findElement(By.xpath("//span[.='Next']")).click();
		Thread.sleep(5000);   */
	//	 String url = driver.getCurrentUrl();//Using split method in url and get code from currentUrl
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AfJohXlDGcpxDzHNcuVKDcfmGbjNPhL19jTQkmwOywhE5hHnKgD5NO9V3UX7ENWlStO3iA&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		//before running have to manuali enter url https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php
		// and then login after that provid current url in above because of getting code
		String partialcode = url.split("code=")[1];
		String code = partialcode.split("&scope")[0];
		System.out.println(code);
		
	
		//2 here generated accesstoken and provided in 1 method
		 String AccessTokenResponse = given()
				                    .urlEncodingEnabled(false)//encoding avoid because above code has special character
		                            .queryParams("code",code)//take code value above 3 method
		                            .queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		                            .queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		                            .queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
		                            .queryParams("grant_type","authorization_code")
		                            
		                      .when()
		                            .log().all()
		                            .post("https://www.googleapis.com/oauth2/v4/token").asString();
		 
		JsonPath js = new JsonPath(AccessTokenResponse);
		String AccessToken = js.getString("access_token");
		System.out.println("Access Token valu is: "+  AccessToken);
		
		
		
		
		//1 wirte this logic not there accessToken so get accesstoken above method and put below
		
		         GetCourseDeserialization gc = given()
	                                .queryParam("access_token", AccessToken)//take token valuve from the above 2 method
	                                .expect().defaultParser(Parser.JSON) // add extra things to whatever getting response type it should be converted to json type then parse it.and defaultParser(Parser.JSON) to avoid if header in content-type = application/json so use it application/json is not there
	                          .when()
	                                .get("https://rahulshettyacademy.com/getCourse.php")
	                                .as(GetCourseDeserialization.class); //once get the json body here it will  take that json body and put to the
		                                                                 //GetCourseDeserialization class here once getting json body it will convert to java object by usig deseriliziation
		                                                                 // here only sending the json body to do desirializaion
		                                                                 //as(Class<T> cls);
		         
	                       
	                       System.out.println(gc.getInstructor());
	                      System.out.println(gc.getLinkdIn());// not getting this one 
	                      System.out.println(gc.getUrl());
	                      System.out.println(gc.getCourses()); // getting like, pojo.Courses@7499eac7 so to do further
	                      
	                      System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());// using index here getting title so to do dynamically below show
	                      
	                       List<Api> apiCourses = gc.getCourses().getApi(); // dynamically used its main important
	                       for(int i=0; i<apiCourses.size(); i++)
	                       {
	                    	  if( apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
	                    	  {
	                    		  System.out.println(apiCourses.get(i).getPrice());
	                    	  }
	                       }
	                      
	                 //Get the Course names of WebAutomation
	                       
	                     ArrayList<String> actual = new ArrayList<String>(); // course title may be increase so dynamically so used arraylist to store the value
	                     
	                     List<WebAutomation> w = gc.getCourses().getWebAutomation();
	                     for(int j=0; j<w.size(); j++)
	                     {
	                    	 actual.add(w.get(j).getCourseTitle()); //all coursetitle to added to arralist
	                     }
	                      
	              //  to convert array to arraylist below for comparing array value and arraylist value  
	                      
	                   List<String> expected = Arrays.asList(courseTitles);
	                   Assert.assertTrue(actual.equals(expected));
	                   
	                      
	                   // below step doing because gooogle not supporting the automation this one so  
	                    // this is main in pojo class run here only do follow procedure
	              		// 1.here getcode url copy from postman and hit browser and login then copy current code url and paste above
	              	  //   2.whenever run everytime to do above proceducre.
	              	//     3.every time automatically code will extract and and generate token and finally gives response body
// above is all for POJO Deserialization process
		                  
	}

}
