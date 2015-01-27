package getThePoint.dbtest;
import java.util.List;

import org.junit.Test;

import de.hdm.getThePoint.db.DataAccess;
import de.hdm.getThePoint.db.dbmodel.Ergebnis;

public class TestingErgebnisseByWissenstestAndStudent {

	@Test
	public void test() {

		DataAccess dataAcces = new DataAccess();
		
		List<Ergebnis> liste = dataAcces.getErgebnisseByWissenstestAndStudent(dataAcces.getStudentByKuerzel("teststudent"), dataAcces.getWissentestsWithErgebnis().get(0));

		for (Ergebnis ergebnis: liste) {
			System.out.println(ergebnis.getStudent().getName());
			System.out.println(ergebnis.getFrage().getText());
			System.out.println(ergebnis.getFrage().getAntwort().getText());
			System.out.println(ergebnis.getRichtig());
			System.out.println(ergebnis.getFrage().getKategorie().getBezeichnung());
			System.out.println(ergebnis.getFrage().getSchwierigkeit());
		}

	}
}
