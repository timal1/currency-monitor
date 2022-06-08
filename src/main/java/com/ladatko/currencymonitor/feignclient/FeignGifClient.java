package com.ladatko.currencymonitor.feignclient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@org.springframework.cloud.openfeign.FeignClient(name = "GIFClient", url = "${giphy.url.general}")
public interface FeignGifClient {

    @GetMapping("/random")
    ResponseEntity<Map> getRandomGif(
            @RequestParam("api_key") String apiKey,
            @RequestParam("tag") String tag
    );
}