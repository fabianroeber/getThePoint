package de.hdm.getThePoint.bo;

/**
 * Dieses Klasse enthält alle nötigen Informationen für eine Menuitem des
 * dynamisch, je nach Rolle, generierten Menus. Das Menu wird beim Login
 * generiert.
 * 
 * @author Fabian
 *
 */
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
