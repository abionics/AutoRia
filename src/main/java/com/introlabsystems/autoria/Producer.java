package com.introlabsystems.autoria;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Producer {
    Document producerDocument;

    Producer(String url) throws IOException {
        producerDocument = Jsoup.connect(url).get();
        openPage(1);
        openPage(3);
        openPage(5);
    }

    void openPage(int page) throws IOException {
        var link = producerDocument.baseUri() + "?page=" + page;
        System.out.println(link);
        var document = Jsoup.connect(link).get();
    }
}
