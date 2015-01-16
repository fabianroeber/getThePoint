package de.hdm.getThePoint.beans;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import de.hdm.getThePoint.bo.ErgebnisBo;
import de.hdm.getThePoint.bo.WissenstestBo;
import de.hdm.getThePoint.db.DataAcces;
import de.hdm.getThePoint.db.mapper.ErgebnisMapper;
import de.hdm.getThePoint.db.mapper.WissenstestMapper;

@ManagedBean(name = "auswertungenBean")
@ViewScoped
public class AuswertungenBean implements Serializable {

	private static final long serialVersionUID = -1378705694453085109L;

	private List<WissenstestBo> wissenstests;
	private List<ErgebnisBo> ergebnisse;
	private List<ErgebnisBo> ergebnisseByWissenstest;
	private WissenstestBo selectedWissenstest;
	private WissenstestMapper wissenstestMapper;
	private ErgebnisMapper ergebnisMapper;
	private DataAcces dataAccess;

	public AuswertungenBean() {
		dataAccess = new DataAcces();
		wissenstestMapper = new WissenstestMapper();
		ergebnisMapper = new ErgebnisMapper();
		getAllWissenstests();

	}

	public void createAuswertungStudenten() throws IOException {
		getErgebnisseByWissenstest(selectedWissenstest);

		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");

		int i = 0;
		int anzahlRichtigeAntw = 0;
		int anzahlRichtigeAntwGes = 0;
		int laststudentid = 0;
		for (ErgebnisBo er : ergebnisseByWissenstest) {
			if (laststudentid != er.getStudent().getId()) {
				Row row1 = sheet.createRow(i);
				Cell cell1 = row1.createCell(0);
				cell1.setCellValue("Richtige Antworten: " + anzahlRichtigeAntw);
				i = +2;
				Row row2 = sheet.createRow(i);
				Cell cell2 = row2.createCell(0);
				cell2.setCellValue(er.getStudent().getName());
				i++;
				anzahlRichtigeAntw = 0;
			}

			Row row = sheet.createRow(i);
			Cell cell1 = row.createCell(0);
			cell1.setCellValue(er.getFrage().getText());
			Cell cell2 = row.createCell(1);
			cell2.setCellValue(er.getAntwort().getText());
			Cell cell3 = row.createCell(2);
			cell3.setCellValue(er.isRichtig());

			i++;
			Row row2 = sheet.createRow(i);
			laststudentid = er.getStudent().getId();
			if (er.isRichtig()) {
				anzahlRichtigeAntw++;
				anzahlRichtigeAntwGes++;
			}
			i++;
			Row row3 = sheet.createRow(i);
			Cell cell4 = row.createCell(0);
			cell4.setCellValue("Prozentualer Anteil richtiger Antworten insgesamt: ");

			Cell cell5 = row.createCell(1);
			cell5.setCellValue(anzahlRichtigeAntwGes++ * 100
					/ ergebnisseByWissenstest.size() + "%");
		}

		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facesContext
				.getExternalContext().getResponse();

		response.reset();
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "testexcel");

		OutputStream out = response.getOutputStream();
		wb.write(out);
		facesContext.responseComplete();

	}

	public void getAllWissenstests() {
		wissenstests = wissenstestMapper.getModelsAsList(dataAccess
				.getAllWissentests());
	}

	public void getAllErgebnisse() {
		ergebnisse = ergebnisMapper.getModelsAsList(dataAccess
				.getAllErgebnisse());
	}

	public void getErgebnisseByWissenstest(WissenstestBo selektierterWissenstest) {
		ergebnisseByWissenstest = ergebnisMapper.getModelsAsList(dataAccess
				.getErgebnisseByWissenstest(selektierterWissenstest.getId()));
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

}
