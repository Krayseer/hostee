package ru.anykeyers.videoservice.factory;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.video.Video;
import ru.anykeyers.videoservice.domain.video.VideoRequest;
import ru.anykeyers.videoservice.domain.video.VideoMapper;

/**
 * Тесты для класса {@link VideoMapper}
 */
public class VideoMapperTest {

    /**
     * Тест метода создания видео на основе данных из DTO
     */
    @Test
    public void testCreateVideo() {
        VideoRequest videoRequest = new VideoRequest();
        videoRequest.setName("Test Video");
        videoRequest.setDescription("Description of Test Video");
        Channel channel = new Channel();

        Video video = VideoMapper.createVideo(videoRequest, channel);

        Assertions.assertEquals(videoRequest.getName(), video.getName());
        Assertions.assertEquals(videoRequest.getDescription(), video.getDescription());
        Assertions.assertEquals(channel, video.getChannel());
    }

}
