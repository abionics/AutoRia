package com.introlabsystems.autoria;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class Producer {
    Document producerDocument;

    Producer(String url) throws IOException {
        producerDocument = Jsoup.connect(url).get();
    }

    void scrap() throws IOException {
        openPage(1);
        openPage(3);
        openPage(5);
    }

    void openPage(int page) throws IOException {
        var link = producerDocument.baseUri() + "?page=" + page;
        System.out.println("page: " + link);
        var document = Jsoup.connect(link).get();
        var cars = document.select("h3.reviews-cars_name_mob > a");
        for (Element car : cars) {
            Car.scrap(car.absUrl("href"));
        }
    }
}
