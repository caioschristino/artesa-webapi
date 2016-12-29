package beans;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.mongodb.core.mapping.Document;

@XmlRootElement
@Document
public class Person extends Entity {
    private String name;
    private String email;
    private String password;
    private String phone;
    private int age;
	
	public Person(String name, String email, String password, String phone, int age) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.age = age;
	}
	
	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
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

	@Override
	public String getNameDocument() {
		// TODO Auto-generated method stub
		return getClass().getSimpleName();
	}
}