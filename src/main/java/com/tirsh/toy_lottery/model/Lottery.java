package com.tirsh.toy_lottery.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lottery {
    private final List<Toy> toysList;
    private final Random random;

    public Lottery(List<Toy> toysList) {
        this.toysList = toysList;
        random = new Random();
    }

    public Toy makeTurn() {
        int turn = random.nextInt(toysList.size());
        return toysList.remove(turn);
    }
}
