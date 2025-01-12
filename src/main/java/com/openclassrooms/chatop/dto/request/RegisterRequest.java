package com.openclassrooms.chatop.dto.request;

public class RegisterRequest {
	
	private String name;
	private String email;
	private String password;

	public RegisterRequest() {
		
	}
	
	// Getters et Setters
    public String getName() {
        return name;
    }

    public void setNname(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
