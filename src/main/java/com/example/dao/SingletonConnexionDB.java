package com.example.dao;

import java.sql.*;


public class SingletonConnexionDB {

    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gerer_joueurs", "root", "");
        }

        catch (Exception e){
            e.getMessage();
        }
    }
    public static Connection getConnection(){
        return connection;
    }
}
