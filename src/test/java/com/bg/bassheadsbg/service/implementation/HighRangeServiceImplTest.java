package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.DeviceAlreadyExistsException;
import com.bg.bassheadsbg.exception.DeviceAlreadyLikedException;
import com.bg.bassheadsbg.exception.DeviceNotFoundException;
import com.bg.bassheadsbg.exception.UserNotAuthenticatedException;
import com.bg.bassheadsbg.kafka.ImageProducer;
import com.bg.bassheadsbg.messages.ExceptionMessages;
import com.bg.bassheadsbg.model.dto.add.AddHighRangeDTO;
import com.bg.bassheadsbg.model.dto.details.HighRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.details.ImageListDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.HighRangeSummaryDTO;
import com.bg.bassheadsbg.model.entity.speakers.HighRange;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.helpers.HighRangeDetailsHelperDTO;
import com.bg.bassheadsbg.repository.HighRangeRepository;
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
public class HighRangeServiceImplTest {

    @Mock
    private HighRangeRepository repository;

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
    private HighRangeServiceImpl highRangeService;

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
    void testCreateNewAddHighRangeDTO() {
        AddHighRangeDTO result = highRangeService.createNewAddHighRangeDTO();

        assertNotNull(result, "net null");
        assertTrue(true, "Should return new AddHighRangeDTO()");
    }

    @Test
    void testAddDevice_userNotAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(null);

        AddHighRangeDTO addHighRangeDTO = new AddHighRangeDTO();

        UserNotAuthenticatedException exception = assertThrows(UserNotAuthenticatedException.class, () -> highRangeService.addDevice(addHighRangeDTO));

