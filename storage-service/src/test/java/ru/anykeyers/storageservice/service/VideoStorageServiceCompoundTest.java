package ru.anykeyers.storageservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import ru.anykeyers.storageservice.service.impl.LocalVideoStorageService;

/**
 * Тесты для {@link VideoStorageServiceCompound}
 */
@ExtendWith(MockitoExtension.class)
class VideoStorageServiceCompoundTest {

    @Mock
    private CacheStorageService cacheStorageService;

    @Mock
    private LocalVideoStorageService localVideoStorageService;

    @InjectMocks
    private VideoStorageServiceCompound videoStorageServiceCompound;

    /**
     * Тест успешного получения видеоролика из локального хранилища
     */
    @Test
    void getVideoFromLocalStorage() {
        String videoUuid = "uuid-test";
        Mockito.when(localVideoStorageService.getVideo(videoUuid)).thenReturn(Mockito.mock(Resource.class));
        videoStorageServiceCompound.getVideo(videoUuid);
        Mockito.verify(cacheStorageService, Mockito.never()).getVideo(videoUuid);
    }

    /**
     * Тест безуспешного получения видеоролика из локального хранилища и получение его из кэша
     */
    @Test
    void getVideoFromCacheStorage() {
        String videoUuid = "uuid-test";
        Mockito.when(localVideoStorageService.getVideo(videoUuid)).thenThrow(new RuntimeException());
        videoStorageServiceCompound.getVideo(videoUuid);
        Mockito.verify(cacheStorageService, Mockito.times(1)).getVideo(videoUuid);
    }

    /**
     * Тест сохранения видеоролика в локальное хранилище и кэш
     */
    @Test
    void saveVideo() {
        byte[] videoBytes = new byte[]{1, 2, 3};
        videoStorageServiceCompound.saveVideo(videoBytes);
        Mockito.verify(localVideoStorageService, Mockito.times(1)).saveVideo(Mockito.any());
        Mockito.verify(cacheStorageService, Mockito.times(1)).addFile(Mockito.any());
    }

}