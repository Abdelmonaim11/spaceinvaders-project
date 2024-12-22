package com.example.controller;

import com.example.entities.*;
import com.example.utils.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;


import java.util.*;

public class SpaceController implements Sounds, Constants, Images {


    private Ship ship;
    private ShipShoot shipShoot;
    //    private final AnimationTimer timer;
    private final FixedFrameRateTimer timer;
    private static int shipDeltaX;
    private List<Brick> walls;

    private Alien[][] aliens;
    private static long movingAliensCount = 0;
    private Group groupExplosion;
    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private static boolean initStartButton = false;
    private static Random random = new Random();
    private static LinkedList<AlienShoot> alienShootList;
    private Saucer saucer;
    private long saucerTime = 0;
    private static final Rectangle saucer100Rect = new Rectangle();


    @FXML
    private ImageView imgLogo;

    @FXML
    private ImageView heart1;
    @FXML
    private ImageView heart2;

    @FXML
    private ImageView heart3;

    @FXML
    private ImageView heart4;

    @FXML
    private ImageView heart5;
    @FXML
    private Pane board;

    @FXML
    private Label lblEndGame, lblRightScore, lblLeftScore, lblFPS;






    public SpaceController() {

        timer = new FixedFrameRateTimer(120) {
            @Override
            protected void loop() {
                movingAliensCount++;
                saucerTime++;
                handleShip();

                collisions();

                if (ship.isShipIsShooting()) {
                    handleShipShoot();
                }
                //Lag effect
                if (movingAliensCount % (100 - (10L * Alien.getSpeed())) == 0) {
                    Alien.alienMoving(aliens);
                }

                if (saucerTime % 1000 == 0) {
                    saucer = new Saucer(X_POS_INIT_SAUCER, Y_POS_INIT_SAUCER, SAUCER_WIDTH, SHIP_HEIGHT);
                    board.getChildren().add(saucer);
                    saucerTime = 1;
                } else if (saucer != null) {
                    saucer.saucerMoving(SAUCER_DELTA_X);
                }

                aliensShooting();
                AlienShoot.handleAliensShot(alienShootList, board);

                lblFPS.setVisible(true);
                lblFPS.setText("FPS : " + getFrameRate());
                endGame();
//                System.out.println(getFrameRate());
            }

        };

    }




    public void initGame() {
//        lives = 5;
//        updateHearts();
//        isHit = false; // Initialize the flag
        ship = new Ship(X_POS_INIT_SHIP, Y_POS_INIT_ShIP, SHIP_WIDTH, SHIP_HEIGHT);
        shipShoot = new ShipShoot(-SHIP_SHOOT_WIDTH, -SHIP_SHOOT_HEIGHT, SHIP_SHOOT_WIDTH, SHIP_SHOOT_HEIGHT);
        walls = new LinkedList<>();
//        hearts = new LinkedList<>();
        aliens = new Alien[5][10];
        movingAliensCount = 0;

        score.set(0);

//        groupExplosion = new Group(Explosion.explode());

        alienShootList = new LinkedList<>();

        lblEndGame.setText("");


    }

    @FXML
    void onStartAction() {
        if (!initStartButton) {

            Animation.animateLogoSpaceInvaders(imgLogo, 0, -500, 500, 1, 0, 300);

            board.requestFocus();
            initGame();
            Initialisation.initShip(ship, board);
            Initialisation.initShipShoot(shipShoot, board);
            Initialisation.initWalls(80, 400, 80, walls, board);
            ////
            // Initialisation.initHearts(hearts, board);
            ////
            Initialisation.initAliens(aliens, board);


            Initialisation.initSaucer100(saucer100Rect, board);

            timer.start();
            lblRightScore.textProperty().bind(Bindings.convert(score));
            lblLeftScore.setVisible(true);
            lblRightScore.setVisible(true);
            heart1.setVisible(true);
            heart2.setVisible(true);
            heart3.setVisible(true);
            heart4.setVisible(true);
            heart5.setVisible(true);
            score.setValue(0);
            initStartButton = true;
        }
    }

    @FXML
    void onKeyPressed(KeyEvent keyEvent) {

        switch (keyEvent.getCode()) {
            case LEFT:
                shipDeltaX = -SHIP_DELTA_X;
                handleShip();
                break;
            case RIGHT:
                shipDeltaX = SHIP_DELTA_X;
                handleShip();
                break;
            case SPACE:

                if (!ship.isShipIsShooting()) {
                    ship.setShipIsShooting(true);
                    ShipShoot.shipShootPlacement(shipShoot, ship);
                    Audio.playSound(SHIP_SHOOT_SOUND);
                }
                break;
        }
    }

