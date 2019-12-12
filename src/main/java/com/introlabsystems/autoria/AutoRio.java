package com.introlabsystems.autoria;

import org.jsoup.Jsoup;

import java.io.IOException;

public class AutoRio {
    private static final int COUNT = 5;

    static void scrap() throws IOException {
        var document = Jsoup.connect("https://auto.ria.com/").get();
        var categories = document.selectFirst("#catalogs-type-reviews .seo-catalog-s");
        var links = categories.select("a");
        for (int i = 0; i < COUNT; i++) {
            String link = links.get(i).absUrl("href");
            var model = new Producer(link);
        }
    }
}
