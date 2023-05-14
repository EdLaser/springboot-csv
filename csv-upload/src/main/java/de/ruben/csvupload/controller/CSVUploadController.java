package de.ruben.csvupload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import de.ruben.csvupload.helpers.CSVHelper;
import de.ruben.csvupload.message.ResponseMessage;
import de.ruben.csvupload.service.CSVService;
import de.ruben.csvupload.model.Student;

@Controller
public class CSVUploadController {

    @Autowired
    CSVService csvService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage>uploadFile(@RequestParam("file") MultipartFile uploadFile) {

        if(CSVHelper.hasCSVFormat(uploadFile)) {
            try {
                csvService.save(uploadFile);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("File upload succes: " + uploadFile.getOriginalFilename()));
            } catch(Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("Could not upload: " + uploadFile.getOriginalFilename()));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Upload csv file"));
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getallStudents() {
        try {
            List<Student> students = csvService.getAllStudents();
            return students.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<List<Student>>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
