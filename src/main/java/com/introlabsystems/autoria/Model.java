package com.introlabsystems.autoria;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Model {
    Document document;

    Model(String url) throws IOException {
        document = Jsoup.connect(url).get();
        openPage(1);
        openPage(3);
        openPage(5);
    }

    void openPage(int page) {
        var link = document.baseUri() + "/?page=" + page;
        System.out.println(link);
    }
}

