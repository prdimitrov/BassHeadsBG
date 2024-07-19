package com.bg.bassheadsbg.web.rest;

import com.bg.bassheadsbg.exception.ApiNotFoundException;
import com.bg.bassheadsbg.model.dto.exchanges.ConversionResultDTO;
import com.bg.bassheadsbg.service.interfaces.ExRateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class CurrencyController {

    private final ExRateService exRateService;

    public CurrencyController(ExRateService exRateService) {
        this.exRateService = exRateService;
    }

    @GetMapping("/api/convert")
    public ResponseEntity<ConversionResultDTO> convert(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("amount") BigDecimal amount
    ) {
        BigDecimal result = exRateService.convert(from, to, amount);

        return ResponseEntity.ok(new ConversionResultDTO(
                from,
                to,
                amount,
                result
        ));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ApiNotFoundException.class)
    @ResponseBody
    public NotFoundErrorInfo handleApiObjectNotFoundException(ApiNotFoundException apiObjectNotFoundException) {
        return new NotFoundErrorInfo("NOT FOUND", apiObjectNotFoundException.getId());
    }


    public record NotFoundErrorInfo(String code, Object id) {

    }
}