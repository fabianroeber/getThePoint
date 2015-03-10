package de.hdm.getThePoint.enums;

public enum TestStatus {

	RUNNING("Läuft"), COMPLETED("Abgeschlossen"), WAITING("In Konfiguration");

	private String text;

	private TestStatus(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
