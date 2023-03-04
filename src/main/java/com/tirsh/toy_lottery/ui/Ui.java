package com.tirsh.toy_lottery.ui;

import com.tirsh.toy_lottery.model.Toy;
import com.tirsh.toy_lottery.service.DataController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ui {
    DataController dataController;
    JFrame toyLotteryFrame;
    DefaultListModel<Toy> currentList, lotteryList;
    JButton newLotteryButton, startLotteryButton, addButton, removeButton;
    JList jList, jListSelected;
    JLabel toys_Label, selectedToysLabel;

    public Ui(DataController dataController) {
        this.dataController = dataController;
        initFrame();
    }

    private void initFrame() {
        toyLotteryFrame = new JFrame("Toys lottery");
        newLotteryButton = new JButton("Новая Лотерея");
        newLotteryButton.setBounds(50, 100, 200, 30);
        toyLotteryFrame.add(newLotteryButton);
        toyLotteryFrame.setSize(320, 300);
        toyLotteryFrame.setLayout(null);
        toyLotteryFrame.setVisible(true);
        newLotteryButton.setActionCommand("newLottery");
        newLotteryButton.addActionListener(new ButtonClickListener());
        toyLotteryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void chooseToysFrame() {
        toys_Label = new JLabel("Список игрушек:");
        toys_Label.setBounds(20, 00, 105, 40);

        selectedToysLabel = new JLabel("Выбраны:");
        selectedToysLabel.setBounds(180, 0, 105, 40);

        currentList = new DefaultListModel<>();
        lotteryList = new DefaultListModel<>();
        currentList.addAll(dataController.getAllToys());
//        currentList.addAll(dataController.getAllToys().stream().map(item -> item.getTitle()).toList());
        jList = new JList(currentList); //data has type Object[]
        jListSelected = new JList<>(lotteryList);

        jList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        jList.setVisibleRowCount(-1);
        jList.setBounds(20, 50, 105, 150);
        //        JScrollPane listScroller = new JScrollPane(jList);
        //        listScroller.setPreferredSize(new Dimension(150, 80));

        jListSelected.setVisibleRowCount(-1);
        jListSelected.setBounds(180, 50, 105, 150);

        addButton = new JButton(">");
        removeButton = new JButton("<");
        addButton.setBounds(130, 100, 45, 20);
        removeButton.setBounds(130, 140, 45, 20);
        addButton.setActionCommand("addToy");
        addButton.addActionListener(new ButtonClickListener());
        removeButton.setActionCommand("removeToy");
        removeButton.addActionListener(new ButtonClickListener());
        removeButton.addActionListener(new ButtonClickListener());

        startLotteryButton = new JButton("Начать розыгрышь");
        startLotteryButton.setBounds(50, 220, 200, 30);
        startLotteryButton.setActionCommand("startLottery");
        startLotteryButton.addActionListener(new ButtonClickListener());

        toyLotteryFrame.add(toys_Label);
        toyLotteryFrame.add(selectedToysLabel);
        toyLotteryFrame.add(jList);
        toyLotteryFrame.add(jListSelected);
        toyLotteryFrame.add(startLotteryButton);
        toyLotteryFrame.add(addButton);
        toyLotteryFrame.add(removeButton);

        toyLotteryFrame.repaint();
    }

    private void addToy() {
        var toys = jList.getSelectedValuesList();
        lotteryList.addAll(toys);
        int[] toysIndexes = jList.getSelectedIndices();

        for (int item : toysIndexes) {
            currentList.remove(item);
        }
        jList.revalidate();
        jListSelected.revalidate();


    }


    private void removeToy() {

    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case "newLottery": {
                    newLotteryButton.setVisible(false);
                    chooseToysFrame();
                    break;
                }
                case "addToy": {
                    addToy();
                    break;
                }
                case "removeToy": {
                    removeToy();
                    break;
                }
                case "startLottery": {
                    break;
                }


            }
        }
    }
}
