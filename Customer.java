package lab4;

import java.io.Serializable;

public class Customer implements Serializable {
	
	String name;
	int age;
	String email;
	
	public Customer(String n, int a, String mail) {;
			name = n;
			age = a;
			email=mail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int id) {
		this.age = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String mail) {
		this.email = mail;
	}
	
	public String toString() {
		return getName() + "\t" + getAge() + "\t" + getEmail() + "\n";
	}

}

