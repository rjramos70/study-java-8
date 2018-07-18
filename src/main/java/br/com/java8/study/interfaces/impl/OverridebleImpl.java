package br.com.java8.study.interfaces.impl;

import br.com.java8.study.interfaces.Defaulable;

public class OverridebleImpl implements Defaulable {
	@Override
	public String notRequired() {
		return "Overridden implementation";
	}
}
