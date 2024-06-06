package ru.anykeyers.statistics.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.stereotype.Service;
import ru.anykeyers.statistics.domain.entity.Channel;
import ru.anykeyers.statistics.domain.entity.Video;
import ru.anykeyers.statistics.repository.ChannelRepository;
import ru.anykeyers.statistics.repository.VideoRepository;
import ru.krayseer.domain.ChannelDTO;
import ru.krayseer.domain.statistics.ChannelStatisticsDTO;
import ru.krayseer.domain.statistics.VideoStatisticsDTO;
import ru.krayseer.service.RemoteChannelService;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Сервис получения статистики
 */
@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final VideoRepository videoRepository;

    private final ChannelRepository channelRepository;

    private final RemoteChannelService remoteChannelService;

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
     * @param videoId идентификатор видео
     */
    public VideoStatisticsDTO getVideoStatistics(String videoId) {
        Video video = videoRepository.findByVideoId(Long.valueOf(videoId));
        if (video == null) {
            return null;
        }
        return new VideoStatisticsDTO(Long.valueOf(videoId), video.getCountWatches());
    }

    /**
     * Получить статистику списка видео
     *
     * @param videoIds идентификаторы видео
     */
    public List<VideoStatisticsDTO> getVideoStatistics(String[] videoIds) {
        return Arrays.stream(videoIds)
                .map(this::getVideoStatistics)
                .filter(Objects::nonNull)
                .toList();
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
        ChannelDTO channelInfo = remoteChannelService.getChannel(String.valueOf(channel.getChannelId()));

        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Font font = getFont();

        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Заголовок
        Paragraph title = new Paragraph("СТАТИСТИКА КАНАЛА", font);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(15);

        // Таблица информации об аккаунте
        PdfPTable channelTable = new PdfPTable(2);
        channelTable.setWidthPercentage(100);
        channelTable.setSpacingAfter(15);

        // Информация о канале
        PdfPCell aboutChannelCell = new PdfPCell();
        aboutChannelCell.setBorder(Rectangle.NO_BORDER);
        aboutChannelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        aboutChannelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        aboutChannelCell.addElement(new Paragraph("Название канала: " + channelInfo.getName(), font));
        aboutChannelCell.addElement(new Paragraph("Описание канала: " + channelInfo.getDescription(), font));

        // Фотография канала
        Image image = Image.getInstance(new URI(channelInfo.getPhotoUrl()).toURL());
        image.scaleToFit(200, 200);
        PdfPCell imageCell = new PdfPCell(image);
        imageCell.setBorder(Rectangle.NO_BORDER);
        imageCell.setHorizontalAlignment(Element.ALIGN_LEFT);

        channelTable.addCell(imageCell);
        channelTable.addCell(aboutChannelCell);

        // Создание таблицы для статистики
        PdfPTable statsTable = getMetricsTable(
                new Phrase("Просмотры", font),
                new Phrase(String.valueOf(channel.getCountWatches()), font),
                new Phrase("Подписчики", font),
                new Phrase(String.valueOf(channel.getSubscribersCount()), font)
        );

        addElementsInDocument(document, title, channelTable, statsTable);

        return outputStream;
    }

    @SneakyThrows
    private void addElementsInDocument(Document document, Element... elements) {
        for (Element element : elements) {
            document.add(element);
        }
        document.close();
    }

    @SneakyThrows
    private Font getFont() {
        String fontPath = "/font/font-rus.ttf";
        byte[] fontBytes = IOUtils.toByteArray(Objects.requireNonNull(getClass().getResourceAsStream(fontPath)));
        BaseFont baseFont = BaseFont.createFont(
                "font-rus.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, fontBytes, null
        );
        return new Font(baseFont, 16, Font.NORMAL, BaseColor.BLACK);
    }

    private PdfPTable getMetricsTable(Phrase... phrases) {
        PdfPTable statsTable = new PdfPTable(2);
        statsTable.setWidthPercentage(100);
        for (Phrase phrase : phrases) {
            PdfPCell cell = new PdfPCell(phrase);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setFixedHeight(30);
            statsTable.addCell(cell);
        }
        return statsTable;
    }

}

