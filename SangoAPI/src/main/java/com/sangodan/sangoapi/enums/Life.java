package com.sangodan.sangoapi.enums;

import java.util.Locale;

public enum Life {

	LOBBY ("LOBBY"),
	STARTING ("STARTING"),
	STARTED ("STARTED"),
	ENDED ("ENDED"),
	
	NOLIFE("NOLIFE");
	
	private final String name;
	
	private Life(String s) {
		name = s;
	}
	
	public String toString() {
		return name;
	}
	
	public static Life fromString(String s) {
		return Life.valueOf(s.toUpperCase(Locale.ENGLISH));
	}
	
	public static void nextLife(Life life) {
		switch(life) {
		case LOBBY:
			life = STARTING;
			break;
		case STARTING:
			life = STARTED;
			break;
		case STARTED:
			life = ENDED;
			break;
		case ENDED:
			life = ENDED;
			break;
		case NOLIFE:
			life = NOLIFE;
			break;
		}
		
	}
}
