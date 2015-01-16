package getThePoint.dbtest;
import java.util.List;

import org.junit.Test;

import de.hdm.getThePoint.db.DataAcces;
import de.hdm.getThePoint.db.dbmodel.Ergebnis;

public class QueryTestingErgebnisseByWissenstest {

	@Test
	public void test() {

		DataAcces dataAcces = new DataAcces();

		List<Ergebnis> liste = dataAcces.getErgebnisseByWissenstest(1);

		for (Ergebnis ergebnis: liste) {
			System.out.println(ergebnis.getFrage().getText());
			System.out.println(ergebnis.getStudent().getName());
		}

	}
}
