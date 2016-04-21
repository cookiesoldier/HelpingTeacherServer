package dtos;


import java.io.Serializable;

public class UserDTO implements Serializable {
	
	String username = " ";
	String email = " ";
	String firstname = " ";
	String lastname = " ";
	String password = " ";
	
	public UserDTO() {
		
	}
	/**
	 * 
	 * @param username
	 * @param email
	 * @param firstname
	 * @param lastname
	 * @param password
	 */
	public UserDTO(String username, String email, String firstname, String lastname, String password) {
		if (username != null) this.username = username;
		if (email != null) this.email = email;
		if (firstname != null) this.firstname = firstname;
		if (lastname != null) this.lastname = lastname; 
		if (password != null) this.password = password;

	}
	
	@Deprecated
	public UserDTO(String username, String password) {
		if (password != null) {
			this.password = password;
		}
		if (username != null) {
			this.username = username;
		}
	}
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "UserDTO{" +
				"username='" + username + '\'' +
				", email='" + email + '\'' +
				", firstname='" + firstname + '\'' +
				", lastname='" + lastname + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
