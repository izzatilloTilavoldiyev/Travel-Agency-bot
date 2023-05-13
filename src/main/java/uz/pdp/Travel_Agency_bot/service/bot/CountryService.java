package uz.pdp.Travel_Agency_bot.service.bot;

import java.sql.*;
import java.util.ArrayList;

import static uz.pdp.Travel_Agency_bot.util.BeanUtil.*;

public class CountryService {
    public ArrayList<String> continentsDB() {
        ArrayList<String> continents = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            String query = "select * from continent";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                continents.add(resultSet.getString(2));
            }
            return continents;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> countyDB(Long continent_id) {
        ArrayList<String> europeDB = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            String query = "select * from countries";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString(2).equals(continent_id.toString())) {
                    europeDB.add(resultSet.getString(3));
                }
            }
            return europeDB;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
