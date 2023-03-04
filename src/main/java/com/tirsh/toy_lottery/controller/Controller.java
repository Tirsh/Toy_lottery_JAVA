package com.tirsh.toy_lottery.controller;

import com.tirsh.toy_lottery.dao.DaoToyController;
import com.tirsh.toy_lottery.model.Toy;
import com.tirsh.toy_lottery.service.DataController;
import com.tirsh.toy_lottery.ui.Ui;

import java.sql.SQLException;
import java.util.List;

public class Controller {
    Ui ui;


    public Controller() {
        DataController dataController = new DataController();
        ui = new Ui(dataController);
    }


}
