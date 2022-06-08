package com.ladatko.currencymonitor.service;

import com.ladatko.currencymonitor.feignclient.FeignCurrencyClient;
import com.ladatko.currencymonitor.model.ExchangeRates;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Log4j2
@Data
@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final FeignCurrencyClient feignCurrencyClient;
    private final GifService gifService;

    private ExchangeRates prevRates;
    private ExchangeRates currentRates;


    @Value("${openexchangerates.app.id}")
    private String appId;
    @Value("${giphy.rich}")
    private String getGifIfUpRate;
    @Value("${giphy.broke}")
    private String getGifIfDownRate;


    public ResponseEntity<Map> compareExchangeRate(String changeCurrency) {

        String dateYesterday = getYesterdayDateString();
        this.prevRates = feignCurrencyClient.getHistoricalRates(dateYesterday, appId);

        Double currentRateChangeCurrency = getRateCurrency(changeCurrency, currentRates);
        Double prevRateChangeCurrency = getRateCurrency(changeCurrency, prevRates);

        if (currentRateChangeCurrency > prevRateChangeCurrency) {
            log.info("Rate " + changeCurrency +" Up");
            return gifService.getRandomGif(getGifIfUpRate);
        }

        if (currentRateChangeCurrency < prevRateChangeCurrency) {
            log.info("Rate " + changeCurrency +" Down");
            return gifService.getRandomGif(getGifIfDownRate);
        }

        log.info("Rate " + changeCurrency +" no changed");
        return null;
    }

    public List<String> loadListCurrency() {
        this.currentRates = feignCurrencyClient.getLatestRates(appId);
        return new ArrayList<>(currentRates.getRates().keySet());
    }

    public Double getRateCurrency(String changeCurrency, ExchangeRates rates) {
        for (Map.Entry<String, Double> currencies : rates.getRates().entrySet()) {

            if (changeCurrency.equals(currencies.getKey())) {
                return currencies.getValue();
            }
        }
        return 0.0;
    }

    public String getYesterdayDateString() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(cal.getTime());
    }
}
