package com.news.rssfilter.repository;

import com.news.rssfilter.model.RssFeedEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository("registerUserRepository")
public interface FindFeedEntryRepository extends JpaRepository<RssFeedEntry,Long>{
    @Query("Select feed from feeds feed where feed.description LIKE  %:keyWord%")
    List<RssFeedEntry> findByDescriptionContaining(String keyWord);

}