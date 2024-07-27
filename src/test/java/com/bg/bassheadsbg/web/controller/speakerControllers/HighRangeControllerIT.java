package com.bg.bassheadsbg.web.controller.speakerControllers;

import com.bg.bassheadsbg.kafka.KafkaConsumer;
import com.bg.bassheadsbg.model.dto.add.AddHighRangeDTO;
import com.bg.bassheadsbg.model.dto.details.HighRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.HighRangeSummaryDTO;
import com.bg.bassheadsbg.model.helpers.HighRangeDetailsHelperDTO;
import com.bg.bassheadsbg.service.interfaces.HighRangeService;
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
public class HighRangeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HighRangeService highRangeService;

    @MockBean
    private KafkaConsumer kafkaConsumer;

    @Test
    @WithMockUser(username = "DASKALA", roles = {"ADMIN", "USER"})
    public void testAddHighRangeGet() throws Exception {
        AddHighRangeDTO mockAddHighRangeDTO = new AddHighRangeDTO();

        Mockito.when(highRangeService.createNewAddHighRangeDTO()).thenReturn(mockAddHighRangeDTO);

        mockMvc.perform(get("/speakers/high-range/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("/speakers/highrange-add"))
                .andExpect(model().attributeExists("addHighRangeDTO"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testAddHighRangePost() throws Exception {
        AddHighRangeDTO mockAddHighRangeDTO = new AddHighRangeDTO();
        mockAddHighRangeDTO.setId(1L);
        mockAddHighRangeDTO.setBrand("BrandX");
        mockAddHighRangeDTO.setModel("ModelX");
        mockAddHighRangeDTO.setPrice("150");
        mockAddHighRangeDTO.setNumberOfCoils("2");
        mockAddHighRangeDTO.setCrossover("12dB");
        mockAddHighRangeDTO.setFrequencyRangeFrom("20");
        mockAddHighRangeDTO.setFrequencyRangeTo("19999");
        mockAddHighRangeDTO.setPowerHandling("1500");
        mockAddHighRangeDTO.setFrequencyResponse("20000");
        mockAddHighRangeDTO.setMaterial("Aluminum");
        mockAddHighRangeDTO.setSensitivity("95");
        mockAddHighRangeDTO.setImpedance("8");
        mockAddHighRangeDTO.setSize("12");
        mockAddHighRangeDTO.setImages(List.of("http://example.com/image1.jpg"));

        Mockito.when(highRangeService.addDevice(Mockito.any(AddHighRangeDTO.class))).thenReturn(1L);

        mockMvc.perform(post("/speakers/high-range/add")
                        .with(csrf())
                        .flashAttr("addHighRangeDTO", mockAddHighRangeDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/speakers/high-range/1"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testGetEditHighRange() throws Exception {
        Long deviceId = 1L;
        HighRangeDetailsDTO mockDetailsDTO = new HighRangeDetailsDTO();

        Mockito.when(highRangeService.getDeviceDetails(deviceId)).thenReturn(mockDetailsDTO);

        mockMvc.perform(get("/speakers/high-range/edit/" + deviceId))
                .andExpect(status().isOk())
                .andExpect(view().name("/speakers/highrange-edit"))
                .andExpect(model().attributeExists("highRangeDetails"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testPostEditHighRange() throws Exception {
        AddHighRangeDTO mockAddHighRangeDTO = new AddHighRangeDTO();
        mockAddHighRangeDTO.setId(1L);
        mockAddHighRangeDTO.setBrand("BrandX");
        mockAddHighRangeDTO.setModel("ModelX");
        mockAddHighRangeDTO.setPrice("150");
        mockAddHighRangeDTO.setNumberOfCoils("2");
        mockAddHighRangeDTO.setCrossover("12dB");
        mockAddHighRangeDTO.setFrequencyRangeFrom("20");
        mockAddHighRangeDTO.setFrequencyRangeTo("19999");
        mockAddHighRangeDTO.setPowerHandling("1500");
        mockAddHighRangeDTO.setFrequencyResponse("20000");
        mockAddHighRangeDTO.setMaterial("Aluminum");
        mockAddHighRangeDTO.setSensitivity("95");
        mockAddHighRangeDTO.setImpedance("8");
        mockAddHighRangeDTO.setSize("12");
        mockAddHighRangeDTO.setImages(List.of("http://example.com/image1.jpg"));

        Mockito.when(highRangeService.editDevice(Mockito.any(AddHighRangeDTO.class))).thenReturn(1L);

        mockMvc.perform(post("/speakers/high-range/edit/" + mockAddHighRangeDTO.getId())
                        .with(csrf())
                        .flashAttr("highRangeDetails", mockAddHighRangeDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/speakers/high-range/1"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testHighRangeDetails() throws Exception {
        Long deviceId = 1L;
        HighRangeDetailsDTO mockDetailsDTO = new HighRangeDetailsDTO();
        HighRangeDetailsHelperDTO mockHelperDTO = new HighRangeDetailsHelperDTO(mockDetailsDTO);

        Mockito.when(highRangeService.getDeviceDetails(deviceId)).thenReturn(mockDetailsDTO);
        Mockito.when(highRangeService.getDeviceDetailsHelper(deviceId)).thenReturn(mockHelperDTO);

        mockMvc.perform(get("/speakers/high-range/" + deviceId))
                .andExpect(status().isOk())
                .andExpect(view().name("/speakers/highrange-details"))
                .andExpect(model().attributeExists("highRangeDetails"))
                .andExpect(model().attributeExists("helperDTO"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testDeleteHighRange() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/speakers/high-range/delete/" + 1L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testRankings() throws Exception {
        HighRangeSummaryDTO highRangeSummaryDTO = new HighRangeSummaryDTO();
        highRangeSummaryDTO.setId(1L);
        highRangeSummaryDTO.setImages(List.of("http://www.caraudio-bg.com/mtmw312d2plus/img.jpg"));
        highRangeSummaryDTO.setBrand("mtm");
        highRangeSummaryDTO.setModel("w312d2+");
        highRangeSummaryDTO.setPowerHandling((short) 3000);
        highRangeSummaryDTO.setSize(15.0f);
        List<HighRangeSummaryDTO> mockSummaryList = List.of(highRangeSummaryDTO);

        Mockito.when(highRangeService.getAllDeviceSummary()).thenReturn(mockSummaryList);

        mockMvc.perform(get("/speakers/high-range/rankings"))
                .andExpect(status().isOk())
                .andExpect(view().name("/speakers/highrange-all"))
                .andExpect(model().attributeExists("allDevices"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testLike() throws Exception {
        Long deviceId = 1L;

        mockMvc.perform(post("/speakers/high-range/like/" + deviceId)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/speakers/high-range/rankings"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testAddHighRangePostWithErrors() throws Exception {
        AddHighRangeDTO mockAddHighRangeDTO = new AddHighRangeDTO();
        mockAddHighRangeDTO.setBrand("");
        mockAddHighRangeDTO.setModel("");

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        lenient().when(bindingResult.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/speakers/high-range/add")
                        .with(csrf())
                        .flashAttr("addHighRangeDTO", mockAddHighRangeDTO)
                        .flashAttr(BindingResult.MODEL_KEY_PREFIX + "addHighRangeDTO", bindingResult))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/speakers/high-range/add"))
                .andExpect(flash().attributeExists("addHighRangeDTO"))
                .andExpect(flash().attributeExists(BindingResult.MODEL_KEY_PREFIX + "addHighRangeDTO"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testPostEditHighRangeWithErrors() throws Exception {
        AddHighRangeDTO mockAddHighRangeDTO = new AddHighRangeDTO();
        mockAddHighRangeDTO.setId(1L);
        mockAddHighRangeDTO.setBrand("5000000000000000000000000000000000000000000000000000000");
        mockAddHighRangeDTO.setModel("5000000000000000000000000000000000000000000000000000000");
        mockAddHighRangeDTO.setPrice("5000000000000000000000000000000000000000000000000000000");
        mockAddHighRangeDTO.setNumberOfCoils("5000000000000000000000000000000000000000000000000000000");
        mockAddHighRangeDTO.setCrossover("5000000000000000000000000000000000000000000000000000000");
        mockAddHighRangeDTO.setFrequencyRangeFrom("5000000000000000000000000000000000000000000000000000000");
        mockAddHighRangeDTO.setFrequencyRangeTo("5000000000000000000000000000000000000000000000000000000");
        mockAddHighRangeDTO.setPowerHandling("5000000000000000000000000000000000000000000000000000000");
        mockAddHighRangeDTO.setFrequencyResponse("5000000000000000000000000000000000000000000000000000000");
        mockAddHighRangeDTO.setMaterial("5000000000000000000000000000000000000000000000000000000");
        mockAddHighRangeDTO.setSensitivity("5000000000000000000000000000000000000000000000000000000");
        mockAddHighRangeDTO.setImpedance("5000000000000000000000000000000000000000000000000000000");
        mockAddHighRangeDTO.setSize("5000000000000000000000000000000000000000000000000000000");
        mockAddHighRangeDTO.setImages(List.of("http://example.com/image1.jpg"));

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        lenient().when(bindingResult.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/speakers/high-range/edit/" + mockAddHighRangeDTO.getId())
                        .with(csrf())
                        .flashAttr("highRangeDetails", mockAddHighRangeDTO)
                        .flashAttr(BindingResult.MODEL_KEY_PREFIX + "highRangeDetails", bindingResult))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/speakers/high-range/edit/" + mockAddHighRangeDTO.getId()))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("highRangeDetails"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists(BindingResult.MODEL_KEY_PREFIX + "highRangeDetails"));
    }
}
