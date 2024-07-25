package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.DeviceAlreadyExistsException;
import com.bg.bassheadsbg.exception.DeviceAlreadyLikedException;
import com.bg.bassheadsbg.exception.DeviceNotFoundException;
import com.bg.bassheadsbg.exception.UserNotAuthenticatedException;
import com.bg.bassheadsbg.kafka.ImageProducer;
import com.bg.bassheadsbg.messages.ExceptionMessages;
import com.bg.bassheadsbg.model.dto.add.AddMonoAmpDTO;
import com.bg.bassheadsbg.model.dto.details.ImageListDetailsDTO;
import com.bg.bassheadsbg.model.dto.details.MonoAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MonoAmpSummaryDTO;
import com.bg.bassheadsbg.model.entity.amplifiers.MonoAmplifier;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.helpers.MonoAmpDetailsHelperDTO;
import com.bg.bassheadsbg.repository.MonoAmplifierRepository;
import com.bg.bassheadsbg.repository.UserRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MonoAmpServiceImplTest {

    @Mock
    private MonoAmplifierRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ImageProducer imageProducer;

    @Mock
    private ExRateService exRateService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private MonoAmpServiceImpl monoAmpService;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        lenient().when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
    }

    @Test
    void testCreateNewAddMonoAmplifierDTO() {
        AddMonoAmpDTO result = monoAmpService.createNewAddMonoAmpDTO();

        assertNotNull(result, "net null");
        assertTrue(true, "Should return new AddMonoAmplifierDTO()");
    }

    @Test
    void testAddDevice_userNotAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(null);

        AddMonoAmpDTO addMonoAmplifierDTO = new AddMonoAmpDTO();

        UserNotAuthenticatedException exception = assertThrows(UserNotAuthenticatedException.class, () -> monoAmpService.addDevice(addMonoAmplifierDTO));

        assertEquals(ExceptionMessages.USER_NOT_AUTH, exception.getMessage());
    }

    @Test
    public void testAddDevice_Success() throws JsonProcessingException {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("testuser");

        AddMonoAmpDTO addMonoAmplifierDTO = new AddMonoAmpDTO();
        addMonoAmplifierDTO.setBrand("TestBrand");
        addMonoAmplifierDTO.setModel("TestModel");

        MonoAmplifier monoAmplifier = new MonoAmplifier();
        monoAmplifier.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testuser");
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(modelMapper.map(addMonoAmplifierDTO, MonoAmplifier.class)).thenReturn(monoAmplifier);
        when(repository.save(monoAmplifier)).thenReturn(monoAmplifier);

        long deviceId = monoAmpService.addDevice(addMonoAmplifierDTO);

        assertEquals(1L, deviceId);
        verify(repository, times(1)).save(monoAmplifier);
        verify(imageProducer, times(1)).sendMessage(any(ImageListDetailsDTO.class));
    }

    @Test
    void testGetDeviceDetails_deviceExists() {
        MonoAmplifier monoAmplifier = new MonoAmplifier();
        monoAmplifier.setId(1L);
        MonoAmpDetailsDTO expectedDetails = new MonoAmpDetailsDTO();
        expectedDetails.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(monoAmplifier));
        when(modelMapper.map(monoAmplifier, MonoAmpDetailsDTO.class)).thenReturn(expectedDetails);

        MonoAmpDetailsDTO result = monoAmpService.getDeviceDetails(1L);

        assertEquals(expectedDetails, result);
    }

    @Test
    void testGetDeviceDetails_deviceNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        DeviceNotFoundException exception = assertThrows(DeviceNotFoundException.class, () -> monoAmpService.getDeviceDetails(1L));

        assertEquals("Device with id 1 not found!", exception.getMessage());
    }

    @Test
    void testEditDevice_withValidData() throws JsonProcessingException {
        AddMonoAmpDTO addMonoAmplifierDTO = new AddMonoAmpDTO();
        addMonoAmplifierDTO.setBrand("TestBrand");
        addMonoAmplifierDTO.setModel("TestModel");
        MonoAmplifier monoAmplifier = new MonoAmplifier();
        monoAmplifier.setId(1L);
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(modelMapper.map(addMonoAmplifierDTO, MonoAmplifier.class)).thenReturn(monoAmplifier);
        when(repository.save(any(MonoAmplifier.class))).thenReturn(monoAmplifier);

        long result = monoAmpService.editDevice(addMonoAmplifierDTO);

        assertEquals(1L, result);
        verify(repository, times(1)).save(any(MonoAmplifier.class));
    }

    @Test
    void testEditDevice_userNotAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(null);

        AddMonoAmpDTO addMonoAmplifierDTO = new AddMonoAmpDTO();

        UserNotAuthenticatedException exception = assertThrows(UserNotAuthenticatedException.class, () -> monoAmpService.editDevice(addMonoAmplifierDTO));

        assertEquals(ExceptionMessages.USER_NOT_AUTH, exception.getMessage());
    }

    @Test
    void testDeleteDevice_withValidData() {
        MonoAmplifier monoAmplifier = new MonoAmplifier();
        monoAmplifier.setId(1L);
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(repository.findById(1L)).thenReturn(Optional.of(monoAmplifier));

        monoAmpService.deleteDevice(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteDevice_userNotAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(null);

        UserNotAuthenticatedException exception = assertThrows(UserNotAuthenticatedException.class, () -> monoAmpService.deleteDevice(1L));

        assertEquals(ExceptionMessages.USER_NOT_AUTH, exception.getMessage());
    }

    @Test
    void testLikeDevice_withValidData() {
        MonoAmplifier monoAmplifier = new MonoAmplifier();
        monoAmplifier.setId(1L);
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(repository.findById(1L)).thenReturn(Optional.of(monoAmplifier));

        monoAmpService.likeDevice(1L);

        verify(repository, times(1)).save(any(MonoAmplifier.class));
    }

    @Test
    void testLikeDevice_deviceNotFound() {
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(repository.findById(1L)).thenReturn(Optional.empty());

        DeviceNotFoundException exception = assertThrows(DeviceNotFoundException.class, () -> monoAmpService.likeDevice(1L));
        assertEquals("Device with id 1 not found!", exception.getMessage());
    }

    @Test
    void testLikeDevice_userNotAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(null);

        UserNotAuthenticatedException exception = assertThrows(UserNotAuthenticatedException.class, () -> monoAmpService.likeDevice(1L));

        assertEquals(ExceptionMessages.USER_NOT_AUTH, exception.getMessage());
    }

    @Test
    void testUpdateDeviceImageUrls_withValidData() {
        String oldUrl = "http://oldurl.com/image1.jpg";
        String newUrl = "http://newurl.com/image1.jpg";

        MonoAmplifier monoAmplifier1 = new MonoAmplifier();
        monoAmplifier1.setId(1L);
        monoAmplifier1.setImages(new ArrayList<>(Arrays.asList("http://oldurl.com/image1.jpg", "http://oldurl.com/image2.jpg")));

        MonoAmplifier monoAmplifier2 = new MonoAmplifier();
        monoAmplifier2.setId(2L);
        monoAmplifier2.setImages(new ArrayList<>(Arrays.asList("http://oldurl.com/image1.jpg", "http://differenturl.com/image3.jpg")));

        List<MonoAmplifier> monoAmplifiers = Arrays.asList(monoAmplifier1, monoAmplifier2);

        when(repository.findByImagesContaining(oldUrl)).thenReturn(monoAmplifiers);

        monoAmpService.updateDeviceImageUrls(oldUrl, newUrl);

        assertEquals(Arrays.asList("http://newurl.com/image1.jpg", "http://oldurl.com/image2.jpg"), monoAmplifier1.getImages());
        assertEquals(Arrays.asList("http://newurl.com/image1.jpg", "http://differenturl.com/image3.jpg"), monoAmplifier2.getImages());

        verify(repository, times(1)).save(monoAmplifier1);
        verify(repository, times(1)).save(monoAmplifier2);
    }

    @Test
    void testGetAllDeviceSummary() {
        UserEntity goshko = new UserEntity();
        goshko.setId(1L);
        goshko.setUsername("Gosho Kokosho");

        UserEntity bojko = new UserEntity();
        bojko.setId(2L);
        bojko.setUsername("Bojidar Mitrofanov");

        UserEntity mitq = new UserEntity();
        mitq.setId(3L);
        mitq.setUsername("Dimitrii M1tqta");

        List<UserEntity> likes1 = List.of(goshko);
        List<UserEntity> likes2 = List.of(bojko);
        List<UserEntity> likes3 = List.of(goshko, mitq);

        MonoAmplifier monoAmplifier1 = new MonoAmplifier();
        monoAmplifier1.setId(1L);
        monoAmplifier1.setUserLikes(likes1);
        monoAmplifier1.setBrand("mtm");
        monoAmplifier1.setModel("nottruemodel");

        MonoAmplifier monoAmplifier2 = new MonoAmplifier();
        monoAmplifier2.setId(2L);
        monoAmplifier2.setUserLikes(likes2);
        monoAmplifier2.setBrand("mtm2");
        monoAmplifier2.setModel("nqmatakuv");

        MonoAmplifier monoAmplifier3 = new MonoAmplifier();
        monoAmplifier3.setId(3L);
        monoAmplifier3.setUserLikes(likes3);
        monoAmplifier3.setBrand("mtm");
        monoAmplifier3.setModel("nottruemodel");

        List<MonoAmplifier> monoAmplifiers = Arrays.asList(monoAmplifier1, monoAmplifier2, monoAmplifier3);

        MonoAmpSummaryDTO summaryDTO1 = new MonoAmpSummaryDTO();
        summaryDTO1.setBrand("mtm");
        summaryDTO1.setModel("nottruemodel");

        MonoAmpSummaryDTO summaryDTO2 = new MonoAmpSummaryDTO();
        summaryDTO2.setBrand("mtm2");
        summaryDTO2.setModel("ModelB");

        MonoAmpSummaryDTO summaryDTO3 = new MonoAmpSummaryDTO();
        summaryDTO3.setBrand("mtm");
        summaryDTO3.setModel("nottruemodel");

        when(repository.findAll()).thenReturn(monoAmplifiers);
        when(modelMapper.map(monoAmplifier1, MonoAmpSummaryDTO.class)).thenReturn(summaryDTO1);
        when(modelMapper.map(monoAmplifier2, MonoAmpSummaryDTO.class)).thenReturn(summaryDTO2);
        when(modelMapper.map(monoAmplifier3, MonoAmpSummaryDTO.class)).thenReturn(summaryDTO3);

        List<MonoAmpSummaryDTO> result = monoAmpService.getAllDeviceSummary();

        assertTrue(result.contains(summaryDTO1), "Result should contain summaryDTO1");
        assertTrue(result.contains(summaryDTO2), "Result should contain summaryDTO2");
        assertTrue(result.contains(summaryDTO3), "Result should contain summaryDTO3");

        verify(repository, times(1)).findAll();
    }

    @Test
    void testAddDevice_deviceAlreadyExists() {
        AddMonoAmpDTO addMonoAmplifierDTO = new AddMonoAmpDTO();
        addMonoAmplifierDTO.setBrand("TestBrand");
        addMonoAmplifierDTO.setModel("TestModel");

        MonoAmplifier existingMonoAmplifier = new MonoAmplifier();
        existingMonoAmplifier.setId(2L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(new UserEntity()));
        when(repository.findByBrandAndModel("TestBrand", "TestModel")).thenReturn(Optional.of(existingMonoAmplifier));

        when(messageSource.getMessage(
                ExceptionMessages.DEVICE_ALREADY_EXISTS,
                null,
                LocaleContextHolder.getLocale()
        )).thenReturn("Device already exists");

        DeviceAlreadyExistsException exception = assertThrows(DeviceAlreadyExistsException.class, () -> monoAmpService.addDevice(addMonoAmplifierDTO));
        assertEquals("Device already exists", exception.getMessage());

        verify(repository, never()).save(any(MonoAmplifier.class));
    }

    @Test
    void testLikeDevice_alreadyLiked() {
        MonoAmplifier monoAmplifier = new MonoAmplifier();
        monoAmplifier.setId(1L);

        UserEntity user = new UserEntity();
        user.setId(1L);

        List<UserEntity> userLikes = new ArrayList<>();
        userLikes.add(user);
        monoAmplifier.setUserLikes(userLikes);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(repository.findById(1L)).thenReturn(Optional.of(monoAmplifier));

        when(messageSource.getMessage(
                ExceptionMessages.DEVICE_ALREADY_LIKED,
                null,
                LocaleContextHolder.getLocale()
        )).thenReturn("Device already liked");

        DeviceAlreadyLikedException exception = assertThrows(DeviceAlreadyLikedException.class, () -> monoAmpService.likeDevice(1L));
        assertEquals("Device already liked", exception.getMessage());
    }

    @Test
    void testGetDeviceDetailsHelper() {
        Long deviceId = 1L;
        MonoAmplifier mockMonoAmplifier = new MonoAmplifier();
        MonoAmpDetailsDTO mockDetailsDTO = new MonoAmpDetailsDTO();
        MonoAmpDetailsHelperDTO expectedHelperDTO = new MonoAmpDetailsHelperDTO(mockDetailsDTO);

        when(repository.findById(deviceId)).thenReturn(Optional.of(mockMonoAmplifier));
        when(modelMapper.map(mockMonoAmplifier, MonoAmpDetailsDTO.class)).thenReturn(mockDetailsDTO);
        when(exRateService.allSupportedCurrencies()).thenReturn(mockDetailsDTO.getAllCurrencies());

        MonoAmpDetailsHelperDTO actualHelperDTO = monoAmpService.getDeviceDetailsHelper(deviceId);

        assertEquals(expectedHelperDTO.formattedCurrentDraw(), actualHelperDTO.formattedCurrentDraw(), "Current draw should match.");
        assertEquals(expectedHelperDTO.formattedDistortion(), actualHelperDTO.formattedDistortion(), "Distortion should match.");
        assertEquals(expectedHelperDTO.formattedFuseRating(), actualHelperDTO.formattedFuseRating(), "Fuse Rating should match.");
    }

    @Test
    void testGetDeviceDetailsHelper_DeviceNotFound() {
        Long deviceId = 1L;
        when(repository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> monoAmpService.getDeviceDetailsHelper(deviceId));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void testAddDevice_UserNotAuthenticated() {
        AddMonoAmpDTO addDeviceDTO = new AddMonoAmpDTO();
        addDeviceDTO.setImages(Arrays.asList("http://testimages.com/image1.jpg", "http://testimages.com/image2.jpg"));

        assertThrows(UserNotAuthenticatedException.class, () -> monoAmpService.addDevice(addDeviceDTO));
    }

    @Test
    void testCreateImageListDetailsDTO() throws Exception {
        AddMonoAmpDTO addDeviceDTO = new AddMonoAmpDTO();
        addDeviceDTO.setImages(Arrays.asList("image1.jpg", "image2.jpg"));

        Method method = MonoAmpServiceImpl.class.getDeclaredMethod("createImageListDetailsDTO", AddMonoAmpDTO.class);
        method.setAccessible(true);

        method.invoke(monoAmpService, addDeviceDTO);

        ArgumentCaptor<ImageListDetailsDTO> imageListDetailsDTOCaptor = ArgumentCaptor.forClass(ImageListDetailsDTO.class);
        verify(imageProducer).sendMessage(imageListDetailsDTOCaptor.capture());

        ImageListDetailsDTO capturedDTO = imageListDetailsDTOCaptor.getValue();
        assertEquals(addDeviceDTO.getImages(), capturedDTO.getImageUrls());
        assertEquals("mono_amplifier_images", capturedDTO.getTableName());
    }
}