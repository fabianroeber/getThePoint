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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import de.hdm.getThePoint.bo.AntwortBo;
import de.hdm.getThePoint.bo.FrageBo;
import de.hdm.getThePoint.bo.KategorieBo;
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

	private static final String FILEDESTINATION = "C:/temp/images";

	public FrageBean() {
		frageMapper = new FrageMapper();
		kategorieMapper = new KategorieMapper();
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

}
