package pojoDeserialization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) //added this to avoid this error, java.lang.RuntimeException: com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException restassured
public class GetCourseDeserialization 
{
	private String url;
	private String services;
	private String expertise;
	private Courses courses;
	private String instructor;
	private String linkedIn;
	
	//getters and setters
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getExpertise() {
		return expertise;
	}
	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}
	
	public Courses getCourses()
	{
		return courses;
	}
	
	public void setCourses(Courses courses) 
	{
		this.courses = courses;
	}
	
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public String getLinkdIn() {
		return linkedIn;
	}
	public void setLinkdIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}
	
	

}
