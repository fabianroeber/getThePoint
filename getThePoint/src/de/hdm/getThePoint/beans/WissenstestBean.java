package de.hdm.getThePoint.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import de.hdm.getThePoint.db.mapper.StudentMapper;
import de.hdm.getThePoint.db.mapper.WissenstestMapper;

/**
 * Diese Bean stellt Daten f&uuml;r ein Quiz bereit und verarbeitet die
 * Benutzerangaben.
 * 
 * @author Fabian
 *
 */
/**
 * @author Fabian
 *
 */
@ManagedBean(name = "wissenstestBean")
@ViewScoped
public class WissenstestBean implements Serializable {

	private static final long serialVersionUID = 4292557396761753803L;

	private WissenstestBo wissenstest;
	private List<FrageBo> fragen;
	private FrageBo frage;
	private ErgebnisBo ergebnis;

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
	
	private boolean lastfrage = false;

	private WissenstestMapper wissenstestMapper;
	private ErgebnisMapper ergebnismapper;
	private StudentMapper studentMapper;

	private List<WissenstestBo> wissenstests;

	/**
	 * Gibt an, ob sich der Test in Bearbeitungen befindet
	 */
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
		studentMapper = new StudentMapper();
		ergebnis = new ErgebnisBo();
		fragen = new ArrayList<FrageBo>();
		wissenstests = new ArrayList<WissenstestBo>();
	}

	@PostConstruct
	public void init() {
		getAllWissenstests();
	}

	public void getAllWissenstests() {

		if (userBean.getStudent() != null) {

			wissenstests = new ArrayList<WissenstestBo>();

			// Wissenstests laden, an denen der Student schon teilgenommen hat
			List<WissenstestBo> wissenstestsAlreadyDone = wissenstestMapper
					.getModelsAsList(dataAccessBean.getDataAccess()
							.getWissentestsByStudentWithErgebnis(
									studentMapper.getDbModel(userBean
											.getStudent())));
			// Alle Wissenstests laden
			List<WissenstestBo> newWissenstests = wissenstestMapper
					.getModelsAsList(dataAccessBean.getDataAccess()
							.getAllWissentests());
			// Filter der Tests. Nur noch nicht Bearbeitete werden angezeigt
			for (WissenstestBo wissenstestNew : newWissenstests) {
				if (!wissenstestsAlreadyDone.contains(wissenstestNew)
						&& isWissenstestActivated(wissenstestNew)) {
					wissenstests.add(wissenstestNew);
				}
			}
		}
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
				// Bei Modus 'Random' Liste mischen.
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
									"Die Ergebnisse können Sie nun unter dem Menupunkt 'Ergebnisse' einsehen."));
		}
		timer = zeitFrage;
	}

	/**
	 * Diese Mehtode &uuml;berpr&uuml;ft, ob ein Wissenstest Aktiv ist.
	 * 
	 * @param wissenstest
	 * @return
	 */
	public boolean isWissenstestActivated(WissenstestBo wissenstest) {

		Date now = new Date(System.currentTimeMillis());

		if (wissenstest.isAktiv()) {
			return true;
		} else if (now.after(wissenstest.getStartzeit())
				&& now.before(wissenstest.getEndzeit())) {
			return true;
		} else
			return false;
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

	public boolean isLastfrage() {
		return lastfrage;
	}

	public void setLastfrage(boolean lastfrage) {
		this.lastfrage = lastfrage;
	}

}
