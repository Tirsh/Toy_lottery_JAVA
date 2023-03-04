package com.tirsh.toy_lottery.service;

import com.tirsh.toy_lottery.dao.DaoToyController;
import com.tirsh.toy_lottery.model.Toy;

import java.sql.SQLException;
import java.util.List;

public class DataController {
    DaoToyController daoToyController;

    public DataController() {
        daoToyController = new DaoToyController();
  }

    public List<Toy> getAllToys() {
        try {
            return daoToyController.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Toy add(Toy toy) {
        return daoToyController.save(toy);
    }

    public boolean delete(int id){
        return daoToyController.delete(id);
    }

    public Toy getToy(int id){
        try {
            return daoToyController.getById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
