package de.hdm.getThePoint.bo;

public class StudentBo extends BusinessObject {

	private static final long serialVersionUID = 5659858238343964483L;

	private String name;

	private String kuerzel;

	private Long matrikelnummer;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKuerzel() {
		return kuerzel;
	}

	public void setKuerzel(String kuerzel) {
		this.kuerzel = kuerzel;
	}

	public Long getMatrikelnummer() {
		return matrikelnummer;
	}

	public void setMatrikelnummer(Long matrikelnummer) {
		this.matrikelnummer = matrikelnummer;
	}

}
