package dev.anar.exchangeratesapi.repository;

import dev.anar.exchangeratesapi.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    List<ExchangeRate> findAllByDate(LocalDate date);

    List<ExchangeRate> findAllByCurrencyCode(String currency);

    List<ExchangeRate> findByCurrencyCodeAndDate(String currency, LocalDate date);

    void deleteAllByDate(LocalDate date);
}

