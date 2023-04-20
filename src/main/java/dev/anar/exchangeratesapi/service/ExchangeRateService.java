package dev.anar.exchangeratesapi.service;

import dev.anar.exchangeratesapi.model.*;
import dev.anar.exchangeratesapi.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static dev.anar.exchangeratesapi.constants.Contstants.*;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    private final RestTemplate restTemplate;

    public void collectExchangeRates(LocalDate date) throws Exception {
        //check if exist in db records for this date
        List<ExchangeRate> exchangeRatesFromDb = exchangeRateRepository.findAllByDate(date);
        if (exchangeRatesFromDb.isEmpty()) {
            // Construct the URL for the API call
            String apiUrl = CBAR_API_BASE_URL + date.format(DATE_FORMATTER) + FILE_TYPE_XML;

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new Exception("Failed to retrieve exchange rates for " + date);
            }

            String xmlResponse = response.getBody();

            JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ValCurs valCurs = (ValCurs) unmarshaller.unmarshal(new StringReader(xmlResponse));

            // Save the exchange rates to the database
            List<ExchangeRate> exchangeRates = new ArrayList<>();
            for (ValType valType : valCurs.getValTypes()) {
                if (valType.getType().equals(CURRENCIES_NODE)) {
                    for (Valute valute : valType.getValutes()) {
                        ExchangeRate exchangeRate = new ExchangeRate();
                        exchangeRate.setDate(date);
                        exchangeRate.setCurrencyCode(valute.getCode());
                        exchangeRate.setExchangeRateValue(new BigDecimal(valute.getValue()));
                        exchangeRates.add(exchangeRate);
                    }
                }
            }
            exchangeRateRepository.saveAll(exchangeRates);
        }
    }

    public List<ExchangeRateDto> getExchangeRatesByDate(LocalDate date) {
        List<ExchangeRate> exchangeRates = exchangeRateRepository.findAllByDate(date);
        return convertToDtoList(exchangeRates);
    }

    public List<ExchangeRateDto> getAllExchangeRates() {
        List<ExchangeRate> exchangeRates = exchangeRateRepository.findAll();
        return convertToDtoList(exchangeRates);
    }

    public List<ExchangeRateDto> getExchangeRatesByCurrency(String currency) {
        List<ExchangeRate> exchangeRates = exchangeRateRepository.findAllByCurrencyCode(currency.toUpperCase());
        return convertToDtoList(exchangeRates);
    }

    public List<ExchangeRateDto> getExchangeRateByCurrencyAndDate(String currency, LocalDate date) {
        List<ExchangeRate> exchangeRate = exchangeRateRepository.findByCurrencyCodeAndDate(currency.toUpperCase(), date);
        return convertToDtoList(exchangeRate);
    }

    @Transactional
    public void deleteExchangeRatesByDate(LocalDate date) {
        exchangeRateRepository.deleteAllByDate(date);
    }

    private List<ExchangeRateDto> convertToDtoList(List<ExchangeRate> exchangeRates) {
        return exchangeRates.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private ExchangeRateDto convertToDto(ExchangeRate exchangeRate) {
        if (exchangeRate == null) {
            return null;
        }
        ExchangeRateDto exchangeRateDto = new ExchangeRateDto();
        exchangeRateDto.setDate(exchangeRate.getDate());
        exchangeRateDto.setCurrencyCode(exchangeRate.getCurrencyCode());
        exchangeRateDto.setRate(exchangeRate.getExchangeRateValue());
        return exchangeRateDto;
    }

}





