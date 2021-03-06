package de.hdm.getThePoint.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import org.primefaces.event.FileUploadEvent;

import de.hdm.getThePoint.bo.AntwortBo;
import de.hdm.getThePoint.bo.FrageBo;
import de.hdm.getThePoint.bo.KategorieBo;
import de.hdm.getThePoint.db.dbmodel.Frage;
import de.hdm.getThePoint.db.mapper.FrageMapper;
import de.hdm.getThePoint.db.mapper.KategorieMapper;
import de.hdm.getThePoint.enums.Schwierigkeit;

/**
 * Diese Bean stellt alle n&ouml;tigen Daten und Methoden zur Verwaltung der
 * Fragen bereit.
 * 
 * @author Fabian
 *
 */
@ManagedBean(name = "frageBean")
@SessionScoped
public class FrageBean implements Serializable {

	private static final long serialVersionUID = -7885565449150992040L;

	private List<FrageBo> fragen;
	private List<KategorieBo> kategorien;
	private KategorieBo selectedKategorie;
	private AntwortBo selectedLoesung;
	private List<FrageBo> fragenToDelete;

	private FrageMapper frageMapper;
	private KategorieMapper kategorieMapper;

	private Schwierigkeit[] schwierigkeiten = Schwierigkeit.values();
	private Schwierigkeit selectedSchwierigkeit;

	private static final String FILEDESTINATION = "C:/temp/images";

	public FrageBean() {
		frageMapper = new FrageMapper();
		kategorieMapper = new KategorieMapper();
		fragenToDelete = new ArrayList<FrageBo>();
	}

	@PostConstruct
	public void init() {
		getAllFragen();
		getAllKategorien();
	}

	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	@ManagedProperty(value = "#{dataAccesBean}")
	public DataAccessBean dataAccessBean;

	/**
	 * Diese Methode ruft alle Kategorien aus der Datenbank ab.
	 */
	public void getAllKategorien() {
		kategorien = kategorieMapper.getModelsAsList(dataAccessBean
				.getDataAccess().getAllKategorie());
	}

	/**
	 * Diese Methode ruft alle Fragen aus der Datenbank ab
	 */
	public void getAllFragen() {
		fragen = frageMapper.getModelsAsList(dataAccessBean.getDataAccess()
				.getAllFrage());
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
	 * @param index
	 */
	public void addAntwortmoeg(int index) {
		List<AntwortBo> antwortmoeg = fragen.get(index)
				.getAntwortmoeglichkeiten();
		antwortmoeg.add(new AntwortBo("Hier Antworttext eingeben!"));
		fragen.get(index).setAntwortmoeglichkeiten(antwortmoeg);
	}

	/**
	 * Diese Methode l&ouml;scht eine Antwortm&ouml;glichkeit einer Frage.
	 */
	public void deleteAntwortmoeg(int indexFrage, int indexAntwortmoeg) {

		List<AntwortBo> newantworten = new ArrayList<AntwortBo>();
		List<AntwortBo> antworten = fragen.get(indexFrage)
				.getAntwortmoeglichkeiten();
		for (AntwortBo antwortBo : antworten) {
			if (!antworten.get(indexAntwortmoeg).equals(antwortBo)) {
				newantworten.add(antwortBo);
			}
		}
		fragen.get(indexFrage).setAntwortmoeglichkeiten(newantworten);

	}

	/**
	 * Speichert Fragen in der Datenbank.
	 */
	public void saveFragen() {

		FacesContext context = FacesContext.getCurrentInstance();

		try {
			List<Frage> dbfragen = new ArrayList<Frage>();
			for (FrageBo frageBo : fragen) {
				dbfragen.add(frageMapper.getDbModel(frageBo));
			}
			dataAccessBean.getDataAccess().saveFragen(dbfragen);
			context.addMessage(null, new FacesMessage(
					"Fragen erfolgreich gespeichert"));
		} catch (PersistenceException e) {
			context.addMessage(null, new FacesMessage("Fehler beim Speichern"));
		}
		if (fragenToDelete.size() > 0) {
			deleteFragen();
		}
		getAllFragen();
	}

	/**
	 * L&ouml;scht Fragen aus der Datenbank.
	 */
	public void deleteFragen() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			for (FrageBo frageBo : fragenToDelete) {
				dataAccessBean.getDataAccess().deleteFrage(
						frageMapper.getDbModel(frageBo));
			}
		} catch (PersistenceException e) {
			context.addMessage(null, new FacesMessage("Fehler beim Löschen"));
		}

	}

	/**
	 * L&auml;dt ein Bild des Benutzers auf den Server hoch.
	 * 
	 * @param event
	 */
	public void upload(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Erfolgreich! ", event.getFile()
				.getFileName() + " wurde hochgeladen.");
		FacesContext.getCurrentInstance().addMessage(null, msg);

		try {
			copyFile(event.getFile().getFileName(), event.getFile()
					.getInputstream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void copyFile(String fileName, InputStream in) {
		try {

			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(FILEDESTINATION
					+ fileName));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();

			System.out.println("Neue Datei erstellt!");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	

	public Schwierigkeit[] getSchwierigkeiten() {
		return schwierigkeiten;
	}

	public void setSchwierigkeiten(Schwierigkeit[] schwierigkeiten) {
		this.schwierigkeiten = schwierigkeiten;
	}

	public void deleteFrage(int index) {
		fragenToDelete.add(fragen.get(index));
		fragen.remove(index);
	}

	public void saveFragen(int index) {

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

	public DataAccessBean getDataAccessBean() {
		return dataAccessBean;
	}

	public void setDataAccessBean(DataAccessBean dataAccessBean) {
		this.dataAccessBean = dataAccessBean;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public KategorieBo getSelectedKategorie() {
		return selectedKategorie;
	}

	public void setSelectedKategorie(KategorieBo selectedKategorie) {
		this.selectedKategorie = selectedKategorie;
	}

	public Schwierigkeit getSelectedSchwierigkeit() {
		return selectedSchwierigkeit;
	}

	public void setSelectedSchwierigkeit(Schwierigkeit selectedSchwierigkeit) {
		this.selectedSchwierigkeit = selectedSchwierigkeit;
	}

	public AntwortBo getSelectedLoesung() {
		return selectedLoesung;
	}

	public void setSelectedLoesung(AntwortBo selectedLoesung) {
		this.selectedLoesung = selectedLoesung;
	}

}
