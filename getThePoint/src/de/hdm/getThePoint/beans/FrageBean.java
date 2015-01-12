package de.hdm.getThePoint.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import de.hdm.getThePoint.bo.AntwortBo;
import de.hdm.getThePoint.bo.FrageBo;
import de.hdm.getThePoint.bo.KategorieBo;
import de.hdm.getThePoint.db.DataAcces;
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

	private DataAcces dataAccess;

	public FrageBean() {
		dataAccess = new DataAcces();
		frageMapper = new FrageMapper();
		kategorieMapper = new KategorieMapper();
		getAllFragen();
		getAllKategorien();
	}

	public void getAllFragen() {
		fragen = frageMapper.getModelsAsList(dataAccess.getAllFrage());
	}

	public void addFrage() {
		FrageBo frage = new FrageBo();
		frage.setText("Hier Frage eingeben");
		frage.setAntwortmoeglichkeiten(new ArrayList<AntwortBo>());
		fragen.add(frage);
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

	public void getAllKategorien() {
		kategorien = kategorieMapper.getModelsAsList(dataAccess
				.getAllKategorie());
	}

}
