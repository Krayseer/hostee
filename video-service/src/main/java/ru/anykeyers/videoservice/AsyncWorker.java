package ru.anykeyers.videoservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

/**
 * Асинхронный обработчик заданий
 */
@Component
@RequiredArgsConstructor
public class AsyncWorker {

    private final ExecutorService executorService;

    /**
     * Добавить задание на обработку
     */
    public void addTask(Runnable task) {
        executorService.execute(task);
    }

}
