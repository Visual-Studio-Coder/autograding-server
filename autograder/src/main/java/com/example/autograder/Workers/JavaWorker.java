package com.example.autograder.Workers;

import org.springframework.stereotype.Component;

@Component
public class JavaWorker extends GradingWorker {

    public JavaWorker() {
        super("queue_java"); // Listen to Java queue
    }

    @Override
    protected Integer[] runCode(String filePath) {
        Integer[] results = new Integer[10];

        return results;
    }
}
