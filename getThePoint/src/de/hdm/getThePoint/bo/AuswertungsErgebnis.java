package de.hdm.getThePoint.bo;

public class AuswertungsErgebnis {

	private String studentname;

	private String matrikelnr;

	private int richtigInProzent;

	public AuswertungsErgebnis(String studentname, String matrikelnr,
			int richtigInProzent) {
		super();
		this.studentname = studentname;
		this.matrikelnr = matrikelnr;
		this.richtigInProzent = richtigInProzent;
	}

	public boolean isoverPercentage(int percent) {
		if (richtigInProzent >= percent) {
			return true;
		}
		return false;
	}

	public String getStudentname() {
		return studentname;
	}

	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}

	public String getMatrikelnr() {
		return matrikelnr;
	}

	public void setMatrikelnr(String matrikelnr) {
		this.matrikelnr = matrikelnr;
	}

	public int getRichtigInProzent() {
		return richtigInProzent;
	}

	public void setRichtigInProzent(int richtigInProzent) {
		this.richtigInProzent = richtigInProzent;
	}

}
