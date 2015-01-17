package de.hdm.getThePoint.bo;

public class ErgebnisBo extends BusinessObject {

	private static final long serialVersionUID = 2250934284088999510L;

	private WissenstestBo wissenstest;

	private StudentBo student;

	private FrageBo frage;

	private AntwortBo antwort;

	private boolean richtig;

	public WissenstestBo getWissenstest() {
		return wissenstest;
	}

	public void setWissenstest(WissenstestBo wissenstest) {
		this.wissenstest = wissenstest;
	}

	public StudentBo getStudent() {
		return student;
	}

	public void setStudent(StudentBo student) {
		this.student = student;
	}

	public FrageBo getFrage() {
		return frage;
	}

	public void setFrage(FrageBo frage) {
		this.frage = frage;
	}

	public AntwortBo getAntwort() {
		return antwort;
	}

	public void setAntwort(AntwortBo antwort) {
		this.antwort = antwort;
	}

	public boolean isRichtig() {
		return richtig;
	}

	public void setRichtig(boolean richtig) {
		this.richtig = richtig;
	}

}
