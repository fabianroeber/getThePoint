package de.hdm.getThePoint.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import de.hdm.getThePoint.bo.AntwortBo;
import de.hdm.getThePoint.bo.FrageBo;
import de.hdm.getThePoint.bo.KategorieBo;
import de.hdm.getThePoint.db.DataAccess;
import de.hdm.getThePoint.db.mapper.FrageMapper;
import de.hdm.getThePoint.db.mapper.KategorieMapper;

@ManagedBean(name = "frageBean")
@ViewScoped
public class FrageBean implements Serializable {

	private static final long serialVersionUID = -7885565449150992040L;

	private List<FrageBo> fragen;
	private List<KategorieBo> kategorien;

	private FrageMapper frageMapper;
	private KategorieMapper kategorieMapper;

	private DataAccess dataAccess;

	public FrageBean() {
		dataAccess = new DataAccess();
		frageMapper = new FrageMapper();
		kategorieMapper = new KategorieMapper();
		getAllFragen();
		getAllKategorien();
	}

	/**
	 * Diese Methode ruft alle Kategorien aus der Datenbank ab.
	 */
	public void getAllKategorien() {
		kategorien = kategorieMapper.getModelsAsList(dataAccess
				.getAllKategorie());
	}

	/**
	 * Diese Methode ruft alle Fragen aus der Datenbank ab
	 */
	public void getAllFragen() {
		fragen = frageMapper.getModelsAsList(dataAccess.getAllFrage());
	}

	/**
	 * Diese Methode f&uuml;gt der Liste eine neue Frage hinzu.
	 */
	public void addFrage() {
		FrageBo frage = new FrageBo();
		frage.setText("Hier Frage eingeben");
		frage.setAntwortmoeglichkeiten(new ArrayList<AntwortBo>());
		fragen.add(frage);
	}

	/**
	 * Diese Methode f&uuml;gt einer Frage eine Antworm&ouml;glichkeit hinzu.
	 * 
	 * @param i
	 */
	public void addAntwortmoeg(int i) {
		List<AntwortBo> antwortmoeg = fragen.get(i).getAntwortmoeglichkeiten();
		antwortmoeg.add(new AntwortBo());
	}

	/**
	 * Diese Methode l&ouml;scht eine Antwortm&ouml;glichkeit einer Frage.
	 */
	public void deleteAntwortmoeg(int indexFrage, int indexAntwortmoeg) {
		fragen.get(indexFrage).getAntwortmoeglichkeiten()
				.remove(indexAntwortmoeg);

	}

	public List<FrageBo> getFragen() {
		return fragen;
	}

	public void setFragen(List<FrageBo> fragen) {
		this.fragen = fragen;
	}

	public List<KategorieBo> getKategorien() {
		return kategorien;
	}

	public void setKategorien(List<KategorieBo> kategorien) {
		this.kategorien = kategorien;
	}

}
