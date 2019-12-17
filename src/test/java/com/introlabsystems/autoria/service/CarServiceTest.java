package com.introlabsystems.autoria.service;

import com.introlabsystems.autoria.model.Car;
import com.introlabsystems.autoria.utils.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CarServiceTest {

    private static Logger log = Logger.getLogger(AutoRiaScraper.class);

    private static int EXPECTED_CAR_COUNT = 61;
    private static int FIRST_CAR_ID = 1;
    private static Car SAMPLE_CAR;
    private static Car TEST_CAR;

    private CarService carService = new CarService();

    @BeforeAll
    static void createSampleCar() {
        SAMPLE_CAR = new Car("https://auto.ria.com/reviews/vaz/21099/132569/",
                "ВАЗ 21099 2008 года",
                "я владел данным авто в течении года",
                new BigDecimal("3.60"));
        TEST_CAR = new Car("test", "test", "test", new BigDecimal("0"));
    }

    @Test
    void findCar() {
        Car car = carService.findCar(FIRST_CAR_ID);
        assertNotNull(car);
        assertEquals(car.getId(), FIRST_CAR_ID);
    }

    @Test
    void findCarByUrl() {
        Car car = carService.findCarByUrl(SAMPLE_CAR.getUrl());
        assertEquals(car.getName(), SAMPLE_CAR.getName());
    }

    @Test
    @Order(1)
    void saveCar() {
        int countCarsBefore = carService.findAllCars().size();
        carService.saveCar(TEST_CAR);
        int countCarsAfter = carService.findAllCars().size();
        assertEquals(countCarsBefore + 1, countCarsAfter);
    }

    @Test
    @Order(2)
    void deleteCar() {
        int countCarsBefore = carService.findAllCars().size();
        carService.deleteCar(TEST_CAR);
        int countCarsAfter = carService.findAllCars().size();
        assertEquals(countCarsBefore - 1, countCarsAfter);
    }

    @Test
    void allCarsScraped() {
        int countCars = carService.findAllCars().size();
        assertEquals(countCars, EXPECTED_CAR_COUNT);
    }

    @AfterAll
    static void closeConnection() {
        HibernateSessionFactoryUtil.getSessionFactory().close();
        log.info("Tests have been passed");
    }
}
