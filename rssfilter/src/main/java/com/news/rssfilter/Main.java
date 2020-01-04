package com.news.rssfilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@EnableJpaRepositories("com.news.rssfilter.repository")
@ComponentScan(basePackages={"com.news.rssfilter"}) @EntityScan(basePackages="com.csdm.rsssave.model")

public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}


