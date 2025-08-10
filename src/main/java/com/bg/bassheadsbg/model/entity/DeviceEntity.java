package com.bg.bassheadsbg.model.entity;

import com.bg.bassheadsbg.model.entity.users.UserEntity;

import java.util.List;

public interface DeviceEntity<I extends DeviceImageEntity<?>> {
    long getId();

    String getBrand();

    String getModel();

    List<I> getImageFiles();

    List<UserEntity> getUserLikes();

    long getLikes();
}