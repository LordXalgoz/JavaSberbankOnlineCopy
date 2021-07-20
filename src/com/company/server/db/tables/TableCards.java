package com.company.server.db.tables;

import com.company.common.communication.Response;
import com.company.common.datatools.DataStorage;
import com.company.common.entities.Card;
import com.company.common.entities.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

//Сокрывает в себя логику с работой с БД

public class TableCards {
    private String url;
    private String login;
    private String password;

    public TableCards(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;
    }

    public boolean ExistCardByNumber(String number) throws Exception {
        try {
            Class.forName("org.postgresql.Driver");

            Properties props = new Properties();
            props.setProperty("user", login);
            props.setProperty("password", password);
            props.setProperty("ssl", "false");

            Connection connection = DriverManager.getConnection(url, props);

            Statement statement = connection.createStatement();

            String query = String.format("SELECT * FROM sberbank.cards WHERE number='%s'", number);

            ResultSet resultSet = statement.executeQuery(query);

            boolean findCardResult = resultSet.next();

            connection.close();

            return findCardResult;
        } catch (Exception e) {
            throw e;
        }
    }

    public void InsertNewCard(String number, int money) throws Exception
    {
        try {
            Class.forName("org.postgresql.Driver");

            Properties props = new Properties();
            props.setProperty("user", login);
            props.setProperty("password", password);
            props.setProperty("ssl", "false");

            Connection connection = DriverManager.getConnection(url, props);

            Statement statement = connection.createStatement();

            String query = String.format("INSERT INTO sberbank.cards (number, money) VALUES ('%s', %d)",number, money);

            statement.executeUpdate(query);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public void AddMoneyToCard(String number, int money) throws Exception
    {
        try {
            Class.forName("org.postgresql.Driver");

            Properties props = new Properties();
            props.setProperty("user", login);
            props.setProperty("password", password);
            props.setProperty("ssl", "false");

            Connection connection = DriverManager.getConnection(url, props);

            Statement statement = connection.createStatement();

            String query = String.format("UPDATE sberbank.cards SET money = money + %d WHERE number='%s'", money, number);

            statement.executeUpdate(query);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public int GetCardMoneyByNumber(String number) throws Exception
    {
        try
        {
            Class.forName("org.postgresql.Driver");

            Properties props = new Properties();
            props.setProperty("user", login);
            props.setProperty("password", password);
            props.setProperty("ssl", "false");

            Connection connection = DriverManager.getConnection(url, props);

            Statement statement = connection.createStatement();

            String query = String.format("SELECT money FROM sberbank.cards WHERE number='%s'", number);

            ResultSet resultSet = statement.executeQuery(query);

            resultSet.next();
            int money = resultSet.getInt(1);

            connection.close();

            return money;
        }catch (Exception e){
            throw e;
        }
    }

    public void SendMoneyFromCardToCard(String numberFrom, String numberTo, int money) throws Exception
    {
        try {
            Class.forName("org.postgresql.Driver");

            Properties props = new Properties();
            props.setProperty("user", login);
            props.setProperty("password", password);
            props.setProperty("ssl", "false");

            Connection connection = DriverManager.getConnection(url, props);

            Statement statement = connection.createStatement();

            String query = String.format("UPDATE sberbank.cards SET money = money - %d WHERE number='%s'",money, numberFrom);

            statement.executeUpdate(query);

            query = String.format("UPDATE sberbank.cards SET money = money + %d WHERE number='%s'",money, numberTo);

            statement.executeUpdate(query);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public int GetLastInsertedCardId() throws SQLException, ClassNotFoundException
    {
        try {
            Class.forName("org.postgresql.Driver");

            Properties props = new Properties();
            props.setProperty("user", login);
            props.setProperty("password", password);
            props.setProperty("ssl", "false");

            Connection connection = DriverManager.getConnection(url, props);

            Statement statement = connection.createStatement();

            String query = String.format("SELECT max(id) FROM sberbank.cards");

            ResultSet resultSet = statement.executeQuery(query);

            resultSet.next();
            int idCard = resultSet.getInt(1);

            connection.close();

            return idCard;
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<Card> GetCardsByIdClient(int idClient) throws Exception
    {
        try {
            Class.forName("org.postgresql.Driver");

            Properties props = new Properties();
            props.setProperty("user", login);
            props.setProperty("password", password);
            props.setProperty("ssl", "false");

            Connection connection = DriverManager.getConnection(url, props);

            Statement statement = connection.createStatement();

            String query = String.format("SELECT * FROM sberbank.cards WHERE id IN(SELECT idcard FROM sberbank.clientscards WHERE idclient=%d)", idClient);

            ResultSet resultSet = statement.executeQuery(query);

            ArrayList<Card> cards = new ArrayList<>();

            while (resultSet.next() == true) {
                Card card = new Card(
                        resultSet.getInt("id"),
                        resultSet.getString("number"),
                        resultSet.getInt("money")
                );

                cards.add(card);
            }

            connection.close();

            return cards;
        } catch (Exception e) {
            throw e;
        }
    }
}
