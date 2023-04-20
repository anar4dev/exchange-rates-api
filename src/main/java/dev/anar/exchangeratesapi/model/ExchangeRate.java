package dev.anar.exchangeratesapi.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "exchange_rates")
@Data
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "exchange_date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "currency_code", nullable = false)
    private String currencyCode;

    @NotNull
    @Column(name = "exchange_rate_value", nullable = false)
    private BigDecimal exchangeRateValue;

    public ExchangeRate() {
    }

}
