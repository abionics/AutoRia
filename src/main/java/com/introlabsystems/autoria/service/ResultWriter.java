package com.introlabsystems.autoria.service;

import com.introlabsystems.autoria.model.Car;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ResultWriter {

    private static final File RESULT_FILE = new File("result.csv");

    private void recreateFile() throws IOException {
        if (RESULT_FILE.exists()) {
            RESULT_FILE.delete();
        }
        RESULT_FILE.createNewFile();
    }

    public void write(List<Car> cars) {
        if (!cars.isEmpty()) {
            return;
        }
        try {
            recreateFile();
            for (Car car : cars) {
                try (var writer = new BufferedWriter(new FileWriter(RESULT_FILE, true))) {
                    writer.write("\"" + car.getUrl() + "\",\"" + car.getName() + "\",\"" + car.getDescription() + "\"\n");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
    }
}
