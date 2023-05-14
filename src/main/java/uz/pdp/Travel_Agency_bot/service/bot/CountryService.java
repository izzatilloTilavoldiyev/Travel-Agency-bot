package uz.pdp.Travel_Agency_bot.service.bot;

import java.sql.*;
import java.util.ArrayList;

import static uz.pdp.Travel_Agency_bot.util.DatabaseUtils.*;

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

    public ArrayList<String> transportsDB() {
        ArrayList<String> transportsDB = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            String query = "select * from transports";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                transportsDB.add(resultSet.getString(2));
            }
            return transportsDB;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String countryInfoDB(String country) {
        String res = "";
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            String query = "select * from country_info where country_name = '"+ country + "'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                res = resultSet.getString(3);
            }
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Long getContinentId(String continent) {
        long continent_id = 0L;
        try {
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            String query = "select id from continent where name like '"+continent+"'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                continent_id = resultSet.getLong(1);
            }
            return continent_id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
