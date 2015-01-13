package de.hdm.getThePoint.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import de.hdm.getThePoint.bo.FrageBo;
import de.hdm.getThePoint.bo.KategorieBo;
import de.hdm.getThePoint.bo.WissenstestBo;
import de.hdm.getThePoint.db.DataAcces;
import de.hdm.getThePoint.db.mapper.FrageMapper;
import de.hdm.getThePoint.db.mapper.KategorieMapper;
import de.hdm.getThePoint.db.mapper.WissenstestMapper;

@ManagedBean(name = "wissenstestBean")
@ViewScoped
public class WissenstestBean implements Serializable {

	private static final long serialVersionUID = -5837697454989959201L;

	private List<FrageBo> fragen;
	private List<WissenstestBo> wissenstests;
	private List<KategorieBo> kategorien;
	private DualListModel<FrageBo> dlmfragen;

	private FrageMapper frageMapper;
	private KategorieMapper kategorieMapper;
	private WissenstestMapper wissenstestMapper;
	private DataAcces dataAccess;

	public WissenstestBean() {
		dataAccess = new DataAcces();
		frageMapper = new FrageMapper();
		wissenstestMapper = new WissenstestMapper();
		kategorieMapper = new KategorieMapper();
		getAllFragen();
		getAllKategorien();
		dlmfragen = new DualListModel<FrageBo>(fragen, fragen);

		getAllWissenstests();

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

	public void getAllFragen() {
		fragen = new ArrayList<FrageBo>();
		fragen = frageMapper.getModelsAsList(dataAccess.getAllFrage());
	}

	public void getAllWissenstests() {
		wissenstests = new ArrayList<WissenstestBo>();
		wissenstests = wissenstestMapper.getModelsAsList(dataAccess
				.getAllWissentests());
	}

	public void getAllKategorien() {
		kategorien = kategorieMapper.getModelsAsList(dataAccess
				.getAllKategorie());
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
}