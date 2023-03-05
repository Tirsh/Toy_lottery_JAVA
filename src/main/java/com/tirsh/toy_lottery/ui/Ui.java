package com.tirsh.toy_lottery.ui;

import com.tirsh.toy_lottery.model.Toy;
import com.tirsh.toy_lottery.service.DataController;
import com.tirsh.toy_lottery.service.Logger;
import com.tirsh.toy_lottery.service.LotteryGame;
import com.tirsh.toy_lottery.util.ButtonColumn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Ui {
    private final DataController dataController;
    private JFrame toyLotteryFrame;
    private DefaultListModel<Toy> currentList, lotteryList;
    private JButton newLotteryButton, startLotteryButton, addButton, removeButton, backFromLotteryButton, addToyButton, allToyButton;
    private JList<Toy> jList, jListSelected;
    private JLabel toysLabel, selectedToysLabel;
    private JDialog jDialog;
    private JDialog addNewDialog;
    private JTextField title, quantity, dropFrequency;

    public Ui(DataController dataController) {
        this.dataController = dataController;
    }

    public void initUI() {
        toyLotteryFrame = new JFrame("Toys lottery");

        newLotteryButton = new JButton("Новая Лотерея");
        newLotteryButton.setBounds(50, 80, 200, 30);
        addToyButton = new JButton("Добавить игрушку");
        addToyButton.setBounds(50, 120, 200, 30);
        allToyButton = new JButton("Показать игрушки");
        allToyButton.setBounds(50, 160, 200, 30);
        toyLotteryFrame.add(newLotteryButton);
        toyLotteryFrame.add(addToyButton);
        toyLotteryFrame.add(allToyButton);

        toyLotteryFrame.setSize(320, 300);
        toyLotteryFrame.setLayout(null);
        toyLotteryFrame.setVisible(true);

        addToyButton.setActionCommand("addNewToy");
        addToyButton.addActionListener(new ButtonClickListener());

        allToyButton.setActionCommand("showAll");
        allToyButton.addActionListener(new ButtonClickListener());

        newLotteryButton.setActionCommand("newLottery");
        newLotteryButton.addActionListener(new ButtonClickListener());

        toyLotteryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void chooseToysFrame() {
        toysLabel = new JLabel("Список игрушек:");
        toysLabel.setBounds(20, 0, 105, 40);

        selectedToysLabel = new JLabel("Выбраны:");
        selectedToysLabel.setBounds(180, 0, 105, 40);

        currentList = new DefaultListModel<>();
        lotteryList = new DefaultListModel<>();
        currentList.addAll(dataController.getAllToys());

        jList = new JList<>(currentList);
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

        startLotteryButton = new JButton("Начать розыгрыш");
        startLotteryButton.setBounds(15, 220, 180, 30);
        startLotteryButton.setActionCommand("startLottery");
        startLotteryButton.addActionListener(new ButtonClickListener());

        backFromLotteryButton = new JButton("Назад");
        backFromLotteryButton.setBounds(210, 220, 80, 30);
        backFromLotteryButton.setActionCommand("menu");
        backFromLotteryButton.addActionListener(new ButtonClickListener());

        toyLotteryFrame.add(toysLabel);
        toyLotteryFrame.add(selectedToysLabel);
        toyLotteryFrame.add(jList);
        toyLotteryFrame.add(jListSelected);
        toyLotteryFrame.add(startLotteryButton);
        toyLotteryFrame.add(backFromLotteryButton);
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

    private void startLottery() {
        var toys = jListSelected.getModel();
        if (toys.getSize() == 0) {
            return;
        }
        List<Toy> toysList = new ArrayList<>();
        for (int i = 0; i < toys.getSize(); i++) {
            toysList.add(toys.getElementAt(i));
        }
        LotteryGame lotteryGame = new LotteryGame(toysList);
        Toy chosenToy = lotteryGame.priceDrawing();
        dataController.decreaseQuantity(chosenToy);
        Logger logger = new Logger(chosenToy);
        logger.writeDataToCSV();
        currentList.removeAllElements();
        lotteryList.removeAllElements();
        currentList.addAll(dataController.getAllToys());
        jList.revalidate();
        jListSelected.revalidate();
        createDialog(chosenToy.getTitle());
    }

    private void createDialog(String toy) {
        jDialog = new JDialog(toyLotteryFrame, "Розыгрыш состоялся", true);
        jDialog.setLayout(new FlowLayout());
        JButton b = new JButton("OK");
        b.addActionListener(e -> jDialog.setVisible(false));
        jDialog.add(new JLabel("Выпала игрушка: %s.".formatted(toy)));
        jDialog.add(new JLabel("Информация о розыгрыше сохранена в draws.csv"));
        jDialog.add(b);
        jDialog.setSize(320, 120);
        jDialog.setVisible(true);
    }

    private void createAddDialog(Toy toy) {
        addNewDialog = new JDialog(toyLotteryFrame, "Добавить новую игрушку", true);
        addNewDialog.setLayout(new FlowLayout());

        title = new JTextField(toy.getTitle(), 30);
        title.setBounds(50, 100, 200, 30);
        quantity = new JTextField("%d".formatted(toy.getQuantity()), 30);
        quantity.setBounds(50, 150, 200, 30);
        dropFrequency = new JTextField("%d".formatted(toy.getDropFrequency()), 30);
        dropFrequency.setBounds(50, 200, 200, 30);
        addNewDialog.add(new JLabel("Введите название:"));
        addNewDialog.add(title);
        addNewDialog.add(new JLabel("Введите колличество:"));
        addNewDialog.add(quantity);
        addNewDialog.add(new JLabel("Введите вероятность:"));
        addNewDialog.add(dropFrequency);
        JButton submitButton = new JButton("OK");
        submitButton.addActionListener(e -> {
            if (title.getText().length() > 0 && isInt(quantity.getText()) && isInt(dropFrequency.getText())) {
                dataController.save(new Toy(toy.getId(), title.getText(),
                        Integer.parseInt(quantity.getText()), Integer.parseInt(dropFrequency.getText())));
            }
            addNewDialog.setVisible(false);
        });
        addNewDialog.add(submitButton);
        addNewDialog.setSize(320, 220);
        addNewDialog.setVisible(true);
    }

    private void showAllDialog() {
        JDialog showAllDialog = new JDialog(toyLotteryFrame, "Список игрушек", true);
        showAllDialog.setLayout(new FlowLayout());
        String[] column = {"ID", "TITLE", "QUANTITY", "DROP", "UPDATE"};

        DefaultTableModel model = new DefaultTableModel(convertDataToTable(dataController.getAllToys()), column);
        JTable jTable = new JTable(model);

        Action delete = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                JTable table = (JTable)e.getSource();
                int modelRow = Integer.parseInt( e.getActionCommand() );
                showAllDialog.setVisible(false);
                updateToy(table.getModel().getValueAt(modelRow,0));
            }
        };

        ButtonColumn buttonColumn = new ButtonColumn(jTable, delete, 4);
        buttonColumn.setMnemonic(KeyEvent.VK_D);


        jTable.setBounds(30, 40, 320, 300);
        JScrollPane sp = new JScrollPane(jTable);
        showAllDialog.add(sp);
        showAllDialog.setSize(480, 300);
        showAllDialog.setVisible(true);
    }

    private void updateToy(Object valueAt) {
        Toy updatedToy = dataController.getToy(Integer.parseInt(valueAt.toString()));
        createAddDialog(updatedToy);
        title.setText(updatedToy.getTitle());
        quantity.setText("%d".formatted(updatedToy.getQuantity()));
        dropFrequency.setText("%d".formatted(updatedToy.getDropFrequency()));
    }

    private String[][] convertDataToTable(List<Toy> allToys) {
        String[][] data = new String[allToys.size()][5];
        int i = 0, j = 0;
        for (Toy toy : allToys) {
            data[i][j++] = "%d".formatted(toy.getId());
            data[i][j++] = toy.getTitle();
            data[i][j++] = "%d".formatted(toy.getQuantity());
            data[i][j++] = "%d".formatted(toy.getDropFrequency());
            data[i][j] = "UPDATE";
            i++;
            j = 0;
        }
        return data;
    }

    private boolean isInt(String num) {
        return num.matches("[-+]?\\d+");
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case "newLottery" -> {
                    newLotteryButton.setVisible(false);
                    addToyButton.setVisible(false);
                    allToyButton.setVisible(false);
                    chooseToysFrame();
                }
                case "addToy" -> addToy();
                case "removeToy" -> removeToy();
                case "startLottery" -> startLottery();
                case "addNewToy" -> createAddDialog(null);
                case "showAll" -> showAllDialog();
                case "menu" -> {
                    toysLabel.setVisible(false);
                    selectedToysLabel.setVisible(false);
                    addButton.setVisible(false);
                    removeButton.setVisible(false);
                    jList.setVisible(false);
                    jListSelected.setVisible(false);
                    startLotteryButton.setVisible(false);
                    backFromLotteryButton.setVisible(false);
                    addToyButton.setVisible(true);
                    allToyButton.setVisible(true);
                    newLotteryButton.setVisible(true);
                }
            }
        }
    }
}
