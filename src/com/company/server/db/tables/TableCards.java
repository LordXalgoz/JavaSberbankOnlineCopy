package com.company.server.db.tables;

import com.company.common.datatools.DataStorage;
import com.company.common.entities.Card;
import com.company.common.entities.Client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

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

    public void InsertNewCard(Card card) throws Exception
    {
        try {
            Class.forName("org.postgresql.Driver");

            Properties props = new Properties();
            props.setProperty("user", login);
            props.setProperty("password", password);
            props.setProperty("ssl", "false");

            Connection connection = DriverManager.getConnection(url, props);

            Statement statement = connection.createStatement();

            String query = String.format("INSERT INTO sberbank.cards (number, money) VALUES ('%s', %s)", card.Number, card.Money);

            statement.executeUpdate(query);
        }
        catch (Exception e)
        {

        }
    }

    public int GetLastInsertedCardId() throws Exception
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
}
