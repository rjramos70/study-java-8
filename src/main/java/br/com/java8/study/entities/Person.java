package br.com.java8.study.entities;

import java.time.LocalDate;

public class Person {
	
	public enum Sex{
		MALE, FEMALE
	}
	
	String name;
	LocalDate birthday;
	Sex gender;
	String emailAddress;
	
	

}
