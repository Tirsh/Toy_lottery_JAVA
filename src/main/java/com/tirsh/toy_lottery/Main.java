package com.tirsh.toy_lottery;

import com.tirsh.toy_lottery.controller.Controller;
import com.tirsh.toy_lottery.dao.DaoToyController;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        var data = controller.getAllToys();
        System.out.println(data);
    }
}
