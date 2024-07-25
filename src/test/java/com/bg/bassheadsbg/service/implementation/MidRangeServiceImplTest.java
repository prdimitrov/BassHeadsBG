package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.*;
import com.bg.bassheadsbg.kafka.ImageProducer;
import com.bg.bassheadsbg.messages.ExceptionMessages;
import com.bg.bassheadsbg.model.dto.add.AddMidRangeDTO;
import com.bg.bassheadsbg.model.dto.details.MidRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.details.ImageListDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MidRangeSummaryDTO;
import com.bg.bassheadsbg.model.entity.speakers.MidRange;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.helpers.MidRangeDetailsHelperDTO;
import com.bg.bassheadsbg.repository.MidRangeRepository;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MidRangeServiceImplTest {

    @Mock
    private MidRangeRepository repository;

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
    private MidRangeServiceImpl midRangeService;

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
    void testCreateNewAddMidRangeDTO() {
        AddMidRangeDTO result = midRangeService.createNewAddMidRangeDTO();

        assertNotNull(result, "net null");
        assertTrue(true, "Should return new AddMidRangeDTO()");
    }

    @Test
    void testAddDevice_userNotAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(null);

        AddMidRangeDTO addMidRangeDTO = new AddMidRangeDTO();

        UserNotAuthenticatedException exception = assertThrows(UserNotAuthenticatedException.class, () -> midRangeService.addDevice(addMidRangeDTO));

        assertEquals(ExceptionMessages.USER_NOT_AUTH, exception.getMessage());
    }

    @Test
    public void testAddDevice_Success() throws JsonProcessingException {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("testuser");

        AddMidRangeDTO addMidRangeDTO = new AddMidRangeDTO();
        addMidRangeDTO.setBrand("TestBrand");
        addMidRangeDTO.setModel("TestModel");

        MidRange midRange = new MidRange();
        midRange.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testuser");
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(modelMapper.map(addMidRangeDTO, MidRange.class)).thenReturn(midRange);
        when(repository.save(midRange)).thenReturn(midRange);

        long deviceId = midRangeService.addDevice(addMidRangeDTO);

        assertEquals(1L, deviceId);
        verify(repository, times(1)).save(midRange);
        verify(imageProducer, times(1)).sendMessage(any(ImageListDetailsDTO.class));
    }

    @Test
    void testGetDeviceDetails_deviceExists() {
        MidRange midRange = new MidRange();
        midRange.setId(1L);
        MidRangeDetailsDTO expectedDetails = new MidRangeDetailsDTO();
        expectedDetails.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(midRange));
        when(modelMapper.map(midRange, MidRangeDetailsDTO.class)).thenReturn(expectedDetails);

        MidRangeDetailsDTO result = midRangeService.getDeviceDetails(1L);

        assertEquals(expectedDetails, result);
    }

    @Test
    void testGetDeviceDetails_deviceNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        DeviceNotFoundException exception = assertThrows(DeviceNotFoundException.class, () -> midRangeService.getDeviceDetails(1L));

        assertEquals("Device with id 1 not found!", exception.getMessage());
    }

    @Test
    void testEditDevice_withValidData() {
        AddMidRangeDTO addMidRangeDTO = new AddMidRangeDTO();
        addMidRangeDTO.setBrand("TestBrand");
        addMidRangeDTO.setModel("TestModel");
        MidRange midRange = new MidRange();
        midRange.setId(1L);
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(modelMapper.map(addMidRangeDTO, MidRange.class)).thenReturn(midRange);
        when(repository.save(any(MidRange.class))).thenReturn(midRange);

        long result = midRangeService.editDevice(addMidRangeDTO);

        assertEquals(1L, result);
        verify(repository, times(1)).save(any(MidRange.class));
    }

    @Test
    void testEditDevice_userNotAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(null);

        AddMidRangeDTO addMidRangeDTO = new AddMidRangeDTO();

        UserNotAuthenticatedException exception = assertThrows(UserNotAuthenticatedException.class, () -> midRangeService.editDevice(addMidRangeDTO));

        assertEquals(ExceptionMessages.USER_NOT_AUTH, exception.getMessage());
    }

    @Test
    void testDeleteDevice_withValidData() {
        MidRange midRange = new MidRange();
        midRange.setId(1L);
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(repository.findById(1L)).thenReturn(Optional.of(midRange));

        midRangeService.deleteDevice(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteDevice_userNotAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(null);

        UserNotAuthenticatedException exception = assertThrows(UserNotAuthenticatedException.class, () -> midRangeService.deleteDevice(1L));

        assertEquals(ExceptionMessages.USER_NOT_AUTH, exception.getMessage());
    }

    @Test
    void testLikeDevice_withValidData() {
        MidRange midRange = new MidRange();
        midRange.setId(1L);
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(repository.findById(1L)).thenReturn(Optional.of(midRange));

        midRangeService.likeDevice(1L);

        verify(repository, times(1)).save(any(MidRange.class));
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

        DeviceNotFoundException exception = assertThrows(DeviceNotFoundException.class, () -> midRangeService.likeDevice(1L));
        assertEquals("Device with id 1 not found!", exception.getMessage());
    }

    @Test
    void testLikeDevice_userNotAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(null);

        UserNotAuthenticatedException exception = assertThrows(UserNotAuthenticatedException.class, () -> midRangeService.likeDevice(1L));

        assertEquals(ExceptionMessages.USER_NOT_AUTH, exception.getMessage());
    }

    @Test
    void testUpdateDeviceImageUrls_withValidData() {
        String oldUrl = "http://oldurl.com/image1.jpg";
        String newUrl = "http://newurl.com/image1.jpg";

        MidRange midRange1 = new MidRange();
        midRange1.setId(1L);
        midRange1.setImages(new ArrayList<>(Arrays.asList("http://oldurl.com/image1.jpg", "http://oldurl.com/image2.jpg")));

        MidRange midRange2 = new MidRange();
        midRange2.setId(2L);
        midRange2.setImages(new ArrayList<>(Arrays.asList("http://oldurl.com/image1.jpg", "http://differenturl.com/image3.jpg")));

        List<MidRange> midRanges = Arrays.asList(midRange1, midRange2);

        when(repository.findByImagesContaining(oldUrl)).thenReturn(midRanges);

        midRangeService.updateDeviceImageUrls(oldUrl, newUrl);

        assertEquals(Arrays.asList("http://newurl.com/image1.jpg", "http://oldurl.com/image2.jpg"), midRange1.getImages());
        assertEquals(Arrays.asList("http://newurl.com/image1.jpg", "http://differenturl.com/image3.jpg"), midRange2.getImages());

        verify(repository, times(1)).save(midRange1);
        verify(repository, times(1)).save(midRange2);
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

        MidRange midRange1 = new MidRange();
        midRange1.setId(1L);
        midRange1.setUserLikes(likes1);
        midRange1.setBrand("mtm");
        midRange1.setModel("nottruemodel");

        MidRange midRange2 = new MidRange();
        midRange2.setId(2L);
        midRange2.setUserLikes(likes2);
        midRange2.setBrand("mtm2");
        midRange2.setModel("nqmatakuv");

        MidRange midRange3 = new MidRange();
        midRange3.setId(3L);
        midRange3.setUserLikes(likes3);
        midRange3.setBrand("mtm");
        midRange3.setModel("nottruemodel");

        List<MidRange> midRanges = Arrays.asList(midRange1, midRange2, midRange3);

        MidRangeSummaryDTO summaryDTO1 = new MidRangeSummaryDTO();
        summaryDTO1.setBrand("mtm");
        summaryDTO1.setModel("nottruemodel");

        MidRangeSummaryDTO summaryDTO2 = new MidRangeSummaryDTO();
        summaryDTO2.setBrand("mtm2");
        summaryDTO2.setModel("ModelB");

        MidRangeSummaryDTO summaryDTO3 = new MidRangeSummaryDTO();
        summaryDTO3.setBrand("mtm");
        summaryDTO3.setModel("nottruemodel");

        when(repository.findAll()).thenReturn(midRanges);
        when(modelMapper.map(midRange1, MidRangeSummaryDTO.class)).thenReturn(summaryDTO1);
        when(modelMapper.map(midRange2, MidRangeSummaryDTO.class)).thenReturn(summaryDTO2);
        when(modelMapper.map(midRange3, MidRangeSummaryDTO.class)).thenReturn(summaryDTO3);

        List<MidRangeSummaryDTO> result = midRangeService.getAllDeviceSummary();

        assertTrue(result.contains(summaryDTO1), "Result should contain summaryDTO1");
        assertTrue(result.contains(summaryDTO2), "Result should contain summaryDTO2");
        assertTrue(result.contains(summaryDTO3), "Result should contain summaryDTO3");

        verify(repository, times(1)).findAll();
    }

    @Test
    void testAddDevice_deviceAlreadyExists() {
        AddMidRangeDTO addMidRangeDTO = new AddMidRangeDTO();
        addMidRangeDTO.setBrand("TestBrand");
        addMidRangeDTO.setModel("TestModel");

        MidRange existingMidRange = new MidRange();
        existingMidRange.setId(2L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(new UserEntity()));
        when(repository.findByBrandAndModel("TestBrand", "TestModel")).thenReturn(Optional.of(existingMidRange));

        when(messageSource.getMessage(
                ExceptionMessages.DEVICE_ALREADY_EXISTS,
                null,
                LocaleContextHolder.getLocale()
        )).thenReturn("Device already exists");

        DeviceAlreadyExistsException exception = assertThrows(DeviceAlreadyExistsException.class, () -> midRangeService.addDevice(addMidRangeDTO));
        assertEquals("Device already exists", exception.getMessage());

        verify(repository, never()).save(any(MidRange.class));
    }

    @Test
    void testLikeDevice_alreadyLiked() {
        MidRange midRange = new MidRange();
        midRange.setId(1L);

        UserEntity user = new UserEntity();
        user.setId(1L);

        List<UserEntity> userLikes = new ArrayList<>();
        userLikes.add(user);
        midRange.setUserLikes(userLikes);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(repository.findById(1L)).thenReturn(Optional.of(midRange));

        when(messageSource.getMessage(
                ExceptionMessages.DEVICE_ALREADY_LIKED,
                null,
                LocaleContextHolder.getLocale()
        )).thenReturn("Device already liked");

        DeviceAlreadyLikedException exception = assertThrows(DeviceAlreadyLikedException.class, () -> midRangeService.likeDevice(1L));
        assertEquals("Device already liked", exception.getMessage());
    }

    @Test
    void testGetDeviceDetailsHelper() {
        Long deviceId = 1L;
        MidRange mockMidRange = new MidRange();
        MidRangeDetailsDTO mockDetailsDTO = new MidRangeDetailsDTO();
        MidRangeDetailsHelperDTO expectedHelperDTO = new MidRangeDetailsHelperDTO(mockDetailsDTO);

        when(repository.findById(deviceId)).thenReturn(Optional.of(mockMidRange));
        when(modelMapper.map(mockMidRange, MidRangeDetailsDTO.class)).thenReturn(mockDetailsDTO);
        when(exRateService.allSupportedCurrencies()).thenReturn(mockDetailsDTO.getAllCurrencies());

        MidRangeDetailsHelperDTO actualHelperDTO = midRangeService.getDeviceDetailsHelper(deviceId);

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

        assertThrows(DeviceNotFoundException.class, () -> midRangeService.getDeviceDetailsHelper(deviceId));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void testAddDevice_UserNotAuthenticated() {
        AddMidRangeDTO addDeviceDTO = new AddMidRangeDTO();
        addDeviceDTO.setImages(Arrays.asList("http://testimages.com/image1.jpg", "http://testimages.com/image2.jpg"));

        assertThrows(UserNotAuthenticatedException.class, () -> midRangeService.addDevice(addDeviceDTO));
    }

    @Test
    void testCreateImageListDetailsDTO() throws Exception {
        AddMidRangeDTO addDeviceDTO = new AddMidRangeDTO();
        addDeviceDTO.setImages(Arrays.asList("image1.jpg", "image2.jpg"));

        Method method = MidRangeServiceImpl.class.getDeclaredMethod("createImageListDetailsDTO", AddMidRangeDTO.class);
        method.setAccessible(true);

        method.invoke(midRangeService, addDeviceDTO);

        ArgumentCaptor<ImageListDetailsDTO> imageListDetailsDTOCaptor = ArgumentCaptor.forClass(ImageListDetailsDTO.class);
        verify(imageProducer).sendMessage(imageListDetailsDTOCaptor.capture());

        ImageListDetailsDTO capturedDTO = imageListDetailsDTOCaptor.getValue();
        assertEquals(addDeviceDTO.getImages(), capturedDTO.getImageUrls());
        assertEquals("mid_range_images", capturedDTO.getTableName());
    }
}