package com.introlabsystems.autoria;

import com.introlabsystems.autoria.model.Car;
import com.introlabsystems.autoria.service.AutoRiaScraper;
import com.introlabsystems.autoria.service.DocumentDownloader;
import com.introlabsystems.autoria.service.ResultWriter;
import com.introlabsystems.autoria.utils.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class Application {

    private static Logger log = Logger.getLogger(AutoRiaScraper.class);

    public static void main(String[] args) {
        DocumentDownloader documentDownloader = new DocumentDownloader();
        AutoRiaScraper scraper = new AutoRiaScraper(documentDownloader);
        List<Integer> pages = Arrays.asList(1, 3, 5);
        log.info("Start scrapping");
        List<Car> resultList = scraper.scrape(pages);
        ResultWriter resultWriter = new ResultWriter();
        log.info("Start writing");
        resultWriter.write(resultList);
        HibernateSessionFactoryUtil.getSessionFactory().close();
    }
}
