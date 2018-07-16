package br.com.java8.study.interfaces;

import java.util.function.Supplier;

public interface PersonFactory {
	// Interfaces now allow static methods
	static PersonInterface create( Supplier<PersonInterface> supplier) {
		return supplier.get();
	}
}
