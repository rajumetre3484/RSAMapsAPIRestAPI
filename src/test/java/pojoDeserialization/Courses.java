package pojoDeserialization;

import java.util.List;

public class Courses   //its parent class GetCourseDeserialization
{
	//injected below variable to parent Courses class by using the getters and setters
	
	private List<WebAutomation> webAutomation; // not string because webAutomation is class so its return type is WebAutomation and its come from aray so used List<WebAutomation>
	private List<Api> api;                          // here also same
	private List<Mobile> mobile;                    // here also same 
	
	
	//getters and setters
	
	public List<WebAutomation> getWebAutomation()
	{
		return webAutomation;
	}
	public void setWebAutomation(List<WebAutomation> webAutomation) {
		this.webAutomation = webAutomation;
	}
	public List<Api> getApi() {
		return api;
	}
	public void setApi(List<Api> api) {
		this.api = api;
	}
	public List<Mobile> getMobile() {
		return mobile;
	}
	public void setMobile(List<Mobile> mobile) {
		this.mobile = mobile;
	}
	
	
	
}
