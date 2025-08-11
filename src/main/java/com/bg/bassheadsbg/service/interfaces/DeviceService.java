package com.bg.bassheadsbg.service.interfaces;

import com.bg.bassheadsbg.exception.DeviceNotFoundException;

import java.io.IOException;
import java.util.List;

/**
 * Service for managing devices.
 * This class provides methods for creating, editing, deleting, and retrieving devices,
 * also handling the likes and updating images.
 */
public interface DeviceService<AddDTO, DetailsDTO, SummaryDTO, HelperDTO> {

    /**
     * Creates a new instance of AddDTO.
     *
     * @return a new AddDTO object
     */
    AddDTO createNewDevice();

    /**
     * Adds a new device.
     *
     * @param dto the DTO containing device information
     * @return the ID of the newly added device
     * @throws IOException if an error occurs while processing the images.
     */
    long addDevice(final AddDTO dto) throws IOException;

    /**
     * This method is used for editing an already existing device.
     *
     * @param dto the DTO, that should contain information about the updated device.
     * @return the ID of the edited device.
     * @throws IOException if an error occurs while processing the images.
     */
    long editDevice(final AddDTO dto) throws IOException;

    /**
     * Deletes a device by ID.
     *
     * @param id the ID of the device, that has to be deleted.
     */
    void deleteDevice(final long id);

    /**
     * The method is used for retrieving a summary of all devices.
     *
     * @return a list of summaries of device.
     */
    List<SummaryDTO> getAllDeviceSummarySorted();

    /**
     * The method is used to retrieve details of a device by its ID.
     *
     * @param id the ID of the device
     * @return the details of the device
     * @throws DeviceNotFoundException if the device with the given ID is not found
     */
    DetailsDTO getDeviceDetails(final Long id) throws DeviceNotFoundException;

    /**
     * Retrieves helper details for a device by its ID.
     *
     * @param id the ID of the device
     * @return the helper details of the device
     */
    HelperDTO getDeviceDetailsHelper(final Long id);

    /**
     * Method for liking a device.
     *
     * @param id the ID of the device, that should be liked.
     * @return
     */
    boolean likeDevice(final Long id);
}