package dev.anar.exchangeratesapi.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExchangeRateDto {
    private LocalDate date;
    private String currencyCode;
    private BigDecimal rate;

}

