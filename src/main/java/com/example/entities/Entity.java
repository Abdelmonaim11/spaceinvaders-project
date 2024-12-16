package com.example.entities;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


public abstract class Entity extends Rectangle {

    protected Image img; // Image associée à l'entité
    protected ImagePattern imagePattern; // Motif d'image pour le rendu visuel

    // Constructeur qui initialise la position et la taille de l'entité
    public Entity(double x, double y, double width, double height){
        super(x, y, width, height);
    }

    public Image getImg(){
        return img;
    }

    public void setImg(Image image){
        this.img = image;
    }

    public ImagePattern getImagePattern(){
        return imagePattern;
    }

    public void setImagePattern(ImagePattern imageP){
        this.imagePattern = imageP;
    }
}
