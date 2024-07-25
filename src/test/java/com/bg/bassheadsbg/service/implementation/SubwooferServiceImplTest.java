package com.bg.bassheadsbg.service.implementation;

import com.bg.bassheadsbg.exception.*;
import com.bg.bassheadsbg.kafka.ImageProducer;
import com.bg.bassheadsbg.messages.ExceptionMessages;
import com.bg.bassheadsbg.model.dto.add.AddSubwooferDTO;
import com.bg.bassheadsbg.model.dto.details.SubwooferDetailsDTO;
import com.bg.bassheadsbg.model.dto.details.ImageListDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.SubwooferSummaryDTO;
import com.bg.bassheadsbg.model.entity.speakers.Subwoofer;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.model.helpers.SubwooferDetailsHelperDTO;
import com.bg.bassheadsbg.repository.SubwooferRepository;
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
public class SubwooferServiceImplTest {

    @Mock
    private SubwooferRepository repository;

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
    private SubwooferServiceImpl subwooferService;

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
    void testCreateNewAddSubwooferDTO() {
        AddSubwooferDTO result = subwooferService.createNewSubwooferDTO();

        assertNotNull(result, "not null");
        assertTrue(true, "Should return new AddSubwooferDTO()");
    }

    @Test
    void testAddDevice_userNotAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(null);

        AddSubwooferDTO addSubwooferDTO = new AddSubwooferDTO();

        UserNotAuthenticatedException exception = assertThrows(UserNotAuthenticatedException.class, () -> subwooferService.addDevice(addSubwooferDTO));

