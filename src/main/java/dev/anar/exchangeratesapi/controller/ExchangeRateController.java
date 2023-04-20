package dev.anar.exchangeratesapi.controller;

import dev.anar.exchangeratesapi.model.ExchangeRateDto;
import dev.anar.exchangeratesapi.service.ExchangeRateService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@Validated
@RequestMapping("/exchange-rates")
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(title = "AZN to Foreign Currencies API"))
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @Operation(summary = "Retrieve from CBAR all currencies by date")
    @PostMapping("/collect")
    public ResponseEntity<String> collectExchangeRates(@RequestParam("date") @Parameter(description = "Date", example = "25.01.2023") @DateTimeFormat(pattern = "dd.MM.yyyy") @PastOrPresent LocalDate date) throws Exception {
        exchangeRateService.collectExchangeRates(date);
        return ResponseEntity.ok().body("Exchange rates collected and saved to database");
    }

    @Operation(summary = "Get currencies")
    @GetMapping
    public ResponseEntity<List<ExchangeRateDto>> getExchangeRates(@RequestParam(required = false) @Parameter(description = "Date", example = "25.01.2023") @DateTimeFormat(pattern = "dd.MM.yyyy") @PastOrPresent LocalDate date,
                                                                  @RequestParam(required = false) @Parameter(description = "ISO 4217 currency code", example = "USD") String currency) {
        List<ExchangeRateDto> exchangeRates = null;
        if (!Objects.isNull(date) && !Objects.isNull(currency)) {
            exchangeRates = exchangeRateService.getExchangeRateByCurrencyAndDate(currency, date);
        }
        if (!Objects.isNull(date) && Objects.isNull(currency)) {
            exchangeRates = exchangeRateService.getExchangeRatesByDate(date);
        }
        if (Objects.isNull(date) && !Objects.isNull(currency)) {
            exchangeRates = exchangeRateService.getExchangeRatesByCurrency(currency);
        }
        if (Objects.isNull(date) && Objects.isNull(currency)) {
            exchangeRates = exchangeRateService.getAllExchangeRates();
        }
        return ResponseEntity.ok().body(exchangeRates);
    }

    @Operation(summary = "Delete currency data by date")
    @DeleteMapping
    public ResponseEntity<String> deleteExchangeRatesByDate(@RequestParam @Parameter(description = "Date", example = "25.01.2023") @DateTimeFormat(pattern = "dd.MM.yyyy") @PastOrPresent LocalDate date) {
        if (exchangeRateService.getExchangeRatesByDate(date).isEmpty()) {
            return ResponseEntity.ok().body("Exchange rates not found by date from database");
        } else {
            exchangeRateService.deleteExchangeRatesByDate(date);
            return ResponseEntity.ok().body("Exchange rates deleted by date from database");
        }
    }

}


