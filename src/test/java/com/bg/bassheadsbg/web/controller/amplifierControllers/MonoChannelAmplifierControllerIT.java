package com.bg.bassheadsbg.web.controller.amplifierControllers;

import com.bg.bassheadsbg.kafka.KafkaConsumer;
import com.bg.bassheadsbg.model.dto.add.AddMonoAmpDTO;
import com.bg.bassheadsbg.model.dto.details.MonoAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MonoAmpSummaryDTO;
import com.bg.bassheadsbg.model.enums.AmpClass;
import com.bg.bassheadsbg.model.helpers.MonoAmpDetailsHelperDTO;
import com.bg.bassheadsbg.service.interfaces.MonoAmpService;
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
public class MonoChannelAmplifierControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonoAmpService monoChannelAmplifierService;

    @MockBean
    private KafkaConsumer kafkaConsumer;

    @Test
    @WithMockUser(username = "DASKALA", roles = {"ADMIN", "USER"})
    public void testAddMonoChannelAmpGet() throws Exception {
        AddMonoAmpDTO mockAddMonoAmpDTO = new AddMonoAmpDTO();

        Mockito.when(monoChannelAmplifierService.createNewAddMonoAmpDTO()).thenReturn(mockAddMonoAmpDTO);

        mockMvc.perform(get("/amplifiers/mono-amplifiers/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("/amplifiers/monoamp-add"))
                .andExpect(model().attributeExists("addMonoAmpDTO"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testAddMonoChannelAmpPost() throws Exception {
        AddMonoAmpDTO mockAddMonoAmpDTO = new AddMonoAmpDTO();
        mockAddMonoAmpDTO.setId(1L);
        mockAddMonoAmpDTO.setBrand("BrandX");
        mockAddMonoAmpDTO.setModel("ModelX");
        mockAddMonoAmpDTO.setPrice("150");
        mockAddMonoAmpDTO.setAmplifierClass(AmpClass.D);
        mockAddMonoAmpDTO.setImpedance("8");
        mockAddMonoAmpDTO.setPower("8");
        mockAddMonoAmpDTO.setHighInputLevel("Yes");
        mockAddMonoAmpDTO.setLowPassFilter("No");
        mockAddMonoAmpDTO.setSubsonicFilter("Maybe");
        mockAddMonoAmpDTO.setRemoteControl(true);
        mockAddMonoAmpDTO.setBassBoost(true);
        mockAddMonoAmpDTO.setLowInputLevel("No");
        mockAddMonoAmpDTO.setHighInputLevel("Yes");
        mockAddMonoAmpDTO.setDistortion("4");
        mockAddMonoAmpDTO.setCurrentDraw("4");
        mockAddMonoAmpDTO.setFuseRating("3");
        mockAddMonoAmpDTO.setNumberOfRca("2");
        mockAddMonoAmpDTO.setNumberOfSpeakerOutputs("2");
        mockAddMonoAmpDTO.setHeight("2");
        mockAddMonoAmpDTO.setWidth("2");
        mockAddMonoAmpDTO.setLength("2");
        mockAddMonoAmpDTO.setImages(List.of("http://example.com/image1.jpg"));

        Mockito.when(monoChannelAmplifierService.addDevice(Mockito.any(AddMonoAmpDTO.class))).thenReturn(1L);

        mockMvc.perform(post("/amplifiers/mono-amplifiers/add")
                        .with(csrf())
                        .flashAttr("addMonoAmpDTO", mockAddMonoAmpDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/amplifiers/mono-amplifiers/1"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testGetEditMonoChannelAmp() throws Exception {
        Long deviceId = 1L;
        MonoAmpDetailsDTO mockDetailsDTO = new MonoAmpDetailsDTO();

        Mockito.when(monoChannelAmplifierService.getDeviceDetails(deviceId)).thenReturn(mockDetailsDTO);

        mockMvc.perform(get("/amplifiers/mono-amplifiers/edit/" + deviceId))
                .andExpect(status().isOk())
                .andExpect(view().name("/amplifiers/monoamp-edit"))
                .andExpect(model().attributeExists("monoAmpDetails"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testPostEditMonoChannelAmp() throws Exception {
        AddMonoAmpDTO mockAddMonoAmpDTO = new AddMonoAmpDTO();
        mockAddMonoAmpDTO.setId(1L);
        mockAddMonoAmpDTO.setBrand("BrandX");
        mockAddMonoAmpDTO.setModel("ModelX");
        mockAddMonoAmpDTO.setPrice("150");
        mockAddMonoAmpDTO.setAmplifierClass(AmpClass.D);
        mockAddMonoAmpDTO.setImpedance("8");
        mockAddMonoAmpDTO.setPower("8");
        mockAddMonoAmpDTO.setHighInputLevel("Yes");
        mockAddMonoAmpDTO.setLowPassFilter("No");
        mockAddMonoAmpDTO.setSubsonicFilter("Maybe");
        mockAddMonoAmpDTO.setRemoteControl(true);
        mockAddMonoAmpDTO.setBassBoost(true);
        mockAddMonoAmpDTO.setLowInputLevel("No");
        mockAddMonoAmpDTO.setHighInputLevel("Yes");
        mockAddMonoAmpDTO.setDistortion("4");
        mockAddMonoAmpDTO.setCurrentDraw("4");
        mockAddMonoAmpDTO.setFuseRating("3");
        mockAddMonoAmpDTO.setNumberOfRca("2");
        mockAddMonoAmpDTO.setNumberOfSpeakerOutputs("2");
        mockAddMonoAmpDTO.setHeight("2");
        mockAddMonoAmpDTO.setWidth("2");
        mockAddMonoAmpDTO.setLength("2");
        mockAddMonoAmpDTO.setImages(List.of("http://example.com/image1.jpg"));

        Mockito.when(monoChannelAmplifierService.editDevice(Mockito.any(AddMonoAmpDTO.class))).thenReturn(1L);

        mockMvc.perform(post("/amplifiers/mono-amplifiers/edit/" + mockAddMonoAmpDTO.getId())
                        .with(csrf())
                        .flashAttr("monoAmpDetails", mockAddMonoAmpDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/amplifiers/mono-amplifiers/1"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testMonoChannelAmpDetails() throws Exception {
        Long deviceId = 1L;
        MonoAmpDetailsDTO mockDetailsDTO = new MonoAmpDetailsDTO();
        MonoAmpDetailsHelperDTO mockHelperDTO = new MonoAmpDetailsHelperDTO(mockDetailsDTO);

        Mockito.when(monoChannelAmplifierService.getDeviceDetails(deviceId)).thenReturn(mockDetailsDTO);
        Mockito.when(monoChannelAmplifierService.getDeviceDetailsHelper(deviceId)).thenReturn(mockHelperDTO);

        mockMvc.perform(get("/amplifiers/mono-amplifiers/" + deviceId))
                .andExpect(status().isOk())
                .andExpect(view().name("/amplifiers/monoamp-details"))
                .andExpect(model().attributeExists("monoAmpDetails"))
                .andExpect(model().attributeExists("helperDTO"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testDeleteMonoChannelAmp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/amplifiers/mono-amplifiers/delete/" + 1L)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testRankings() throws Exception {
        MonoAmpSummaryDTO monoAmpSummaryDTO = new MonoAmpSummaryDTO();
        monoAmpSummaryDTO.setId(1L);
        monoAmpSummaryDTO.setImages(List.of("http://www.caraudio-bg.com/mtmw312d2plus/img.jpg"));
        monoAmpSummaryDTO.setBrand("mtm");
        monoAmpSummaryDTO.setModel("w312d2+");
        monoAmpSummaryDTO.setPower((short) 3000);
        monoAmpSummaryDTO.setAmplifierClass("D");
        List<MonoAmpSummaryDTO> mockSummaryList = List.of(monoAmpSummaryDTO);

        Mockito.when(monoChannelAmplifierService.getAllDeviceSummary()).thenReturn(mockSummaryList);

        mockMvc.perform(get("/amplifiers/mono-amplifiers/rankings"))
                .andExpect(status().isOk())
                .andExpect(view().name("/amplifiers/monoamp-all"))
                .andExpect(model().attributeExists("allDevices"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testLike() throws Exception {
        Long deviceId = 1L;

        mockMvc.perform(post("/amplifiers/mono-amplifiers/like/" + deviceId)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/amplifiers/mono-amplifiers/rankings"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testAddMonoChannelAmpPostWithErrors() throws Exception {
        AddMonoAmpDTO mockAddMonoAmpDTO = new AddMonoAmpDTO();
        mockAddMonoAmpDTO.setBrand("");
        mockAddMonoAmpDTO.setModel("");

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        lenient().when(bindingResult.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/amplifiers/mono-amplifiers/add")
                        .with(csrf())
                        .flashAttr("addMonoAmpDTO", mockAddMonoAmpDTO)
                        .flashAttr(BindingResult.MODEL_KEY_PREFIX + "addMonoAmpDTO", bindingResult))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/amplifiers/mono-amplifiers/add"))
                .andExpect(flash().attributeExists("addMonoAmpDTO"))
                .andExpect(flash().attributeExists(BindingResult.MODEL_KEY_PREFIX + "addMonoAmpDTO"));
    }

    @Test
    @WithMockUser(username = "DASKALA", roles = {"USER", "ADMIN"})
    public void testPostEditMonoChannelAmpWithErrors() throws Exception {
        final var mockAddMonoAmpDTO = getAddMonoAmpDTO();

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        lenient().when(bindingResult.hasErrors()).thenReturn(true);

        mockMvc.perform(post("/amplifiers/mono-amplifiers/edit/" + mockAddMonoAmpDTO.getId())
                        .with(csrf())
                        .flashAttr("monoAmpDetails", mockAddMonoAmpDTO)
                        .flashAttr(BindingResult.MODEL_KEY_PREFIX + "monoAmpDetails", bindingResult))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/amplifiers/mono-amplifiers/edit/" + mockAddMonoAmpDTO.getId()))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("monoAmpDetails"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists(BindingResult.MODEL_KEY_PREFIX + "monoAmpDetails"));
    }

    private static AddMonoAmpDTO getAddMonoAmpDTO() {
        AddMonoAmpDTO mockAddMonoAmpDTO = new AddMonoAmpDTO();
        mockAddMonoAmpDTO.setId(1L);
        mockAddMonoAmpDTO.setBrand("5555555555555");
        mockAddMonoAmpDTO.setModel("55555555555555555");
        mockAddMonoAmpDTO.setPrice("55555555555555555555");
        mockAddMonoAmpDTO.setAmplifierClass(AmpClass.D);
        mockAddMonoAmpDTO.setImpedance("555555555555555555");
        mockAddMonoAmpDTO.setPower("8");
        mockAddMonoAmpDTO.setHighInputLevel("Yes");
        mockAddMonoAmpDTO.setLowPassFilter("No");
        mockAddMonoAmpDTO.setSubsonicFilter("Maybe");
        mockAddMonoAmpDTO.setRemoteControl(true);
        mockAddMonoAmpDTO.setBassBoost(true);
        mockAddMonoAmpDTO.setLowInputLevel("No");
        mockAddMonoAmpDTO.setHighInputLevel("Yes");
        mockAddMonoAmpDTO.setDistortion("4");
        mockAddMonoAmpDTO.setCurrentDraw("4");
        mockAddMonoAmpDTO.setFuseRating("3");
        mockAddMonoAmpDTO.setNumberOfRca("2");
        mockAddMonoAmpDTO.setNumberOfSpeakerOutputs("2");
        mockAddMonoAmpDTO.setHeight("2");
        mockAddMonoAmpDTO.setWidth("2");
        mockAddMonoAmpDTO.setLength("2");
        mockAddMonoAmpDTO.setImages(List.of("http://example.com/image1.jpg"));
        return mockAddMonoAmpDTO;
    }
}
