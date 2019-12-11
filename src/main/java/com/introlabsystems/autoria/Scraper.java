package com.introlabsystems.autoria;

import org.jsoup.Jsoup;

import java.io.IOException;

public class Scraper {
    Scraper() throws IOException {
        var document = Jsoup.connect("https://auto.ria.com/").get();
        var elements = document.selectFirst("#catalogs-type-reviews .seo-catalog-s");
        System.out.println(elements.html());
    }
}
