package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.add.AddMonoAmpDTO;
import com.bg.bassheadsbg.model.dto.details.MonoAmpDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MonoAmpSummaryDTO;
import com.bg.bassheadsbg.model.helpers.MonoAmpDetailsHelperDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MonoAmpService {

    AddMonoAmpDTO createNewAmplifier();

    long addAmplifier(AddMonoAmpDTO addMonoAmpDTO) throws IOException;

    long editAmplifier(AddMonoAmpDTO addMonoAmpDTO, List<MultipartFile> multipartFiles) throws IOException;

    void deleteAmplifier(long amplifierId);

    List<MonoAmpSummaryDTO> getAllAmplifiersSummarySorted();

    MonoAmpDetailsDTO getAmplifierDetails(Long id);

    MonoAmpDetailsHelperDTO getAmplifierDetailsHelper(Long id);

    boolean likeAmplifier(Long id);
}