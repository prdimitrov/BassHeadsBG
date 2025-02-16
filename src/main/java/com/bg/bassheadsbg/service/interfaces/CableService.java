package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.add.AddCableDTO;
import com.bg.bassheadsbg.model.dto.details.CableDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.CableSummaryDTO;
import com.bg.bassheadsbg.model.entity.other.Cable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CableService {

    AddCableDTO createNewCableDTO();

    long addCable(AddCableDTO addCableDTO, List<MultipartFile> multipartFiles) throws IOException;

    List<byte[]> getCableImages(Long cableId);

    Optional<Cable> getCable(Long id);

    long editCable(AddCableDTO addCableDTO);

    void deleteCable(long cableId);

    List<CableSummaryDTO> getAllCableSummary();

    CableDetailsDTO getCableDetails(Long id);
}
