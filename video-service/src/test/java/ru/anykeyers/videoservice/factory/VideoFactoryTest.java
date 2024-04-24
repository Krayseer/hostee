package ru.anykeyers.videoservice.factory;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.anykeyers.videoservice.domain.Channel;
import ru.anykeyers.videoservice.domain.Video;
import ru.anykeyers.videoservice.domain.dto.UploadVideoDTO;

/**
 * Тесты для класса {@link VideoFactory}
 */
public class VideoFactoryTest {

    /**
     * Тест метода создания видео на основе данных из DTO
     */
    @Test
    public void testCreateVideoFromDto() {
        UploadVideoDTO uploadVideoDTO = new UploadVideoDTO();
        uploadVideoDTO.setName("Test Video");
        uploadVideoDTO.setDescription("Description of Test Video");
        Channel channel = new Channel();

        VideoFactory videoFactory = new VideoFactory();
        Video video = videoFactory.createVideoFromDto(uploadVideoDTO, channel);

        Assertions.assertEquals(uploadVideoDTO.getName(), video.getName());
        Assertions.assertEquals(uploadVideoDTO.getDescription(), video.getDescription());
        Assertions.assertEquals(channel, video.getChannel());
    }

}
