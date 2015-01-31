package de.hdm.getThePoint.bo;

import java.util.List;

import de.hdm.getThePoint.enums.Schwierigkeit;

/**
 * Dieses Model enthält alle nötigen Informationen für eine Frage.
 * @author Fabian
 *
 */
public class FrageBo extends BusinessObject {

	private static final long serialVersionUID = 1632688091736338890L;

	private String text;

	private KategorieBo kategorie;

	private List<AntwortBo> antwortmoeglichkeiten;

	private AntwortBo loesung;

	private String bild;

	private Schwierigkeit schwierigkeit;

	/**
	 * Diese Methode legt fest, dass eine Frage maximal vier
	 * Antwortm&ouml;glichkeiten haben darf.
	 * 
	 * @return
	 */
	public boolean hasMaxAntworten() {
		if (antwortmoeglichkeiten.size() < 4) {
			return false;
		}
		return true;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public KategorieBo getKategorie() {
		return kategorie;
	}

	public void setKategorie(KategorieBo kategorie) {
		this.kategorie = kategorie;
	}

	public List<AntwortBo> getAntwortmoeglichkeiten() {
		return antwortmoeglichkeiten;
	}

	public void setAntwortmoeglichkeiten(List<AntwortBo> antwortmoeglichkeiten) {
		this.antwortmoeglichkeiten = antwortmoeglichkeiten;
	}

	public AntwortBo getLoesung() {
		return loesung;
	}

	public void setLoesung(AntwortBo loesung) {
		this.loesung = loesung;
	}

	public String getBild() {
		return bild;
	}

	public void setBild(String bild) {
		this.bild = bild;
	}

	public Schwierigkeit getSchwierigkeit() {
		return schwierigkeit;
	}

	public void setSchwierigkeit(Schwierigkeit schwierigkeit) {
		this.schwierigkeit = schwierigkeit;
	}

}
