package com.news.rssfilter.service;

import com.news.rssfilter.model.RssFeedEntry;
import com.news.rssfilter.repository.FindFeedEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RssFeedFinderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RssSyncService.class);

    @Autowired
    private FindFeedEntryRepository repository;

//Not used
    public void findByDescription() {
        ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
        RssFeedEntry feedEntry = new RssFeedEntry();
        feedEntry.setDescription("aa");
        Example<RssFeedEntry> example = Example.of(feedEntry, caseInsensitiveExampleMatcher);
        Optional<RssFeedEntry> actual = repository.findOne(example);
        LOGGER.info("Found {} entries", actual);
    }
    @Scheduled(
            initialDelayString = "#{${rss.sync.initial.delay.sec} * 500}",
            fixedRateString = "#{${rss.sync.interval.sec} * 500}")
    public void findContainingKeyWord() {
        List<RssFeedEntry> matchingFeeds = repository.findByDescriptionContaining("vremea");
        LOGGER.info("Found {} entries matching with keyword", matchingFeeds.size());
    }
}
