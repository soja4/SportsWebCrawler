package com.crawler.sport;

import com.crawler.sport.crawler.HtmlCrawler;
import com.crawler.sport.service.MatchService;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.File;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class SportWebCrawlerApplication {

    @Value("${url.root}")
    private String urlRoot;

    @Autowired private MatchService matchService;

    public static void main(String[] args) {
        SpringApplication.run(SportWebCrawlerApplication.class, args);
    }

    @PostConstruct
    public void onStartup() throws Exception {
        File crawlStorage = new File("src/test/resources/crawler4j");
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorage.getAbsolutePath());

        int numCrawlers = 12;

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed(urlRoot);

        CrawlController.WebCrawlerFactory<HtmlCrawler> factory =
                () -> new HtmlCrawler(matchService);

        controller.start(factory, numCrawlers);
    }
}
