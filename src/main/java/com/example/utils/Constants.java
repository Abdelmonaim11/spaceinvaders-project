package com.example.utils;

public interface Constants {

    /********************************  WINDOW  *********************************/

    int WINDOW_WIDTH = 600;
    int WINDOW_HEIGHT = 600;
    int WINDOW_MARGIN = 50;

    /***********************************  ALIEN  ***************************************/

    int ALIEN_WIDTH = 32;  // Largeur d'un alien
    int ALIEN_HEIGHT = 24;  // Hauteur d'un alien

    int X_POS_INIT_ALIEN = 40 + WINDOW_MARGIN;  // Position initiale X des aliens
    int Y_POS_INIT_ALIEN = 50;  // Position initiale Y des aliens
    int GAP_LINES_ALIEN = 10;  // Espace entre les lignes d'aliens
    int GAP_COLUMNS_ALIEN = 10;  // Espace entre les colonnes d'aliens

    int ALIEN_DELTA_X = 5;  // Déplacement horizontal des aliens
    int ALIEN_DELTA_Y = 20;  // Déplacement vertical des aliens
    int ALIEN_SPEED = 1;  // Vitesse des aliens
    int ALIEN_POINT = 10; // Points attribués par alien

}
