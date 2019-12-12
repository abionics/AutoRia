package com.introlabsystems.autoria;

import org.jsoup.Jsoup;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Car {
    static void scrap(String url) throws IOException {
        var document = Jsoup.connect(url).get();
        String title = document.title();
        int to = title.indexOf(" от");
        String name = title.substring(17, to);
        String info = document.selectFirst("div.review-text > p").text().strip();
        var writer = new BufferedWriter(new FileWriter(AutoRio.RESULT_FILE, true));
        writer.write("\"" + url + "\",\"" + name + "\",\"" + info + "\"\n");
        writer.close();
    }
}
