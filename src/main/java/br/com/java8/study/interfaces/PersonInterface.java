package br.com.java8.study.interfaces;

public interface PersonInterface {
	// Interfaces now allow default methods, the implementer may or
    // may not implement (override) them.
	default String myName() {
		return "Default name from the interface PersonInterface";
	}
}
