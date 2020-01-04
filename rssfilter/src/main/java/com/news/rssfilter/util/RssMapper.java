package com.news.rssfilter.util;

import com.news.rssfilter.model.RssFeedEntry;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;

import java.util.Optional;

public class RssMapper {
    public static RssFeedEntry map(SyndEntry input) {
        String imageUrl = findImageUrl(input);
        RssFeedEntry entry = RssFeedEntry.createEntry(
                input.getUri(),
                input.getTitle(),
                input.getDescription().getValue(),
                input.getPublishedDate(),
                imageUrl);

        return entry;
    }

    private static String findImageUrl(SyndEntry input) {
        if (input.getEnclosures() != null) {
            Optional<SyndEnclosure> imageEnclosure = input.getEnclosures()
                    .stream()
                    .filter(e -> "image/jpeg".equalsIgnoreCase(e.getType()))
                    .findFirst();
            if (imageEnclosure.isPresent())
                return imageEnclosure.get().getUrl();
        }
        return null;
    }
}
