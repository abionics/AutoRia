package com.introlabsystems.autoria;

import com.introlabsystems.autoria.model.Car;
import com.introlabsystems.autoria.service.AutoRiaScraper;
import com.introlabsystems.autoria.service.DocumentDownloader;
import com.introlabsystems.autoria.service.ResultWriter;

import java.util.Arrays;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        DocumentDownloader documentDownloader = new DocumentDownloader();
        AutoRiaScraper scraper = new AutoRiaScraper(documentDownloader);
        List<Integer> pages = Arrays.asList(1, 3, 5);
        List<Car> resultList = scraper.scrape(pages);
        ResultWriter resultWriter = new ResultWriter();
        resultWriter.write(resultList);
    }
}
