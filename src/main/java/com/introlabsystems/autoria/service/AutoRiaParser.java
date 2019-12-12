package com.introlabsystems.autoria.service;

import com.introlabsystems.autoria.model.Car;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.substring;
import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

class AutoRiaParser {

    private static final int START_CAR_NAME_INDEX = 17;

    private static final String DESCRIPTION_SELECTOR = "div.review-text > p";
    private static final String SEPARATOR = " от";

    private static final Pattern QUOTE_PATTERN = Pattern.compile("\"");
    private static final Pattern NEW_LINE_PATTERN = Pattern.compile("\n");
    private static final String QUOTE = "'";

    List<String> parseCategories(Document document) {
        var categories = document.selectFirst("#catalogs-type-reviews .seo-catalog-s");
        return categories.select("a")
                .stream()
                .map(x -> x.absUrl("href"))
                .collect(Collectors.toList());
    }

    Car parseCar(Document document, String url) {
        String title = substring(document.title(), START_CAR_NAME_INDEX);
        String name = substringBeforeLast(title, SEPARATOR);
        String description = parseDescription(document);
        Car car = new Car();
        car.setUrl(url);
        car.setName(name);
        car.setDescription(description);
        return car;
    }

    private String parseDescription(Document document) {
        String description = document.select(DESCRIPTION_SELECTOR).text();
        description = QUOTE_PATTERN.matcher(description.trim()).replaceAll(QUOTE);
        description = NEW_LINE_PATTERN.matcher(description).replaceAll(SPACE);
        return description;
    }
}
