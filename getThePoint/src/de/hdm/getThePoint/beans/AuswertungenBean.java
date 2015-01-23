package de.hdm.getThePoint.beans;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

import de.hdm.getThePoint.bo.AuswertungsErgebnis;
import de.hdm.getThePoint.bo.ErgebnisBo;
import de.hdm.getThePoint.bo.FrageBo;
import de.hdm.getThePoint.bo.StudentBo;
import de.hdm.getThePoint.bo.WissenstestBo;
import de.hdm.getThePoint.db.DataAccess;
import de.hdm.getThePoint.db.mapper.ErgebnisMapper;
import de.hdm.getThePoint.db.mapper.WissenstestMapper;

@ManagedBean(name = "auswertungenBean")
@ViewScoped
public class AuswertungenBean implements Serializable {

	private static final long serialVersionUID = -1378705694453085109L;

	private List<WissenstestBo> wissenstests;
	private List<ErgebnisBo> ergebnisse;
	private List<ErgebnisBo> ergebnisseByWissenstestOrderByStudent;
	private List<ErgebnisBo> ergebnisseByWissenstest;
	private List<ErgebnisBo> ergebnisseByWissenstestOrderByFrageUndRichtig;
	private WissenstestBo selectedWissenstest;
	private WissenstestMapper wissenstestMapper;
	private ErgebnisMapper ergebnisMapper;
	private DataAccess dataAccess;
	private BarChartModel barModel;
	private List<AuswertungsErgebnis> auswertungsErgebnis;
	private int definedPercentage;

	private int maxanz = 0;

	public AuswertungenBean() {
		dataAccess = new DataAccess();
		wissenstestMapper = new WissenstestMapper();
		ergebnisMapper = new ErgebnisMapper();
		getAllWissenstests();
		selectedWissenstest = wissenstests.get(0);
		createAuswertungsErgebnisse();
	}

	@PostConstruct
	public void init() {
		createBarModels();
	}

	/**
	 * Diese Methode befüllt die Liste von Auswertungsergebnis, in der in der
	 * View ersichtlich macht, welche Studenten einen Bonuspunkt bekommen.
	 */
	private void createAuswertungsErgebnisse() {
		getErgebnisseByWissenstestOrderByStudent(selectedWissenstest);

//		int anzrichtig = 0;
//		int anzfalsch = 0;
//		AuswertungsErgebnis auswerg;
//		ErgebnisBo lasterg = null;
//
//		for (ErgebnisBo erg : ergebnisseByWissenstestOrderByStudent) {
//			if (lasterg != null)
//				if (erg.getStudent().getId() != lasterg.getStudent().getId()) {
//					auswerg = new AuswertungsErgebnis(erg.getStudent()
//							.getName(), erg.getStudent().getMatrikelnummer(),
//							(anzrichtig
//									* 100
//									/ selectedWissenstest.getFrageZuordungen()
//											.size()));
//					auswertungsErgebnis.add(auswerg);
//					anzrichtig = 0;
//					anzfalsch = 0;
//				}
//
//			if (erg.isRichtig())
//				anzrichtig++;
//			else
//				anzfalsch++;
//
//			lasterg = erg;
//		}
//		auswerg = new AuswertungsErgebnis(lasterg.getStudent().getName(),
//				lasterg.getStudent().getMatrikelnummer(), anzrichtig * 100
//						/ lasterg.getWissenstest().getFrageZuordungen().size());
//		auswertungsErgebnis.add(auswerg);

	}

