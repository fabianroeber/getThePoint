package de.hdm.getThePoint.bo;

/**
 * Dieses Model enthält alle nötigen Informationen für einen Lehrenden.
 * 
 * @author Fabian
 *
 */
public class LehrenderBo extends BusinessObject {

	private static final long serialVersionUID = 5382845302404475300L;

	private String vorname;

	private String nachname;

	private String kuerzel;

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getKuerzel() {
		return kuerzel;
	}

	public void setKuerzel(String kuerzel) {
		this.kuerzel = kuerzel;
	}

}
