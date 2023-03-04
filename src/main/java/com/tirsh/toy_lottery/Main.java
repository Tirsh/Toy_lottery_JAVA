package com.tirsh.toy_lottery;

import com.tirsh.toy_lottery.controller.Controller;
import com.tirsh.toy_lottery.model.Toy;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
//        Toy new_toy = new Toy("Crocodile", 2, 15);
//        controller.add(new_toy);
//        controller.delete(5);
        var data = controller.getAllToys();
        System.out.println(data);
        System.out.println(controller.getToy(3));
    }
}
