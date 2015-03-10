package de.hdm.getThePoint.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import de.hdm.getThePoint.bo.FrageBo;
import de.hdm.getThePoint.bo.KategorieBo;
import de.hdm.getThePoint.bo.WissenstestBo;
import de.hdm.getThePoint.db.mapper.FrageMapper;
import de.hdm.getThePoint.db.mapper.KategorieMapper;
import de.hdm.getThePoint.db.mapper.LehrenderMapper;
import de.hdm.getThePoint.db.mapper.WissenstestMapper;
import de.hdm.getThePoint.enums.TestStatus;

/**
 * 
 * Dieses Bean stellt alle Daten und Methoden zur Verwaltung der Wissenstests
 * bereit.
 * 
 * @author Fabian
 *
 */
@ManagedBean(name = "wissenstestVerwaltungBean")
@ViewScoped
public class WissenstestVerwaltungBean implements Serializable {

	private static final long serialVersionUID = -5837697454989959201L;

	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	@ManagedProperty(value = "#{dataAccesBean}")
	public DataAccessBean dataAccessBean;

	@ManagedProperty(value = "#{wissenstestBean}")
	public WissenstestBean wissenstestBean;

	private List<FrageBo> fragen;
	private List<WissenstestBo> wissenstests;
	private List<WissenstestBo> alleTests;
	private List<WissenstestBo> laufendeTests;
	private WissenstestBo selectedWissenstest;
	private List<KategorieBo> kategorien;
	private List<FrageBo> wissenstestfragen;
	private DualListModel<FrageBo> dlmfragen;
	private KategorieBo selectedKategorie;

	// Attribute für den aktuellen Wissenstest
	private int laufzeit;

	static final long ONE_MINUTE_IN_MILLIS = 60000;

	private FrageMapper frageMapper;
	private KategorieMapper kategorieMapper;
	private WissenstestMapper wissenstestMapper;
	private LehrenderMapper lehrenderMapper;

	public WissenstestVerwaltungBean() {
		frageMapper = new FrageMapper();
		wissenstestMapper = new WissenstestMapper();
		kategorieMapper = new KategorieMapper();
		lehrenderMapper = new LehrenderMapper();
		dlmfragen = new DualListModel<FrageBo>(fragen, fragen);
	}

	@PostConstruct
	public void init() {
		getAllFragen();
		getAllKategorien();
		getAllWissenstests();
		getAllLaufendeTests();
		getAlleWissensTests();
	}

	private void getAlleWissensTests() {
		alleTests = wissenstestMapper.getModelsAsList(dataAccessBean
				.getDataAccess().getAllWissentests());
	}

	/**
	 * Lädt alle laufenden Tests
	 */
	private void getAllLaufendeTests() {

	}

	public boolean checkLoaded() {
		if (wissenstests == null) {
			init();
			return true;
		}
		return false;
	}

	public void onTransfer(TransferEvent event) {
		StringBuilder builder = new StringBuilder();
		for (Object item : event.getItems()) {
			builder.append(((FrageBo) item).getText()).append("<br />");
		}

		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary("Items Transferred");
		msg.setDetail(builder.toString());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowSelect(WissenstestBo bo) {
		selectedWissenstest = bo;
	}

	/**
	 * Lädt alle Fragen aus der Datenbank
	 */
	public void getAllFragen() {
		fragen = new ArrayList<FrageBo>();
		fragen = frageMapper.getModelsAsList(dataAccessBean.getDataAccess()
				.getAllFrage());
	}

	/**
	 * Lädt alle Wissenstests, die bearbeitet werden dürfen, aus der Datenbank.
	 */
	public void getAllWissenstests() {
		wissenstests = wissenstestMapper.getModelsAsList(dataAccessBean
				.getDataAccess().getWissentestsByLehrenderWithoutErgebnis(
						lehrenderMapper.getDbModel(userBean.getLehrender())));
	}

	/**
	 * Lädt alle Kategortien
	 */
	public void getAllKategorien() {
		kategorien = kategorieMapper.getModelsAsList(dataAccessBean
				.getDataAccess().getAllKategorie());
	}

	/**
	 * Diese Methode startet den ausgewählten Wissenstest manuell.
	 */
	public void startTestNow(WissenstestBo test) {

		Date date = new Date(System.currentTimeMillis());
		test.setStartzeit(date);
		test.setEndzeit(new Date(date.getTime()
				+ (laufzeit * ONE_MINUTE_IN_MILLIS)));
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage("Erfolgreich gestartet!",
						"Wissenstest wurde gestartet. Die Bearbeitungdauer berträgt "
								+ laufzeit + " Minuten ab jetzt."));
		saveWissenstest(test);
	}