    private void handleShip() {
        shipMoveHorizontal(shipDeltaX);
    }

    private void handleShipShoot() {
        if (shipShoot.getY() <= -20) {
            ship.setShipIsShooting(false);
        } else if (shipShoot.getY() >= -20) {
            shipShoot.setY(shipShoot.getY() - SHIP_SHOOT_DELTA_Y);
        }
    }

    private void aliensShooting() {
        for (Alien[] alienRow : aliens) {
            for (Alien alien : alienRow) {
                if (!alien.isDead()) {
                    if (random.nextInt(ALIEN_SHOOT_PROBABILITY) == 50) {
                        AlienShoot alienShoot = new AlienShoot(alien.getX() + (float) (ALIEN_WIDTH / 2), alien.getY(), ALIEN_SHOOT_WIDTH, ALIEN_SHOOT_HEIGHT);
                        alienShootList.add(alienShoot);
                        board.getChildren().add(alienShoot);

                        int randomNumber = (int) (Math.round(Math.random() * 3) + 1);
                        switch (randomNumber) {
                            case 1:
                                AudioClip alienShoot1 = new AudioClip(ALIEN_SHOOT_SOUND_1);
                                alienShoot1.setVolume(0.15);
                                alienShoot1.play();
                                break;
                            case 2:
                                AudioClip alienShoot2 = new AudioClip(ALIEN_SHOOT_SOUND_2);
                                alienShoot2.setVolume(0.15);
                                alienShoot2.play();
                                break;
                            case 3:
                                AudioClip alienShoot3 = new AudioClip(ALIEN_SHOOT_SOUND_3);
                                alienShoot3.setVolume(0.15);
                                alienShoot3.play();
                                break;
                            case 4:
                                AudioClip alienShoot4 = new AudioClip(ALIEN_SHOOT_SOUND_4);
                                alienShoot4.setVolume(0.15);
                                alienShoot4.play();
                                break;
                        }

                    }
                }

            }
        }
    }

    private void shipMoveHorizontal(int shipDeltaX) {
        ship.setX(ship.shipMoving(shipDeltaX));
    }

    private void collisions() {
        shipShootCollisions();
        aliensShootBrickCollisions();
        aliensWallsCollisions();
    }

    private void aliensWallsCollisions() {
        Brick brickToRemove = null;
        for (Brick brick : walls) {
            for (Alien[] alienRow : aliens) {
                for (Alien alien : alienRow) {
                    if (brick.getBoundsInParent().intersects(alien.getBoundsInParent())) {
                        brickToRemove = brick;

                    }
                }
            }
        }
        removeBrick(brickToRemove, true);
    }

    private void aliensShootBrickCollisions() {
        Brick brickToRemove = null;
        AlienShoot shootToRemove = null;
        for (Brick brick : walls) {
            for (AlienShoot alienShoot : alienShootList) {
                if (brick.getBoundsInParent().intersects(alienShoot.getBoundsInParent())) {
                    brickToRemove = brick;
                    shootToRemove = alienShoot;
                }

            }
        }
        removeBrick(brickToRemove, true);
        removeShoot(shootToRemove);
    }

    private void removeBrick(Brick brickToRemove, boolean whoShoot) {
        if (brickToRemove != null) {
            walls.remove(brickToRemove);
            board.getChildren().remove(brickToRemove);
            Audio.playSound(BRICK_DESTRUCTION_SOUND);

            if (score.get() >= BRICK_POINT && !whoShoot) {
                score.set(score.get() - BRICK_POINT);
            }
        }
    }

    private void removeShoot(AlienShoot shootToRemove) {
        alienShootList.remove(shootToRemove);
        board.getChildren().remove(shootToRemove);

    }

