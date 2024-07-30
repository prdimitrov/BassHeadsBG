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
        if (log.isInfoEnabled()) {
            StringBuilder logMessage = new StringBuilder();
            logMessage.append("*********************************************\n")
                    .append("---------------------------------------------\n");

            List<UserEntity> usersList = userRepository.findAll();
            logMessage.append("Number of users: ").append(usersList.size()).append("\n")
                    .append("---------------------------------------------\n");

            List<HighRange> highRangeList = highRangeRepository.findAll();
            List<MidRange> midRangeList = midRangeRepository.findAll();
            List<Subwoofer> subwoofersList = subwooferRepository.findAll();
            logMessage.append("Number of HighRange Speakers: ").append(highRangeList.size()).append("\n")
                    .append("Number of MidRange Speakers: ").append(midRangeList.size()).append("\n")
                    .append("Number of Subwoofers: ").append(subwoofersList.size()).append("\n")
                    .append("Total speakers: ").append(highRangeList.size() + midRangeList.size() + subwoofersList.size()).append("\n")
                    .append("---------------------------------------------\n");

            List<MonoAmplifier> monoAmplifiers = monoAmplifierRepository.findAll();
            List<MultiChannelAmplifier> multiChannelAmplifiers = multiChannelAmplifierRepository.findAll();
            logMessage.append("Number of Mono-block Amps: ").append(monoAmplifiers.size()).append("\n")
                    .append("Number of Multi-Channel Amps: ").append(multiChannelAmplifiers.size()).append("\n")
                    .append("Total Amplifiers: ").append(monoAmplifiers.size() + multiChannelAmplifiers.size()).append("\n")
                    .append("---------------------------------------------\n")
                    .append("*********************************************\n")
                    .append("▒█▀▀█ █▀▀█ █▀▀ █▀▀ ▒█░▒█ █▀▀ █▀▀█ █▀▀▄ █▀▀ 　 ▒█▀▀█ ▒█▀▀█ \n" +
                            "▒█▀▀▄ █▄▄█ ▀▀█ ▀▀█ ▒█▀▀█ █▀▀ █▄▄█ █░░█ ▀▀█ 　 ▒█▀▀▄ ▒█░▄▄ \n" +
                            "▒█▄▄█ ▀░░▀ ▀▀▀ ▀▀▀ ▒█░▒█ ▀▀▀ ▀░░▀ ▀▀▀░ ▀▀▀ 　 ▒█▄▄█ ▒█▄▄█");

            log.info(logMessage.toString());
        }
    }
}