	/**
	 * Erstellt Objekte f&uuml;r die Erstellung einer Statistik.
	 * 
	 * @return
	 */
	private BarChartModel initBarModel() {
		getErgebnisseByWissenstestOrderByFrageUndRichtig(selectedWissenstest);
		HorizontalBarChartModel model = new HorizontalBarChartModel();

		ChartSeries richtig = new ChartSeries();
		ChartSeries falsch = new ChartSeries();
		richtig.setLabel("richtig");
		falsch.setLabel("falsch");

		FrageBo lastFrage = null;
		int anzrichtig = 0;
		int anzfalsch = 0;
		for (ErgebnisBo erg : ergebnisseByWissenstestOrderByFrageUndRichtig) {
			if (lastFrage != null) {
				if (erg.getFrage().getId() != lastFrage.getId()) {
					richtig.set(lastFrage.getText(), anzrichtig);
					falsch.set(lastFrage.getText(), anzfalsch);
					if (anzrichtig > maxanz)
						maxanz = anzrichtig;
					if (anzfalsch > maxanz)
						maxanz = anzfalsch;
					anzrichtig = 0;
					anzfalsch = 0;
				}
			}
			if (erg.isRichtig())
				anzrichtig++;
			else
				anzfalsch++;

			lastFrage = erg.getFrage();

		}
		richtig.set(lastFrage.getText(), anzrichtig);
		falsch.set(lastFrage.getText(), anzfalsch);

		if (anzrichtig > maxanz)
			maxanz = anzrichtig;
		if (anzfalsch > maxanz)
			maxanz = anzfalsch;

		model.addSeries(richtig);
		model.addSeries(falsch);
		return model;
	}

	public void createBarModels() {
		createBarModel();
	}

	private void createBarModel() {
		barModel = initBarModel();

		barModel.setTitle("Richtige und falsche Antworten pro Frage");
		barModel.setLegendPosition("ne");

		Axis xAxis = barModel.getAxis(AxisType.X);
		xAxis.setLabel("Anzahl Antworten");
		xAxis.setMin(0);
		xAxis.setMax(maxanz + 1);
		xAxis.setTickCount(1);

		Axis yAxis = barModel.getAxis(AxisType.Y);
		yAxis.setLabel("Fragen");

	}

	/**
	 * Erstellt ein Report als .xls Datei zu einem Wissenstest.
	 * 
	 * @throws IOException
	 */
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
				cell2.setCellValue(er.getStudent().getName() + ", "
						+ er.getStudent().getKuerzel());
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
		cell8.setCellValue("Richtige Antworten: " + anzahlRichtigeAntw + "/"
				+ anzahlFragen);

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

	/**
	 * Diese Methode prüft ob ein Ergebnis unter oder über der eingegeben
	 * Prozentzahl liegt.
	 * 
	 * @param index
	 * @return
	 */
	public boolean isOverPerc(int index) {
		return auswertungsErgebnis.get(index).isoverPercentage(
				definedPercentage);
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

	public void getErgebnisseByWissenstestOrderByStudent(
			WissenstestBo selektierterWissenstest) {
		ergebnisseByWissenstestOrderByStudent = ergebnisMapper
				.getModelsAsList(dataAccess
						.getErgebnisseByWissenstest(selektierterWissenstest));
	}

	public void getErgebnisseByWissenstestOrderByFrageUndRichtig(
			WissenstestBo selektierterWissenstest) {
		ergebnisseByWissenstestOrderByFrageUndRichtig = ergebnisMapper
				.getModelsAsList(dataAccess
						.getErgebnisseByWissenstestOrderByFrageUndRichtig(selektierterWissenstest));
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

	public List<ErgebnisBo> getErgebnisseByWissenstestOrderByFrageUndRichtig() {
		return ergebnisseByWissenstestOrderByFrageUndRichtig;
	}

	public void setErgebnisseByWissenstestOrderByFrageUndRichtig(
			List<ErgebnisBo> ergebnisseByWissenstestOrderByFrageUndRichtig) {
		this.ergebnisseByWissenstestOrderByFrageUndRichtig = ergebnisseByWissenstestOrderByFrageUndRichtig;
	}

	public List<AuswertungsErgebnis> getAuswertungsErgebnis() {
		return auswertungsErgebnis;
	}

	public void setAuswertungsErgebnis(
			List<AuswertungsErgebnis> auswertungsErgebnis) {
		this.auswertungsErgebnis = auswertungsErgebnis;
	}

	public int getDefinedPercentage() {
		return definedPercentage;
	}

	public void setDefinedPercentage(int definedPercentage) {
		this.definedPercentage = definedPercentage;
	}

}
