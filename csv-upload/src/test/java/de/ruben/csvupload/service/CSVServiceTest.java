package de.ruben.csvupload.service;

import de.ruben.csvupload.model.Student;
import de.ruben.csvupload.repository.StudentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
    "jdbc:postgresql://localhost:5432/compose-postgres"
})
class CSVServiceTest {
    
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CSVService csvService;

    @BeforeEach
    void cleanUp() {
        csvService.deleteAll();
    }

    @Test
    void save_ValidUploadFile_InvokesSaveAllOnStudentRepository() throws IOException {
        // Arrange
        byte[] fileContent = "title,first_name,last_name,email,faculty\nMr.,John,Doe,john.doe@example.com,Computer Science\nMrs.,Jane,Smith,jane.smith@example.com,Mathematics".getBytes();
        MultipartFile uploadFile = new MockMultipartFile("test.csv", fileContent);
        List<Student> students = Arrays.asList(
                new Student("Mr.", "John", "Doe", "john.doe@example.com", "Computer Science"),
                new Student("Ms.", "Jane", "Smith", "jane.smith@example.com", "Mathematics")
        );

        // Act
        csvService.save(uploadFile);

        // Assert
        List<Student> savedStudents = studentRepository.findAll();
        assertEquals(students.size(), savedStudents.size());
        for (int i = 0; i < students.size(); i++) {
            assertEquals(students.get(i), savedStudents.get(i));
        }
    }

    @Test
    void getByLastName_ExistingLastName_ReturnsStudent() {
        // Arrange
        String lastName = "Doe";
        Student expectedStudent = new Student("Mr.", "John", "Doe", "john.doe@example.com", "Computer Science");
        studentRepository.save(expectedStudent);

        // Act
        Student result = csvService.getByLastName(lastName);

        // Assert
        assertEquals(expectedStudent, result);
    }

    @Test
    void getByLastName_NonExistingLastName_ReturnsNull() {
        // Arrange
        String lastName = "Smith";

        // Act
        Student result = csvService.getByLastName(lastName);

        // Assert
        assertNull(result);
    }

    @Test
    void getAllStudents_ExistingStudents_ReturnsAllStudents() {
        // Arrange
        List<Student> expectedStudents = Arrays.asList(
                new Student("Mr.", "John", "Doe", "john.doe@example.com", "Computer Science"),
                new Student("Ms.", "Jane", "Smith", "jane.smith@example.com", "Mathematics")
        );
        for (Student student : expectedStudents) {
            studentRepository.save(student);
        }

        // Act
        List<Student> result = csvService.getAllStudents();

        // Assert
        assertEquals(expectedStudents.size(), result.size());
        for (int i = 0; i < expectedStudents.size(); i++) {
            assertEquals(expectedStudents.get(i), result.get(i));
        }
    }

    @Test
    void getAllStudents_NoStudents_ReturnsEmptyList() {
        // Arrange

        // Act
        List<Student> result = csvService.getAllStudents();

        // Assert
        assertTrue(result.isEmpty());
    }
}
