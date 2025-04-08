package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.add.AddMultiChannelAmpDTO;
import com.bg.bassheadsbg.model.dto.details.MultiChannelAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MultiChannelAmpSummaryDTO;
import com.bg.bassheadsbg.model.helpers.MultiChannelAmpDetailsHelperDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MultiChannelAmpService {

    AddMultiChannelAmpDTO createNewAmplifier();

    long addAmplifier(AddMultiChannelAmpDTO addMultiChannelAmpDTO) throws IOException;

    long editAmplifier(AddMultiChannelAmpDTO addMultiChannelAmpDTO, List<MultipartFile> multipartFiles) throws IOException;

    void deleteAmplifier(long amplifierId);

    List<MultiChannelAmpSummaryDTO> getAllAmplifiersSummarySorted();

    MultiChannelAmpDetailsDTO getAmplifierDetails(Long id);

    MultiChannelAmpDetailsHelperDTO getAmplifierDetailsHelper(Long id);

    boolean likeAmplifier(Long id);
}