    private void shipShootCollisions() {
        Brick brickToRemove = null;



        for (Brick brick : walls) {
            if (brick.getBoundsInParent().intersects(shipShoot.getBoundsInParent())) {

                shipShoot.setX(-10);
                shipShoot.setY(-10);

                ship.setShipIsShooting(false);

                brickToRemove = brick;
            }
        }

        removeBrick(brickToRemove, false);

        for (Alien[] alienRow : aliens) {
            for (Alien alien : alienRow) {
                if (alien.getBoundsInParent().intersects(shipShoot.getBoundsInParent())) {
                    shipShoot.setX(-10);
                    shipShoot.setY(-10);

                    ship.setShipIsShooting(false);


                    groupExplosion = new Group(Explosion.explode());
                    groupExplosion.setLayoutX(alien.getX() - 10);
                    groupExplosion.setLayoutY(alien.getY() - 10);
                    board.getChildren().addAll(groupExplosion);

                    alien.setX(100);
                    alien.setY(-10000);

                    alien.setDead(true);

                    score.set(score.get() + (Alien.getType() * ALIEN_POINT));

                    board.getChildren().remove(alien);
                    Audio.playSound(ALIEN_DESTRUCTION_SOUND);
                }
            }
        }
        if (saucer != null) {
            if (!saucer.isDead()) {
                if (saucer.getBoundsInParent().intersects(shipShoot.getBoundsInParent())) {
                    saucer.setDead(true);
                    board.getChildren().remove(saucer);
                    shipShoot.setY(-10);
                    shipShoot.setX(-10);

                    Audio.playSound(SAUCER_DESTRUCTION_SOUND);

                    groupExplosion = new Group(Explosion.explode());
                    groupExplosion.setLayoutX(saucer.getX() - (double) (SAUCER_WIDTH / 3));
                    groupExplosion.setLayoutY(saucer.getY() - (double) (SAUCER_HEIGHT / 2));
                    board.getChildren().addAll(groupExplosion);

                    saucer.getSaucerPassingSound().stop();

                    score.set(score.get() + SAUCER_SCORE_POINTS);

                    saucer100Rect.setX(saucer.getX() + 30);
                    saucer100Rect.setY(saucer.getY());

                    saucer.setX(X_POS_INIT_SAUCER);
                    saucer.setY(Y_POS_INIT_SAUCER);

                    Timer timerScoreSaucer = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            saucer100Rect.setX(X_POS_SAUCER_SCORE);
                        }
                    };
                    timerScoreSaucer.schedule(timerTask, 700);
                }
            }

        }

        for (AlienShoot alienShoot : alienShootList) {
            if (alienShoot.getBoundsInParent().intersects(shipShoot.getBoundsInParent())) {

                board.getChildren().remove(alienShoot);
                Group explosionShoot = new Group(Explosion.explodeShoot());
                explosionShoot.setLayoutX(alienShoot.getX() - 10);
                explosionShoot.setLayoutY(alienShoot.getY() - 10);
                board.getChildren().addAll(explosionShoot);



                shipShoot.setX(WINDOW_WIDTH);
                shipShoot.setY(WINDOW_HEIGHT);
                alienShoot.setX(WINDOW_WIDTH - 100);
                alienShoot.setY(WINDOW_HEIGHT - 100);
                score.set(score.getValue() + SHIP_SHOOT_ALIEN_SHOOT);

            }
        }

    }


    @FXML
    void onKeyReleased(KeyEvent keyEvent) {

        shipDeltaX = 0;
    }

    @FXML
    void onStopAction() {

        timer.stop();
        initStartButton = false;
        walls.clear();
        alienShootList.clear();
        Alien.setSpeed(ALIEN_SPEED);
        board.getChildren().clear();
        if (saucer != null) {
            saucer.getSaucerPassingSound().stop();
        }
        lblLeftScore.setVisible(false);
        lblRightScore.setVisible(false);
        Animation.animateLogoSpaceInvaders(imgLogo, -500, 0, 500, 0, 1, 700);
        heart1.setVisible(false);
        heart2.setVisible(false);
        heart3.setVisible(false);
        heart4.setVisible(false);
        heart5.setVisible(false);
    }


