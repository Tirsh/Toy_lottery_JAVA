package com.tirsh.toy_lottery.controller;

import com.tirsh.toy_lottery.dao.DaoToyController;
import com.tirsh.toy_lottery.model.Toy;
import com.tirsh.toy_lottery.util.DBConnection;

import java.sql.SQLException;
import java.util.List;

public class Controller {
    DaoToyController daoToyController;

    public Controller() {
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
}
