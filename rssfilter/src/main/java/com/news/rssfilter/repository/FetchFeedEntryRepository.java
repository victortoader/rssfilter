package com.news.rssfilter.repository;

import com.news.rssfilter.model.RssFeedEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FetchFeedEntryRepository extends JpaRepository<RssFeedEntry, String> {
}
