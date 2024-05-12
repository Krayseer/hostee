package ru.anykeyers.videoservice.service.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anykeyers.videoservice.domain.Channel;
import ru.anykeyers.videoservice.domain.User;
import ru.anykeyers.videoservice.domain.dto.CreateChannelDTO;
import ru.anykeyers.videoservice.factory.ChannelFactory;
import ru.anykeyers.videoservice.factory.UserFactory;

/**
 * Тесты для фабрики {@link ChannelFactory}
 */
@ExtendWith(MockitoExtension.class)
public class ChannelFactoryTest {

    @Mock
    private UserFactory userFactory;

    @InjectMocks
    private ChannelFactory channelFactory;

    /**
     * Тест создания канала из DTO объекта
     */
    @Test
    public void createChannelFromDTOTest() {
        CreateChannelDTO createChannelDTO = new CreateChannelDTO();
        createChannelDTO.setName("Test Channel");
        createChannelDTO.setDescription("Description");

        User user = new User();
        user.setUsername("testUser");

        Channel channel = channelFactory.createChannelFromDTO(createChannelDTO, user);

        Assertions.assertNotNull(channel);
        Assertions.assertEquals("Test Channel", channel.getName());
        Assertions.assertEquals(user, channel.getUser());
        Assertions.assertEquals("Description", channel.getDescription());
    }
}
