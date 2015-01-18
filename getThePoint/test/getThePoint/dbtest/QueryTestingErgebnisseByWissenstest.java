package getThePoint.dbtest;
import java.util.List;

import org.junit.Test;

import de.hdm.getThePoint.bo.WissenstestBo;
import de.hdm.getThePoint.db.DataAcces;
import de.hdm.getThePoint.db.dbmodel.Ergebnis;
import de.hdm.getThePoint.db.dbmodel.Kategorie;
import de.hdm.getThePoint.db.dbmodel.Wissenstest;

public class QueryTestingErgebnisseByWissenstest {

	@Test
	public void test() {

		DataAcces dataAcces = new DataAcces();

		List<Ergebnis> liste = dataAcces.getErgebnisseByWissenstest(new WissenstestBo());

		for (Ergebnis ergebnis: liste) {
			System.out.println(ergebnis.getFrage().getText());
			System.out.println(ergebnis.getStudent().getName());
		}

	}
}
