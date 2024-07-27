package com.bg.bassheadsbg.web.controller.speakerControllers;

import com.bg.bassheadsbg.kafka.KafkaConsumer;
import com.bg.bassheadsbg.model.dto.add.AddSubwooferDTO;
import com.bg.bassheadsbg.model.dto.details.SubwooferDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.SubwooferSummaryDTO;
import com.bg.bassheadsbg.model.helpers.SubwooferDetailsHelperDTO;
import com.bg.bassheadsbg.service.interfaces.SubwooferService;
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
public class SubwooferControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubwooferService subwooferService;

    @MockBean
    private KafkaConsumer kafkaConsumer;

    @Test
    @WithMockUser(username = "DASKALA", roles = {"ADMIN", "USER"})
    public void testAddSubwooferGet() throws Exception {
        AddSubwooferDTO mockAddSubwooferDTO = new AddSubwooferDTO();

        Mockito.when(subwooferService.createNewSubwooferDTO()).thenReturn(mockAddSubwooferDTO);

        mockMvc.perform(get("/speakers/subwoofers/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("/speakers/subwoofer-add"))
                .andExpect(model().attributeExists("addSubwooferDTO"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testAddSubwooferPost() throws Exception {
        AddSubwooferDTO mockAddSubwooferDTO = new AddSubwooferDTO();
        mockAddSubwooferDTO.setId(1L);
        mockAddSubwooferDTO.setBrand("BrandX");
        mockAddSubwooferDTO.setModel("ModelX");
        mockAddSubwooferDTO.setPrice("150");
        mockAddSubwooferDTO.setNumberOfCoils("2");
        mockAddSubwooferDTO.setPowerHandling("1500");
        mockAddSubwooferDTO.setFrequencyResponse("20000");
        mockAddSubwooferDTO.setSensitivity("95");
        mockAddSubwooferDTO.setImpedance("8");
        mockAddSubwooferDTO.setSize("12");
        mockAddSubwooferDTO.setCoilHeight("3");
        mockAddSubwooferDTO.setCoilLayers("4");
        mockAddSubwooferDTO.setMagnetSize("300");
        mockAddSubwooferDTO.setVas("1");
        mockAddSubwooferDTO.setXmax("1");
        mockAddSubwooferDTO.setQms("1");
        mockAddSubwooferDTO.setQts("1");
        mockAddSubwooferDTO.setQes("1");
        mockAddSubwooferDTO.setSd("1");
        mockAddSubwooferDTO.setBl("1");
        mockAddSubwooferDTO.setMms("443");
        mockAddSubwooferDTO.setImages(List.of("http://example.com/image1.jpg"));

        Mockito.when(subwooferService.addDevice(Mockito.any(AddSubwooferDTO.class))).thenReturn(1L);

        mockMvc.perform(post("/speakers/subwoofers/add")
                        .with(csrf())
                        .flashAttr("addSubwooferDTO", mockAddSubwooferDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/speakers/subwoofers/1"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testGetEditSubwoofer() throws Exception {
        Long deviceId = 1L;
        SubwooferDetailsDTO mockDetailsDTO = new SubwooferDetailsDTO();

        Mockito.when(subwooferService.getDeviceDetails(deviceId)).thenReturn(mockDetailsDTO);

        mockMvc.perform(get("/speakers/subwoofers/edit/" + deviceId))
                .andExpect(status().isOk())
                .andExpect(view().name("/speakers/subwoofer-edit"))
                .andExpect(model().attributeExists("subwooferDetails"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testPostEditSubwoofer() throws Exception {
        AddSubwooferDTO mockAddSubwooferDTO = new AddSubwooferDTO();
        mockAddSubwooferDTO.setId(1L);
        mockAddSubwooferDTO.setId(1L);
        mockAddSubwooferDTO.setBrand("BrandX");
        mockAddSubwooferDTO.setModel("ModelX");
        mockAddSubwooferDTO.setPrice("150");
        mockAddSubwooferDTO.setNumberOfCoils("2");
        mockAddSubwooferDTO.setPowerHandling("1500");
        mockAddSubwooferDTO.setFrequencyResponse("20000");
        mockAddSubwooferDTO.setSensitivity("95");
        mockAddSubwooferDTO.setImpedance("8");
        mockAddSubwooferDTO.setSize("12");
        mockAddSubwooferDTO.setCoilHeight("3");
        mockAddSubwooferDTO.setCoilLayers("4");
        mockAddSubwooferDTO.setMagnetSize("300");
        mockAddSubwooferDTO.setVas("1");
        mockAddSubwooferDTO.setXmax("1");
        mockAddSubwooferDTO.setQms("1");
        mockAddSubwooferDTO.setQts("1");
        mockAddSubwooferDTO.setQes("1");
        mockAddSubwooferDTO.setSd("1");
        mockAddSubwooferDTO.setBl("1");
        mockAddSubwooferDTO.setMms("443");
        mockAddSubwooferDTO.setImages(List.of("http://example.com/image1.jpg"));

        Mockito.when(subwooferService.editDevice(Mockito.any(AddSubwooferDTO.class))).thenReturn(1L);

        mockMvc.perform(post("/speakers/subwoofers/edit/" + mockAddSubwooferDTO.getId())
                        .with(csrf())
                        .flashAttr("subwooferDetails", mockAddSubwooferDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/speakers/subwoofers/1"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testSubwooferDetails() throws Exception {
        Long deviceId = 1L;
        SubwooferDetailsDTO mockDetailsDTO = new SubwooferDetailsDTO();
        SubwooferDetailsHelperDTO mockHelperDTO = new SubwooferDetailsHelperDTO(mockDetailsDTO);

        Mockito.when(subwooferService.getDeviceDetails(deviceId)).thenReturn(mockDetailsDTO);
        Mockito.when(subwooferService.getDeviceDetailsHelper(deviceId)).thenReturn(mockHelperDTO);

        mockMvc.perform(get("/speakers/subwoofers/" + deviceId))
                .andExpect(status().isOk())
                .andExpect(view().name("/speakers/subwoofer-details"))
                .andExpect(model().attributeExists("subwooferDetails"))
                .andExpect(model().attributeExists("helperDTO"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testDeleteSubwoofer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/speakers/subwoofers/delete/" + 1L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testRankings() throws Exception {
        SubwooferSummaryDTO subwooferSummaryDTO = new SubwooferSummaryDTO();
        subwooferSummaryDTO.setId(1L);
        subwooferSummaryDTO.setImages(List.of("http://www.caraudio-bg.com/mtmw312d2plus/img.jpg"));
        subwooferSummaryDTO.setBrand("mtm");
        subwooferSummaryDTO.setModel("w312d2+");
        subwooferSummaryDTO.setPowerHandling((short) 3000);
        subwooferSummaryDTO.setSize(15.0f);
        List<SubwooferSummaryDTO> mockSummaryList = List.of(subwooferSummaryDTO);

        Mockito.when(subwooferService.getAllDeviceSummary()).thenReturn(mockSummaryList);

        mockMvc.perform(get("/speakers/subwoofers/rankings"))
                .andExpect(status().isOk())
                .andExpect(view().name("/speakers/subwoofers-all"))
                .andExpect(model().attributeExists("allDevices"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testLike() throws Exception {
        Long deviceId = 1L;

        mockMvc.perform(post("/speakers/subwoofers/like/" + deviceId)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/speakers/subwoofers/rankings"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testAddSubwooferPostWithErrors() throws Exception {
        AddSubwooferDTO mockAddSubwooferDTO = new AddSubwooferDTO();
        mockAddSubwooferDTO.setBrand("");
        mockAddSubwooferDTO.setModel("");

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        lenient().when(bindingResult.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/speakers/subwoofers/add")
                        .with(csrf())
                        .flashAttr("addSubwooferDTO", mockAddSubwooferDTO)
                        .flashAttr(BindingResult.MODEL_KEY_PREFIX + "addSubwooferDTO", bindingResult))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/speakers/subwoofers/add"))
                .andExpect(flash().attributeExists("addSubwooferDTO"))
                .andExpect(flash().attributeExists(BindingResult.MODEL_KEY_PREFIX + "addSubwooferDTO"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testPostEditSubwooferWithErrors() throws Exception {
        AddSubwooferDTO mockAddSubwooferDTO = new AddSubwooferDTO();
        mockAddSubwooferDTO.setId(1L);
        mockAddSubwooferDTO.setId(1L);
        mockAddSubwooferDTO.setBrand("55555555555555555555555555");
        mockAddSubwooferDTO.setModel("55555555555555555555555555");
        mockAddSubwooferDTO.setPrice("55555555555555555555555555");
        mockAddSubwooferDTO.setNumberOfCoils("55555555555555555555555555");
        mockAddSubwooferDTO.setPowerHandling("55555555555555555555555555");
        mockAddSubwooferDTO.setFrequencyResponse("55555555555555555555555555");
        mockAddSubwooferDTO.setSensitivity("55555555555555555555555555");
        mockAddSubwooferDTO.setImpedance("55555555555555555555555555");
        mockAddSubwooferDTO.setSize("55555555555555555555555555");
        mockAddSubwooferDTO.setCoilHeight("55555555555555555555555555");
        mockAddSubwooferDTO.setCoilLayers("55555555555555555555555555");
        mockAddSubwooferDTO.setMagnetSize("55555555555555555555555555");
        mockAddSubwooferDTO.setVas("55555555555555555555555555");
        mockAddSubwooferDTO.setXmax("55555555555555555555555555");
        mockAddSubwooferDTO.setQms("55555555555555555555555555");
        mockAddSubwooferDTO.setQts("55555555555555555555555555");
        mockAddSubwooferDTO.setQes("55555555555555555555555555");
        mockAddSubwooferDTO.setSd("55555555555555555555555555");
        mockAddSubwooferDTO.setBl("55555555555555555555555555");
        mockAddSubwooferDTO.setMms("55555555555555555555555555");
        mockAddSubwooferDTO.setImages(List.of("http://example.com/image1.jpg"));

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        lenient().when(bindingResult.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/speakers/subwoofers/edit/" + mockAddSubwooferDTO.getId())
                        .with(csrf())
                        .flashAttr("subwooferDetails", mockAddSubwooferDTO)
                        .flashAttr(BindingResult.MODEL_KEY_PREFIX + "subwooferDetails", bindingResult))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/speakers/subwoofers/edit/" + mockAddSubwooferDTO.getId()))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("subwooferDetails"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists(BindingResult.MODEL_KEY_PREFIX + "subwooferDetails"));
    }
}
