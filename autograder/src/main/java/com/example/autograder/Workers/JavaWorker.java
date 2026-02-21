package com.example.autograder.Workers;

import com.example.autograder.Models.TestCase;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class JavaWorker extends GradingWorker {

    public JavaWorker() {
        super("queue_java");
    }

    @Override
    protected Integer[] runCode(String filePath) {
        Integer[] results = new Integer[10]; // Default all to null/0

        // Extract ID and create temp folder
        String jobId = new File(filePath).getName();
        Path jobDir = Paths.get("temp_jobs", jobId);

        try {
            Files.createDirectories(jobDir);

            Files.copy(
                Paths.get("uploads", filePath),
                jobDir.resolve("Main.java"),
                StandardCopyOption.REPLACE_EXISTING
            );

            ObjectMapper mapper = new ObjectMapper();
            // Testcases location
            File jsonFile = new File(
                "/Users/vaibhavsatishkumar/autograding-server/autograder/TestCaseSchema.json"
            );

            TestCase[] tests = mapper.readValue(jsonFile, TestCase[].class);

            for (int i = 0; i < tests.length; i++) {
                TestCase test = tests[i];

                Files.writeString(jobDir.resolve("input.txt"), test.input());

                ProcessBuilder builder = new ProcessBuilder(
                    "docker",
                    "run",
                    "--rm",
                    "--memory=512m",
                    "--cpus=1.0",
                    "-v",
                    jobDir.toAbsolutePath().toString() + ":/app",
                    "-w",
                    "/app",
                    "eclipse-temurin:17-jdk", // <--- BETTER IMAGE
                    "sh",
                    "-c",
                    "javac Main.java && java Main < input.txt"
                );

                Process process = builder.start();

                String output = new String(
                    process.getInputStream().readAllBytes()
                );
                String error = new String(
                    process.getErrorStream().readAllBytes()
                );

                boolean finished = process.waitFor(5, TimeUnit.SECONDS);

                if (!finished) {
                    process.destroy();
                    results[i] = 1; // TLE
                } else if (process.exitValue() != 0) {
                    System.out.println("Runtime Error: " + error);
                    results[i] = 1; // Error
                } else {
                    if (output.trim().equals(test.expected_output().trim())) {
                        results[i] = 2; // PASS
                    } else {
                        results[i] = 0; // FAIL
                    }
                }

                String message =
                    jobId + ":" + i + ":" + (results[i] == 2 ? "PASS" : "FAIL");
                getRedis().convertAndSend("job_updates", message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }
}
