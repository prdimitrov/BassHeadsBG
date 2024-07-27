package com.bg.bassheadsbg.web.controller.speakerControllers;

import com.bg.bassheadsbg.kafka.KafkaConsumer;
import com.bg.bassheadsbg.model.dto.add.AddMidRangeDTO;
import com.bg.bassheadsbg.model.dto.details.MidRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MidRangeSummaryDTO;
import com.bg.bassheadsbg.model.helpers.MidRangeDetailsHelperDTO;
import com.bg.bassheadsbg.service.interfaces.MidRangeService;
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
public class MidRangeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MidRangeService midRangeService;

    @MockBean
    private KafkaConsumer kafkaConsumer;

    @Test
    @WithMockUser(username = "DASKALA", roles = {"ADMIN", "USER"})
    public void testAddMidRangeGet() throws Exception {
        AddMidRangeDTO mockAddMidRangeDTO = new AddMidRangeDTO();

        Mockito.when(midRangeService.createNewAddMidRangeDTO()).thenReturn(mockAddMidRangeDTO);

        mockMvc.perform(get("/speakers/mid-range/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("/speakers/midrange-add"))
                .andExpect(model().attributeExists("addMidRangeDTO"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testAddMidRangePost() throws Exception {
        AddMidRangeDTO mockAddMidRangeDTO = new AddMidRangeDTO();
        mockAddMidRangeDTO.setId(1L);
        mockAddMidRangeDTO.setBrand("BrandX");
        mockAddMidRangeDTO.setModel("ModelX");
        mockAddMidRangeDTO.setPrice("150");
        mockAddMidRangeDTO.setNumberOfCoils("2");
        mockAddMidRangeDTO.setFrequencyRangeFrom("20");
        mockAddMidRangeDTO.setFrequencyRangeTo("19999");
        mockAddMidRangeDTO.setPowerHandling("1500");
        mockAddMidRangeDTO.setFrequencyResponse("20000");
        mockAddMidRangeDTO.setSensitivity("95");
        mockAddMidRangeDTO.setImpedance("8");
        mockAddMidRangeDTO.setSize("12");
        mockAddMidRangeDTO.setImages(List.of("http://example.com/image1.jpg"));

        Mockito.when(midRangeService.addDevice(Mockito.any(AddMidRangeDTO.class))).thenReturn(1L);

        mockMvc.perform(post("/speakers/mid-range/add")
                        .with(csrf())
                        .flashAttr("addMidRangeDTO", mockAddMidRangeDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/speakers/mid-range/1"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testGetEditMidRange() throws Exception {
        Long deviceId = 1L;
        MidRangeDetailsDTO mockDetailsDTO = new MidRangeDetailsDTO();

        Mockito.when(midRangeService.getDeviceDetails(deviceId)).thenReturn(mockDetailsDTO);

        mockMvc.perform(get("/speakers/mid-range/edit/" + deviceId))
                .andExpect(status().isOk())
                .andExpect(view().name("/speakers/midrange-edit"))
                .andExpect(model().attributeExists("midRangeDetails"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testPostEditMidRange() throws Exception {
        AddMidRangeDTO mockAddMidRangeDTO = new AddMidRangeDTO();
        mockAddMidRangeDTO.setId(1L);
        mockAddMidRangeDTO.setBrand("BrandX");
        mockAddMidRangeDTO.setModel("ModelX");
        mockAddMidRangeDTO.setPrice("150");
        mockAddMidRangeDTO.setNumberOfCoils("2");
        mockAddMidRangeDTO.setFrequencyRangeFrom("20");
        mockAddMidRangeDTO.setFrequencyRangeTo("19999");
        mockAddMidRangeDTO.setPowerHandling("1500");
        mockAddMidRangeDTO.setFrequencyResponse("20000");
        mockAddMidRangeDTO.setSensitivity("95");
        mockAddMidRangeDTO.setImpedance("8");
        mockAddMidRangeDTO.setSize("12");
        mockAddMidRangeDTO.setImages(List.of("http://example.com/image1.jpg"));

        Mockito.when(midRangeService.editDevice(Mockito.any(AddMidRangeDTO.class))).thenReturn(1L);

        mockMvc.perform(post("/speakers/mid-range/edit/" + mockAddMidRangeDTO.getId())
                        .with(csrf())
                        .flashAttr("midRangeDetails", mockAddMidRangeDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/speakers/mid-range/1"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testMidRangeDetails() throws Exception {
        Long deviceId = 1L;
        MidRangeDetailsDTO mockDetailsDTO = new MidRangeDetailsDTO();
        MidRangeDetailsHelperDTO mockHelperDTO = new MidRangeDetailsHelperDTO(mockDetailsDTO);

        Mockito.when(midRangeService.getDeviceDetails(deviceId)).thenReturn(mockDetailsDTO);
        Mockito.when(midRangeService.getDeviceDetailsHelper(deviceId)).thenReturn(mockHelperDTO);

        mockMvc.perform(get("/speakers/mid-range/" + deviceId))
                .andExpect(status().isOk())
                .andExpect(view().name("/speakers/midrange-details"))
                .andExpect(model().attributeExists("midRangeDetails"))
                .andExpect(model().attributeExists("helperDTO"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testDeleteMidRange() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/speakers/mid-range/delete/" + 1L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testRankings() throws Exception {
        MidRangeSummaryDTO midRangeSummaryDTO = new MidRangeSummaryDTO();
        midRangeSummaryDTO.setId(1L);
        midRangeSummaryDTO.setImages(List.of("http://www.caraudio-bg.com/mtmw312d2plus/img.jpg"));
        midRangeSummaryDTO.setBrand("mtm");
        midRangeSummaryDTO.setModel("w312d2+");
        midRangeSummaryDTO.setPowerHandling((short) 3000);
        midRangeSummaryDTO.setSize(15.0f);
        List<MidRangeSummaryDTO> mockSummaryList = List.of(midRangeSummaryDTO);

        Mockito.when(midRangeService.getAllDeviceSummary()).thenReturn(mockSummaryList);

        mockMvc.perform(get("/speakers/mid-range/rankings"))
                .andExpect(status().isOk())
                .andExpect(view().name("/speakers/midrange-all"))
                .andExpect(model().attributeExists("allDevices"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testLike() throws Exception {
        Long deviceId = 1L;

        mockMvc.perform(post("/speakers/mid-range/like/" + deviceId)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/speakers/mid-range/rankings"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testAddMidRangePostWithErrors() throws Exception {
        AddMidRangeDTO mockAddMidRangeDTO = new AddMidRangeDTO();
        mockAddMidRangeDTO.setBrand("");
        mockAddMidRangeDTO.setModel("");

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        lenient().when(bindingResult.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/speakers/mid-range/add")
                        .with(csrf())
                        .flashAttr("addMidRangeDTO", mockAddMidRangeDTO)
                        .flashAttr(BindingResult.MODEL_KEY_PREFIX + "addMidRangeDTO", bindingResult))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/speakers/mid-range/add"))
                .andExpect(flash().attributeExists("addMidRangeDTO"))
                .andExpect(flash().attributeExists(BindingResult.MODEL_KEY_PREFIX + "addMidRangeDTO"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testPostEditMidRangeWithErrors() throws Exception {
        AddMidRangeDTO mockAddMidRangeDTO = new AddMidRangeDTO();
        mockAddMidRangeDTO.setId(1L);
        mockAddMidRangeDTO.setBrand("5000000000000000000000000000000000000000000000000000000");
        mockAddMidRangeDTO.setModel("5000000000000000000000000000000000000000000000000000000");
        mockAddMidRangeDTO.setPrice("5000000000000000000000000000000000000000000000000000000");
        mockAddMidRangeDTO.setNumberOfCoils("5000000000000000000000000000000000000000000000000000000");
        mockAddMidRangeDTO.setFrequencyRangeFrom("5000000000000000000000000000000000000000000000000000000");
        mockAddMidRangeDTO.setFrequencyRangeTo("5000000000000000000000000000000000000000000000000000000");
        mockAddMidRangeDTO.setPowerHandling("5000000000000000000000000000000000000000000000000000000");
        mockAddMidRangeDTO.setFrequencyResponse("5000000000000000000000000000000000000000000000000000000");
        mockAddMidRangeDTO.setSensitivity("5000000000000000000000000000000000000000000000000000000");
        mockAddMidRangeDTO.setImpedance("5000000000000000000000000000000000000000000000000000000");
        mockAddMidRangeDTO.setSize("5000000000000000000000000000000000000000000000000000000");
        mockAddMidRangeDTO.setImages(List.of("http://example.com/image1.jpg"));

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        lenient().when(bindingResult.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/speakers/mid-range/edit/" + mockAddMidRangeDTO.getId())
                        .with(csrf())
                        .flashAttr("midRangeDetails", mockAddMidRangeDTO)
                        .flashAttr(BindingResult.MODEL_KEY_PREFIX + "midRangeDetails", bindingResult))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/speakers/mid-range/edit/" + mockAddMidRangeDTO.getId()))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("midRangeDetails"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists(BindingResult.MODEL_KEY_PREFIX + "midRangeDetails"));
    }
}
