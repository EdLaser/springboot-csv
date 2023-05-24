package de.ruben.csvupload.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import de.ruben.csvupload.model.Student;

public class CSVHelper {
    public static final String TYPE = "text/csv";
    static String[] headers = { "title", "first_name", "last_name", "email", "faculty" };

    public static boolean hasCSVFormat(MultipartFile uploadFile) {
        return TYPE.equals(uploadFile.getContentType());
    }

    public static List<Student> csvToStudents(InputStream inputStream) {
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            try (CSVParser csvParser = new CSVParser(fileReader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
                List<Student> students = new ArrayList<>();

                Iterable<CSVRecord> csvRecords = csvParser.getRecords();

                for (CSVRecord csvRecord : csvRecords) {
                    Student student = new Student(
                            csvRecord.get("title"),
                            csvRecord.get("first_name"),
                            csvRecord.get("last_name"),
                            csvRecord.get("email"),
                            csvRecord.get("faculty"));
                    students.add(student);
                }

                return students;
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("failed to parse CSV: " + e.getMessage());
        }
    }

    private CSVHelper() {
        throw new UnsupportedOperationException("This is utility and can not be instantiated");
    }
}
