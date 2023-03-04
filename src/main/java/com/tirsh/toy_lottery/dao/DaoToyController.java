package com.tirsh.toy_lottery.dao;

import com.tirsh.toy_lottery.model.Toy;
import com.tirsh.toy_lottery.util.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DaoToyController {
    DBConnection dbConnection = new DBConnection();

    private ResultSet dbOperate(String query) {
        try {
            Statement statement = dbConnection.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Toy> getAll() throws SQLException {
        var result = dbOperate("SELECT * FROM toys");
        if (result != null) {
            return getDataFromResultSet(result);
        }
        return null;
    }

    public Toy getById(int id) throws SQLException {
        var result = dbOperate("SELECT * FROM toys WHERE id = %d".formatted(id));
        if (result != null) {
            return getDataFromResultSet(result).get(0);
        }
        return null;
    }

    public Toy save(Toy toy){
        if (toy.isNew()) {
            var result = dbOperate("INSERT INTO toys (title, quantity, drop_frequency) VALUES (%s, %d, %d);".formatted(toy.getTitle(), toy.getQuantity(), toy.getDropFrequency()));
            return result == null ? null : toy;
        } else {
            var result = dbOperate("UPDATE toys SET title = %s, quantity = %d, drop_frequency = %d WHERE id = %d;".formatted(toy.getTitle(), toy.getQuantity(), toy.getDropFrequency(), toy.getId()));
            return result == null ? null : toy;
        }
    }

    public boolean delete(int id){
        var result = dbOperate("DELETE FROM toys WHERE id = %d;".formatted(id));

        return result != null;
    }

    private List<Toy> getDataFromResultSet(ResultSet resultSet) throws SQLException {
        List<Toy> toys = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            int quantity = resultSet.getInt("quantity");
            int dropFreq = resultSet.getInt("drop_frequency");
            toys.add(new Toy(id, title, quantity, dropFreq));
        }
        return toys;
    }
}
