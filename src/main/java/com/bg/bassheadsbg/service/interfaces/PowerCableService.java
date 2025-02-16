package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.add.AddPowerCableDTO;
import com.bg.bassheadsbg.model.dto.details.PowerCableDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.PowerCableSummaryDTO;
import com.bg.bassheadsbg.model.entity.cables.PowerCable;
import com.bg.bassheadsbg.model.helpers.PowerCableDetailsHelperDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PowerCableService {

    AddPowerCableDTO createNewCableDTO();

    long addCable(AddPowerCableDTO addPowerCableDTO, List<MultipartFile> multipartFiles) throws IOException;

    List<byte[]> getCableImages(Long cableId);

    Optional<PowerCable> getCable(Long id);

    long editCable(AddPowerCableDTO addPowerCableDTO, List<MultipartFile> multipartFiles) throws IOException;

    void deleteCable(long cableId);

    List<PowerCableSummaryDTO> getAllCableSummary();

    PowerCableDetailsDTO getCableDetails(Long id);

    PowerCableDetailsHelperDTO getPowerCableDetailsHelper(Long id);

}
