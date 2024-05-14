package ru.anykeyers.statistics.processor;

/**
 * Пакетный обработчик данных
 */
public interface BatchProcessor {

    /**
     * Обработать пакет
     */
    Runnable process();

}
