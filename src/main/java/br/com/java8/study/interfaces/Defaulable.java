package br.com.java8.study.interfaces;

public interface Defaulable {
	// Interfaces now allow defaut methods, the implementer may
	// or may not implement (override) them.
	default String notRequired() {
		return "Default implementation";
	}

}
