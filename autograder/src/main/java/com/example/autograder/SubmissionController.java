package com.example.autograder;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SubmissionController {

    @PostMapping("/submit")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) return "empty file...";

        String fileName = file.getOriginalFilename();

        try {
            byte[] bytes = file.getBytes();
            System.out.println(
                "Received file: " + fileName + " " + bytes.length + " bytes"
            );
        } catch (Exception e) {
            return e.toString();
        }

        return "Upload successful!";
    }
}
