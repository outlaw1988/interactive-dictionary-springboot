package com.intdict.interactivedictionary.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;
	
	@Column(name = "email")
	@NotNull
    private String email;
	
    @Column(name = "password")
    @NotNull
    private String password;
    
    @Column(name = "password_confirm")
    @NotNull
    private String passwordConfirm;
    
    @Column(name = "login")
    @NotNull
    private String login;
    
    public User() {
    	// empty
    }
    
    public User(User user) {
    	this.id = user.getId();
    	this.email = user.getEmail();
    	this.login = user.getLogin();
    	this.password = user.getPassword();
    }

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
    
    
	
}
