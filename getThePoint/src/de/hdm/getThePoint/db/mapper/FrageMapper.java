package de.hdm.getThePoint.db.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hdm.getThePoint.bo.AntwortBo;
import de.hdm.getThePoint.bo.FrageBo;
import de.hdm.getThePoint.db.dbmodel.Antwort;
import de.hdm.getThePoint.db.dbmodel.Frage;
import de.hdm.getThePoint.enums.Schwierigkeit;

public class FrageMapper implements DbMapperInterface<FrageBo, Frage> {

	AntwortMapper antwortMapper;
	KategorieMapper kategorieMapper;

	public FrageMapper() {
		antwortMapper = new AntwortMapper();
		kategorieMapper = new KategorieMapper();
	}

	@Override
	public List<FrageBo> getModelsAsList(List<Frage> dbmodels) {
		List<FrageBo> fragen = new ArrayList<>();
		for (Frage frage : dbmodels) {
			fragen.add(getModel(frage));
		}
		return fragen;
	}

	@Override
	public FrageBo getModel(Frage dbmodel) {
		FrageBo frageBo = new FrageBo();
		frageBo.setId(dbmodel.getId());
		frageBo.setText(dbmodel.getText());
		frageBo.setBild(dbmodel.getBild());
		frageBo.setKategorie(kategorieMapper.getModel(dbmodel.getKategorie()));

		if (dbmodel.getSchwierigkeit() != null) {

			switch (dbmodel.getSchwierigkeit()) {
			case 0:
				frageBo.setSchwierigkeit(Schwierigkeit.LEICHT);
				break;
			case 1:
				frageBo.setSchwierigkeit(Schwierigkeit.MITTEL);
				break;
			case 2:
				frageBo.setSchwierigkeit(Schwierigkeit.SCHWER);
				break;
			default:
				break;
			}
		}

		frageBo.setAntwortmoeglichkeiten(antwortMapper
				.getModelsAsList(new ArrayList<>(dbmodel.getAntworts())));
		frageBo.setLoesung(antwortMapper.getModel(dbmodel.getAntwort()));
		return frageBo;
	}

	@Override
	public Frage getDbModel(FrageBo model) {
		Frage frage = new Frage();
		frage.setId(model.getId());
		frage.setText(model.getText());
		frage.setKategorie(kategorieMapper.getDbModel(model.getKategorie()));
		Set<Antwort> antwortmoeglichkeiten = new HashSet<>();

		for (AntwortBo antwort : model.getAntwortmoeglichkeiten()) {
			antwortmoeglichkeiten.add(antwortMapper.getDbModel(antwort));
		}

		if (model.getSchwierigkeit() != null) {

			switch (model.getSchwierigkeit()) {
			case LEICHT:
				frage.setSchwierigkeit(0);
				break;
			case MITTEL:
				frage.setSchwierigkeit(1);
				break;
			case SCHWER:
				frage.setSchwierigkeit(2);
				break;

			default:
				break;
			}
		}

		frage.setAntworts(antwortmoeglichkeiten);
		frage.setAntwort(antwortMapper.getDbModel(model.getLoesung()));

		return frage;
	}

}
