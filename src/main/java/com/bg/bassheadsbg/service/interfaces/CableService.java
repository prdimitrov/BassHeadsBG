package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.model.dto.add.AddCableDTO;
import com.bg.bassheadsbg.model.dto.details.CableDetailsDTO;
import com.bg.bassheadsbg.model.dto.summary.CableSummaryDTO;

import java.util.List;

public interface CableService {

    AddCableDTO createNewCableDTO();

    long addCable(AddCableDTO addCableDTO);

    long editCable(AddCableDTO addCableDTO);

    void deleteCable(long cableId);

    List<CableSummaryDTO> getAllCableSummary();

    CableDetailsDTO getCableDetails(Long id);
}
