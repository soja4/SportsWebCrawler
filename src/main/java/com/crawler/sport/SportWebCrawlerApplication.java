package com.crawler.sport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class SportWebCrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportWebCrawlerApplication.class, args);
    }
}
