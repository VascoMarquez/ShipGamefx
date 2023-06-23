package com.example.demo;

import javafx.animation.*;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class HelloApplication extends Application {

    private static final double OBJECT_WIDTH = 50;
    private static final double OBJECT_HEIGHT = 50;
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
    private double speed = 0;
    private double xVelocity;
    private double yVelocity;
    private boolean MouseIsJustClick;
    private double ThetaD;
    private int r = 75;
    public Scene scene;
    Rectangle enemy;
    private boolean canShoot = true;
    Circle bullet;
    double xVelocityB;
    double yVelocityB;


    @Override
    public void start(Stage primaryStage) {


        // Create the AnchorPane and add an object to it
        anchorPaneMain = new AnchorPane();

        anchorPaneMain.setPrefSize(5000, 5000);
        enemy = new Rectangle(OBJECT_WIDTH, OBJECT_HEIGHT, Color.BLUE);
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
        enemy.setTranslateX(playerOne.getTranslateX() + 400);
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
        scene = new Scene(root, 5000, 5000, true);
        scene.setCamera(camera);

        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(480);
        primaryStage.show();
        playerOne.requestFocus(); // Make the object receive key events
        initialize();
    }

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(16.66666), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            RotateBoat();
            camera.setTranslateX(playerOne.getTranslateX());
            camera.setTranslateY(playerOne.getTranslateY());
            Shoot();
            if(playerOne.getBoundsInParent().intersects(enemy.getBoundsInParent())){
                System.out.println("Here");
            }
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
        anchorPaneMain.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me){
                MarkMovement.setFill(Color.RED);
                MBx = playerOne.getTranslateX()+15+360;
                MBy = playerOne.getTranslateY()+12.5+270;
                XPosM = me.getX()-playerOne.getTranslateX()-400;
                YPosM = me.getY()-playerOne.getTranslateY()-230;
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
                XPosM = me.getX()-playerOne.getTranslateX()-400;
                YPosM = me.getY()-playerOne.getTranslateY()-240;
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
        anchorPaneMain.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me){
                MouseIsJustClick = true;
            }
        });
        anchorPaneMain.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me){
                MouseIsJustClick = false;
                speed = 0;
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
        if (speed < 5){speed = speed+0.0625;}
        else if(speed>5){speed = 5;}
        xVelocity = speed * Math.cos(theta);
        yVelocity = speed * Math.sin(theta);
        playerOne.setTranslateY(playerOne.getTranslateY()+yVelocity);
        playerOne.setTranslateX(playerOne.getTranslateX()+xVelocity);
        playerOne.rotateProperty().set(ThetaD+90);
        CheckBoundries();
    }

    private void CheckBoundries(){
        if (playerOne.getTranslateX()<-400){
            playerOne.setTranslateX(playerOne.getTranslateX()+5);
        }else if(playerOne.getTranslateX()>4600){
            playerOne.setTranslateX(playerOne.getTranslateX()-5);
        }
        if (playerOne.getTranslateY()<-400){
            playerOne.setTranslateY(playerOne.getTranslateY()+5);
        }else if(playerOne.getTranslateY()>2300){
            playerOne.setTranslateY(playerOne.getTranslateY()-5);
        }
    }

    private void Shoot(){
        scene.setOnKeyPressed(events -> {
            KeyCode keyCode = events.getCode();
            if (keyCode == KeyCode.Z ){
                bullet = new Circle(5, Color.BLACK);
                bullet.setTranslateX(playerOne.getTranslateX()+390);
                bullet.setTranslateY(playerOne.getTranslateY()+290);
                xVelocityB = speed * Math.cos(theta);
                yVelocityB = speed * Math.sin(theta);
                anchorPaneMain.getChildren().add(bullet);
                canShoot = false;
            }else if(keyCode == KeyCode.X && canShoot){
                xVelocityB = speed * Math.cos(theta);
                yVelocityB = speed * Math.sin(theta);
                bullet = new Circle(5, Color.BLACK);
                anchorPaneMain.getChildren().add(bullet);
                canShoot = false;
            }

        });
        scene.setOnKeyReleased(events -> {
            KeyCode keyCode = events.getCode();
            if (keyCode == KeyCode.Z){

            }else if(keyCode == KeyCode.X){

            }
        });
        if (!canShoot){
            //bullet.setTranslateX(bullet.getTranslateX()+ xVelocityB);
            //bullet.setTranslateY(bullet.getTranslateX()+ yVelocityB);

        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}