package com.introlabsystems.autoria;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;

public class AutoRio {
    private static final int COUNT = 5;
    static final File RESULT_FILE = new File("result.csv");

    static void scrap() throws IOException {
        recreateFile();
        var document = Jsoup.connect("https://auto.ria.com/").get();
        var categories = document.selectFirst("#catalogs-type-reviews .seo-catalog-s");
        var links = categories.select("a");
        for (int i = 0; i < COUNT; i++) {
            String link = links.get(i).absUrl("href");
            var producer = new Producer(link);
            producer.scrap();
        }
    }

    static void recreateFile() throws IOException {
        if (RESULT_FILE.exists()) {
            RESULT_FILE.delete();
        }
        RESULT_FILE.createNewFile();
    }
}
