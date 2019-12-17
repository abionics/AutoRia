package com.introlabsystems.autoria.service;

import com.introlabsystems.autoria.model.Car;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ResultWriter {

    private static final File RESULT_FILE = new File("result.csv");
    private static final String[] COLUMNS = new String[]{"url", "name", "description"};

    CarService carService = new CarService();

    private void recreateFile() throws IOException {
        if (RESULT_FILE.exists()) {
            RESULT_FILE.delete();
        }
        RESULT_FILE.createNewFile();
    }

    public void write(List<Car> cars) {
        if (cars.isEmpty()) {
            return;
        }
        try {
            recreateFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(RESULT_FILE, true));
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(COLUMNS));
            for (Car car : cars) {
                printer.printRecord(
                        car.getUrl(),
                        car.getName(),
                        car.getDescription()
                );
                carService.saveCar(car);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
