package br.com.java8.study.entities;

import br.com.java8.study.interfaces.PersonInterface;

public class Jhon implements PersonInterface {
	@Override
	public String myName() {
		return "My name is Jhon";
	}
}
