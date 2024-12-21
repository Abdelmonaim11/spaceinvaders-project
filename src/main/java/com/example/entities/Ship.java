package com.example.entities;

import com.example.utils.Images;
import javafx.scene.paint.ImagePattern;

public class Ship extends Entity{

    private boolean shipIsShooting;

    public Ship(double x, double y, double width, double height) {
        super(x, y, width, height);
        super.setImagePattern(new ImagePattern(Images.SHIP));
        super.setFill(super.getImagePattern());
        this.shipIsShooting = false;
    }


}
