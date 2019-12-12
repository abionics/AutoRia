package com.introlabsystems.autoria.service;

import com.introlabsystems.autoria.model.Car;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AutoRiaScraper {

    private static final int CATEGORIES_LIMIT = 5;
    private static final String DOMAIN = "https://auto.ria.com/";
    private static final AutoRiaParser PARSER = new AutoRiaParser();
    private static final String CATEGORY_PAGE_FORMAT = "https://auto.ria.com/%s/?page=%d";

    private final DocumentDownloader documentDownloader;

    public AutoRiaScraper(DocumentDownloader documentDownloader) {
        this.documentDownloader = documentDownloader;
    }

    public List<Car> scrape(List<Integer> pages) {
        Document mainPageDocument = documentDownloader.get(DOMAIN);
        List<String> categoriesLinks = PARSER.parseCategories(mainPageDocument);
        List<Car> cars = new ArrayList<>();
        for (int i = 0; i < CATEGORIES_LIMIT; i++) {
            String linkToScrape = categoriesLinks.get(i);
            for (String linkToPage : generateLinks(linkToScrape, pages)) {
                cars.addAll(scrapePage(linkToPage));
            }
        }
        return cars;
    }

    public List<Car> scrapePage(String linkToPage) {
        Document document = documentDownloader.get(linkToPage);
        return document.select("h3.reviews-cars_name_mob > a")
                .stream()
                .map(x -> scrapeCar(x.absUrl("href")))
                .collect(Collectors.toList());
    }

    public List<String> generateLinks(String categoryPage, List<Integer> pages) {
        List<String> urls = new ArrayList<>();
        for (Integer page : pages) {
            urls.add(String.format(CATEGORY_PAGE_FORMAT, categoryPage, page));
        }
        return urls;
    }

    private Car scrapeCar(String linkToCar) {
        System.out.println(linkToCar);
        Document document = documentDownloader.get(linkToCar);
        return PARSER.parseCar(document, linkToCar);
    }
}
