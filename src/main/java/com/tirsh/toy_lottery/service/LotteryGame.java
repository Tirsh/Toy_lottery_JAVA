package com.tirsh.toy_lottery.service;

import com.tirsh.toy_lottery.model.Toy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LotteryGame {
    private List<Toy> toysList;
    private List<Double> dropFrequency;
    private Random random;


    public LotteryGame(List<Toy> toysList) {
        this.toysList = toysList;
        countDropFrequency();
    }

    private void countDropFrequency() {
        List<Integer> frequency = toysList.stream().map(Toy::getDropFrequency).toList();
        double k = 100f / frequency.stream().reduce(0, (f1, f2) -> f1 + f2);
        dropFrequency = frequency.stream().map(item -> item * k).toList();
    }

    public Toy priceDrawing() {
        Toy price = toysList.get(getRandomIndex());

        return price;
    }

    private int getRandomIndex() {
        List<Integer> frequencyList = new ArrayList<>(1000);
        for (int i = 0; i < dropFrequency.size(); i++) {
            frequencyList.addAll(repeatedValueGenerator(i, (int) (dropFrequency.get(i) * 10)));
        }
        random = new Random();
        return frequencyList.get(random.nextInt(1000));
    }

    private List<Integer> repeatedValueGenerator(Integer num, int count) {
        return Stream.generate(() -> num)
                .limit(count)
                .collect(Collectors.toList());
    }
}
