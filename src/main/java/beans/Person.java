package beans;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Person extends Entity {
	private String name;
    private String email;
    private String password;
    private String phone;
    private boolean acceptTerms;
    private String gender;
    private String idIn;
    private String tokenIn;
    private String birthday;
	
	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getPassword() {
		return password;
	}
	
	public boolean isAcceptTerms() {
		return acceptTerms;
	}
	
	public String getGender() {
		return gender;
	}
	
	public String getIdIn() {
		return idIn;
	}
	
	public String getTokenIn() {
		return tokenIn;
	}
	
	public String getBirthday() {
		return birthday;
	}

	@Override
	public String getNameDocument() {
		// TODO Auto-generated method stub
		return getClass().getSimpleName();
	}
}