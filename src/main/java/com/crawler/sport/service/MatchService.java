package com.crawler.sport.service;

import com.crawler.sport.domain.MatchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {

    @Autowired private MatchRepository matchRepository;

    public void save(MatchResult matchResult) {
        matchRepository.save(matchResult);
    }
}
