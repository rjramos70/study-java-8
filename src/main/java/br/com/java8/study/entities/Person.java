package br.com.java8.study.entities;

import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.List;

public class Person {
	
	public enum Sex{
		MALE, FEMALE
	}
	
	String name;
	LocalDate birthday;
	Sex gender;
	String emailAddress;
	
	public Person(String nameArg, LocalDate birthdayArg, Sex genderArg, String emailAddressArg) {
		this.name = nameArg;
		this.birthday = birthdayArg;
		this.gender = genderArg;
		this.emailAddress = emailAddressArg;
	}
	
	public int getAge() {
		return this.birthday
				.until(IsoChronology.INSTANCE.dateNow())
				.getYears();
	}
	
	public void printPerson() {
		System.out.println(this.name.concat(", ").concat(String.valueOf(this.getAge())));
	}
	
	public Sex getGender() {
		return gender;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public LocalDate getBirthday() {
		return birthday;
	}
	
	public static int compareByAge(Person a, Person b) {
		return a.birthday.compareTo(b.birthday);
	}
	
	public static int compareByName(Person a, Person b) {
		return a.name.compareTo(b.name);
	}

	public static List<Person> createRoster() {
        
        List<Person> roster = new ArrayList<>(); 
        roster.add(
                new Person(
                "Bob",
                IsoChronology.INSTANCE.date(2000, 9, 12),
                Person.Sex.MALE, "bob@example.com"));
        roster.add(
            new Person(
            "Jane",
            IsoChronology.INSTANCE.date(1990, 7, 15),
            Person.Sex.FEMALE, "jane@example.com"));
        roster.add(
                new Person(
                "Fred",
                IsoChronology.INSTANCE.date(1980, 6, 20),
                Person.Sex.MALE,
                "fred@example.com"));
        roster.add(
            new Person(
            "George",
            IsoChronology.INSTANCE.date(1991, 8, 13),
            Person.Sex.MALE, "george@example.com"));
        
        
        return roster;
    }
}
