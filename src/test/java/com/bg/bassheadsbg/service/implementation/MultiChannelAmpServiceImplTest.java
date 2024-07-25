package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.DeviceAlreadyExistsException;
import com.bg.bassheadsbg.exception.DeviceAlreadyLikedException;
import com.bg.bassheadsbg.exception.DeviceNotFoundException;
import com.bg.bassheadsbg.exception.UserNotAuthenticatedException;
import com.bg.bassheadsbg.kafka.ImageProducer;
import com.bg.bassheadsbg.messages.ExceptionMessages;
import com.bg.bassheadsbg.model.dto.add.AddMultiChannelAmpDTO;
import com.bg.bassheadsbg.model.dto.details.ImageListDetailsDTO;
import com.bg.bassheadsbg.model.dto.details.MultiChannelAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MultiChannelAmpSummaryDTO;
import com.bg.bassheadsbg.model.entity.amplifiers.MultiChannelAmplifier;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.helpers.MultiChannelAmpDetailsHelperDTO;
import com.bg.bassheadsbg.repository.MultiChannelAmplifierRepository;
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
public class MultiChannelAmpServiceImplTest {

    @Mock
    private MultiChannelAmplifierRepository repository;

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
    private MultiChannelAmpServiceImpl multiChannelAmpService;

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
    void testCreateNewAddMultiChannelAmplifierDTO() {
        AddMultiChannelAmpDTO result = multiChannelAmpService.createNewAddMultiChannelAmpDTO();

        assertNotNull(result, "net null");
        assertTrue(true, "Should return new AddMultiChannelAmplifierDTO()");
    }

    @Test
    void testAddDevice_userNotAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(null);

        AddMultiChannelAmpDTO addMultiChannelAmpDTO = new AddMultiChannelAmpDTO();

        UserNotAuthenticatedException exception = assertThrows(UserNotAuthenticatedException.class, () -> multiChannelAmpService.addDevice(addMultiChannelAmpDTO));

