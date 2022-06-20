package com.algorithm.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TimerService {
    @Async
    public void timer(int limit, Process process) {
        try {
            System.out.println("run");
            Thread.sleep((long) limit);
            System.out.println("stop");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (process.isAlive())
            process.destroy();
    }
}
