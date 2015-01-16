package de.hdm.getThePoint.beans;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import de.hdm.getThePoint.bo.AntwortBo;
import de.hdm.getThePoint.bo.ErgebnisBo;
import de.hdm.getThePoint.bo.FrageBo;
import de.hdm.getThePoint.bo.KategorieBo;
import de.hdm.getThePoint.bo.WissenstestBo;
import de.hdm.getThePoint.db.DataAcces;
import de.hdm.getThePoint.db.mapper.ErgebnisMapper;
import de.hdm.getThePoint.db.mapper.FrageMapper;
import de.hdm.getThePoint.db.mapper.KategorieMapper;
import de.hdm.getThePoint.db.mapper.WissenstestMapper;

@ManagedBean(name = "auswertungenBean")
@ViewScoped
public class AuswertungenBean implements Serializable {

	private static final long serialVersionUID = -1378705694453085109L;

	private List<WissenstestBo> wissenstests;
	private List<ErgebnisBo> ergebnisse;
	private WissenstestBo selectedWissenstest;
	private WissenstestMapper wissenstestMapper;
	private ErgebnisMapper ergebnisMapper;
	private DataAcces dataAccess;

	public AuswertungenBean() {
		dataAccess = new DataAcces();
		wissenstestMapper = new WissenstestMapper();
		getAllWissenstests();
		getAllErgebnisse();
	}

	public void createAuswertungStudenten() throws IOException {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");

		int i = 0;
		for (int z = 0; z < i; z++) {

			Row row = sheet.createRow(0);
		}

		FileOutputStream fileOut = new FileOutputStream(
				"StudentenAuswertung.xls");
		wb.write(fileOut);
		fileOut.close();

	}

	public void getAllWissenstests() {
		wissenstests = wissenstestMapper.getModelsAsList(dataAccess
				.getAllWissentests());
	}

	public void getAllErgebnisse() {
		ergebnisse = ergebnisMapper.getModelsAsList(dataAccess
				.getAllErgebnisse());
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

}
