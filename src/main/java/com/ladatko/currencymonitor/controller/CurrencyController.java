package com.ladatko.currencymonitor.controller;

import com.ladatko.currencymonitor.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public List<String> loadListCurrency() {
        return currencyService.loadListCurrency();
    }

    @GetMapping("/rates/{titleCurrency}")
    public ResponseEntity<Map> compareExchangeRate(@PathVariable String titleCurrency) {
        log.info("Received titleCurrency: {}", titleCurrency);
        return currencyService.compareExchangeRate(titleCurrency);
    }
}
