package com.bg.bassheadsbg.scheduling;

import com.bg.bassheadsbg.model.entity.amplifiers.MonoAmplifier;
import com.bg.bassheadsbg.model.entity.amplifiers.MultiChannelAmplifier;
import com.bg.bassheadsbg.model.entity.speakers.HighRange;
import com.bg.bassheadsbg.model.entity.speakers.MidRange;
import com.bg.bassheadsbg.model.entity.speakers.Subwoofer;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CronScheduler {
    private final UserRepository userRepository;
    private final HighRangeRepository highRangeRepository;
    private final MidRangeRepository midRangeRepository;
    private final SubwooferRepository subwooferRepository;
    private final MonoAmplifierRepository monoAmplifierRepository;
    private final MultiChannelAmplifierRepository multiChannelAmplifierRepository;

    public CronScheduler(UserRepository userRepository, HighRangeRepository highRangeRepository, MidRangeRepository midRangeRepository, SubwooferRepository subwooferRepository, MonoAmplifierRepository monoAmplifierRepository, MultiChannelAmplifierRepository multiChannelAmplifierRepository) {
        this.userRepository = userRepository;
        this.highRangeRepository = highRangeRepository;
        this.midRangeRepository = midRangeRepository;
        this.subwooferRepository = subwooferRepository;

        this.monoAmplifierRepository = monoAmplifierRepository;
        this.multiChannelAmplifierRepository = multiChannelAmplifierRepository;
    }

    private final Logger LOGGER = LoggerFactory.getLogger(CronScheduler.class);

    //TODO Timer for Cron job

    @Scheduled(cron = "1 * * * * *")
    public void onCron() {

        LOGGER.info("*********************************************");
        LOGGER.info("---------------------------------------------");
        List<UserEntity> usersList;
        usersList = new ArrayList<>(userRepository.findAll());
        LOGGER.info("Number of users: {}", usersList.size());
        LOGGER.info("---------------------------------------------");
        List<HighRange> highRangeList;
        List<MidRange> midRangeList;
        List<Subwoofer> subwoofersList;
        highRangeList = new ArrayList<>(highRangeRepository.findAll());
        midRangeList = new ArrayList<>(midRangeRepository.findAll());
        subwoofersList = new ArrayList<>(subwooferRepository.findAll());
        LOGGER.info("Number of HighRange Speakers: {}", highRangeList.size());
        LOGGER.info("Number of MidRange Speakers: {}", midRangeList.size());
        LOGGER.info("Number of Subwoofers: {}", subwoofersList.size());
        LOGGER.info("Total speakers: {}", subwoofersList.size() + midRangeList.size() + highRangeList.size());
        LOGGER.info("---------------------------------------------");
        List<MonoAmplifier> monoAmplifiers;
        monoAmplifiers = new ArrayList<>(monoAmplifierRepository.findAll());
        List<MultiChannelAmplifier> multiChannelAmplifiers;
        multiChannelAmplifiers = new ArrayList<>(multiChannelAmplifierRepository.findAll());

        LOGGER.info("Number of Mono-block Amps: {}", monoAmplifiers.size());
        LOGGER.info("Number of Multi-Channel Amps: {}", multiChannelAmplifiers.size());
        LOGGER.info("Total Amplifiers: {}", monoAmplifiers.size() + multiChannelAmplifiers.size());
        LOGGER.info("---------------------------------------------");
        LOGGER.info("*********************************************");
        LOGGER.info("\n" +
                "▒█▀▀█ █▀▀█ █▀▀ █▀▀ ▒█░▒█ █▀▀ █▀▀█ █▀▀▄ █▀▀ 　 ▒█▀▀█ ▒█▀▀█ \n" +
                "▒█▀▀▄ █▄▄█ ▀▀█ ▀▀█ ▒█▀▀█ █▀▀ █▄▄█ █░░█ ▀▀█ 　 ▒█▀▀▄ ▒█░▄▄ \n" +
                "▒█▄▄█ ▀░░▀ ▀▀▀ ▀▀▀ ▒█░▒█ ▀▀▀ ▀░░▀ ▀▀▀░ ▀▀▀ 　 ▒█▄▄█ ▒█▄▄█");

    }

    //TODO Cron Scheduler have to delete old user offers.

}