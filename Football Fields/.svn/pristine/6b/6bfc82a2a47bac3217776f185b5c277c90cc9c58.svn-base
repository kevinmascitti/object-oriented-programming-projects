package it.polito.oop.futsal;

import java.util.Map;
import java.util.TreeMap;

import it.polito.oop.futsal.Fields.Features;

public class Field implements FieldOption {

	int code;
	Features features;
	Map<String, Associate> prenotazioni = new TreeMap<>();

	public Field(int code, Features features) {
		this.code=code;
		this.features=features;
	}

	@Override
	public int getField() {
		return code;
	}

	@Override
	public int getOccupation() {
		return prenotazioni.size();
	}

	public boolean possiede(Features required) {
		if(required.ac==true) {
			if(features.ac!=true)
				return false;
		}
		if(required.heating==true) {
			if(features.heating!=true)
				return false;
		}
		if(required.indoor==true) {
			if(features.indoor!=true)
				return false;
		}
		return true;
	}

}
