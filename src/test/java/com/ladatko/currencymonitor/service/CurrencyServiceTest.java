package com.ladatko.currencymonitor.service;

import com.ladatko.currencymonitor.feignclient.FeignCurrencyClient;
import com.ladatko.currencymonitor.model.ExchangeRates;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.ladatko.currencymonitor")
public class CurrencyServiceTest {

    @Autowired
    private CurrencyService currencyService;
    @MockBean
    private FeignCurrencyClient feignCurrencyClient;

    private ExchangeRates currentRates;
    private ExchangeRates prevRates;

    @Before
    public void init() {
        int time = 1654524000;
        this.currentRates = new ExchangeRates();
        this.currentRates.setTimestamp(time);
        this.currentRates.setBase("CUR_BASE");
        Map<String, Double> currentRatesMap = new HashMap<>();
        currentRatesMap.put("CUR1", 12.1);
        currentRatesMap.put("CUR2", 34.3);
        currentRatesMap.put("CUR3", 54.7);
        currentRatesMap.put("CUR_BASE", 63.0);
        this.currentRates.setRates(currentRatesMap);

        time = 1654387186;
        this.prevRates = new ExchangeRates();
        this.prevRates.setTimestamp(time);
        this.prevRates.setBase("CUR_BASE");
        Map<String, Double> prevRatesMap = new HashMap<>();
        prevRatesMap.put("CUR1", 12.1);
        prevRatesMap.put("CUR2", 45.0);
        prevRatesMap.put("CUR3", 53.3);
        prevRatesMap.put("CUR_BASE", 63.0);
        this.prevRates.setRates(prevRatesMap);
    }

    @Test
    public void whenPositiveChanges() {
        Mockito.when(feignCurrencyClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        Mockito.when(feignCurrencyClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(this.prevRates);
        currencyService.setCurrentRates(this.currentRates);
        currencyService.setPrevRates(this.prevRates);
        ResponseEntity<Map> result = currencyService.compareExchangeRate("CUR3");
        assertEquals("200 OK", String.valueOf(result.getStatusCode()));
    }

    @Test
    public void whenNegativeChanges() {
        Mockito.when(feignCurrencyClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        Mockito.when(feignCurrencyClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(this.prevRates);
        currencyService.setCurrentRates(this.currentRates);
        currencyService.setPrevRates(this.prevRates);
        ResponseEntity<Map> result = currencyService.compareExchangeRate("CUR2");
        assertEquals("200 OK", String.valueOf(result.getStatusCode()));
    }

    @Test
    public void whenZeroChanges() {
        Mockito.when(feignCurrencyClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        Mockito.when(feignCurrencyClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(this.prevRates);
        currencyService.setCurrentRates(this.currentRates);
        currencyService.setPrevRates(this.prevRates);
        ResponseEntity<Map> result = currencyService.compareExchangeRate("CUR1");
        assertNull(result);
    }

    @Test
    public void whenLoadsListCurrency() {
        Mockito.when(feignCurrencyClient.getLatestRates(anyString()))
                .thenReturn(this.currentRates);
        currencyService.setCurrentRates(this.currentRates);
        List<String> result = currencyService.loadListCurrency();
        assertThat(result, containsInAnyOrder("CUR1", "CUR2", "CUR3", "CUR_BASE"));
    }
}