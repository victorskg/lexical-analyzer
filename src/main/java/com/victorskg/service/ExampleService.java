package com.victorskg.service;

import com.victorskg.model.Code;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static io.vavr.collection.List.ofAll;

@Service
public class ExampleService {

    private static final List<String> examples =
            List.of("src/resources/Example1.java", "src/resources/Example2.java", "src/resources/Example3.java");

    public List<Code> findAllExamples() {
        return ofAll(examples).map(this::toCode).asJava();
    }

    private String readExample(String fileName) throws IOException {
        var fileData = new StringBuffer();
        try (var reader = new BufferedReader(new FileReader(fileName))) {
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
        }
        return fileData.toString();
    }

    private Code toCode(String fileName) {
        try {
            return new Code(readExample(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
