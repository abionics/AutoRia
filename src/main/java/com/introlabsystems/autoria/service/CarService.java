package com.introlabsystems.autoria.service;

import com.introlabsystems.autoria.dao.CarDAO;
import com.introlabsystems.autoria.model.Car;

import java.util.List;

public class CarService {

    private CarDAO carDAO = new CarDAO();

    public CarService() {
    }

    public Car findCar(int id) {
        return carDAO.findById(id);
    }

    public Car findCarByUrl(String url) {
        return carDAO.findByUrl(url);
    }

    public void saveCar(Car car) {
        carDAO.save(car);
    }

    public void deleteCar(Car car) {
        carDAO.delete(car);
    }

    public void updateCar(Car car) {
        carDAO.update(car);
    }

    public List<Car> findAllCars() {
        return carDAO.findAll();
    }
}
