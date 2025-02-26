package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.add.AddHighRangeDTO;
import com.bg.bassheadsbg.model.dto.details.HighRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.HighRangeSummaryDTO;
import com.bg.bassheadsbg.model.helpers.HighRangeDetailsHelperDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface HighRangeService {
    AddHighRangeDTO createNewSpeaker();

    long addSpeaker(AddHighRangeDTO addHighRangeDTO) throws IOException;

    long editSpeaker(AddHighRangeDTO addHighRangeDTO, List<MultipartFile> multipartFiles) throws IOException;

    void deleteSpeaker(long speakerId);

    List<HighRangeSummaryDTO> getAllSpeakerSummary();

    HighRangeDetailsDTO getSpeakerDetails(Long id);

    HighRangeDetailsHelperDTO getSpeakerDetailsHelper(Long id);

    void likeSpeaker(Long id);
}