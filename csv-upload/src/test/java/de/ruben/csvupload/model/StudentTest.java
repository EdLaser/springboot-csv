package de.ruben.csvupload.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentTest {

    @Test
    public void testStudentCreation() {
        // Arrange
        String title = "Mr.";
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String faculty = "Computer Science";

        // Act
        Student student = new Student(title, firstName, lastName, email, faculty);

        // Assert
        assertNotNull(student);
        assertEquals(title, student.getTitle());
        assertEquals(firstName, student.getFirstName());
        assertEquals(lastName, student.getLastName());
        assertEquals(email, student.getEmail());
        assertEquals(faculty, student.getFaculty());
    }
}
