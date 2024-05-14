package ru.anykeyers.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.statistics.domain.entity.Channel;

/**
 * DAO для работы со статистикой каналов
 */
@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
}
