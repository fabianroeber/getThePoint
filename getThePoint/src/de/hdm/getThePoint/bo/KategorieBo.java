package de.hdm.getThePoint.bo;

public class KategorieBo extends BusinessObject {

	private static final long serialVersionUID = -3274946166002195898L;

	private String bezeichnung;

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public LehrenderBo getLehrender() {
		return lehrender;
	}

	public void setLehrender(LehrenderBo lehrender) {
		this.lehrender = lehrender;
	}

	private LehrenderBo lehrender;

}
