package getThePoint.dbtest;
import java.util.List;

import org.junit.Test;

import de.hdm.getThePoint.db.DataAccess;
import de.hdm.getThePoint.db.dbmodel.Ergebnis;

public class QueryTestingErgebnis {

	@Test
	public void test() {

		DataAccess dataAcces = new DataAccess();

		List<Ergebnis> liste = dataAcces.getAllErgebnisse();

		for (Ergebnis ergebnis: liste) {
			System.out.println(ergebnis.getFrage().getText());
			System.out.println(ergebnis.getStudent().getName());
		}

	}
}
