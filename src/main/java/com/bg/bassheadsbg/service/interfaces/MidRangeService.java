package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.add.AddMidRangeDTO;
import com.bg.bassheadsbg.model.dto.details.MidRangeDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.MidRangeSummaryDTO;
import com.bg.bassheadsbg.model.helpers.MidRangeDetailsHelperDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MidRangeService {
    AddMidRangeDTO createNewSpeaker();

    long addSpeaker(AddMidRangeDTO addMidRangeDTO) throws IOException;

    long editSpeaker(AddMidRangeDTO addMidRangeDTO, List<MultipartFile> multipartFiles) throws IOException;

    void deleteSpeaker(long speakerId);

    List<MidRangeSummaryDTO> getAllSpeakersSummarySorted();

    MidRangeDetailsDTO getSpeakerDetails(Long id);

    MidRangeDetailsHelperDTO getSpeakerDetailsHelper(Long id);

    void likeSpeaker(Long id);
}