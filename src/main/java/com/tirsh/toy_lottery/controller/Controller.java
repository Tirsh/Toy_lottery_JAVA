package com.tirsh.toy_lottery.controller;

import com.tirsh.toy_lottery.service.DataController;
import com.tirsh.toy_lottery.ui.Ui;

public class Controller {
    Ui ui;

    public Controller() {
        DataController dataController = new DataController();
        ui = new Ui(dataController);
    }

    public void run() {
        ui.initUI();
    }
}
