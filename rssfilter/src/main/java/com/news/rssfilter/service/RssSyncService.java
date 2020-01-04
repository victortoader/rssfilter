package com.news.rssfilter.service;

import com.news.rssfilter.model.RssFeedEntry;
import com.news.rssfilter.repository.FetchFeedEntryRepository;
import com.rometools.rome.io.FeedException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RssSyncService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RssSyncService.class);


    @Autowired
    private FetchFeedEntryRepository repository;

    @Autowired
    private RssFeedReaderService feedReader;

    @Scheduled(
            initialDelayString = "#{${rss.sync.initial.delay.sec} * 1000}",
            fixedRateString = "#{${rss.sync.interval.sec} * 1000}")
    public void updateRss() {
        ArrayList<String> urlList = new ArrayList<>();
        urlList.add("http://rss.realitatea.net/stiri.xml");
        urlList.add("http://stiri.tvr.ro/rss/homepage.xml");
        for (String url: urlList) {
            Map<String, RssFeedEntry> entryMap;
            try {
                entryMap = feedReader.getNewEntries(url).
                        stream().
                        collect(Collectors.toMap(RssFeedEntry::getGuid, Function.identity()));
            } catch (IOException | FeedException e) {
                LOGGER.error("Cannot read news feed", e);
                return;
            }
            if (repository.findAll().size() > 0) {
                //Get the id's that are already in the database
                List<String> IdsInDB = repository.findAllById(entryMap.keySet())
                        .stream()
                        .map(dbEntry -> dbEntry.getGuid())
                        .collect(Collectors.toList());

                //and remove them from the fetched list
                entryMap.keySet().removeAll(IdsInDB);
            }
            //fetch the actual image data as there's only an url in the feed
            entryMap.values().stream().forEach(entry -> entry.setImage(getImageBytes(entry)));

            //save the remaining entries
            repository.saveAll(entryMap.values());
            LOGGER.info("Done updating the RSS Feed entries. {} entries have been added", entryMap.values().size());
        }
    }

    private byte[] getImageBytes(RssFeedEntry entry) {
        if (entry.getImageUrl() != null) {
            try {
                URL imgUrl = new URL(entry.getImageUrl());
                return IOUtils.toByteArray(imgUrl);
            } catch (IOException e) {
                LOGGER.error("Cannot open url " + entry.getImageUrl(), e);
                return null;
            }
        }
        return null;
    }


}
