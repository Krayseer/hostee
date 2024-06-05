package ru.anykeyers.storageservice;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import ru.anykeyers.storageservice.domain.VideoChunk;

import java.util.UUID;

/**
 * DAO для работы с чанками видеороликов
 */
@Repository
public interface VideoChunkRepository extends CassandraRepository<VideoChunk, UUID> {

    @Query("SELECT * FROM video_chunk WHERE video_id = ?0 AND chunk_index >= ?1 ORDER BY chunk_index ASC")
    Slice<VideoChunk> findByVideoIdAndChunkIndexGreaterThanEqualOrderByChunkIndexAsc(UUID videoId, int chunkIndex, Pageable pageable);

}
