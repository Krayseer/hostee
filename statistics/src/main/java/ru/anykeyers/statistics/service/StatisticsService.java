package ru.anykeyers.statistics.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.anykeyers.statistics.domain.entity.Channel;
import ru.anykeyers.statistics.domain.entity.Video;
import ru.anykeyers.statistics.repository.ChannelRepository;
import ru.anykeyers.statistics.repository.VideoRepository;
import ru.krayseer.domain.dto.statistics.ChannelStatisticsDTO;
import ru.krayseer.domain.dto.statistics.VideoStatisticsDTO;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Сервис получения статистики
 */
@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final ChannelRepository channelRepository;

    private final VideoRepository videoRepository;

    /**
     * Получить статистику канала
     *
     * @param channelId идентификатор канала
     */
    public ChannelStatisticsDTO getChannelStatistics(Long channelId) {
        Channel channel = channelRepository.findById(channelId).orElseThrow(
                () -> new RuntimeException("Channel not found")
        );
        return new ChannelStatisticsDTO(channelId, channel.getCountWatches(), channel.getSubscribersCount());
    }

    /**
     * Получить статистику видео
     *
     * @param videoUuid идентификатор видео
     */
    public VideoStatisticsDTO getVideoStatistics(String videoUuid) {
        Video video = videoRepository.findByUuid(videoUuid);
        return new VideoStatisticsDTO(videoUuid, video.getCountWatches());
    }

    /**
     * Получить статистику списка видео
     *
     * @param videosUuid идентификаторы видео
     */
    public List<VideoStatisticsDTO> getVideoStatistics(String[] videosUuid) {
        return Arrays.stream(videosUuid).map(this::getVideoStatistics).toList();
    }

    /**
     * Получить PDF файл со статистикой канала
     *
     * @param channelId идентификатор канала
     */
    @SneakyThrows
    public ByteArrayOutputStream getChannelStatisticsPdf(Long channelId) {
        Channel channel = channelRepository.findById(channelId).orElseThrow(
                () -> new RuntimeException("Channel not found")
        );
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        document.add(new Paragraph("Count watches: " + channel.getCountWatches()));
        document.add(new Paragraph("Count subscribers: " + channel.getSubscribersCount()));
        document.close();
        return outputStream;
    }

}

