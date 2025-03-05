package com.bg.bassheadsbg.scheduling;

import com.bg.bassheadsbg.repository.HighRangeRepository;
import com.bg.bassheadsbg.repository.MidRangeRepository;
import com.bg.bassheadsbg.repository.MonoAmplifierRepository;
import com.bg.bassheadsbg.repository.MultiChannelAmplifierRepository;
import com.bg.bassheadsbg.repository.PowerCableRepository;
import com.bg.bassheadsbg.repository.SubwooferRepository;
import com.bg.bassheadsbg.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CronScheduler {
    private final UserRepository userRepository;
    private final HighRangeRepository highRangeRepository;
    private final MidRangeRepository midRangeRepository;
    private final SubwooferRepository subwooferRepository;
    private final MonoAmplifierRepository monoAmplifierRepository;
    private final MultiChannelAmplifierRepository multiChannelAmplifierRepository;
    private final PowerCableRepository powerCableRepository;

    public CronScheduler(UserRepository userRepository, HighRangeRepository highRangeRepository, MidRangeRepository midRangeRepository, SubwooferRepository subwooferRepository, MonoAmplifierRepository monoAmplifierRepository, MultiChannelAmplifierRepository multiChannelAmplifierRepository, PowerCableRepository powerCableRepository) {
        this.userRepository = userRepository;
        this.highRangeRepository = highRangeRepository;
        this.midRangeRepository = midRangeRepository;
        this.subwooferRepository = subwooferRepository;

        this.monoAmplifierRepository = monoAmplifierRepository;
        this.multiChannelAmplifierRepository = multiChannelAmplifierRepository;
        this.powerCableRepository = powerCableRepository;
    }

    @Scheduled(cron = "0 0/30 * * * ?")
    public void onCron() {
        if (log.isInfoEnabled()) {
            StringBuilder logMessage = new StringBuilder();
            logMessage.append("*********************************************\n")
                    .append("---------------------------------------------\n");

            long totalUsers = userRepository.count();

            logMessage.append("Number of users: ").append(totalUsers).append("\n")
                    .append("---------------------------------------------\n")
                    .append("__SPEAKERS__\n");

            long totalHighRangeSpeakers = highRangeRepository.count();
            long totalMidRangeSpeakers = midRangeRepository.count();
            long totalSubwoofers = subwooferRepository.count();

            logMessage.append("Number of HighRange Speakers: ").append(totalHighRangeSpeakers).append("\n")
                    .append("Number of MidRange Speakers: ").append(totalMidRangeSpeakers).append("\n")
                    .append("Number of Subwoofers: ").append(totalSubwoofers).append("\n")
                    .append("Total speakers: ").append(totalHighRangeSpeakers + totalMidRangeSpeakers + totalSubwoofers).append("\n")
                    .append("---------------------------------------------\n")
                    .append("__AMPLIFIERS__\n");

            long totalMonoChannelAmps = monoAmplifierRepository.count();
            long totalMultiChannelAmps = multiChannelAmplifierRepository.count();

            logMessage.append("Number of Mono-block Amps: ").append(totalMonoChannelAmps).append("\n")
                    .append("Number of Multi-Channel Amps: ").append(totalMultiChannelAmps).append("\n")
                    .append("Total Amplifiers: ").append(totalMonoChannelAmps + totalMultiChannelAmps).append("\n")
                    .append("---------------------------------------------\n")
                    .append("__CABLES__\n");

            long totalPowerCables = powerCableRepository.count();

            logMessage.append("Number of Power Cables: ").append(totalPowerCables).append("\n")
                    .append("Total Cables: ").append(totalPowerCables)
                    .append("*********************************************\n")
                    .append("▒█▀▀█ █▀▀█ █▀▀ █▀▀ ▒█░▒█ █▀▀ █▀▀█ █▀▀▄ █▀▀ 　 ▒█▀▀█ ▒█▀▀█ \n" +
                            "▒█▀▀▄ █▄▄█ ▀▀█ ▀▀█ ▒█▀▀█ █▀▀ █▄▄█ █░░█ ▀▀█ 　 ▒█▀▀▄ ▒█░▄▄ \n" +
                            "▒█▄▄█ ▀░░▀ ▀▀▀ ▀▀▀ ▒█░▒█ ▀▀▀ ▀░░▀ ▀▀▀░ ▀▀▀ 　 ▒█▄▄█ ▒█▄▄█");

            log.info(logMessage.toString());
        }
    }
}