package beans;

import util.GsonUtils;

public class AuthResponse {
	private String token;
	private Person person;
	
	public AuthResponse(Person person){
		this.person = person;
		this.token = new TokenSecurity(person.getEmail(), person.getPassword()).toString();
	}
	
	public String getToken() {
		return token;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public String getJson(){
		return GsonUtils.getJsonFromClass(this);
	}
}