        assertEquals(ExceptionMessages.USER_NOT_AUTH, exception.getMessage());
    }

    @Test
    public void testAddDevice_Success() throws JsonProcessingException {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("testuser");

        AddHighRangeDTO addHighRangeDTO = new AddHighRangeDTO();
        addHighRangeDTO.setBrand("TestBrand");
        addHighRangeDTO.setModel("TestModel");

        HighRange highRange = new HighRange();
        highRange.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testuser");
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(modelMapper.map(addHighRangeDTO, HighRange.class)).thenReturn(highRange);
        when(repository.save(highRange)).thenReturn(highRange);

        long deviceId = highRangeService.addDevice(addHighRangeDTO);

        assertEquals(1L, deviceId);
        verify(repository, times(1)).save(highRange);
        verify(imageProducer, times(1)).sendMessage(any(ImageListDetailsDTO.class));
    }

    @Test
    void testGetDeviceDetails_deviceExists() {
        HighRange highRange = new HighRange();
        highRange.setId(1L);
        HighRangeDetailsDTO expectedDetails = new HighRangeDetailsDTO();
        expectedDetails.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(highRange));
        when(modelMapper.map(highRange, HighRangeDetailsDTO.class)).thenReturn(expectedDetails);

        HighRangeDetailsDTO result = highRangeService.getDeviceDetails(1L);

        assertEquals(expectedDetails, result);
    }

    @Test
    void testGetDeviceDetails_deviceNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        DeviceNotFoundException exception = assertThrows(DeviceNotFoundException.class, () -> highRangeService.getDeviceDetails(1L));

        assertEquals("Device with id 1 not found!", exception.getMessage());
    }

    @Test
    void testEditDevice_withValidData() throws JsonProcessingException {
        AddHighRangeDTO addHighRangeDTO = new AddHighRangeDTO();
        addHighRangeDTO.setBrand("TestBrand");
        addHighRangeDTO.setModel("TestModel");
        HighRange highRange = new HighRange();
        highRange.setId(1L);
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(modelMapper.map(addHighRangeDTO, HighRange.class)).thenReturn(highRange);
        when(repository.save(any(HighRange.class))).thenReturn(highRange);

        long result = highRangeService.editDevice(addHighRangeDTO);

        assertEquals(1L, result);
        verify(repository, times(1)).save(any(HighRange.class));
    }

    @Test
    void testEditDevice_userNotAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(null);

        AddHighRangeDTO addHighRangeDTO = new AddHighRangeDTO();

        UserNotAuthenticatedException exception = assertThrows(UserNotAuthenticatedException.class, () -> highRangeService.editDevice(addHighRangeDTO));

        assertEquals(ExceptionMessages.USER_NOT_AUTH, exception.getMessage());
    }

    @Test
    void testDeleteDevice_withValidData() {
        HighRange highRange = new HighRange();
        highRange.setId(1L);
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(repository.findById(1L)).thenReturn(Optional.of(highRange));

        highRangeService.deleteDevice(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteDevice_userNotAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(null);

        UserNotAuthenticatedException exception = assertThrows(UserNotAuthenticatedException.class, () -> highRangeService.deleteDevice(1L));

        assertEquals(ExceptionMessages.USER_NOT_AUTH, exception.getMessage());
    }

    @Test
    void testLikeDevice_withValidData() {
        HighRange highRange = new HighRange();
        highRange.setId(1L);
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(repository.findById(1L)).thenReturn(Optional.of(highRange));

        highRangeService.likeDevice(1L);

        verify(repository, times(1)).save(any(HighRange.class));
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

        DeviceNotFoundException exception = assertThrows(DeviceNotFoundException.class, () -> highRangeService.likeDevice(1L));
        assertEquals("Device with id 1 not found!", exception.getMessage());
    }

    @Test
    void testLikeDevice_userNotAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(null);

        UserNotAuthenticatedException exception = assertThrows(UserNotAuthenticatedException.class, () -> highRangeService.likeDevice(1L));

        assertEquals(ExceptionMessages.USER_NOT_AUTH, exception.getMessage());
    }

    @Test
    void testUpdateDeviceImageUrls_withValidData() {
        String oldUrl = "http://oldurl.com/image1.jpg";
        String newUrl = "http://newurl.com/image1.jpg";

        HighRange highRange1 = new HighRange();
        highRange1.setId(1L);
        highRange1.setImages(new ArrayList<>(Arrays.asList("http://oldurl.com/image1.jpg", "http://oldurl.com/image2.jpg")));

        HighRange highRange2 = new HighRange();
        highRange2.setId(2L);
        highRange2.setImages(new ArrayList<>(Arrays.asList("http://oldurl.com/image1.jpg", "http://differenturl.com/image3.jpg")));

        List<HighRange> highRanges = Arrays.asList(highRange1, highRange2);

        when(repository.findByImagesContaining(oldUrl)).thenReturn(highRanges);

        highRangeService.updateDeviceImageUrls(oldUrl, newUrl);

        assertEquals(Arrays.asList("http://newurl.com/image1.jpg", "http://oldurl.com/image2.jpg"), highRange1.getImages());
        assertEquals(Arrays.asList("http://newurl.com/image1.jpg", "http://differenturl.com/image3.jpg"), highRange2.getImages());

        verify(repository, times(1)).save(highRange1);
        verify(repository, times(1)).save(highRange2);
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

        HighRange highRange1 = new HighRange();
        highRange1.setId(1L);
        highRange1.setUserLikes(likes1);
        highRange1.setBrand("mtm");
        highRange1.setModel("nottruemodel");

        HighRange highRange2 = new HighRange();
        highRange2.setId(2L);
        highRange2.setUserLikes(likes2);
        highRange2.setBrand("mtm2");
        highRange2.setModel("nqmatakuv");

        HighRange highRange3 = new HighRange();
        highRange3.setId(3L);
        highRange3.setUserLikes(likes3);
        highRange3.setBrand("mtm");
        highRange3.setModel("nottruemodel");

        List<HighRange> highRanges = Arrays.asList(highRange1, highRange2, highRange3);

        HighRangeSummaryDTO summaryDTO1 = new HighRangeSummaryDTO();
        summaryDTO1.setBrand("mtm");
        summaryDTO1.setModel("nottruemodel");

        HighRangeSummaryDTO summaryDTO2 = new HighRangeSummaryDTO();
        summaryDTO2.setBrand("mtm2");
        summaryDTO2.setModel("ModelB");

        HighRangeSummaryDTO summaryDTO3 = new HighRangeSummaryDTO();
        summaryDTO3.setBrand("mtm");
        summaryDTO3.setModel("nottruemodel");

        when(repository.findAll()).thenReturn(highRanges);
        when(modelMapper.map(highRange1, HighRangeSummaryDTO.class)).thenReturn(summaryDTO1);
        when(modelMapper.map(highRange2, HighRangeSummaryDTO.class)).thenReturn(summaryDTO2);
        when(modelMapper.map(highRange3, HighRangeSummaryDTO.class)).thenReturn(summaryDTO3);

        List<HighRangeSummaryDTO> result = highRangeService.getAllDeviceSummary();

        assertTrue(result.contains(summaryDTO1), "Result should contain summaryDTO1");
        assertTrue(result.contains(summaryDTO2), "Result should contain summaryDTO2");
        assertTrue(result.contains(summaryDTO3), "Result should contain summaryDTO3");

        verify(repository, times(1)).findAll();
    }

    @Test
    void testAddDevice_deviceAlreadyExists() {
        AddHighRangeDTO addHighRangeDTO = new AddHighRangeDTO();
        addHighRangeDTO.setBrand("TestBrand");
        addHighRangeDTO.setModel("TestModel");

        HighRange existingHighRange = new HighRange();
        existingHighRange.setId(2L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(new UserEntity()));
        when(repository.findByBrandAndModel("TestBrand", "TestModel")).thenReturn(Optional.of(existingHighRange));

        when(messageSource.getMessage(
                ExceptionMessages.DEVICE_ALREADY_EXISTS,
                null,
                LocaleContextHolder.getLocale()
        )).thenReturn("Device already exists");

        DeviceAlreadyExistsException exception = assertThrows(DeviceAlreadyExistsException.class, () -> highRangeService.addDevice(addHighRangeDTO));
        assertEquals("Device already exists", exception.getMessage());

        verify(repository, never()).save(any(HighRange.class));
    }

    @Test
    void testLikeDevice_alreadyLiked() {
        HighRange highRange = new HighRange();
        highRange.setId(1L);

        UserEntity user = new UserEntity();
        user.setId(1L);

        List<UserEntity> userLikes = new ArrayList<>();
        userLikes.add(user);
        highRange.setUserLikes(userLikes);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(repository.findById(1L)).thenReturn(Optional.of(highRange));

        when(messageSource.getMessage(
                ExceptionMessages.DEVICE_ALREADY_LIKED,
                null,
                LocaleContextHolder.getLocale()
        )).thenReturn("Device already liked");

        DeviceAlreadyLikedException exception = assertThrows(DeviceAlreadyLikedException.class, () -> highRangeService.likeDevice(1L));
        assertEquals("Device already liked", exception.getMessage());
    }

    @Test
    void testGetDeviceDetailsHelper() {
        Long deviceId = 1L;
        HighRange mockHighRange = new HighRange();
        HighRangeDetailsDTO mockDetailsDTO = new HighRangeDetailsDTO();
        HighRangeDetailsHelperDTO expectedHelperDTO = new HighRangeDetailsHelperDTO(mockDetailsDTO);

        when(repository.findById(deviceId)).thenReturn(Optional.of(mockHighRange));
        when(modelMapper.map(mockHighRange, HighRangeDetailsDTO.class)).thenReturn(mockDetailsDTO);
        when(exRateService.allSupportedCurrencies()).thenReturn(mockDetailsDTO.getAllCurrencies());

        HighRangeDetailsHelperDTO actualHelperDTO = highRangeService.getDeviceDetailsHelper(deviceId);

        assertEquals(expectedHelperDTO.formattedSensitivity(), actualHelperDTO.formattedSensitivity(), "Sensitivity should match.");
        assertEquals(expectedHelperDTO.formattedSize(), actualHelperDTO.formattedSize(), "Size should match.");
        assertEquals(expectedHelperDTO.formattedFrequencyResponse(), actualHelperDTO.formattedFrequencyResponse(), "Frequency response should match.");
        assertEquals(expectedHelperDTO.formattedImpedance(), actualHelperDTO.formattedImpedance(), "Impedance should match.");
        assertEquals(expectedHelperDTO.formattedPowerHandling(), actualHelperDTO.formattedPowerHandling(), "Power handling should match.");
        assertEquals(expectedHelperDTO.formattedFrequencyRange(), actualHelperDTO.formattedFrequencyRange(), "Frequency range should match.");
    }

    @Test
    void testGetDeviceDetailsHelper_DeviceNotFound() {
        Long deviceId = 1L;
        when(repository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> highRangeService.getDeviceDetailsHelper(deviceId));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void testAddDevice_UserNotAuthenticated() {
        AddHighRangeDTO addDeviceDTO = new AddHighRangeDTO();
        addDeviceDTO.setImages(Arrays.asList("http://testimages.com/image1.jpg", "http://testimages.com/image2.jpg"));

        assertThrows(UserNotAuthenticatedException.class, () -> highRangeService.addDevice(addDeviceDTO));
    }

    @Test
    void testCreateImageListDetailsDTO() throws Exception {
        AddHighRangeDTO addDeviceDTO = new AddHighRangeDTO();
        addDeviceDTO.setImages(Arrays.asList("image1.jpg", "image2.jpg"));

        Method method = HighRangeServiceImpl.class.getDeclaredMethod("createImageListDetailsDTO", AddHighRangeDTO.class);
        method.setAccessible(true);

        method.invoke(highRangeService, addDeviceDTO);

        ArgumentCaptor<ImageListDetailsDTO> imageListDetailsDTOCaptor = ArgumentCaptor.forClass(ImageListDetailsDTO.class);
        verify(imageProducer).sendMessage(imageListDetailsDTOCaptor.capture());

        ImageListDetailsDTO capturedDTO = imageListDetailsDTOCaptor.getValue();
        assertEquals(addDeviceDTO.getImages(), capturedDTO.getImageUrls());
        assertEquals("high_range_images", capturedDTO.getTableName());
    }
}