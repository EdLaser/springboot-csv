package de.ruben.csvupload.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import de.ruben.csvupload.helpers.CSVHelper;
import de.ruben.csvupload.message.ResponseMessage;
import de.ruben.csvupload.service.CSVService;
import lombok.RequiredArgsConstructor;
import de.ruben.csvupload.model.Student;

@Controller
@CrossOrigin("http://localhost:8081")
@RequiredArgsConstructor
public class CSVUploadController {

    private final CSVService csvService;

    @GetMapping("/")
    @ResponseBody
    public String test() {
        return """
                <html>
                <header><title>Welcome</title></header>
                <body>
                Hello world
                </body>
                </html>""";
    }

    @GetMapping("/upload")
    public String showuploadSite() {
        return "uploadSite.html";
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                csvService.save(file);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseMessage("File upload succes: " + file.getOriginalFilename()));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                        .body(new ResponseMessage("Could not upload: " + file.getOriginalFilename()));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Upload csv file"));
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getallStudents() {
        try {
            List<Student> students = csvService.getAllStudents();
            return students.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                    : new ResponseEntity<List<Student>>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/students/{lastName}")
    public ResponseEntity<Student> getByLastName(@PathVariable("lastName") String lastName) {
        var student = csvService.getByLastName(lastName);
        return student == null ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(student, HttpStatus.OK);
    }
}
