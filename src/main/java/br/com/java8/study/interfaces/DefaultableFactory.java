package br.com.java8.study.interfaces;

import java.util.function.Supplier;

public interface DefaultableFactory {
	// Interfaces now allow static methods
	static Defaulable create( Supplier<Defaulable> supplier) {
		return supplier.get();
	}

}