//private int lives = 5;
//    private boolean isHit = false; // Flag to prevent multiple hits in one frame
//
//    private void endGame() {
//        // Check if all aliens are dead (win condition)
//        boolean allAliensDead = Arrays.stream(aliens).allMatch(a -> Arrays.stream(a).allMatch(Alien::isDead));
//        if (allAliensDead) {
//            timer.stop();
//            lblEndGame.setText(END_GAME_WIN);
//            board.getChildren().remove(ship);
//            if (saucer != null) {
//                board.getChildren().remove(saucer);
//            }
//            return;
//        }
//
//        // Check for ship collision with alien or alien shoot (lose condition)
//        boolean shipCollision = alienShootList.stream().anyMatch(shootAlien -> shootAlien.getBoundsInParent().intersects(ship.getBoundsInParent()))
//                || Arrays.stream(aliens).anyMatch(a -> Arrays.stream(a).anyMatch(alien -> alien.getBoundsInParent().intersects(ship.getBoundsInParent())));
//
//        // Check if any alien reaches the bottom (lose condition)
//        boolean alienReachedBottom = Arrays.stream(aliens).anyMatch(a -> Arrays.stream(a).anyMatch(alien -> alien.getY() > WINDOW_HEIGHT - WINDOW_MARGIN));
//
//        if ((shipCollision || alienReachedBottom) && !isHit) { // Check the flag here
//            isHit = true; // Set the flag to true to prevent further hits this frame
//            lives--;
//
//            if (lives > 0) {
//                ship.setX(X_POS_INIT_SHIP);
//                ship.setY(Y_POS_INIT_ShIP);
//                updateHearts();
//                // Optional invulnerability period (using a Timer):
//                Timer invulnerabilityTimer = new Timer();
//                invulnerabilityTimer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        isHit = false; // Reset the flag after a short delay
//                    }
//                }, 500); // 500 milliseconds (adjust as needed)
//
//                return;
//            } else {
//                // Game Over
//                Group groupExplosionShip = new Group(Explosion.explodeShip());
//                groupExplosionShip.setLayoutX(ship.getX() - (SHIP_WIDTH / 2));
//                groupExplosionShip.setLayoutY(ship.getY() - SHIP_HEIGHT);
//                board.getChildren().addAll(groupExplosionShip);
//                Audio.playSound(SHIP_DESTRUCTION_SOUND);
//
//                timer.stop();
//                lblEndGame.setText(END_GAME_LOOSE);
//                board.getChildren().remove(ship);
//                if (saucer != null) {
//                    saucer.getSaucerPassingSound().stop();
//                    board.getChildren().remove(saucer);
//                }
//            }
//        } else if (!shipCollision && !alienReachedBottom){
//            isHit = false;
//        }
//    }
//    private void updateHearts() {
//        // This method updates the heart images based on the remaining lives.
//        // Assuming you have ImageView objects for hearts (heart1, heart2, etc.)
//
//        heart1.setVisible(lives >= 1);
//        heart2.setVisible(lives >= 2);
//        heart3.setVisible(lives >= 3);
//        heart4.setVisible(lives >= 4);
//        heart5.setVisible(lives == 5);
//    }

    private int playerHitCount = 0; // Initialisé à 0
    private static final int MAX_HITS = 5; // Nombre maximum de hits avant de mourir

    private boolean hasBeenHit = false; // Flag pour savoir si un impact a déjà été pris en compte
    private long lastHitTime = 0; // Pour vérifier combien de temps s'est écoulé depuis le dernier impact

    private void endGame() {
        boolean result = Arrays.stream(aliens).allMatch(a -> Arrays.stream(a).allMatch(Alien::isDead));

        if (result) {
            timer.stop();
            lblEndGame.setText(END_GAME_WIN);
            board.getChildren().remove(ship);
            board.getChildren().remove(saucer);
        }

        boolean isHit = alienShootList.stream().anyMatch(shootAlien -> shootAlien.getBoundsInParent().intersects(ship.getBoundsInParent())) ||
                Arrays.stream(aliens).anyMatch(a -> Arrays.stream(a).anyMatch(alien -> alien.getBoundsInParent().intersects(ship.getBoundsInParent()))) ||
                Arrays.stream(aliens).anyMatch(a -> Arrays.stream(a).anyMatch(alien -> alien.getY() > WINDOW_HEIGHT - WINDOW_MARGIN));

        // Vérifier si l'impact est pris en compte et que l'intervalle entre les impacts est assez grand
        if (isHit && playerHitCount < MAX_HITS && !hasBeenHit) {
            hasBeenHit = true; // Marquer que l'impact a été pris en compte

            Group groupExplosionShip = new Group(Explosion.explodeShip());
            groupExplosionShip.setLayoutX(ship.getX() - (SHIP_WIDTH / 2));
            groupExplosionShip.setLayoutY(ship.getY() - SHIP_HEIGHT);
            board.getChildren().addAll(groupExplosionShip);
            Audio.playSound(SHIP_DESTRUCTION_SOUND);

            playerHitCount++;
            heart5.setVisible(!(playerHitCount >= 1));
            heart4.setVisible(!(playerHitCount >= 2));
            heart3.setVisible(!(playerHitCount >= 3));
            heart2.setVisible(!(playerHitCount >= 4));
            heart1.setVisible(!(playerHitCount == 5));
            // Réinitialiser le flag après un court délai ou sous certaines conditions
            lastHitTime = System.currentTimeMillis(); // Enregistrer l'heure de l'impact

            if (playerHitCount >= MAX_HITS) {
                timer.stop();
                lblEndGame.setText(END_GAME_LOOSE);
                board.getChildren().remove(ship);
                board.getChildren().remove(saucer);
                if (saucer != null) {
                    saucer.getSaucerPassingSound().stop();
                }
            }
        }

        // Réinitialiser le flag hasBeenHit après un certain temps (par exemple, 1 seconde)
        if (hasBeenHit && System.currentTimeMillis() - lastHitTime > 1000) {
            hasBeenHit = false; // Réinitialiser le flag après 1 seconde
        }
    }

}