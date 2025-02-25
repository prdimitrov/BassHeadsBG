package com.bg.bassheadsbg.util;

import com.bg.bassheadsbg.model.entity.users.UserEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ObjectLogger {

    public static void logMessage(UserEntity userEntity,
                                  String action,
                                  Object object,
                                  long id,
                                  String brand,
                                  String model) {
        log.info("User with id ({}) and username ({}) {} {} object with ID ({}), brand ({}) and model ({}).",
                userEntity.getId(),
                userEntity.getUsername(),
                action,
                object.getClass().getName(),
                id,
                brand,
                model);
    }

    public static void logMessageWithoutImages(UserEntity userEntity,
                                            String action,
                                            Object object,
                                            long id,
                                            String brand,
                                            String model) {
        log.info("User with id ({}) and username ({}) {} {} object with ID ({}), brand ({}) and model ({}), but did not upload any images.",
                userEntity.getId(),
                userEntity.getUsername(),
                action,
                object.getClass().getName(),
                id,
                brand,
                model);
    }

    public static void logDeleteMessage(UserEntity user,
                                        Object object,
                                        String brand,
                                        String model) {
        log.info("User with id ({}) and username ({}) deleted {} object, with brand ({}) and model ({}).",
                user.getId(),
                user.getUsername(),
                object.getClass().getName(),
                brand,
                model);
    }
}
