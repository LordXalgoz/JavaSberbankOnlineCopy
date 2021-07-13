package com.company.server.db.tables;

import com.company.common.entities.Card;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class TableCardsClients
{
    private String url;
    private String login;
    private String password;

    public TableCardsClients(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;
    }

    public void InsertNewCardForClient(int idClient, int idCard) throws Exception
    {
        try {
            Class.forName("org.postgresql.Driver");

            Properties props = new Properties();
            props.setProperty("user", login);
            props.setProperty("password", password);
            props.setProperty("ssl", "false");

            Connection connection = DriverManager.getConnection(url, props);

            Statement statement = connection.createStatement();

            String query = String.format("INSERT INTO sberbank.clientscards (idclient, idcard) VALUES (%d,%d)", idClient, idCard);

            statement.executeUpdate(query);
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}
