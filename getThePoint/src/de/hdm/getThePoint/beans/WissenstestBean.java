package de.hdm.getThePoint.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import de.hdm.getThePoint.bo.ErgebnisBo;
import de.hdm.getThePoint.bo.FrageBo;
import de.hdm.getThePoint.bo.FrageZuordung;
import de.hdm.getThePoint.bo.WissenstestBo;
import de.hdm.getThePoint.db.mapper.ErgebnisMapper;
import de.hdm.getThePoint.db.mapper.WissenstestMapper;

/**
 * Diese Bean stellt Daten f&uuml;r ein Quiz bereit und verarbeitet die
 * Benutzerangaben.
 * 
 * @author Fabian
 *
 */
@ManagedBean(name = "wissenstestBean")
@ViewScoped
public class WissenstestBean implements Serializable {

	private static final long serialVersionUID = 4292557396761753803L;

	private WissenstestBo wissenstest;
	private List<FrageBo> fragen = new ArrayList<FrageBo>();
	private FrageBo frage;
	private ErgebnisBo ergebnis = new ErgebnisBo();

	/**
	 * Zeit pro Frage
	 */
	private int zeitFrage = 0;
	/**
	 * Aktuelle Frage
	 */
	private int frageindex = 0;
	/**
	 * Timer f&uuml;r die aktuelle Frage, der herunterl&auml;uft.
	 */
	private int timer = 0;

	private WissenstestMapper wissenstestMapper;
	private ErgebnisMapper ergebnismapper;

	private List<WissenstestBo> wissenstests;
	private boolean testInProgress = false;

	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	@ManagedProperty(value = "#{dataAccesBean}")
	public DataAccessBean dataAccessBean;

	public List<WissenstestBo> getWissenstests() {
		return wissenstests;
	}

	public WissenstestBean() {
		wissenstestMapper = new WissenstestMapper();
		ergebnismapper = new ErgebnisMapper();
	}

	@PostConstruct
	public void init() {
		getAllWissenstests();
	}

	public void getAllWissenstests() {

		wissenstests = wissenstestMapper.getModelsAsList(dataAccessBean
				.getDataAccess().getAllWissentests());
	}

	/**
	 * Diese Methode startet den Test.
	 * 
	 * @param index
	 */
	public void startTest(int index) {
		wissenstest = wissenstests.get(index);
		zeitFrage = wissenstest.getBearbeitungszeit();
		testInProgress = true;
		if (wissenstest.getFrageZuordungen() != null
				&& wissenstest.getFrageZuordungen().size() > 1) {
			for (FrageZuordung frageObj : wissenstest.getFrageZuordungen()) {
				fragen.add(frageObj.getFrage());
			}
			if (wissenstest.isRandom()) {
				Collections.shuffle(fragen);
			}
			frage = fragen.get(frageindex++);

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Keine Fragen vorhanden"));
		}
		timer = zeitFrage;
		ergebnis = new ErgebnisBo();
	}

	/**
	 * Diese Methode springt zur n&auml;chsten Frage und speichert das Ergebnis
	 * der letzten.
	 */
	public void nextFrage() {
		ergebnis.setFrage(frage);
		ergebnis.setWissenstest(wissenstest);
		ergebnis.setStudent(userBean.getStudent());

		if (ergebnis.getAntwort() != null) {
			if (ergebnis.getAntwort().equals(frage.getLoesung())) {
				ergebnis.setRichtig(true);
			} else {
				ergebnis.setRichtig(false);
			}
		} else {
			ergebnis.setRichtig(false);
		}

		dataAccessBean.getDataAccess().saveErgebnis(
				ergebnismapper.getDbModel(ergebnis));
		ergebnis = new ErgebnisBo();
		if (fragen.size() > frageindex) {
			frage = fragen.get(frageindex++);
		} else {
			testAbbrechen();
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage("Test Abgeschlossen!",
									"Die Ergebnisse können Sie nun unter dem Menupunkte 'Ergebnisse' einsehen."));
		}
		timer = zeitFrage;
	}

	/**
	 * Diese Methode bricht den Test ab.
	 */
	public void testAbbrechen() {
		testInProgress = false;
		frage = null;
		wissenstest = null;
		zeitFrage = 0;
		frageindex = 0;
	}

	/**
	 * Diese Methode wird &uuml;ber ein Poll-Element in der GUI angesteuert und
	 * singnalisiert dem Benutzer, wie viel Zeit er noch hat.
	 */
	public void timerDown() {
		if (timer > 0) {
			timer--;
		}
	}

	public void setWissenstests(List<WissenstestBo> wissenstests) {
		this.wissenstests = wissenstests;
	}

	public boolean isTestInProgress() {
		return testInProgress;
	}

	public void setTestInProgress(boolean testInProgress) {
		this.testInProgress = testInProgress;
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

	public int getZeitFrage() {
		return zeitFrage;
	}

	public void setZeitFrage(int zeitFrage) {
		this.zeitFrage = zeitFrage;
	}

	public WissenstestBo getWissenstest() {
		return wissenstest;
	}

	public void setWissenstest(WissenstestBo wissenstest) {
		this.wissenstest = wissenstest;
	}

	public FrageBo getFrage() {
		return frage;
	}

	public void setFrage(FrageBo frage) {
		this.frage = frage;
	}

	public ErgebnisBo getErgebnis() {
		return ergebnis;
	}

	public void setErgebnis(ErgebnisBo ergebnis) {
		this.ergebnis = ergebnis;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public int getFrageindex() {
		return frageindex;
	}

	public void setFrageindex(int frageindex) {
		this.frageindex = frageindex;
	}

	public List<FrageBo> getFragen() {
		return fragen;
	}

	public void setFragen(List<FrageBo> fragen) {
		this.fragen = fragen;
	}

}
