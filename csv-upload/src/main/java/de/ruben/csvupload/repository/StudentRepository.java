package de.ruben.csvupload.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.ruben.csvupload.model.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    List<Student> findByLastName(String lastName);
}
