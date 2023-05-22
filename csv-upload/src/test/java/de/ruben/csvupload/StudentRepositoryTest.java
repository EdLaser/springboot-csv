package de.ruben.csvupload;

import de.ruben.csvupload.model.Student;
import de.ruben.csvupload.repository.StudentRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
    "jdbc:postgresql://localhost:5432/compose-postgres"
})
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestEntityManager entityManager;

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
        assertNotNull(savedStudent.getId());
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
}
