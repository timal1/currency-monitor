package com.ladatko.currencymonitor.service;

import com.ladatko.currencymonitor.feignclient.FeignGifClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class GifService {

    private final FeignGifClient feignGifClient;

    @Value("${giphy.api.key}")
    private String apiKey;
    public ResponseEntity<Map> getRandomGif(String tag) {
        log.info("Requested gif by tag: " + tag);
        return feignGifClient.getRandomGif(this.apiKey, tag);
    }
}
