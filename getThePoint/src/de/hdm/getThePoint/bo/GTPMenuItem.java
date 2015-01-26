package de.hdm.getThePoint.bo;

public class GTPMenuItem {

	private int id;

	private String outcome;

	public GTPMenuItem(int id, String outcome, String label) {
		super();
		this.id = id;
		this.outcome = outcome;
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	private String label;

}
