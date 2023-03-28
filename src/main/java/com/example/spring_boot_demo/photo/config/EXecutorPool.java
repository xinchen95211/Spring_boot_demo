package com.example.spring_boot_demo.photo.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EXecutorPool {
    private static ExecutorService executorService = Executors.newFixedThreadPool(12);

    public static ExecutorService getExecutorService() {
        return executorService;

    }

}
