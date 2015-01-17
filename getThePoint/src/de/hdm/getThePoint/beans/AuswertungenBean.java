package de.hdm.getThePoint.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ComparisonOperator;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FontFormatting;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

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
	private BarChartModel barModel;

	@PostConstruct
	public void init() {
		createBarModels();
	}

	private BarChartModel initBarModel() {
		BarChartModel model = new BarChartModel();

		ChartSeries richtig = new ChartSeries();
		richtig.setLabel("richtig");
		richtig.set("2004", 120);
		richtig.set("2005", 100);
		richtig.set("2006", 44);
		richtig.set("2007", 150);
		richtig.set("2008", 25);

		ChartSeries falsch = new ChartSeries();
		falsch.setLabel("falsch");
		falsch.set("2004", 52);
		falsch.set("2005", 60);
		falsch.set("2006", 110);
		falsch.set("2007", 135);
		falsch.set("2008", 120);

		model.addSeries(richtig);
		model.addSeries(falsch);

		return model;
	}

	private void createBarModels() {
		createBarModel();
	}

	private void createBarModel() {
		barModel = initBarModel();

		barModel.setTitle("Richtige und falsche Antworten pro Frage");
		barModel.setLegendPosition("ne");

		Axis xAxis = barModel.getAxis(AxisType.X);
		xAxis.setLabel("Fragen");

		Axis yAxis = barModel.getAxis(AxisType.Y);
		yAxis.setLabel("Anzahl Antworten");
		yAxis.setMin(0);
		yAxis.setMax(200);
	}

	public AuswertungenBean() {
		dataAccess = new DataAcces();
		wissenstestMapper = new WissenstestMapper();
		ergebnisMapper = new ErgebnisMapper();
		getAllWissenstests();

	}

	public void createAuswertungStudenten() throws IOException {
		getErgebnisseByWissenstest(selectedWissenstest);

		Workbook wb = new HSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();
		Sheet sheet = wb.createSheet("new sheet");

		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);

		int i = 1;
		int anzahlRichtigeAntw = 0;
		int anzahlRichtigeAntwGes = 0;
		int anzahlFragen = 0;
		int laststudentid = 0;
		for (ErgebnisBo er : ergebnisseByWissenstest) {
			if (laststudentid != er.getStudent().getId()) {
				i++;
				anzahlFragen = 0;
				Row row2 = sheet.createRow(i);
				Cell cell2 = row2.createCell(0);
				cell2.setCellStyle(style);
				cell2.setCellValue(er.getStudent().getName() + ", " + er.getStudent().getKuerzel());
				anzahlRichtigeAntw = 0;
				i++;
			}
			Row row3 = sheet.createRow(i);
			Cell cell3 = row3.createCell(0);
			cell3.setCellValue(er.getFrage().getText());
			Cell cell4 = row3.createCell(1);
			cell4.setCellValue(er.getAntwort().getText());
			Cell cell5 = row3.createCell(2);

			cell5.setCellValue(er.isRichtig() ? "richtig" : "falsch");

			i++;
			laststudentid = er.getStudent().getId();
			anzahlFragen++;
			if (er.isRichtig()) {
				anzahlRichtigeAntw++;
				anzahlRichtigeAntwGes++;
			}

			if (ergebnisseByWissenstest.indexOf(er) < ergebnisseByWissenstest
					.size() - 1) {
				if (er.getStudent().getId() != ergebnisseByWissenstest
						.get(ergebnisseByWissenstest.indexOf(er) + 1)
						.getStudent().getId()) {
					i++;
					Row row1 = sheet.createRow(i);
					Cell cell1 = row1.createCell(0);
					cell1.setCellValue("Richtige Antworten: "
							+ anzahlRichtigeAntw + "/" + anzahlFragen);
					i++;
				}
			}

		}
		
		i++;
		Row row5 = sheet.createRow(i);
		Cell cell8 = row5.createCell(0);
		cell8.setCellValue("Richtige Antworten: " + anzahlRichtigeAntw + "/" + anzahlFragen);

		i = i + 3;
		Row row4 = sheet.createRow(i);
		Cell cell6 = row4.createCell(0);
		cell6.setCellStyle(style);
		cell6.setCellValue("Prozentualer Anteil richtiger Antworten insgesamt: ");

		Cell cell7 = row4.createCell(1);
		cell7.setCellStyle(style);
		cell7.setCellValue(anzahlRichtigeAntwGes++ * 100
				/ ergebnisseByWissenstest.size() + "%");

		sheet.autoSizeColumn((short) 0);
		sheet.autoSizeColumn((short) 1);
		sheet.autoSizeColumn((short) 2);
		sheet.autoSizeColumn((short) 3);

		FacesContext facesContext = FacesContext.getCurrentInstance();

		ExternalContext externalContext = facesContext.getExternalContext();

		externalContext.responseReset();
		externalContext.setResponseContentType("application/vnd.ms-excel");

		externalContext.setResponseHeader("Content-Disposition", "testexcel");

		OutputStream outputStream = externalContext.getResponseOutputStream();
		wb.write(outputStream);
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
				.getErgebnisseByWissenstest(selektierterWissenstest));
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

	public BarChartModel getBarModel() {
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

}
