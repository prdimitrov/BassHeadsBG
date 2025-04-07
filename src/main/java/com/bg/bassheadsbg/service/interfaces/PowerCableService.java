package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.add.AddPowerCableDTO;
import com.bg.bassheadsbg.model.dto.details.PowerCableDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.PowerCableSummaryDTO;
import com.bg.bassheadsbg.model.helpers.PowerCableDetailsHelperDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PowerCableService {

    AddPowerCableDTO createNewCableDTO();

    long addCable(AddPowerCableDTO addPowerCableDTO) throws IOException;

    long editCable(AddPowerCableDTO addPowerCableDTO, List<MultipartFile> multipartFiles) throws IOException;

    void deleteCable(long cableId);

    List<PowerCableSummaryDTO> getAllPowerCablesSummarySorted();

    PowerCableDetailsDTO getCableDetails(Long id);

    PowerCableDetailsHelperDTO getPowerCableDetailsHelper(Long id);

    void likeCable(Long id);
}
