package com.example.entities;

import com.example.utils.Constants;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Alien extends Entity {

    private static boolean goRight = true;

    private static boolean alienPosition = true;

    private static int speed = Constants.ALIEN_SPEED;

    private static int type;

    private boolean isDead;

    public Alien(double x, double y, double width, double height, Image image, int type) {
        super(x, y, width, height);
        super.setImg(image);
        super.setImagePattern(new ImagePattern(super.getImg()));
        super.setFill(super.getImagePattern());
        this.type = type;
    }


    public static void alienMoving(Alien[][] aliens){

        if (goRight){
            for (int column = 0 ; column < 10 ; column++){
                for (int ligne = 0 ; ligne < 5 ; ligne++){
                    aliens[ligne][column].setX(aliens[ligne][column].getX() + Constants.ALIEN_DELTA_X);
                }
            }
        }
        else {
            for (int column = 0 ; column < 10 ; column++){
                for (int ligne = 0 ; ligne < 5 ; ligne++){
                    aliens[ligne][column].setX(aliens[ligne][column].getX() - Constants.ALIEN_DELTA_Y);
                }
            }
        }

        int imageNumber;

        if (alienPosition){
            imageNumber = 2;
            alienPosition = false;
        }
        else {
            imageNumber = 1;
            alienPosition = true;
        }
    }

    public static void aliensMovingIntoBoard(Alien[][] aliens){

    }


}
