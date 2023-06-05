package de.ruben.csvupload.repository;

import de.ruben.csvupload.model.Student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
    "jdbc:postgresql://localhost:5432/compose-postgres"
})
public class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @BeforeEach
    void cleanUp() {
        studentRepository.deleteAll();
    }

    @Test
    public void testSaveStudent() {
        // Arrange
        String title = "Mr.";
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String faculty = "Computer Science";

        Student student = new Student(title, firstName, lastName, email, faculty);

        // Act
        Student savedStudent = studentRepository.save(student);

        // Assert
        assertNotNull(savedStudent);
        assertInstanceOf(long.class, savedStudent.getId());
        assertEquals(title, savedStudent.getTitle());
        assertEquals(firstName, savedStudent.getFirstName());
        assertEquals(lastName, savedStudent.getLastName());
        assertEquals(email, savedStudent.getEmail());
        assertEquals(faculty, savedStudent.getFaculty());
    }

    @Test
    public void testFindAllStudents() {
        // Arrange
        String title = "Mr.";
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String faculty = "Computer Science";

        Student student = new Student(title, firstName, lastName, email, faculty);
        studentRepository.save(student);

        // Act
        List<Student> students = studentRepository.findAll();

        // Assert
        assertNotNull(students);
        assertEquals(1, students.size());
        assertEquals(student, students.get(0));
    }

    @Test
    public void testFindStudentByLastName() {
        // Arrange
        String title = "Mr.";
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String faculty = "Computer Science";

        Student student = new Student(title, firstName, lastName, email, faculty);
        studentRepository.save(student);

        // Act
        Student searchedStudent = studentRepository.findByLastName(lastName);

        // Assert
        assertNotNull(student);
        assertEquals(lastName, searchedStudent.getLastName());
    }
}
