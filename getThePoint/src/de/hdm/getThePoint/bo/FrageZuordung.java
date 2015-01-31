package de.hdm.getThePoint.bo;

/**
 * Dieses Model enthält alle nötigen Informationen für eine Zuordnung einer
 * {@link FrageBo} zu einem {@link WissenstestBo} (m : n Beziehung).
 * 
 * @author Fabian
 *
 */
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
