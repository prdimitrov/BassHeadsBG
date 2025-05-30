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
    protected static final String ASTERIX_BREAKER = "*********************************************\n";
    protected static final String MINUS_BREAKER = "---------------------------------------------\n";
    protected static final String CRON_EXPRESSION = "0 0/30 * * * ?";
    protected static final String NUMBER_OF_USERS = "Number of users: ";
    protected static final String SPEAKERS_BREAKER = "__SPEAKERS__\n";
    protected static final String NUMBER_OF_HIGH_RANGE_SPEAKERS = "Number of HighRange Speakers: ";
    protected static final String NUMBER_OF_MID_RANGE_SPEAKERS = "Number of MidRange Speakers: ";
    protected static final String NUMBER_OF_SUBWOOFERS = "Number of Subwoofers: ";
    protected static final String TOTAL_SPEAKERS = "Total speakers: ";
    protected static final String AMPLIFIERS_BREAKER = "__AMPLIFIERS__\n";
    protected static final String NUMBER_OF_MONO_BLOCK_AMPS = "Number of Mono-block Amps: ";
    protected static final String NUMBER_OF_MULTI_CHANNEL_AMPS = "Number of Multi-Channel Amps: ";
    protected static final String TOTAL_AMPLIFIERS = "Total Amplifiers: ";
    protected static final String CABLES_BREAKER = "__CABLES__\n";
    protected static final String NUMBER_OF_POWER_CABLES = "Number of Power Cables: ";
    protected static final String TOTAL_CABLES = "Total Cables: ";
    protected static final String LINE_BREAKER = "\n";
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

    @Scheduled(cron = CRON_EXPRESSION)
    public void onCron() {
        if (log.isInfoEnabled()) {
            StringBuilder logMessage = new StringBuilder();
            logMessage.append(ASTERIX_BREAKER)
                    .append(MINUS_BREAKER);

            long totalUsers = userRepository.count();

            logMessage.append(NUMBER_OF_USERS).append(totalUsers).append(LINE_BREAKER)
                    .append(MINUS_BREAKER)
                    .append(SPEAKERS_BREAKER);

            long totalHighRangeSpeakers = highRangeRepository.count();
            long totalMidRangeSpeakers = midRangeRepository.count();
            long totalSubwoofers = subwooferRepository.count();

            logMessage.append(NUMBER_OF_HIGH_RANGE_SPEAKERS).append(totalHighRangeSpeakers).append(LINE_BREAKER)
                    .append(NUMBER_OF_MID_RANGE_SPEAKERS).append(totalMidRangeSpeakers).append(LINE_BREAKER)
                    .append(NUMBER_OF_SUBWOOFERS).append(totalSubwoofers).append(LINE_BREAKER)
                    .append(TOTAL_SPEAKERS).append(totalHighRangeSpeakers + totalMidRangeSpeakers + totalSubwoofers).append(LINE_BREAKER)
                    .append(MINUS_BREAKER)
                    .append(AMPLIFIERS_BREAKER);

            long totalMonoChannelAmps = monoAmplifierRepository.count();
            long totalMultiChannelAmps = multiChannelAmplifierRepository.count();

            logMessage.append(NUMBER_OF_MONO_BLOCK_AMPS).append(totalMonoChannelAmps).append(LINE_BREAKER)
                    .append(NUMBER_OF_MULTI_CHANNEL_AMPS).append(totalMultiChannelAmps).append(LINE_BREAKER)
                    .append(TOTAL_AMPLIFIERS).append(totalMonoChannelAmps + totalMultiChannelAmps).append(LINE_BREAKER)
                    .append(MINUS_BREAKER)
                    .append(CABLES_BREAKER);

            long totalPowerCables = powerCableRepository.count();

            logMessage.append(NUMBER_OF_POWER_CABLES).append(totalPowerCables).append(LINE_BREAKER)
                    .append(TOTAL_CABLES).append(totalPowerCables)
                    .append(ASTERIX_BREAKER)
                    .append("▒█▀▀█ █▀▀█ █▀▀ █▀▀ ▒█░▒█ █▀▀ █▀▀█ █▀▀▄ █▀▀ 　 ▒█▀▀█ ▒█▀▀█ \n" +
                            "▒█▀▀▄ █▄▄█ ▀▀█ ▀▀█ ▒█▀▀█ █▀▀ █▄▄█ █░░█ ▀▀█ 　 ▒█▀▀▄ ▒█░▄▄ \n" +
                            "▒█▄▄█ ▀░░▀ ▀▀▀ ▀▀▀ ▒█░▒█ ▀▀▀ ▀░░▀ ▀▀▀░ ▀▀▀ 　 ▒█▄▄█ ▒█▄▄█");

            log.info(logMessage.toString());
        }
    }
}