package com.bg.bassheadsbg.service.implementation;


import com.bg.bassheadsbg.config.ForexApiConfig;
import com.bg.bassheadsbg.exception.ApiNotFoundException;
import com.bg.bassheadsbg.model.dto.exchanges.ExRatesDTO;
import com.bg.bassheadsbg.model.entity.ExRateEntity;
import com.bg.bassheadsbg.repository.ExRateRepository;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service implementation for managing exchange rates from www.exchangerates.org.
 * This class provides methods for fetching, updating, and converting the exchange rates.
 */
@Service
public class ExRateServiceImpl implements ExRateService {

    private final Logger LOGGER = LoggerFactory.getLogger(ExRateServiceImpl.class);
    private final ExRateRepository exRateRepository;
    private final RestClient restClient;
    private final ForexApiConfig forexApiConfig;

    public ExRateServiceImpl(ExRateRepository exRateRepository,
                             RestClient restClient,
                             ForexApiConfig forexApiConfig) {
        this.exRateRepository = exRateRepository;
        this.restClient = restClient;
        this.forexApiConfig = forexApiConfig;
    }

    /**
     * Returns a list of all supported currencies.
     *
     * @return a list of currency codes
     */
    @Override
    public List<String> allSupportedCurrencies() {
        return exRateRepository
                .findAll()
                .stream()
                .map(ExRateEntity::getCurrency)
                .toList();
    }

    /**
     * Checks if the exchange rates have been initialized in the database.
     *
     * @return boolean - true if exchange rates have been initialized, otherwise false
     */
    @Override
    public boolean hasInitializedExRates() {
        return exRateRepository.count() > 0;
    }

    /**
     * Fetches the exchange rates from the Forex API (www.exchangerates.org).
     *
     * @return an ExRatesDTO that contains the fetched exchange rates
     */
    @Override
    public ExRatesDTO fetchExRates() {
        return restClient
                .get()
                .uri(forexApiConfig.getUrl(), forexApiConfig.getKey())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ExRatesDTO.class);
    }

    /**
     * Updates the exchange rates in the database with the provided exchange rates.
     *
     * @param exRatesDTO the exchange rates to update
     */
    @Override
    public void updateRates(ExRatesDTO exRatesDTO) {
        LOGGER.info("Updating {} rates.", exRatesDTO.rates().size());

        if (!forexApiConfig.getBase().equals(exRatesDTO.base())) {
            throw new IllegalArgumentException("The exchange rates that should be updated are not based on " +
                    forexApiConfig.getBase() + " but rather on " + exRatesDTO.base());
        }

        exRatesDTO.rates().forEach((currency, rate) -> {
            var exRateEntity = exRateRepository.findByCurrency(currency)
                    .orElseGet(() -> new ExRateEntity().setCurrency(currency));

            exRateEntity.setRate(rate);

            exRateRepository.save(exRateEntity);
        });
    }

    /**
     * Finds the exchange rate between two currencies.
     *
     * @param from - the source currency
     * @param to - the target currency
     * @return an Optional containing the exchange rate if it's found, or empty if the exchange rate is not found
     */
    private Optional<BigDecimal> findExRate(String from, String to) {

        if (Objects.equals(from, to)) {
            return Optional.of(BigDecimal.ONE);
        }

        // USD/BGN=x
        // USD/EUR=y
        //USD = x * BGN
        //USD = y * EUR
        //EUR/BGN = x / y

        Optional<BigDecimal> fromOpt = forexApiConfig.getBase().equals(from) ?
                Optional.of(BigDecimal.ONE) :
                exRateRepository.findByCurrency(from).map(ExRateEntity::getRate);

        Optional<BigDecimal> toOpt = forexApiConfig.getBase().equals(to) ?
                Optional.of(BigDecimal.ONE) :
                exRateRepository.findByCurrency(to).map(ExRateEntity::getRate);

        if (fromOpt.isEmpty() || toOpt.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(toOpt.get().divide(fromOpt.get(), 2, RoundingMode.HALF_DOWN));
        }
    }

    /**
     * Converts an amount from one currency to another currency.
     *
     * @param from - the source currency
     * @param to - the target currency
     * @param amount - the amount that has to be converted
     * @return the converted amount
     * @throws ApiNotFoundException if the conversion is not possible
     */
    @Override
    public BigDecimal convert(String from, String to, BigDecimal amount) {
        return findExRate(from, to)
                .orElseThrow(() -> new ApiNotFoundException("Conversion from " + from + " to " + to + " not possible!", from + "~" + to))
                .multiply(amount);
    }

    /**
     * Retrieves all exchange rates from the database.
     *
     * @return a list, containing all of the ExRateEntity objects
     */
    @Override
    public List<ExRateEntity> getAllExRates() {
        return exRateRepository.findAll();
    }
}