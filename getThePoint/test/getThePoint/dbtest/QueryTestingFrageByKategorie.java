package getThePoint.dbtest;

import java.util.List;

import org.junit.Test;

import de.hdm.getThePoint.db.DataAcces;
import de.hdm.getThePoint.db.dbmodel.Frage;

public class QueryTestingFrageByKategorie {

	@Test
	public void test() {

		DataAcces dataAcces = new DataAcces();

		List<Frage> liste = dataAcces.getFragenByKategorie(1);

		for (Frage frage : liste) {
			System.out.println(frage.getText());
		}
	}

}
