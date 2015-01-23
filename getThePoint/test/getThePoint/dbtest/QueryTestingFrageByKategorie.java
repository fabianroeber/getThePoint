package getThePoint.dbtest;

import java.util.List;

import org.junit.Test;

import de.hdm.getThePoint.db.DataAccess;
import de.hdm.getThePoint.db.dbmodel.Frage;

public class QueryTestingFrageByKategorie {

	@Test
	public void test() {

		DataAccess dataAcces = new DataAccess();

		List<Frage> liste = dataAcces.getFragenByKategorie(1);

		for (Frage frage : liste) {
			System.out.println(frage.getText());
		}
	}

}