        assertEquals(ExceptionMessages.USER_NOT_AUTH, exception.getMessage());
    }

    @Test
    public void testAddDevice_Success() throws JsonProcessingException {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("testuser");

        AddMultiChannelAmpDTO addMultiChannelAmpDTO = new AddMultiChannelAmpDTO();
        addMultiChannelAmpDTO.setBrand("TestBrand");
        addMultiChannelAmpDTO.setModel("TestModel");

        MultiChannelAmplifier multiChannelAmp = new MultiChannelAmplifier();
        multiChannelAmp.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testuser");
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(modelMapper.map(addMultiChannelAmpDTO, MultiChannelAmplifier.class)).thenReturn(multiChannelAmp);
        when(repository.save(multiChannelAmp)).thenReturn(multiChannelAmp);

        long deviceId = multiChannelAmpService.addDevice(addMultiChannelAmpDTO);

        assertEquals(1L, deviceId);
        verify(repository, times(1)).save(multiChannelAmp);
        verify(imageProducer, times(1)).sendMessage(any(ImageListDetailsDTO.class));
    }

    @Test
    void testGetDeviceDetails_deviceExists() {
        MultiChannelAmplifier multiChannelAmp = new MultiChannelAmplifier();
        multiChannelAmp.setId(1L);
        MultiChannelAmpDetailsDTO expectedDetails = new MultiChannelAmpDetailsDTO();
        expectedDetails.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(multiChannelAmp));
        when(modelMapper.map(multiChannelAmp, MultiChannelAmpDetailsDTO.class)).thenReturn(expectedDetails);

        MultiChannelAmpDetailsDTO result = multiChannelAmpService.getDeviceDetails(1L);

        assertEquals(expectedDetails, result);
    }

    @Test
    void testGetDeviceDetails_deviceNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        DeviceNotFoundException exception = assertThrows(DeviceNotFoundException.class, () -> multiChannelAmpService.getDeviceDetails(1L));

        assertEquals("Device with id 1 not found!", exception.getMessage());
    }

    @Test
    void testEditDevice_withValidData() throws JsonProcessingException {
        AddMultiChannelAmpDTO addMultiChannelAmpDTO = new AddMultiChannelAmpDTO();
        addMultiChannelAmpDTO.setBrand("TestBrand");
        addMultiChannelAmpDTO.setModel("TestModel");
        MultiChannelAmplifier multiChannelAmp = new MultiChannelAmplifier();
        multiChannelAmp.setId(1L);
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(modelMapper.map(addMultiChannelAmpDTO, MultiChannelAmplifier.class)).thenReturn(multiChannelAmp);
        when(repository.save(any(MultiChannelAmplifier.class))).thenReturn(multiChannelAmp);

        long result = multiChannelAmpService.editDevice(addMultiChannelAmpDTO);

        assertEquals(1L, result);
        verify(repository, times(1)).save(any(MultiChannelAmplifier.class));
    }

    @Test
    void testEditDevice_userNotAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(null);

        AddMultiChannelAmpDTO addMultiChannelAmpDTO = new AddMultiChannelAmpDTO();

        UserNotAuthenticatedException exception = assertThrows(UserNotAuthenticatedException.class, () -> multiChannelAmpService.editDevice(addMultiChannelAmpDTO));

        assertEquals(ExceptionMessages.USER_NOT_AUTH, exception.getMessage());
    }

    @Test
    void testDeleteDevice_withValidData() {
        MultiChannelAmplifier multiChannelAmp = new MultiChannelAmplifier();
        multiChannelAmp.setId(1L);
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(repository.findById(1L)).thenReturn(Optional.of(multiChannelAmp));

        multiChannelAmpService.deleteDevice(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteDevice_userNotAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(null);

        UserNotAuthenticatedException exception = assertThrows(UserNotAuthenticatedException.class, () -> multiChannelAmpService.deleteDevice(1L));

        assertEquals(ExceptionMessages.USER_NOT_AUTH, exception.getMessage());
    }

    @Test
    void testLikeDevice_withValidData() {
        MultiChannelAmplifier multiChannelAmp = new MultiChannelAmplifier();
        multiChannelAmp.setId(1L);
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(repository.findById(1L)).thenReturn(Optional.of(multiChannelAmp));

        multiChannelAmpService.likeDevice(1L);

        verify(repository, times(1)).save(any(MultiChannelAmplifier.class));
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

        DeviceNotFoundException exception = assertThrows(DeviceNotFoundException.class, () -> multiChannelAmpService.likeDevice(1L));
        assertEquals("Device with id 1 not found!", exception.getMessage());
    }

    @Test
    void testLikeDevice_userNotAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(null);

        UserNotAuthenticatedException exception = assertThrows(UserNotAuthenticatedException.class, () -> multiChannelAmpService.likeDevice(1L));

        assertEquals(ExceptionMessages.USER_NOT_AUTH, exception.getMessage());
    }

    @Test
    void testUpdateDeviceImageUrls_withValidData() {
        String oldUrl = "http://oldurl.com/image1.jpg";
        String newUrl = "http://newurl.com/image1.jpg";

        MultiChannelAmplifier multiChannelAmplifier1 = new MultiChannelAmplifier();
        multiChannelAmplifier1.setId(1L);
        multiChannelAmplifier1.setImages(new ArrayList<>(Arrays.asList("http://oldurl.com/image1.jpg", "http://oldurl.com/image2.jpg")));

        MultiChannelAmplifier multiChannelAmplifier2 = new MultiChannelAmplifier();
        multiChannelAmplifier2.setId(2L);
        multiChannelAmplifier2.setImages(new ArrayList<>(Arrays.asList("http://oldurl.com/image1.jpg", "http://differenturl.com/image3.jpg")));

        List<MultiChannelAmplifier> multiChannelAmplifiers = Arrays.asList(multiChannelAmplifier1, multiChannelAmplifier2);

        when(repository.findByImagesContaining(oldUrl)).thenReturn(multiChannelAmplifiers);

        multiChannelAmpService.updateDeviceImageUrls(oldUrl, newUrl);

        assertEquals(Arrays.asList("http://newurl.com/image1.jpg", "http://oldurl.com/image2.jpg"), multiChannelAmplifier1.getImages());
        assertEquals(Arrays.asList("http://newurl.com/image1.jpg", "http://differenturl.com/image3.jpg"), multiChannelAmplifier2.getImages());

        verify(repository, times(1)).save(multiChannelAmplifier1);
        verify(repository, times(1)).save(multiChannelAmplifier2);
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

        MultiChannelAmplifier multiChannelAmplifier1 = new MultiChannelAmplifier();
        multiChannelAmplifier1.setId(1L);
        multiChannelAmplifier1.setUserLikes(likes1);
        multiChannelAmplifier1.setBrand("mtm");
        multiChannelAmplifier1.setModel("nottruemodel");

        MultiChannelAmplifier multiChannelAmplifier2 = new MultiChannelAmplifier();
        multiChannelAmplifier2.setId(2L);
        multiChannelAmplifier2.setUserLikes(likes2);
        multiChannelAmplifier2.setBrand("mtm2");
        multiChannelAmplifier2.setModel("nqmatakuv");

        MultiChannelAmplifier multiChannelAmplifier3 = new MultiChannelAmplifier();
        multiChannelAmplifier3.setId(3L);
        multiChannelAmplifier3.setUserLikes(likes3);
        multiChannelAmplifier3.setBrand("mtm");
        multiChannelAmplifier3.setModel("nottruemodel");

        List<MultiChannelAmplifier> multiChannelAmplifiers = Arrays.asList(multiChannelAmplifier1, multiChannelAmplifier2, multiChannelAmplifier3);

        MultiChannelAmpSummaryDTO summaryDTO1 = new MultiChannelAmpSummaryDTO();
        summaryDTO1.setBrand("mtm");
        summaryDTO1.setModel("nottruemodel");

        MultiChannelAmpSummaryDTO summaryDTO2 = new MultiChannelAmpSummaryDTO();
        summaryDTO2.setBrand("mtm2");
        summaryDTO2.setModel("ModelB");

        MultiChannelAmpSummaryDTO summaryDTO3 = new MultiChannelAmpSummaryDTO();
        summaryDTO3.setBrand("mtm");
        summaryDTO3.setModel("nottruemodel");

        when(repository.findAll()).thenReturn(multiChannelAmplifiers);
        when(modelMapper.map(multiChannelAmplifier1, MultiChannelAmpSummaryDTO.class)).thenReturn(summaryDTO1);
        when(modelMapper.map(multiChannelAmplifier2, MultiChannelAmpSummaryDTO.class)).thenReturn(summaryDTO2);
        when(modelMapper.map(multiChannelAmplifier3, MultiChannelAmpSummaryDTO.class)).thenReturn(summaryDTO3);

        List<MultiChannelAmpSummaryDTO> result = multiChannelAmpService.getAllDeviceSummary();

        assertTrue(result.contains(summaryDTO1), "Result should contain summaryDTO1");
        assertTrue(result.contains(summaryDTO2), "Result should contain summaryDTO2");
        assertTrue(result.contains(summaryDTO3), "Result should contain summaryDTO3");

        verify(repository, times(1)).findAll();
    }

    @Test
    void testAddDevice_deviceAlreadyExists() {
        AddMultiChannelAmpDTO addMultiChannelAmpDTO = new AddMultiChannelAmpDTO();
        addMultiChannelAmpDTO.setBrand("TestBrand");
        addMultiChannelAmpDTO.setModel("TestModel");

        MultiChannelAmplifier existingMultiChannelAmp = new MultiChannelAmplifier();
        existingMultiChannelAmp.setId(2L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(new UserEntity()));
        when(repository.findByBrandAndModel("TestBrand", "TestModel")).thenReturn(Optional.of(existingMultiChannelAmp));

        when(messageSource.getMessage(
                ExceptionMessages.DEVICE_ALREADY_EXISTS,
                null,
                LocaleContextHolder.getLocale()
        )).thenReturn("Device already exists");

        DeviceAlreadyExistsException exception = assertThrows(DeviceAlreadyExistsException.class, () -> multiChannelAmpService.addDevice(addMultiChannelAmpDTO));
        assertEquals("Device already exists", exception.getMessage());

        verify(repository, never()).save(any(MultiChannelAmplifier.class));
    }

    @Test
    void testLikeDevice_alreadyLiked() {
        MultiChannelAmplifier multiChannelAmp = new MultiChannelAmplifier();
        multiChannelAmp.setId(1L);

        UserEntity user = new UserEntity();
        user.setId(1L);

        List<UserEntity> userLikes = new ArrayList<>();
        userLikes.add(user);
        multiChannelAmp.setUserLikes(userLikes);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(repository.findById(1L)).thenReturn(Optional.of(multiChannelAmp));

        when(messageSource.getMessage(
                ExceptionMessages.DEVICE_ALREADY_LIKED,
                null,
                LocaleContextHolder.getLocale()
        )).thenReturn("Device already liked");

        DeviceAlreadyLikedException exception = assertThrows(DeviceAlreadyLikedException.class, () -> multiChannelAmpService.likeDevice(1L));
        assertEquals("Device already liked", exception.getMessage());
    }

    @Test
    void testGetDeviceDetailsHelper() {
        Long deviceId = 1L;
        MultiChannelAmplifier mockAmp = new MultiChannelAmplifier();
        MultiChannelAmpDetailsDTO mockDetailsDTO = new MultiChannelAmpDetailsDTO();
        MultiChannelAmpDetailsHelperDTO expectedHelperDTO = new MultiChannelAmpDetailsHelperDTO(mockDetailsDTO);

        when(repository.findById(deviceId)).thenReturn(Optional.of(mockAmp));
        when(modelMapper.map(mockAmp, MultiChannelAmpDetailsDTO.class)).thenReturn(mockDetailsDTO);
        when(exRateService.allSupportedCurrencies()).thenReturn(mockDetailsDTO.getAllCurrencies());

        MultiChannelAmpDetailsHelperDTO actualHelperDTO = multiChannelAmpService.getDeviceDetailsHelper(deviceId);

        assertEquals(expectedHelperDTO.formattedCurrentDraw(), actualHelperDTO.formattedCurrentDraw(), "Current draw should match.");
        assertEquals(expectedHelperDTO.formattedDistortion(), actualHelperDTO.formattedDistortion(), "Distortion should match.");
        assertEquals(expectedHelperDTO.formattedFuseRating(), actualHelperDTO.formattedFuseRating(), "Fuse Rating should match.");
    }

    @Test
    void testGetDeviceDetailsHelper_DeviceNotFound() {
        Long deviceId = 1L;
        when(repository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> multiChannelAmpService.getDeviceDetailsHelper(deviceId));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void testAddDevice_UserNotAuthenticated() {
        AddMultiChannelAmpDTO addDeviceDTO = new AddMultiChannelAmpDTO();
        addDeviceDTO.setImages(Arrays.asList("http://testimages.com/image1.jpg", "http://testimages.com/image2.jpg"));

        assertThrows(UserNotAuthenticatedException.class, () -> multiChannelAmpService.addDevice(addDeviceDTO));
    }

    @Test
    void testCreateImageListDetailsDTO() throws Exception {
        AddMultiChannelAmpDTO addDeviceDTO = new AddMultiChannelAmpDTO();
        addDeviceDTO.setImages(Arrays.asList("image1.jpg", "image2.jpg"));

        Method method = MultiChannelAmpServiceImpl.class.getDeclaredMethod("createImageListDetailsDTO", AddMultiChannelAmpDTO.class);
        method.setAccessible(true);

        method.invoke(multiChannelAmpService, addDeviceDTO);

        ArgumentCaptor<ImageListDetailsDTO> imageListDetailsDTOCaptor = ArgumentCaptor.forClass(ImageListDetailsDTO.class);
        verify(imageProducer).sendMessage(imageListDetailsDTOCaptor.capture());

        ImageListDetailsDTO capturedDTO = imageListDetailsDTOCaptor.getValue();
        assertEquals(addDeviceDTO.getImages(), capturedDTO.getImageUrls());
        assertEquals("multi_channel_amplifier_images", capturedDTO.getTableName());
    }
}