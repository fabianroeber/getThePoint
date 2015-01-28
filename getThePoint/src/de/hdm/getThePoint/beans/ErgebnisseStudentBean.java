package de.hdm.getThePoint.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import de.hdm.getThePoint.bo.ErgebnisBo;
import de.hdm.getThePoint.bo.WissenstestBo;
import de.hdm.getThePoint.db.mapper.ErgebnisMapper;
import de.hdm.getThePoint.db.mapper.StudentMapper;
import de.hdm.getThePoint.db.mapper.WissenstestMapper;

/**
 * Diese Bean stellt alle ben&ouml;tigten Daten und Methoden f&uuml;r die
 * Anzeige der Ergebnisse eines Studenten bereit.
 * 
 * @author Fabian
 *
 */
@ManagedBean(name = "ergebnisseStudentBean")
@ViewScoped
public class ErgebnisseStudentBean implements Serializable {

	private static final long serialVersionUID = 3371866323292299244L;

	private List<WissenstestBo> wissenstests;
	private WissenstestBo selectedWissenstest;

	private WissenstestMapper wissenstestMapper;
	private ErgebnisMapper ergebnisMapper;
	private StudentMapper studentMapper;

	private List<ErgebnisBo> ergebnisse;

	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	@ManagedProperty(value = "#{dataAccesBean}")
	public DataAccessBean dataAccessBean;

	public ErgebnisseStudentBean() {
		wissenstestMapper = new WissenstestMapper();
		ergebnisMapper = new ErgebnisMapper();
		studentMapper = new StudentMapper();
	}

	@PostConstruct
	public void init() {
		if (userBean.getStudent() != null) {
			getAllWissenstests();
		}
	}

	/**
	 * L&aauml;dt alle Wissenstests eines Studenten, zu dem Ergebnisse
	 * vorliegen, aus der Datenbank.
	 */
	public void getAllWissenstests() {
		wissenstests = wissenstestMapper.getModelsAsList(dataAccessBean
				.getDataAccess().getWissentestsByStudentWithErgebnis(
						studentMapper.getDbModel(userBean.getStudent())));

	}

	/**
	 * L&auuml;dt die Ergebnisse zu einem Wissenstest aus der Datenbank.
	 */
	public void loadErgebnisse() {
		if (selectedWissenstest != null) {
			ergebnisse = ergebnisMapper.getModelsAsList(dataAccessBean
					.getDataAccess().getErgebnisseByWissenstestAndStudent(
							studentMapper.getDbModel(userBean.getStudent()),
							wissenstestMapper.getDbModel(selectedWissenstest)));
		} else {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage("Kein Wissenstest ausgewählt",
									"Bitte wählen Sie einen Wissenstest aus der Liste aus!"));
		}
	}

	/**
	 * Auswahl eines Test &uuml;ber die mobile Seite per Rowindex.
	 * 
	 * @param index
	 */
	public void getErgebnisseMobile(int index) {
		selectedWissenstest = wissenstests.get(index);
		loadErgebnisse();
	}

	/**
	 * Gibt die Anzahl der richtigen Antworten zur&uuml;ck
	 * 
	 * @return
	 */
	public int getAnzahlRichtige() {
		int anzahl = 0;
		if (ergebnisse != null) {
			for (ErgebnisBo ergebnisBo : ergebnisse) {
				if (ergebnisBo.isRichtig()) {
					anzahl++;
				}
			}
		}
		return anzahl;
	}

	/**
	 * Gibt die Anzahl der Ergebnisse zur&uuml;ck.
	 * 
	 * @return
	 */
	public int getAnzahlErgebnisse() {
		return ergebnisse.size();
	}

	public List<WissenstestBo> getWissenstests() {
		return wissenstests;
	}

	public void setWissenstests(List<WissenstestBo> wissenstests) {
		this.wissenstests = wissenstests;
	}

	public WissenstestBo getSelectedWissenstest() {
		return selectedWissenstest;
	}

	public void setSelectedWissenstest(WissenstestBo selectedWissenstest) {
		this.selectedWissenstest = selectedWissenstest;
	}

	public List<ErgebnisBo> getErgebnisse() {
		return ergebnisse;
	}

	public void setErgebnisse(List<ErgebnisBo> ergebnisse) {
		this.ergebnisse = ergebnisse;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public DataAccessBean getDataAccessBean() {
		return dataAccessBean;
	}

	public void setDataAccessBean(DataAccessBean dataAccessBean) {
		this.dataAccessBean = dataAccessBean;
	}

}
