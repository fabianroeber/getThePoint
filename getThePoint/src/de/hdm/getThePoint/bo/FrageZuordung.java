package de.hdm.getThePoint.bo;

public class FrageZuordung extends BusinessObject {

	private static final long serialVersionUID = -6239226464328697530L;

	private FrageBo frage;

	public FrageZuordung(Integer id, FrageBo frage) {
		super();
		this.setId(id);
		this.frage = frage;
	}

	public FrageBo getFrage() {
		return frage;
	}

	public void setFrage(FrageBo frage) {
		this.frage = frage;
	}
}
