package de.hdm.getThePoint.db.mapper;

import java.util.ArrayList;
import java.util.List;

import de.hdm.getThePoint.bo.StudentBo;
import de.hdm.getThePoint.db.dbmodel.Student;

public class StudentMapper implements DbMapperInterface<StudentBo, Student> {

	@Override
	public List<StudentBo> getModelsAsList(List<Student> dbmodels) {
		List<StudentBo> studentBos = new ArrayList<StudentBo>();
		for (Student student : dbmodels) {
			studentBos.add(getModel(student));
		}
		return studentBos;
	}

	@Override
	public StudentBo getModel(Student dbmodel) {
		StudentBo studentBo = new StudentBo();
		studentBo.setId(dbmodel.getId());
		studentBo.setKuerzel(dbmodel.getKuerzel());
		studentBo.setMatrikelnummer(dbmodel.getMatrikelNr());
		studentBo.setName(dbmodel.getName());
		return studentBo;
	}

	@Override
	public Student getDbModel(StudentBo model) {
		Student student = new Student(model.getId());
		student.setKuerzel(model.getKuerzel());
		student.setMatrikelNr(model.getMatrikelnummer());
		student.setName(model.getName());
		return student;
	}

}
