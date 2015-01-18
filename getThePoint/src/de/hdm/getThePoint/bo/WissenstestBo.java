package de.hdm.getThePoint.bo;

import java.util.Date;
import java.util.List;

/**
 * @author Fabian
 *
 */
public class WissenstestBo extends BusinessObject {

	private static final long serialVersionUID = -4218497524010213019L;

	private String bezeichnung;

	private LehrenderBo lehrender;

	private Date startzeit;

	private Date endzeit;

	private int bearbeitungszeit;

	private boolean random;

	private boolean aktiv;

	private boolean gestartet;

	private boolean editable;

	private List<FrageZuordung> frageZuordungen;

	private List<ErgebnisBo> ergebnisse;

	public LehrenderBo getLehrender() {
		return lehrender;
	}

	public void setLehrender(LehrenderBo lehrender) {
		this.lehrender = lehrender;
	}

	public Date getStartzeit() {
		return startzeit;
	}

	public void setStartzeit(Date startzeit) {
		this.startzeit = startzeit;
	}

	public Date getEndzeit() {
		return endzeit;
	}

	public void setEndzeit(Date endzeit) {
		this.endzeit = endzeit;
	}

	public int getBearbeitungszeit() {
		return bearbeitungszeit;
	}

	public void setBearbeitungszeit(int bearbeitungszeit) {
		this.bearbeitungszeit = bearbeitungszeit;
	}

	public boolean isRandom() {
		return random;
	}

	public void setRandom(boolean random) {
		this.random = random;
	}

	public boolean isAktiv() {
		return aktiv;
	}

	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}

	public boolean isGestartet() {
		return gestartet;
	}

	public void setGestartet(boolean gestartet) {
		this.gestartet = gestartet;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public List<ErgebnisBo> getErgebnisse() {
		return ergebnisse;
	}

	public void setErgebnisse(List<ErgebnisBo> ergebnisse) {
		this.ergebnisse = ergebnisse;
	}

	public List<FrageZuordung> getFrageZuordungen() {
		return frageZuordungen;
	}

	public void setFrageZuordungen(List<FrageZuordung> frageZuordungen) {
		this.frageZuordungen = frageZuordungen;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
