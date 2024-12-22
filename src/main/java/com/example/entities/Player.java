package com.example.entities;

import java.util.List;

public class Player {

    private int idPlayer;
    private String nomPrenom;
    private int score;

    public Player(int idPlayer, String nomPrenom, int score) {
        this.idPlayer = idPlayer;
        this.nomPrenom = nomPrenom;
        this.score = score;
    }

    public Player(String nomPrenom, int score){
        this.nomPrenom = nomPrenom;
        this.score = score;
    }

    public String getNomPrenom() {
        return nomPrenom;
    }

    public void setNomPrenom(String nomPrenom) {
        this.nomPrenom = nomPrenom;
    }

    public double getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }
}
