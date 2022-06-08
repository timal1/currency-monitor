package com.ladatko.currencymonitor.service;

import com.ladatko.currencymonitor.feignclient.FeignGifClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.ladatko.currencymonitor")
public class GifServiceTest {

    @Autowired
    private GifService gifService;
    @MockBean
    private FeignGifClient gifClient;

    @Test
    public void whenPositiveChanges() {
        ResponseEntity<Map> testEntity = new ResponseEntity<>(new HashMap(), HttpStatus.OK);
        Mockito.when(gifClient.getRandomGif(anyString(), anyString()))
                .thenReturn(testEntity);
        ResponseEntity<Map> result = gifService.getRandomGif("rich");
        assertEquals("200 OK", String.valueOf(result.getStatusCode()));
    }

    @Test
    public void whenNegativeChanges() {
        ResponseEntity<Map> testEntity = new ResponseEntity<>(new HashMap(), HttpStatus.OK);
        Mockito.when(gifClient.getRandomGif(anyString(), anyString()))
                .thenReturn(testEntity);
        ResponseEntity<Map> result = gifService.getRandomGif("broke");
        assertEquals("200 OK", String.valueOf(result.getStatusCode()));
    }

}
