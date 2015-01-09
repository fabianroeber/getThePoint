package de.hdm.getThePoint.enums;

public enum Schwierigkeit {

	SCHWER("Schwer"), MITTEL("Mittel"), LEICHT("Leicht");

	private String text;

	private Schwierigkeit(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
