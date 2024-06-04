package ru.anykeyers.videoservice.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anykeyers.videoservice.domain.channel.Channel;
import ru.anykeyers.videoservice.domain.channel.ChannelMapper;
import ru.anykeyers.videoservice.domain.user.User;
import ru.anykeyers.videoservice.domain.channel.ChannelRequest;
import ru.anykeyers.videoservice.domain.user.UserMapper;

/**
 * Тесты для фабрики {@link ChannelMapper}
 */
@ExtendWith(MockitoExtension.class)
public class ChannelMapperTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private ChannelMapper channelMapper;

    /**
     * Тест создания канала из DTO объекта
     */
    @Test
    public void createChannelTest() {
        ChannelRequest channelRequest = new ChannelRequest();
        channelRequest.setName("Test Channel");
        channelRequest.setDescription("Description");

        User user = new User();
        user.setUsername("testUser");

        Channel channel = channelMapper.createChannel(channelRequest, user);

        Assertions.assertNotNull(channel);
        Assertions.assertEquals("Test Channel", channel.getName());
        Assertions.assertEquals(user, channel.getUser());
        Assertions.assertEquals("Description", channel.getDescription());
    }
}
