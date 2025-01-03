package com.example.entities;

import com.example.utils.Images;
import javafx.scene.paint.ImagePattern;

public class ShipShoot extends Entity{
    public ShipShoot(double x, double y, double width, double height) {
        super(x, y, width, height);
        super.setImgPattern(new ImagePattern(Images.SHIP_SHOOT));
        super.setFill(super.getImgPattern());
    }

    public static void shipShootPlacement(ShipShoot shipShoot, Ship ship) {
        shipShoot.setX(ship.getX() + 15);
        shipShoot.setY(ship.getY());
    }
}
