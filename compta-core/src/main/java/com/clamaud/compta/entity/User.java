package com.clamaud.compta.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	private static final long serialVersionUID = -5474405241555394262L;

	
	
	public User() {
		super();
	}

	public User(String name, String lastName, String password, String email) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.pass = password;
		this.email = email;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String name;
	
	private String lastName;
	
	private Integer age;
	
	private String pass;
	
	private String email;
	
	
	
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String password) {
		this.pass = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public Integer getId() {
		return id;
	}

	
	
	




}
