package de.ruben.csvupload.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.ruben.csvupload.model.Student;

@Repository
@Transactional
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByLastName(String lastName);
}
