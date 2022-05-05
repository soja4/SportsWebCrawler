package com.crawler.sport.crawler;

import com.crawler.sport.domain.MatchResult;
import com.crawler.sport.service.MatchService;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Slf4j
@Component
public class HtmlCrawler extends WebCrawler {
    private static Integer counter = 0;

    private final MatchService matchService;

    private static final Pattern EXCLUSIONS =
            Pattern.compile(".*(\\.(css|js|xml|gif|jpg|png|mp3|mp4|zip|gz|pdf))$");

    public HtmlCrawler(MatchService matchService) {
        this.matchService = matchService;
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String urlString = url.getURL().toLowerCase();
        return !EXCLUSIONS.matcher(urlString).matches()
                && urlString.startsWith("https://www.xscores.com/soccer");
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();

        if (page.getParseData() instanceof HtmlParseData && url.contains("/soccer/match")) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();

            String html = htmlParseData.getHtml();

            Document doc = Jsoup.parseBodyFragment(html);
            String matchScore = doc.getElementsByClass("match_details_score").text();
            if (!matchScore.isEmpty() && counter < 10) {
                counter++;
                String homeTeam = doc.getElementsByClass("hTeam").text();
                String awayTeam = doc.getElementsByClass("aTeam").text();
                log.info(matchScore);
                log.info(homeTeam);
                log.info(awayTeam);
                log.info("----------");
                log.info(counter.toString());
                String[] score = matchScore.replaceAll(" ", "").split("-");

                MatchResult matchResult =
                        MatchResult.builder()
                                .homeTeam(homeTeam)
                                .awayTeam(awayTeam)
                                .homeTeamGoals(Integer.valueOf(score[0]))
                                .awayTeamGoals(Integer.valueOf(score[1]))
                                .build();

                matchService.save(matchResult);
            }
        }
    }
}
