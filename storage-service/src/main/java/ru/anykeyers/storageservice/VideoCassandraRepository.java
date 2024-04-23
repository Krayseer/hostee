package ru.anykeyers.storageservice;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import ru.anykeyers.storageservice.domain.Video;

/**
 * DAO для работы с таблицей видеороликов в Cassandra
 */
@Repository
public interface VideoCassandraRepository extends CassandraRepository<Video, String> {
}
