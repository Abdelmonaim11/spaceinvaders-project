package com.example.entities;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

// Classe abstraite représentant une entité du jeu, dérivée de Rectangle pour bénéficier des propriétés géométriques.
// Elle permet de définir une entité avec une image et des propriétés visuelles.
public abstract class Entity extends Rectangle {

    // Image associée à l'entité. Représente l'image visuelle de l'entité dans le jeu.
    protected Image img;

    // Motif d'image qui permet de remplir la forme (ici un Rectangle) avec une image.
    protected ImagePattern imagePattern;

    // Constructeur qui initialise la position (x, y) et la taille (width, height) de l'entité.
    // Il appelle le constructeur de la classe Rectangle pour définir les propriétés géométriques.
    public Entity(double x, double y, double width, double height) {
        super(x, y, width, height); // Initialise la position et les dimensions de l'entité.
    }

    // Méthode pour récupérer l'image de l'entité.
    public Image getImg() {
        return img;
    }

    // Méthode pour définir l'image de l'entité.
    public void setImg(Image image) {
        this.img = image; // Affecte une nouvelle image à l'entité.
    }

    // Méthode pour récupérer le motif d'image utilisé pour le rendu visuel.
    public ImagePattern getImagePattern() {
        return imagePattern;
    }

    // Méthode pour définir un motif d'image pour l'entité, ce qui change son apparence.
    public void setImagePattern(ImagePattern imageP) {
        this.imagePattern = imageP; // Affecte un nouveau motif d'image à l'entité.
    }
}
