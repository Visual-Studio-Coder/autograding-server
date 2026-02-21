package com.example.autograder.Workers;

import com.example.autograder.JobItems.*;
import jakarta.annotation.PostConstruct;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

public abstract class GradingWorker {

    private String queueName;

    public GradingWorker(String queueName) {
        this.queueName = queueName;
    }

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private JobRepository jobRepository;

    public void startGradingLoop() {
        new Thread(() -> {
            while (true) {
                String jobId = redis
                    .opsForList()
                    .leftPop(queueName, Duration.ofSeconds(10));
                if (jobId != null) {
                    Job job = jobRepository
                        .findById(Integer.parseInt(jobId))
                        .orElseThrow();

                    job.setStatus("GRADING");
                    job.setTestcases(runCode(job.getFile()));

                    jobRepository.save(job);
                }
            }
        })
            .start();
    }

    @PostConstruct
    public void init() {
        this.startGradingLoop();
    }

    protected abstract Integer[] runCode(String filePath);
}
