package de.ruben.csvupload.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import de.ruben.csvupload.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import de.ruben.csvupload.helpers.CSVHelper;
import de.ruben.csvupload.model.Student;


@Service
@RequiredArgsConstructor
public class CSVService {
    private final StudentRepository studentRepository;

    public void save(MultipartFile uploadFile) {
        try {
            List<Student> students = CSVHelper.csvToStudents(uploadFile.getInputStream());
            studentRepository.saveAll(students);
        } catch (IOException e) {
            throw new IllegalArgumentException("failed to store csv data: " +  e.getMessage());
        }
    }

    public Student getByLastName(String lastName) {
        return studentRepository.findByLastName(lastName);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void deleteAll() {
        studentRepository.deleteAll();
    }
}
