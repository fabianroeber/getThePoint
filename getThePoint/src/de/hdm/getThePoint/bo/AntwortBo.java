package de.hdm.getThePoint.bo;


public class AntwortBo extends BusinessObject {

	private static final long serialVersionUID = 2078688206447859120L;

	private FrageBo frage;

	private String text;

	public AntwortBo() {
		super();
	}

	public AntwortBo(String text) {
		super();
		this.text = text;
	}

	public AntwortBo(Integer id, String text) {
		super();
		this.setId(id);
		this.text = text;
	}

	public FrageBo getFrage() {
		return frage;
	}

	public void setFrage(FrageBo frage) {
		this.frage = frage;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
