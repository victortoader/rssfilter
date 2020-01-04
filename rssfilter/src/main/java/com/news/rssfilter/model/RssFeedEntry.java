package com.news.rssfilter.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity(name = "feeds")
public class RssFeedEntry implements Serializable {
    @Id
    private String guid;

    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "publication_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publicationDate;

    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @Column(name = "image_url")
    private String imageUrl;

    public static RssFeedEntry createEntry(String guid, String title, String description, Date publicationDate, String imageUrl) {
        RssFeedEntry entry = new RssFeedEntry();
        entry.setGuid(guid);
        entry.setTitle(title);
        entry.setDescription(description);
        entry.setPublicationDate(publicationDate);
        entry.setImageUrl(imageUrl);
        return entry;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
