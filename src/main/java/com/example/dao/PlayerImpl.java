package com.example.dao;

import com.example.entities.Player;

import java.security.spec.ECField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlayerImpl {

    private Connection connection = SingletonConnexionDB.getConnection();

    public void add(Player player){

        String query = "INSERT INTO player (nomPrenom, score) VALUES (?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, player.getNomPrenom());
            ps.setInt(2, 0);

        }
        catch (Exception e){
            e.getMessage();
        }
    }

    public List<Player> findAll(){
        List<Player> players = new ArrayList<>();
        String query ="SELECT * FROM player";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                Player player = new Player(
                        rs.getInt("idPlayer "),
                        rs.getString("nomPrenom"),
                        rs.getInt("score")
                );
                players.add(player);
                System.out.printf(player.toString());
            }
        }
        catch (Exception e){
            e.getMessage();
        }

        return players;
    }
}
