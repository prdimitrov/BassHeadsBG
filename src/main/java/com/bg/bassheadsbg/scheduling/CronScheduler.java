package com.bg.bassheadsbg.scheduling;

import com.bg.bassheadsbg.model.entity.amplifiers.MonoAmplifier;
import com.bg.bassheadsbg.model.entity.amplifiers.MultiChannelAmplifier;
import com.bg.bassheadsbg.model.entity.speakers.HighRange;
import com.bg.bassheadsbg.model.entity.speakers.MidRange;
import com.bg.bassheadsbg.model.entity.speakers.Subwoofer;
import com.bg.bassheadsbg.model.entity.users.UserEntity;
import com.bg.bassheadsbg.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
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

    @Scheduled(cron = "30 * * * * *")
    public void onCron() {

        log.info("*********************************************");
        log.info("---------------------------------------------");
        List<UserEntity> usersList;
        usersList = new ArrayList<>(userRepository.findAll());
        log.info("Number of users: {}", usersList.size());
        log.info("---------------------------------------------");
        List<HighRange> highRangeList;
        List<MidRange> midRangeList;
        List<Subwoofer> subwoofersList;
        highRangeList = new ArrayList<>(highRangeRepository.findAll());
        midRangeList = new ArrayList<>(midRangeRepository.findAll());
        subwoofersList = new ArrayList<>(subwooferRepository.findAll());
        log.info("Number of HighRange Speakers: {}", highRangeList.size());
        log.info("Number of MidRange Speakers: {}", midRangeList.size());
        log.info("Number of Subwoofers: {}", subwoofersList.size());
        log.info("Total speakers: {}", subwoofersList.size() + midRangeList.size() + highRangeList.size());
        log.info("---------------------------------------------");
        List<MonoAmplifier> monoAmplifiers;
        monoAmplifiers = new ArrayList<>(monoAmplifierRepository.findAll());
        List<MultiChannelAmplifier> multiChannelAmplifiers;
        multiChannelAmplifiers = new ArrayList<>(multiChannelAmplifierRepository.findAll());

        log.info("Number of Mono-block Amps: {}", monoAmplifiers.size());
        log.info("Number of Multi-Channel Amps: {}", multiChannelAmplifiers.size());
        log.info("Total Amplifiers: {}", monoAmplifiers.size() + multiChannelAmplifiers.size());
        log.info("---------------------------------------------");
        log.info("*********************************************");
        log.info("\n" +
                "▒█▀▀█ █▀▀█ █▀▀ █▀▀ ▒█░▒█ █▀▀ █▀▀█ █▀▀▄ █▀▀ 　 ▒█▀▀█ ▒█▀▀█ \n" +
                "▒█▀▀▄ █▄▄█ ▀▀█ ▀▀█ ▒█▀▀█ █▀▀ █▄▄█ █░░█ ▀▀█ 　 ▒█▀▀▄ ▒█░▄▄ \n" +
                "▒█▄▄█ ▀░░▀ ▀▀▀ ▀▀▀ ▒█░▒█ ▀▀▀ ▀░░▀ ▀▀▀░ ▀▀▀ 　 ▒█▄▄█ ▒█▄▄█");

    }

}