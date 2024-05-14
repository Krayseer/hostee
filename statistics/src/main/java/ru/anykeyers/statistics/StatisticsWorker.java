package ru.anykeyers.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.anykeyers.statistics.processor.BatchProcessor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Воркер, обрабатывающий все пакетные сервисы
 */
@Component
@RequiredArgsConstructor
public class StatisticsWorker {

    /**
     * Время задержки в секундах
     */
    private static final int DELAY_IN_SECONDS = 24 * 60 * 60;

    /**
     * Список пакетных обработчиков приложения
     */
    private final List<BatchProcessor> batchProcessors;

    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    /**
     * Сохранить статистику из всех пакетных обработчиков
     */
    @Scheduled(fixedDelay = DELAY_IN_SECONDS)
    public void saveStatistics() {
        for (BatchProcessor batchProcessor : batchProcessors) {
            executorService.execute(batchProcessor.process());
        }
    }

}
