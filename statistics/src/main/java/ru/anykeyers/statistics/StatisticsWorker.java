package ru.anykeyers.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.anykeyers.statistics.processor.BatchProcessor;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Воркер, обрабатывающий все пакетные сервисы
 */
@Component
@RequiredArgsConstructor
public class StatisticsWorker {

    /**
     * Время задержки в секундах
     */
    private static final int DELAY = 1000 * 60;

    private final ExecutorService executorService;

    /**
     * Список пакетных обработчиков приложения
     */
    private final List<BatchProcessor> batchProcessors;

    /**
     * Сохранить статистику из всех пакетных обработчиков
     */
    @Scheduled(fixedDelay = DELAY)
    public void saveStatistics() {
        for (BatchProcessor batchProcessor : batchProcessors) {
            executorService.execute(batchProcessor.process());
        }
    }

}
