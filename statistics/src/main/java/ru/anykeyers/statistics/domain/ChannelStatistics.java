package ru.anykeyers.statistics.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Промежуточная статистика аккаунта
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelStatistics {

    /**
     * Количество просмотров канала
     */
    private AtomicInteger countWatches = new AtomicInteger(0);

    /**
     * Количество подписчиков
     */
    private AtomicInteger subscriberCount = new AtomicInteger(0);

    /**
     * Добавить подписчика
     */
    public void addSubscriber() {
        subscriberCount.incrementAndGet();
    }

    /**
     * Добавить просмотр канала
     */
    public void addCountWatches() {
        countWatches.incrementAndGet();
    }

}
