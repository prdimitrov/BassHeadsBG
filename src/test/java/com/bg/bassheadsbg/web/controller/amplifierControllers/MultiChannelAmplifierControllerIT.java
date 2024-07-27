package com.bg.bassheadsbg.web.controller.amplifierControllers;

import com.bg.bassheadsbg.kafka.KafkaConsumer;
import com.bg.bassheadsbg.model.dto.add.AddMultiChannelAmpDTO;
import com.bg.bassheadsbg.model.dto.details.MultiChannelAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MultiChannelAmpSummaryDTO;
import com.bg.bassheadsbg.model.enums.AmpClass;
import com.bg.bassheadsbg.model.helpers.MultiChannelAmpDetailsHelperDTO;
import com.bg.bassheadsbg.service.interfaces.MultiChannelAmpService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class MultiChannelAmplifierControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MultiChannelAmpService multiChannelAmpService;

    @MockBean
    private KafkaConsumer kafkaConsumer;

    @Test
    @WithMockUser(username = "DASKALA", roles = {"ADMIN", "USER"})
    public void testAddMultiChannelAmpGet() throws Exception {
        AddMultiChannelAmpDTO mockAddMultiChannelAmpDTO = new AddMultiChannelAmpDTO();

        Mockito.when(multiChannelAmpService.createNewAddMultiChannelAmpDTO()).thenReturn(mockAddMultiChannelAmpDTO);

        mockMvc.perform(get("/amplifiers/multi-channel-amplifiers/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("/amplifiers/multichannel-amp-add"))
                .andExpect(model().attributeExists("addMultiChannelAmpDTO"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testAddMultiChannelAmpPost() throws Exception {
        AddMultiChannelAmpDTO mockAddMultiChannelAmpDTO = new AddMultiChannelAmpDTO();
        mockAddMultiChannelAmpDTO.setId(1L);
        mockAddMultiChannelAmpDTO.setBrand("BrandX");
        mockAddMultiChannelAmpDTO.setModel("ModelX");
        mockAddMultiChannelAmpDTO.setPrice("150");
        mockAddMultiChannelAmpDTO.setAmplifierClass(AmpClass.D);
        mockAddMultiChannelAmpDTO.setImpedance("8");
        mockAddMultiChannelAmpDTO.setPower("8");
        mockAddMultiChannelAmpDTO.setHighInputLevel("Yes");
        mockAddMultiChannelAmpDTO.setLowPassFilter("No");
        mockAddMultiChannelAmpDTO.setSubsonicFilter("Maybe");
        mockAddMultiChannelAmpDTO.setRemoteControl(true);
        mockAddMultiChannelAmpDTO.setBassBoost(true);
        mockAddMultiChannelAmpDTO.setLowInputLevel("No");
        mockAddMultiChannelAmpDTO.setHighInputLevel("Yes");
        mockAddMultiChannelAmpDTO.setDistortion("4");
        mockAddMultiChannelAmpDTO.setCurrentDraw("4");
        mockAddMultiChannelAmpDTO.setFuseRating("3");
        mockAddMultiChannelAmpDTO.setHeight("2");
        mockAddMultiChannelAmpDTO.setWidth("2");
        mockAddMultiChannelAmpDTO.setLength("2");
        mockAddMultiChannelAmpDTO.setNumberOfChannels("2");
        mockAddMultiChannelAmpDTO.setImages(List.of("http://example.com/image1.jpg"));

        Mockito.when(multiChannelAmpService.addDevice(Mockito.any(AddMultiChannelAmpDTO.class))).thenReturn(1L);

        mockMvc.perform(post("/amplifiers/multi-channel-amplifiers/add")
                        .with(csrf())
                        .flashAttr("addMultiChannelAmpDTO", mockAddMultiChannelAmpDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/amplifiers/multi-channel-amplifiers/1"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testGetEditMultiChannelAmp() throws Exception {
        Long deviceId = 1L;
        MultiChannelAmpDetailsDTO mockDetailsDTO = new MultiChannelAmpDetailsDTO();

        Mockito.when(multiChannelAmpService.getDeviceDetails(deviceId)).thenReturn(mockDetailsDTO);

        mockMvc.perform(get("/amplifiers/multi-channel-amplifiers/edit/" + deviceId))
                .andExpect(status().isOk())
                .andExpect(view().name("/amplifiers/multichannel-amp-edit"))
                .andExpect(model().attributeExists("multiChannelAmpDetails"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testPostEditMultiChannelAmp() throws Exception {
        AddMultiChannelAmpDTO mockAddMultiChannelAmpDTO = new AddMultiChannelAmpDTO();
        mockAddMultiChannelAmpDTO.setId(1L);
        mockAddMultiChannelAmpDTO.setBrand("BrandX");
        mockAddMultiChannelAmpDTO.setModel("ModelX");
        mockAddMultiChannelAmpDTO.setPrice("150");
        mockAddMultiChannelAmpDTO.setAmplifierClass(AmpClass.D);
        mockAddMultiChannelAmpDTO.setImpedance("8");
        mockAddMultiChannelAmpDTO.setPower("8");
        mockAddMultiChannelAmpDTO.setHighInputLevel("Yes");
        mockAddMultiChannelAmpDTO.setLowPassFilter("No");
        mockAddMultiChannelAmpDTO.setSubsonicFilter("Maybe");
        mockAddMultiChannelAmpDTO.setRemoteControl(true);
        mockAddMultiChannelAmpDTO.setBassBoost(true);
        mockAddMultiChannelAmpDTO.setLowInputLevel("No");
        mockAddMultiChannelAmpDTO.setHighInputLevel("Yes");
        mockAddMultiChannelAmpDTO.setDistortion("4");
        mockAddMultiChannelAmpDTO.setCurrentDraw("4");
        mockAddMultiChannelAmpDTO.setFuseRating("3");
        mockAddMultiChannelAmpDTO.setHeight("2");
        mockAddMultiChannelAmpDTO.setWidth("2");
        mockAddMultiChannelAmpDTO.setLength("2");
        mockAddMultiChannelAmpDTO.setNumberOfChannels("2");
        mockAddMultiChannelAmpDTO.setImages(List.of("http://example.com/image1.jpg"));

        Mockito.when(multiChannelAmpService.editDevice(Mockito.any(AddMultiChannelAmpDTO.class))).thenReturn(1L);

        mockMvc.perform(post("/amplifiers/multi-channel-amplifiers/edit/" + mockAddMultiChannelAmpDTO.getId())
                        .with(csrf())
                        .flashAttr("multiChannelAmpDetails", mockAddMultiChannelAmpDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/amplifiers/multi-channel-amplifiers/1"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testMultiChannelAmpDetails() throws Exception {
        Long deviceId = 1L;
        MultiChannelAmpDetailsDTO mockDetailsDTO = new MultiChannelAmpDetailsDTO();
        MultiChannelAmpDetailsHelperDTO mockHelperDTO = new MultiChannelAmpDetailsHelperDTO(mockDetailsDTO);

        Mockito.when(multiChannelAmpService.getDeviceDetails(deviceId)).thenReturn(mockDetailsDTO);
        Mockito.when(multiChannelAmpService.getDeviceDetailsHelper(deviceId)).thenReturn(mockHelperDTO);

        mockMvc.perform(get("/amplifiers/multi-channel-amplifiers/" + deviceId))
                .andExpect(status().isOk())
                .andExpect(view().name("/amplifiers/multichannel-amp-details"))
                .andExpect(model().attributeExists("multiChannelAmpDetails"))
                .andExpect(model().attributeExists("helperDTO"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testDeleteMultiChannelAmp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/amplifiers/multi-channel-amplifiers/delete/" + 1L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testRankings() throws Exception {
        MultiChannelAmpSummaryDTO multiChannelAmpSummaryDTO = new MultiChannelAmpSummaryDTO();
        multiChannelAmpSummaryDTO.setId(1L);
        multiChannelAmpSummaryDTO.setImages(List.of("http://www.caraudio-bg.com/mtmw312d2plus/img.jpg"));
        multiChannelAmpSummaryDTO.setBrand("mtm");
        multiChannelAmpSummaryDTO.setModel("w312d2+");
        multiChannelAmpSummaryDTO.setPower((short) 3000);
        multiChannelAmpSummaryDTO.setAmplifierClass("D");
        List<MultiChannelAmpSummaryDTO> mockSummaryList = List.of(multiChannelAmpSummaryDTO);

        Mockito.when(multiChannelAmpService.getAllDeviceSummary()).thenReturn(mockSummaryList);

        mockMvc.perform(get("/amplifiers/multi-channel-amplifiers/rankings"))
                .andExpect(status().isOk())
                .andExpect(view().name("/amplifiers/multichannel-amp-all"))
                .andExpect(model().attributeExists("allDevices"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testLike() throws Exception {
        Long deviceId = 1L;

        mockMvc.perform(post("/amplifiers/multi-channel-amplifiers/like/" + deviceId)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/amplifiers/multi-channel-amplifiers/rankings"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testAddMultiChannelAmpPostWithErrors() throws Exception {
        AddMultiChannelAmpDTO mockAddMultiChannelAmpDTO = new AddMultiChannelAmpDTO();
        mockAddMultiChannelAmpDTO.setBrand("");
        mockAddMultiChannelAmpDTO.setModel("");

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        lenient().when(bindingResult.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/amplifiers/multi-channel-amplifiers/add")
                        .with(csrf())
                        .flashAttr("addMultiChannelAmpDTO", mockAddMultiChannelAmpDTO)
                        .flashAttr(BindingResult.MODEL_KEY_PREFIX + "addMultiChannelAmpDTO", bindingResult))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/amplifiers/multi-channel-amplifiers/add"))
                .andExpect(flash().attributeExists("addMultiChannelAmpDTO"))
                .andExpect(flash().attributeExists(BindingResult.MODEL_KEY_PREFIX + "addMultiChannelAmpDTO"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testPostEditMultiChannelAmpWithErrors() throws Exception {
        AddMultiChannelAmpDTO mockAddMultiChannelAmpDTO = new AddMultiChannelAmpDTO();
        mockAddMultiChannelAmpDTO.setId(1L);
        mockAddMultiChannelAmpDTO.setBrand("BrandX");
        mockAddMultiChannelAmpDTO.setModel("ModelX");
        mockAddMultiChannelAmpDTO.setPrice("5555555555555555");
        mockAddMultiChannelAmpDTO.setAmplifierClass(AmpClass.D);
        mockAddMultiChannelAmpDTO.setImpedance("555555555555555555555555555");
        mockAddMultiChannelAmpDTO.setPower("8");
        mockAddMultiChannelAmpDTO.setHighInputLevel("Yes");
        mockAddMultiChannelAmpDTO.setLowPassFilter("No");
        mockAddMultiChannelAmpDTO.setSubsonicFilter("Maybe");
        mockAddMultiChannelAmpDTO.setRemoteControl(true);
        mockAddMultiChannelAmpDTO.setBassBoost(true);
        mockAddMultiChannelAmpDTO.setLowInputLevel("No");
        mockAddMultiChannelAmpDTO.setHighInputLevel("Yes");
        mockAddMultiChannelAmpDTO.setDistortion("555555555555555555");
        mockAddMultiChannelAmpDTO.setCurrentDraw("4");
        mockAddMultiChannelAmpDTO.setFuseRating("5555555555555555555");
        mockAddMultiChannelAmpDTO.setHeight("55555555555555");
        mockAddMultiChannelAmpDTO.setWidth("2");
        mockAddMultiChannelAmpDTO.setLength("5555555555555");
        mockAddMultiChannelAmpDTO.setNumberOfChannels("2");
        mockAddMultiChannelAmpDTO.setImages(List.of("http://example.com/image1.jpg"));

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        lenient().when(bindingResult.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/amplifiers/multi-channel-amplifiers/edit/" + mockAddMultiChannelAmpDTO.getId())
                        .with(csrf())
                        .flashAttr("multiChannelAmpDetails", mockAddMultiChannelAmpDTO)
                        .flashAttr(BindingResult.MODEL_KEY_PREFIX + "multiChannelAmpDetails", bindingResult))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/amplifiers/multi-channel-amplifiers/edit/" + mockAddMultiChannelAmpDTO.getId()))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("multiChannelAmpDetails"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists(BindingResult.MODEL_KEY_PREFIX + "multiChannelAmpDetails"));
    }
}
