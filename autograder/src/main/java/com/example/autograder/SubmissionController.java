package com.example.autograder;

import com.example.autograder.JobItems.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SubmissionController {

    private final Path rootLocation = Paths.get("uploads");

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/submit")
    public String uploadFile(@RequestParam("file") MultipartFile file)
        throws IOException {
        if (file.isEmpty()) return "empty file...";

        Files.createDirectories(rootLocation);

        String fileName = file.getOriginalFilename();

        var uuid = UUID.randomUUID();

        String randomFileName = "" + uuid.toString() + "_" + fileName;

        Files.copy(
            file.getInputStream(),
            rootLocation.resolve(randomFileName),
            StandardCopyOption.REPLACE_EXISTING
        );

        try {
            byte[] bytes = file.getBytes();
            System.out.println(
                "Received file: " + fileName + " " + bytes.length + " bytes"
            );
        } catch (Exception e) {
            return e.toString();
        }

        Job job = new Job("" + uuid.toString() + "_" + fileName);

        jobRepository.save(job);

        redisTemplate
            .opsForList()
            .rightPush(
                "queue_" + fileName.split("\\.")[1],
                job.getId().toString()
            );

        return "Upload successful!";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Job> getAllUsers() {
        // This returns a JSON or XML with the users
        return jobRepository.findAll();
    }
}
