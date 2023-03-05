package com.tirsh.toy_lottery.service;

import com.tirsh.toy_lottery.model.Toy;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Logger {
    private final static String DRAW_HISTORY_CSV_FILE = "draws.csv";
    private String[] dataLines;

    public Logger(Toy toy) {
        createDataLines(toy);
    }

    private void createDataLines(Toy toy){
        dataLines = new String[4];
        dataLines[0] = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        dataLines[1] = toy.getTitle();
        dataLines[2] = "остаток %d шт.".formatted(toy.getQuantity());
        dataLines[3] = "вероятность выпадения %d".formatted(toy.getDropFrequency());
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }


    public void writeDataToCSV(){
        File csvOutputFile = new File(DRAW_HISTORY_CSV_FILE);
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(csvOutputFile,true))) {
            pw.println(convertToCSV(dataLines));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
