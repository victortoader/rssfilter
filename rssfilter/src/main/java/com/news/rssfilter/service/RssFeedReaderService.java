package com.news.rssfilter.service;

import com.news.rssfilter.model.RssFeedEntry;
import com.news.rssfilter.util.RssMapper;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RssFeedReaderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RssFeedReaderService.class);

    public static List<RssFeedEntry> getNewEntries(String rssUrl) throws IOException, FeedException {
        LOGGER.info("Starting update of the RSS Feed entries from URL {}", rssUrl);
        URL feedSource = new URL(rssUrl);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(feedSource));
        LOGGER.info("Fetched {} entries", feed.getEntries().size());
        return feed.getEntries().stream().map(e -> RssMapper.map(e)).collect(Collectors.toList());
    }
}