        assertEquals(ExceptionMessages.USER_NOT_AUTH, exception.getMessage());
    }

    @Test
    public void testAddDevice_Success() throws JsonProcessingException {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("testuser");

        AddSubwooferDTO addSubwooferDTO = new AddSubwooferDTO();
        addSubwooferDTO.setBrand("TestBrand");
        addSubwooferDTO.setModel("TestModel");

        Subwoofer subwoofer = new Subwoofer();
        subwoofer.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testuser");
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(modelMapper.map(addSubwooferDTO, Subwoofer.class)).thenReturn(subwoofer);
        when(repository.save(subwoofer)).thenReturn(subwoofer);

        long deviceId = subwooferService.addDevice(addSubwooferDTO);

        assertEquals(1L, deviceId);
        verify(repository, times(1)).save(subwoofer);
        verify(imageProducer, times(1)).sendMessage(any(ImageListDetailsDTO.class));
    }

    @Test
    void testGetDeviceDetails_deviceExists() {
        Subwoofer subwoofer = new Subwoofer();
        subwoofer.setId(1L);
        SubwooferDetailsDTO expectedDetails = new SubwooferDetailsDTO();
        expectedDetails.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(subwoofer));
        when(modelMapper.map(subwoofer, SubwooferDetailsDTO.class)).thenReturn(expectedDetails);

        SubwooferDetailsDTO result = subwooferService.getDeviceDetails(1L);

        assertEquals(expectedDetails, result);
    }

    @Test
    void testGetDeviceDetails_deviceNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        DeviceNotFoundException exception = assertThrows(DeviceNotFoundException.class, () -> subwooferService.getDeviceDetails(1L));

        assertEquals("Device with id 1 not found!", exception.getMessage());
    }

    @Test
    void testEditDevice_withValidData() throws JsonProcessingException {
        AddSubwooferDTO addSubwooferDTO = new AddSubwooferDTO();
        addSubwooferDTO.setBrand("TestBrand");
        addSubwooferDTO.setModel("TestModel");
        Subwoofer subwoofer = new Subwoofer();
        subwoofer.setId(1L);
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(modelMapper.map(addSubwooferDTO, Subwoofer.class)).thenReturn(subwoofer);
        when(repository.save(any(Subwoofer.class))).thenReturn(subwoofer);

        long result = subwooferService.editDevice(addSubwooferDTO);

        assertEquals(1L, result);
        verify(repository, times(1)).save(any(Subwoofer.class));
    }

    @Test
    void testEditDevice_userNotAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(null);

        AddSubwooferDTO addSubwooferDTO = new AddSubwooferDTO();

        UserNotAuthenticatedException exception = assertThrows(UserNotAuthenticatedException.class, () -> subwooferService.editDevice(addSubwooferDTO));

        assertEquals(ExceptionMessages.USER_NOT_AUTH, exception.getMessage());
    }

    @Test
    void testDeleteDevice_withValidData() {
        Subwoofer subwoofer = new Subwoofer();
        subwoofer.setId(1L);
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(repository.findById(1L)).thenReturn(Optional.of(subwoofer));

        subwooferService.deleteDevice(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteDevice_userNotAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(null);

        UserNotAuthenticatedException exception = assertThrows(UserNotAuthenticatedException.class, () -> subwooferService.deleteDevice(1L));

        assertEquals(ExceptionMessages.USER_NOT_AUTH, exception.getMessage());
    }

    @Test
    void testLikeDevice_withValidData() {
        Subwoofer subwoofer = new Subwoofer();
        subwoofer.setId(1L);
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setId(1L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(repository.findById(1L)).thenReturn(Optional.of(subwoofer));

        subwooferService.likeDevice(1L);

        verify(repository, times(1)).save(any(Subwoofer.class));
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

        DeviceNotFoundException exception = assertThrows(DeviceNotFoundException.class, () -> subwooferService.likeDevice(1L));
        assertEquals("Device with id 1 not found!", exception.getMessage());
    }

    @Test
    void testLikeDevice_userNotAuthenticated() {
        when(authentication.getPrincipal()).thenReturn(null);

        UserNotAuthenticatedException exception = assertThrows(UserNotAuthenticatedException.class, () -> subwooferService.likeDevice(1L));

        assertEquals(ExceptionMessages.USER_NOT_AUTH, exception.getMessage());
    }

    @Test
    void testUpdateDeviceImageUrls_withValidData() {
        String oldUrl = "http://oldurl.com/image1.jpg";
        String newUrl = "http://newurl.com/image1.jpg";

        Subwoofer subwoofer1 = new Subwoofer();
        subwoofer1.setId(1L);
        subwoofer1.setImages(new ArrayList<>(Arrays.asList("http://oldurl.com/image1.jpg", "http://oldurl.com/image2.jpg")));

        Subwoofer subwoofer2 = new Subwoofer();
        subwoofer2.setId(2L);
        subwoofer2.setImages(new ArrayList<>(Arrays.asList("http://oldurl.com/image1.jpg", "http://differenturl.com/image3.jpg")));

        List<Subwoofer> subwoofers = Arrays.asList(subwoofer1, subwoofer2);

        when(repository.findByImagesContaining(oldUrl)).thenReturn(subwoofers);

        subwooferService.updateDeviceImageUrls(oldUrl, newUrl);

        assertEquals(Arrays.asList("http://newurl.com/image1.jpg", "http://oldurl.com/image2.jpg"), subwoofer1.getImages());
        assertEquals(Arrays.asList("http://newurl.com/image1.jpg", "http://differenturl.com/image3.jpg"), subwoofer2.getImages());

        verify(repository, times(1)).save(subwoofer1);
        verify(repository, times(1)).save(subwoofer2);
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

        Subwoofer subwoofer1 = new Subwoofer();
        subwoofer1.setId(1L);
        subwoofer1.setUserLikes(likes1);
        subwoofer1.setBrand("mtm");
        subwoofer1.setModel("nottruemodel");

        Subwoofer subwoofer2 = new Subwoofer();
        subwoofer2.setId(2L);
        subwoofer2.setUserLikes(likes2);
        subwoofer2.setBrand("mtm2");
        subwoofer2.setModel("nqmatakuv");

        Subwoofer subwoofer3 = new Subwoofer();
        subwoofer3.setId(3L);
        subwoofer3.setUserLikes(likes3);
        subwoofer3.setBrand("mtm");
        subwoofer3.setModel("nottruemodel");

        List<Subwoofer> subwoofers = Arrays.asList(subwoofer1, subwoofer2, subwoofer3);

        SubwooferSummaryDTO summaryDTO1 = new SubwooferSummaryDTO();
        summaryDTO1.setBrand("mtm");
        summaryDTO1.setModel("nottruemodel");

        SubwooferSummaryDTO summaryDTO2 = new SubwooferSummaryDTO();
        summaryDTO2.setBrand("mtm2");
        summaryDTO2.setModel("ModelB");

        SubwooferSummaryDTO summaryDTO3 = new SubwooferSummaryDTO();
        summaryDTO3.setBrand("mtm");
        summaryDTO3.setModel("nottruemodel");

        when(repository.findAll()).thenReturn(subwoofers);
        when(modelMapper.map(subwoofer1, SubwooferSummaryDTO.class)).thenReturn(summaryDTO1);
        when(modelMapper.map(subwoofer2, SubwooferSummaryDTO.class)).thenReturn(summaryDTO2);
        when(modelMapper.map(subwoofer3, SubwooferSummaryDTO.class)).thenReturn(summaryDTO3);

        List<SubwooferSummaryDTO> result = subwooferService.getAllDeviceSummary();

        assertTrue(result.contains(summaryDTO1), "Result should contain summaryDTO1");
        assertTrue(result.contains(summaryDTO2), "Result should contain summaryDTO2");
        assertTrue(result.contains(summaryDTO3), "Result should contain summaryDTO3");

        verify(repository, times(1)).findAll();
    }

    @Test
    void testAddDevice_deviceAlreadyExists() {
        AddSubwooferDTO addSubwooferDTO = new AddSubwooferDTO();
        addSubwooferDTO.setBrand("TestBrand");
        addSubwooferDTO.setModel("TestModel");

        Subwoofer existingSubwoofer = new Subwoofer();
        existingSubwoofer.setId(2L);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(new UserEntity()));
        when(repository.findByBrandAndModel("TestBrand", "TestModel")).thenReturn(Optional.of(existingSubwoofer));

        when(messageSource.getMessage(
                ExceptionMessages.DEVICE_ALREADY_EXISTS,
                null,
                LocaleContextHolder.getLocale()
        )).thenReturn("Device already exists");

        DeviceAlreadyExistsException exception = assertThrows(DeviceAlreadyExistsException.class, () -> subwooferService.addDevice(addSubwooferDTO));
        assertEquals("Device already exists", exception.getMessage());

        verify(repository, never()).save(any(Subwoofer.class));
    }

    @Test
    void testLikeDevice_alreadyLiked() {
        Subwoofer subwoofer = new Subwoofer();
        subwoofer.setId(1L);

        UserEntity user = new UserEntity();
        user.setId(1L);

        List<UserEntity> userLikes = new ArrayList<>();
        userLikes.add(user);
        subwoofer.setUserLikes(userLikes);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(repository.findById(1L)).thenReturn(Optional.of(subwoofer));

        when(messageSource.getMessage(
                ExceptionMessages.DEVICE_ALREADY_LIKED,
                null,
                LocaleContextHolder.getLocale()
        )).thenReturn("Device already liked");

        DeviceAlreadyLikedException exception = assertThrows(DeviceAlreadyLikedException.class, () -> subwooferService.likeDevice(1L));
        assertEquals("Device already liked", exception.getMessage());
    }

    @Test
    void testGetDeviceDetailsHelper() {
        Long deviceId = 1L;
        Subwoofer mockSubwoofer = new Subwoofer();
        SubwooferDetailsDTO mockDetailsDTO = new SubwooferDetailsDTO();
        SubwooferDetailsHelperDTO expectedHelperDTO = new SubwooferDetailsHelperDTO(mockDetailsDTO);

        when(repository.findById(deviceId)).thenReturn(Optional.of(mockSubwoofer));
        when(modelMapper.map(mockSubwoofer, SubwooferDetailsDTO.class)).thenReturn(mockDetailsDTO);
        when(exRateService.allSupportedCurrencies()).thenReturn(mockDetailsDTO.getAllCurrencies());

        SubwooferDetailsHelperDTO actualHelperDTO = subwooferService.getDeviceDetailsHelper(deviceId);

        assertEquals(expectedHelperDTO.formattedSensitivity(), actualHelperDTO.formattedSensitivity(), "Sensitivity should match.");
        assertEquals(expectedHelperDTO.formattedSize(), actualHelperDTO.formattedSize(), "Size should match.");
        assertEquals(expectedHelperDTO.formattedFrequencyResponse(), actualHelperDTO.formattedFrequencyResponse(), "Frequency response should match.");
        assertEquals(expectedHelperDTO.formattedImpedance(), actualHelperDTO.formattedImpedance(), "Impedance should match.");
        assertEquals(expectedHelperDTO.formattedPowerHandling(), actualHelperDTO.formattedPowerHandling(), "Power handling should match.");
    }

    @Test
    void testGetDeviceDetailsHelper_DeviceNotFound() {
        Long deviceId = 1L;
        when(repository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> subwooferService.getDeviceDetailsHelper(deviceId));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    void testAddDevice_UserNotAuthenticated() {
        AddSubwooferDTO addDeviceDTO = new AddSubwooferDTO();
        addDeviceDTO.setImages(Arrays.asList("http://testimages.com/image1.jpg", "http://testimages.com/image2.jpg"));

        assertThrows(UserNotAuthenticatedException.class, () -> subwooferService.addDevice(addDeviceDTO));
    }

    @Test
    void testCreateImageListDetailsDTO() throws Exception {
        AddSubwooferDTO addDeviceDTO = new AddSubwooferDTO();
        addDeviceDTO.setImages(Arrays.asList("image1.jpg", "image2.jpg"));

        Method method = SubwooferServiceImpl.class.getDeclaredMethod("createImageListDetailsDTO", AddSubwooferDTO.class);
        method.setAccessible(true);

        method.invoke(subwooferService, addDeviceDTO);

        ArgumentCaptor<ImageListDetailsDTO> imageListDetailsDTOCaptor = ArgumentCaptor.forClass(ImageListDetailsDTO.class);
        verify(imageProducer).sendMessage(imageListDetailsDTOCaptor.capture());

        ImageListDetailsDTO capturedDTO = imageListDetailsDTOCaptor.getValue();
        assertEquals(addDeviceDTO.getImages(), capturedDTO.getImageUrls());
        assertEquals("subwoofer_images", capturedDTO.getTableName());
    }
}