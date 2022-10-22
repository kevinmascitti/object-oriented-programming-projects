package it.polito.oop.futsal;

import java.util.Map;
import java.util.TreeMap;

public class Associate {

	int code;
	String first;
	String last;
	String mobile;
	Map<String, Field> prenotazioni = new TreeMap<>();

	public Associate(Integer code, String first, String last, String mobile) {
		this.code=code;
		this.first=first;
		this.last=last;
		this.mobile=mobile;
	}

}
