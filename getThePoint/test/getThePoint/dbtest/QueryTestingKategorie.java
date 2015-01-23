package getThePoint.dbtest;
import java.util.List;

import org.junit.Test;

import de.hdm.getThePoint.db.DataAccess;
import de.hdm.getThePoint.db.dbmodel.Kategorie;

public class QueryTestingKategorie {

	@Test
	public void test() {

		DataAccess dataAcces = new DataAccess();

		List<Kategorie> liste = dataAcces.getAllKategorie();

		for (Kategorie kategorie : liste) {
			System.out.println(kategorie.getBezeichnung());
			System.out.println(kategorie.getLehrender().getName());
		}

	}
}
