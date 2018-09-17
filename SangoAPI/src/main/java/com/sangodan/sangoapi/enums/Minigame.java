package com.sangodan.sangoapi.enums;

import java.util.Locale;

public enum Minigame {

	DEFAULT("DEFAULT"),
	SPLEEF("SPLEEF");
	
	private final String name;
	private Minigame(String name) {
		this.name = name;
	}
	
	public String toString() {
		String n = this.name().substring(0, 1) + this.name.substring(1).toLowerCase(Locale.ENGLISH);
		return n;
	}
	
	public static Life fromString(String s) {
		return Life.valueOf(s.toUpperCase(Locale.ENGLISH));
	}
}
