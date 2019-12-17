package com.introlabsystems.autoria.service;

import com.introlabsystems.autoria.model.Car;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.substring;
import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

class AutoRiaParser {

    private static final int START_CAR_NAME_INDEX = 17;

    private static final String DESCRIPTION_SELECTOR = "div.review-text > p";
    private static final String PRICE_SELECTOR = "body > div.app-content > div > div.page.m-padding > main > div.app-feedback-box.box-panel > section > article > div > div.wrapper > div.rev-search-card_rat-i.rev-search-card_rat-i_main > div > strong";
    private static final String SEPARATOR = " от";

    private static final Pattern QUOTE_PATTERN = Pattern.compile("\"");
    private static final Pattern NEW_LINE_PATTERN = Pattern.compile("\n");
    private static final String QUOTE = "'";

    List<String> parseCategories(Document document) {
        Element categories = document.selectFirst("#catalogs-type-reviews .seo-catalog-s");
        return categories.select("a")
                .stream()
                .map(x -> x.absUrl("href"))
                .collect(Collectors.toList());
    }

    Car parseCar(Document document, String url) {
        String name = parseName(document);
        String description = parseDescription(document);
        BigDecimal mark = parseMark(document);
        return new Car(url, name, description, mark);
    }

    private String parseName(Document document) {
        String title = substring(document.title(), START_CAR_NAME_INDEX);
        return substringBeforeLast(title, SEPARATOR);
    }

    private String parseDescription(Document document) {
        String description = document.select(DESCRIPTION_SELECTOR).text();
        description = QUOTE_PATTERN.matcher(description.trim()).replaceAll(QUOTE);
        description = NEW_LINE_PATTERN.matcher(description).replaceAll(SPACE);
        return description;
    }

    private BigDecimal parseMark(Document document) {
        String mark = document.select(PRICE_SELECTOR).text();
        return new BigDecimal(mark);
    }
}
