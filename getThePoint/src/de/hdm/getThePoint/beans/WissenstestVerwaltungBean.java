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
import de.hdm.getThePoint.db.mapper.WissenstestMapper;

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

	private List<FrageBo> fragen;
	private List<WissenstestBo> wissenstests;
	private WissenstestBo selectedWissenstest;
	private List<KategorieBo> kategorien;
	private List<FrageBo> wissenstestfragen;
	private DualListModel<FrageBo> dlmfragen;
	private KategorieBo selectedKategorie;

	// Attribute für den aktuellen Wissenstest
	
	
	
	private int laufzeit;
	

	private FrageMapper frageMapper;
	private KategorieMapper kategorieMapper;
	private WissenstestMapper wissenstestMapper;

	public WissenstestVerwaltungBean() {
		frageMapper = new FrageMapper();
		wissenstestMapper = new WissenstestMapper();
		kategorieMapper = new KategorieMapper();
		dlmfragen = new DualListModel<FrageBo>(fragen, fragen);
	}

	@PostConstruct
	public void init() {
		getAllFragen();
		getAllKategorien();
		getAllWissenstests();

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

	public void onRowSelect(int id) {
		selectedWissenstest = wissenstests.get(id);
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
	 * Lädt alle Wissenstests aus der Datenbank
	 */
	public void getAllWissenstests() {
		List<WissenstestBo> allwissenstests = wissenstestMapper
				.getModelsAsList(dataAccessBean.getDataAccess()
						.getAllWissentests());
		wissenstests = new ArrayList<WissenstestBo>();

		for (WissenstestBo wissenstestBo : allwissenstests) {
			if (userBean.getLehrender() != null) {
				if (userBean.getLehrender()
						.equals(wissenstestBo.getLehrender())) {
					wissenstests.add(wissenstestBo);
				}
			}
		}
	}

	public void getAllKategorien() {
		kategorien = kategorieMapper.getModelsAsList(dataAccessBean
				.getDataAccess().getAllKategorie());
	}

	/**
	 * Starten den ausgewählten Wissenstest manuell
	 */
	public void startTestNow() {
		selectedWissenstest.setStartzeit(new Date(System.currentTimeMillis()));
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage("Erfolgreich gestartet!",
						"Wissenstest wurde gestartet. Die Bearbeitungdauer berträgt "
								+ bearbeitungsZeit + " Minuten ab jetzt."));
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

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public int getBearbeitungsZeit() {
		return bearbeitungsZeit;
	}

	public void setBearbeitungsZeit(int bearbeitungsZeit) {
		this.bearbeitungsZeit = bearbeitungsZeit;
	}

	public Date getStartzeit() {
		return startzeit;
	}

	public void setStartzeit(Date startzeit) {
		this.startzeit = startzeit;
	}

	public boolean isRandom() {
		return random;
	}

	public void setRandom(boolean random) {
		this.random = random;
	}

	public int getLaufzeit() {
		return laufzeit;
	}

	public void setLaufzeit(int laufzeit) {
		this.laufzeit = laufzeit;
	}
}