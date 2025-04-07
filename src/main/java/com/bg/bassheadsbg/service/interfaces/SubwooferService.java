package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.add.AddSubwooferDTO;
import com.bg.bassheadsbg.model.dto.details.SubwooferDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.SubwooferSummaryDTO;
import com.bg.bassheadsbg.model.entity.speakers.Subwoofer;
import com.bg.bassheadsbg.model.helpers.SubwooferDetailsHelperDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SubwooferService {
    AddSubwooferDTO createNewSpeaker();

    long addSpeaker(AddSubwooferDTO addSubwooferDTO) throws IOException;

    long editSpeaker(AddSubwooferDTO addSubwooferDTO, List<MultipartFile> multipartFiles) throws IOException;

    void deleteSpeaker(long speakerId);

    List<SubwooferSummaryDTO> getAllSpeakersSummarySorted();

    SubwooferDetailsDTO getSpeakerDetails(Long id);

    SubwooferDetailsHelperDTO getSpeakerDetailsHelper(Long id);

    void likeSpeaker(Long id);
}
