package oauth2;

import static io.restassured.RestAssured.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.path.json.JsonPath;

public class OAuthTest {

	public static void main(String[] args) throws InterruptedException 
	{
		//google is not supporting automation thats way used the manually enter
		
		//3 getting code and using split method extract code from current url
		WebDriverManager.chromedriver().setup();
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
		Thread.sleep(5000);  
	//	 String url = driver.getCurrentUrl();//Using split method in url and get code from currentUrl
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AfJohXkW1Tzd3Prv6TZSc-6YCPPY0rQK5SmLKiliLVkYrkqrpRbluXDRgUL0qARRYG1Acw&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
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
		System.out.println("Access Token valu is: "+AccessToken);
		
		
		
		
		//1 wirte this logic not there accessToken so get accesstoken above method and put below
		    String response  = given()
	                                .queryParam("access_token", AccessToken)//take token valuve from the above 2 method
	                          .when()
	                                .log().all()
	                                .get("https://rahulshettyacademy.com/getCourse.php")
	                                .asString();// here avoid validation and get raw response in string
		                            
		    System.out.println(response);
	      
		    // below step doing because gooogle not supporting the automation this one so
		// 1.here code url copy from postman and hit browser and login then copy current code url and paste above
	  //   2.whenever run everytime to do above proceducre.
	//     3.every time automatically code will extract and and generate token and finally gives response body

	}

}
