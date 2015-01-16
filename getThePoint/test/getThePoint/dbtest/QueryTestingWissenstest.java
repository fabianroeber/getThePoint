package getThePoint.dbtest;
import java.util.List;

import org.junit.Test;

import de.hdm.getThePoint.bo.WissenstestBo;
import de.hdm.getThePoint.db.DataAcces;
import de.hdm.getThePoint.db.dbmodel.Kategorie;
import de.hdm.getThePoint.db.dbmodel.Wissenstest;

public class QueryTestingWissenstest {

	@Test
	public void test() {

		DataAcces dataAcces = new DataAcces();

		List<Wissenstest> liste = dataAcces.getAllWissentests();

		for (Wissenstest wissenstest: liste) {
			System.out.println(wissenstest.getBezeichnung());
			System.out.println(wissenstest.getLehrender().getName());
		}

	}
}
