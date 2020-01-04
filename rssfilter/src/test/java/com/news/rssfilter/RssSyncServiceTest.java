package com.news.rssfilter;

import com.news.rssfilter.model.RssFeedEntry;
import com.news.rssfilter.repository.FetchFeedEntryRepository;
import com.news.rssfilter.service.RssFeedReaderService;
import com.news.rssfilter.service.RssSyncService;
import com.rometools.rome.io.FeedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RssSyncServiceTest {
    @MockBean
    private RssFeedReaderService mockReaderService;

    @Autowired
    private RssSyncService syncService;

    @Autowired
    private FetchFeedEntryRepository repository;

    @Test
    public void testDatabaseAdd() throws IOException, FeedException {
        List<RssFeedEntry> mockEntries = new ArrayList<>();
        Date now = new Date();
        mockEntries.add(RssFeedEntry.createEntry("guid1", "title1", "desc1", now, null));
        given(mockReaderService.getNewEntries("#{${rss.url.1}")).willReturn(mockEntries);

        syncService.updateRss();

        assertTrue(repository.count() == 1);
        RssFeedEntry entry = repository.getOne("guid1");

        checkEntry(entry, "guid1", "title1", "desc1", now);

        mockEntries.add(RssFeedEntry.createEntry("guid2", "title2", "desc2", now, null));
        reset(mockReaderService);
        given(mockReaderService.getNewEntries("#{${rss.url.1}")).willReturn(mockEntries);

        syncService.updateRss();

        assertTrue(repository.count() == 2);
        entry = repository.getOne("guid2");

        checkEntry(entry, "guid2", "title2", "desc2", now);
    }

    private void checkEntry(RssFeedEntry entry, String guid, String title, String desc, Date publicationDate) {
        assertTrue(entry.getGuid().equals(guid));
        assertTrue(entry.getTitle().equals(title));
        assertTrue(entry.getDescription().equals(desc));
        long dateDiff = Math.abs(entry.getPublicationDate().getTime() - publicationDate.getTime());
        assertTrue(dateDiff < 1000L);
    }
}
