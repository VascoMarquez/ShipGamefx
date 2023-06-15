package com.example.demo;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class HelloApplication extends Application {

    private static final double WIDTH = 800;
    private static final double HEIGHT = 480;
    private static final double OBJECT_WIDTH = 50;
    private static final double OBJECT_HEIGHT = 50;
    private static final double CAMERA_INITIAL_X = -200;
    private static final double CAMERA_INITIAL_Y = -200;
    private static final double CAMERA_INITIAL_Z = -100;
    private PerspectiveCamera camera;

    //hellosss
    public AnchorPane anchorPaneMain;
    //private Image ShipImage = new Image(getClass().getResource("/Images/pirate_ship_00000.png").toExternalForm());
    private Double XPosM;
    private Double YPosM;
    private Double MBx = 0.0;
    private Double MBy = 0.0;
    private Double PastMBx = 0.0;
    private Double PastMBy = 0.0;
    Polygon MarkMovement = new Polygon();
    PlayerBoat playerOne = new PlayerBoat("peru", 30.0, 0.0,
            20.0, 50.0,
            40.0, 50.0);
    private Double theta;
    private int MouseTimer = 0;
    private double Opacacity = 100.0;
    private double speed = 5;
    private double xVelocity;
    private double yVelocity;
    private boolean MouseIsJustClick;
    private double ThetaD;
    private int r = 75;
    public Scene scene;
    boolean MoveUp = false;
    boolean MoveDown = false;
    boolean MoveLeft = false;
    boolean MoveRight = false;


    @Override
    public void start(Stage primaryStage) {


        // Create the AnchorPane and add an object to it
        anchorPaneMain = new AnchorPane();

        anchorPaneMain.setPrefSize(5000, 5000);
        Rectangle enemy = new Rectangle(OBJECT_WIDTH, OBJECT_HEIGHT, Color.BLUE);
        anchorPaneMain.getChildren().addAll(playerOne, enemy);
        AnchorPane.setTopAnchor(playerOne, 275.0);
        AnchorPane.setLeftAnchor(playerOne, 375.0);
        AnchorPane.setTopAnchor(enemy, 275.0);
        AnchorPane.setLeftAnchor(enemy, 375.0);
        MarkMovement.getPoints().addAll(20.0, 15.0,
                0.0, 10.0,
                0.0, 20.0);
        MarkMovement.setFill(Color.TRANSPARENT);
        anchorPaneMain.getChildren().add(MarkMovement);

        enemy.setTranslateX(playerOne.getTranslateX() + 5000);
        enemy.setTranslateY(playerOne.getTranslateY());

        Image backgroundImage = new Image(getClass().getResource("/worldMapImage.jpg").toExternalForm());
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );
        anchorPaneMain.setBackground(new Background(background));

        // Create the camera
        camera = new PerspectiveCamera(false);
        camera.setTranslateZ(CAMERA_INITIAL_Z);

        // Set the field of view
        camera.setFieldOfView(45);

        // Create the scene
        Group root = new Group();
        root.getChildren().addAll(anchorPaneMain);
        scene = new Scene(root, WIDTH, HEIGHT, true);
        scene.setCamera(camera);

        primaryStage.setScene(scene);
        primaryStage.show();
        playerOne.requestFocus(); // Make the object receive key events
        initialize();
    }

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(16.66666), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            RotateBoat();
           MoveInArrows();
            camera.setTranslateX(playerOne.getTranslateX());
            camera.setTranslateY(playerOne.getTranslateY());
        }
    }));

    public void initialize()
    {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setRate(1); //1 is normal speed. 2 is double, etc. -1 is reverse.
        timeline.play();
    }

    private void RotateBoat(){

        PastMBx = XPosM;
        PastMBy = YPosM;
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me){
                MarkMovement.setFill(Color.RED);
                MBx = playerOne.getTranslateX()+15+360;
                MBy = playerOne.getTranslateY()+12.5+270;
                XPosM = me.getX()-400;
                YPosM = me.getY()-240;
                int r = 75;
                if (XPosM < 0){
                    if (YPosM > 0){
                        //3Quadrant 180-theta
                        theta =  Math.PI-Math.atan(-1*YPosM/XPosM);
                    }else {
                        //2Quadrant 180+theta
                        theta =  Math.PI+Math.atan(YPosM/XPosM);
                    }
                }else {
                    if (YPosM > 0){
                        //4Quadrant 360-theta
                        theta =  Math.PI*2-Math.atan(-1*YPosM/XPosM);

                    }else {
                        //1Quadrant theta
                        theta = Math.atan(YPosM/XPosM);
                    }
                }
                ThetaD = Math.toDegrees(theta);
                MarkMovement.setTranslateX(MBx + r*Math.cos(theta));
                MarkMovement.setTranslateY(MBy + r*Math.sin(theta));
                MarkMovement.rotateProperty().set(ThetaD);
            }
        });
        anchorPaneMain.setOnMouseDragged(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent me) {
                MarkMovement.setFill(Color.RED);
                XPosM = me.getX()-400;
                YPosM = me.getY()-240;
                if (XPosM < 0){
                    if (YPosM > 0){
                        //3Quadrant 180-theta
                        theta =  Math.PI-Math.atan(-1*YPosM/XPosM);
                    }else {
                        //2Quadrant 180+theta
                        theta =  Math.PI+Math.atan(YPosM/XPosM);
                    }
                }else {
                    if (YPosM > 0){
                        //4Quadrant 360-theta
                        theta =  Math.PI*2-Math.atan(-1*YPosM/XPosM);

                    }else {
                        //1Quadrant theta
                        theta = Math.atan(YPosM/XPosM);
                    }
                }
                ThetaD = Math.toDegrees(theta);
                MarkMovement.setTranslateX(MBx + r*Math.cos(theta));
                MarkMovement.setTranslateY(MBy + r*Math.sin(theta));
                MarkMovement.rotateProperty().set(ThetaD);
            }
        });
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me){
                MouseIsJustClick = true;
            }
        });
        scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me){
                MouseIsJustClick = false;
            }
        });
        if (MouseIsJustClick){moveBoat();}

    }
    private void moveBoat(){
        MBx = playerOne.getTranslateX()+15+360;
        MBy = playerOne.getTranslateY()+12.5+270;
        MarkMovement.setTranslateX(MBx + r*Math.cos(theta));
        MarkMovement.setTranslateY(MBy + r*Math.sin(theta));
        MarkMovement.rotateProperty().set(ThetaD);
        xVelocity = speed * Math.cos(theta);
        yVelocity = speed * Math.sin(theta);
        playerOne.setTranslateY(playerOne.getTranslateY()+yVelocity);
        playerOne.setTranslateX(playerOne.getTranslateX()+xVelocity);
        playerOne.rotateProperty().set(ThetaD+90);

    }

    private void MoveInArrows(){
        scene.setOnKeyPressed(events -> {
            KeyCode keyCode = events.getCode();

            if (keyCode == KeyCode.UP || keyCode == KeyCode.W && !MoveUp) {
                MoveUp = true;
                playerOne.rotateProperty().set(0);
            } else if (keyCode == KeyCode.DOWN || keyCode == KeyCode.S && !MoveDown) {
                MoveDown = true;
                playerOne.rotateProperty().set(180);
            }
            if (keyCode == KeyCode.LEFT || keyCode == KeyCode.A && !MoveLeft) {
                MoveLeft = true;
            } else if (keyCode == KeyCode.RIGHT || keyCode == KeyCode.D && !MoveRight) {
                MoveRight = true;
            }
        });
        scene.setOnKeyReleased(events -> {
            KeyCode keyCode = events.getCode();

            if (keyCode == KeyCode.UP || keyCode == KeyCode.W) {
                MoveUp = false;
            } else if (keyCode == KeyCode.DOWN || keyCode == KeyCode.S) {
                MoveDown = false;
            }
            if (keyCode == KeyCode.LEFT || keyCode == KeyCode.A) {
                MoveLeft = false;
            } else if (keyCode == KeyCode.RIGHT || keyCode == KeyCode.D) {
                MoveRight = false;
            }
        });
        if (MoveUp){
            playerOne.setTranslateY(playerOne.getTranslateY()-10);
            playerOne.rotateProperty().set(0);
        }else if (MoveDown){
            playerOne.setTranslateY(playerOne.getTranslateY()+10);
            playerOne.rotateProperty().set(180);
        }
        if (MoveLeft){
            playerOne.setTranslateX(playerOne.getTranslateX()-10);
            playerOne.rotateProperty().set(270);
        }else if (MoveRight){
            playerOne.setTranslateX(playerOne.getTranslateX()+10);
            playerOne.rotateProperty().set(90);
        }
        if (MoveRight && MoveDown){
            playerOne.rotateProperty().set(135);
        }else if(MoveRight && MoveUp){
            playerOne.rotateProperty().set(45);
        }else if (MoveLeft && MoveDown){
            playerOne.rotateProperty().set(225);
        }else if (MoveLeft && MoveUp){
            playerOne.rotateProperty().set(315);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}