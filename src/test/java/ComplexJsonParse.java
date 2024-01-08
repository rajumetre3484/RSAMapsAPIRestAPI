import java.util.ArrayList;

import org.testng.Assert;

import filesPayload.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) 
	{
		JsonPath js = new JsonPath(Payload.CoursePrice());
		
		//1. Print No of courses returned by API
		
		 int count = js.getInt("courses.size()");  //size is apply only in array.
		 System.out.println(count);
		 
		 //2.Print Purchase Amount
		 
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);	
		
		//3. Print Title of the first course
		
		String TitleofFirstCourse = js.get("courses[0].title"); //getString or get is default pull the of string 
        System.out.println(TitleofFirstCourse);
        
        
        //4. Print All course titles and their respective Prices        
      
   
      for(int i=0; i<count; i++)
      {
    	  String courseTitles = js.get("courses["+i+"].title");
    	  System.out.println(courseTitles);
    	  System.out.println(js.get("courses["+i+"].price").toString());//sysout accepts string argments
    	  
      }
      
      //5. Print no of copies sold by RPA Course
      
      System.out.println("Print no of copies sold by RPA Course");
     
      for(int i=0; i<count; i++)
      {
    	   String courseTitles = js.get("courses["+i+"].title");
    	   if(courseTitles.equalsIgnoreCase("RPA"))
    	   {
    		   int copies = js.get("courses["+i+"].copies");
    		   System.out.println(copies);
    		   break;
    	   }
    	   
      }
      
     // 6. Verify if Sum of all Course prices matches with Purchase Amount
      
      int sum=0;
      
      for(int i=0; i<count; i++)
      {
    	  int coursePrice = js.get("courses["+i+"].price"); 
    	  int copiesPrice = js.get("courses["+i+"].copies"); 
    	  sum = sum+ coursePrice*copiesPrice ;
      }
      
          int actualAmount =js.get("dashboard.purchaseAmount");
          
          System.out.println("sum of course amount " +sum);
          System.out.println("Purchase Amount " +actualAmount); 
          
          Assert.assertEquals(sum, actualAmount);
		

	}

}