	/**
	 * Diese Mehtode stoppt einen laufenden Test
	 */
	public void stopTestNow(WissenstestBo test) {
		test.setEndzeit(new Date(System.currentTimeMillis()));
		FacesContext
				.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage("Erfolgreich beendet!",
								"Wissenstest wurde beendet, er kann nun nicht mehr durchgeführt werden"));
		saveWissenstest(test);
	}

	/**
	 * Diese Methode prüft, ob der Anwender der Ersteller des Wissenstest ist.
	 * 
	 * @param wissenstest
	 * @return
	 */
	public boolean isUserErsteller(WissenstestBo wissenstest) {
		if (wissenstest.getLehrender().equals(userBean.getLehrender())) {
			return true;
		} else
			return false;
	}

	/**
	 * Diese Methode liefert den Status des Wissenstests
	 * 
	 * @return
	 */
	public TestStatus getTestStatus(WissenstestBo wissenstest) {
		if (wissenstestBean.isWissenstestActivated(wissenstest)) {
			return TestStatus.RUNNING;
		} else if (wissenstests.contains(wissenstest)) {
			return TestStatus.WAITING;
		} else
			return TestStatus.COMPLETED;
	}

	/**
	 * Diese Methode speichert einen Wissenstest
	 * 
	 * @param test
	 */
	public void saveWissenstest(WissenstestBo test) {
		dataAccessBean.getDataAccess().saveWissenstest(
				wissenstestMapper.getDbModel(test));
		init();
	}

	public List<FrageBo> getFragen() {
		return fragen;
	}

	public void setFragen(List<FrageBo> fragen) {
		this.fragen = fragen;
	}

	public List<WissenstestBo> getWissenstests() {
		return wissenstests;
	}

	public void setWissenstests(List<WissenstestBo> wissenstests) {
		this.wissenstests = wissenstests;
	}

	public DualListModel<FrageBo> getDlmfragen() {
		return dlmfragen;
	}

	public void setDlmfragen(DualListModel<FrageBo> dlmfragen) {
		this.dlmfragen = dlmfragen;
	}

	public List<KategorieBo> getKategorien() {
		return kategorien;
	}

	public void setKategorien(List<KategorieBo> kategorien) {
		this.kategorien = kategorien;
	}

	public KategorieBo getSelectedKategorie() {
		return selectedKategorie;
	}

	public void setSelectedKategorie(KategorieBo selectedKategorie) {
		this.selectedKategorie = selectedKategorie;
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

	public WissenstestBo getSelectedWissenstest() {
		return selectedWissenstest;
	}

	public void setSelectedWissenstest(WissenstestBo selectedWissenstest) {
		this.selectedWissenstest = selectedWissenstest;
	}

	public int getLaufzeit() {
		return laufzeit;
	}

	public void setLaufzeit(int laufzeit) {
		this.laufzeit = laufzeit;
	}

	public List<WissenstestBo> getAlleTests() {
		return alleTests;
	}

	public void setAlleTests(List<WissenstestBo> alleTests) {
		this.alleTests = alleTests;
	}

	public List<WissenstestBo> getLaufendeTests() {
		return laufendeTests;
	}

	public void setLaufendeTests(List<WissenstestBo> laufendeTests) {
		this.laufendeTests = laufendeTests;
	}

	public WissenstestBean getWissenstestBean() {
		return wissenstestBean;
	}

	public void setWissenstestBean(WissenstestBean wissenstestBean) {
		this.wissenstestBean = wissenstestBean;
	}

}