package com.tirsh.toy_lottery.ui;

import com.tirsh.toy_lottery.model.Toy;
import com.tirsh.toy_lottery.service.DataController;
import com.tirsh.toy_lottery.service.Logger;
import com.tirsh.toy_lottery.service.LotteryGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Component.LEFT_ALIGNMENT;

public class Ui {
    private DataController dataController;
    private LotteryGame lotteryGame;
    private Logger logger;
    private JFrame toyLotteryFrame;
    private DefaultListModel<Toy> currentList, lotteryList;
    private JButton newLotteryButton, startLotteryButton, addButton, removeButton;
    private JList jList, jListSelected;
    private JLabel toys_Label, selectedToysLabel;
    private JDialog jDialog;
    Container contentpane;

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

        jList = new JList(currentList);
        jListSelected = new JList<>(lotteryList);

        jList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jList.setLayoutOrientation(JList.VERTICAL_WRAP);
        jList.setVisibleRowCount(-1);
        jList.setBounds(20, 50, 105, 150);

        jListSelected.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jListSelected.setLayoutOrientation(JList.VERTICAL_WRAP);
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
        for (Object toy : toys) {
            currentList.removeElement(toy);
        }
        jList.revalidate();
        jListSelected.revalidate();
    }

    private void removeToy() {
        var toys = jListSelected.getSelectedValuesList();
        currentList.addAll(toys);
        for (Object toy : toys) {
            lotteryList.removeElement(toy);
        }
        jList.revalidate();
        jListSelected.revalidate();
    }

    private void startLottery(){
        var toys = jListSelected.getModel();
        List<Toy> toysList= new ArrayList<>();
        for (int i = 0; i < toys.getSize(); i++){
            toysList.add(((Toy)toys.getElementAt(i)));
        }
        lotteryGame = new LotteryGame(toysList);
        Toy chosenToy = lotteryGame.priceDrawing();
        dataController.decreaseQuantity(chosenToy);
        logger = new Logger(chosenToy);
        logger.writeDataToCSV();
        currentList.removeAllElements();
        lotteryList.removeAllElements();
        currentList.addAll(dataController.getAllToys());
        jList.revalidate();
        jListSelected.revalidate();
        createDialog(chosenToy.getTitle());
    }
    private void createDialog(String toy){
        jDialog = new JDialog(toyLotteryFrame, "Розыгрыш состоялся", true);
        jDialog.setLayout( new FlowLayout() );
        JButton b = new JButton ("OK");
        b.addActionListener ( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                jDialog.setVisible(false);
            }
        });
        jDialog.add( new JLabel ("Выпала игрушка: %s.".formatted(toy)));
        jDialog.add( new JLabel ("Информация о розыгрыше сохранена в draws.csv"));
        jDialog.add(b);
        jDialog.setSize(320,120);
        jDialog.setVisible(true);
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
                    startLottery();
                    break;
                }
            }
        }
    }
}
