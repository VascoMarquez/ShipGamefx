package com.example.demo;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.ProgressBar;

import java.io.IOException;


public class HelloApplication extends Application {

    private static final double CAMERA_INITIAL_Z = -100;
    private PerspectiveCamera camera;
    public AnchorPane anchorPaneMain;
    //private Image ShipImage = new Image(getClass().getResource("/Images/pirate_ship_00000.png").toExternalForm());
    private Double XPosM;
    private Double YPosM;
    private Double MBx = 0.0;
    private Double MBy = 0.0;
    Polygon MarkMovement = new Polygon();
    PlayerBoat playerOne = new PlayerBoat("peru", 30.0, 0.0,
            20.0, 50.0,
            40.0, 50.0);
    private Double theta;
    private double speed = 0;
    private double xVelocity;
    private double yVelocity;
    private boolean MouseIsJustClick;
    private double ThetaD;
    private int r = 75;
    public Scene scene;
    private Rectangle enemy = new Rectangle(100, 100, Color.TRANSPARENT);
    private Rectangle Arica = new Rectangle(200, 200, Color.TRANSPARENT);
    private Rectangle SantiagoCh = new Rectangle(200, 200, Color.TRANSPARENT);
    private final Circle Pointer1 = new Circle(3, Color.RED);
    private final Circle Pointer2 = new Circle(3, Color.RED);
    private final Circle Pointer3 = new Circle(3, Color.RED);
    private final Circle AreaToDamage1 = new Circle(300, Color.TRANSPARENT);
    private final Circle AreaToDamage2 = new Circle(300, Color.TRANSPARENT);
    private final Circle AreaToDamage3 = new Circle(300, Color.TRANSPARENT);

    private boolean canShoot = true;
    private boolean LeftShoot = true;
    private Circle bullet;
    private double xVelocityB;
    private double yVelocityB;
    private double thetaE = -Math.PI/2;
    private double thetaEstatic = -Math.PI/2;
    private double speedB = 20;
    private boolean alreadykilled = false;
    private boolean ChatUp = false;
    private Text chat = new Text();
    private Rectangle chatbox = new Rectangle(1000, 350, Color.WHITE);
    private Boolean CanMove = true;

    PlayerBoat Chile1 = new PlayerBoat("Esmeralda", 30.0, 0.0,
            20.0, 50.0,
            40.0, 50.0);
    PlayerBoat Chile2 = new PlayerBoat("Barco2", 30.0, 0.0,
            20.0, 50.0,
            40.0, 50.0);
    PlayerBoat Chile3 = new PlayerBoat("Barco3", 30.0, 0.0,
            20.0, 50.0,
            40.0, 50.0);
    boolean enemyShoot = true;
    Circle shot;

    double velocityX;
    double velocityY;
    double SpeedBullet = 0.1;
    double thetaNewEnemy;
    private Text healthBar = new Text();

    private Boolean HitEnemyOneTime = false;
    private Boolean HitPlayerOneTime = false;

    private String LimaMes = "During World War II, Lima, the capital city of Peru, was not directly involved in the Pacific War.\n" +
            "The Pacific War primarily refers to the conflict between the Allied powers (led by the United States) \n" +
            "and the Axis powers (led by Japan) in the Pacific theater of the war.\n" +
            "While Peru was not a belligerent in the war, it did play a role in supporting the Allied effort.\n" +
            "Peru declared war on Japan and Germany in 1942, following the attack on Pearl Harbor.\n" +
            "The country cooperated with the United States by providing resources, such as rubber, minerals, and foodstuffs, to aid the war effort.\n" +
            "Lima, being the political and administrative center of Peru, played a crucial role in coordinating these efforts.\n" +
            "The city served as a hub for logistical operations and the coordination of supplies destined for the war effort in the Pacific.\n" +
            " However, Lima itself did not witness direct military engagements or host significant military installations related to the Pacific War.\n" +
            "\n"+
            "Press F to quit...";

    private String SantiagoMes = "Santiago de Chile, the capital city of Chile, was not directly involved in the Pacific War during World War II.\n" +
            "Chile maintained a policy of neutrality and did not participate militarily in the conflict.\n" +
            " However, Chile provided support to the Allied powers by supplying strategic \n" +
            "resources and allowing the United States to establish military bases on its territory. \n" +
            " While Santiago served as the political center of the country, \n" +
            " it did not witness any direct military engagements or significant military installations related to the Pacific War, \n" +
            "primarily playing a role in coordinating and supporting logistical efforts for the Chilean government's assistance to the Allies.\n" +
            "\n" +
            "Press F to quit...";
    private String AricaMes = "During the War of the Pacific, there were several significant maritime battle campaigns, including:\n" +
            "\n" +
            "1) Battle of Iquique (1879): Fought off the coast of Iquique, a port city in northern Chile, this battle saw the Chilean Navy engage the Peruvian Navy.\n" +
            " The most renowned episode of this battle was the heroic sacrifice of the Chilean warship \"Esmeralda\" \n" +
            "and its captain, Arturo Prat, who became a national hero.\n" +
            "\n" +
            "2) Battle of Angamos (1879): Occurring near the port of Angamos, this battle involved the Chilean Navy's pursuit and eventual capture \n" +
            " of the Peruvian ironclad \"Huáscar.\" The victory significantly weakened the Peruvian Navy's capabilities and marked a turning point in the war.\n" +
            "\n" +
            "3) Naval blockade and coastal bombardments: Throughout the war, the Chilean Navy imposed a naval blockade on the Peruvian and Bolivian coasts, \n" +
            "limiting their access to supplies and crippling their economies. Chilean naval forces also conducted coastal bombardments,\n" +
            " targeting key ports and fortifications to weaken their adversaries.\n" +
            "\n" +
            "4) Naval operations and landings: The Chilean Navy played a crucial role in facilitating amphibious landings and supporting ground operations. \n" +
            "They transported troops, provided fire support, and secured maritime supply lines for the Chilean forces as they advanced into enemy territory.\n" +
            "\n" +
            "These maritime battle campaigns were pivotal in shaping the outcome of the War of the Pacific,\n" +
            " resulting in Chile gaining significant territorial gains and asserting its dominance in the region.\n"+
            "\n" +
            "Press F to quit...";

    @Override
    public void start(Stage primaryStage) {


        // Create the AnchorPane and add an object to it
        anchorPaneMain = new AnchorPane();
        anchorPaneMain.setPrefSize(5000, 5000);
        anchorPaneMain.getChildren().addAll(playerOne, Pointer1,Pointer2, Pointer3, Chile1, Chile2, Chile3, enemy, healthBar);

        Arica.setTranslateX(1850);
        Arica.setTranslateY(2200);
        SantiagoCh.setTranslateX(1850);
        SantiagoCh.setTranslateY(3150);
        Pointer2.setTranslateX(Arica.getTranslateX()+50);
        Pointer2.setTranslateY(Arica.getTranslateY());
        Pointer3.setTranslateX(SantiagoCh.getTranslateX()+100);
        Pointer3.setTranslateY(SantiagoCh.getTranslateY());

        Chile1.setFill(Color.RED);
        Chile2.setFill(Color.RED);
        Chile3.setFill(Color.RED);
        AreaToDamage1.setTranslateX(500);
        AreaToDamage1.setTranslateY(700);
        AreaToDamage2.setTranslateX(1000);
        AreaToDamage2.setTranslateY(1700);
        AreaToDamage3.setTranslateX(750);
        AreaToDamage3.setTranslateY(2700);
        Chile1.setTranslateX(AreaToDamage1.getTranslateX());
        Chile1.setTranslateY(AreaToDamage1.getTranslateY());
        Chile2.setTranslateX(AreaToDamage2.getTranslateX());
        Chile2.setTranslateY(AreaToDamage2.getTranslateY());
        Chile3.setTranslateX(AreaToDamage3.getTranslateX());
        Chile3.setTranslateY(AreaToDamage3.getTranslateY());



        chat.setFont(Font.font("Arial",14));
        healthBar.setFont(Font.font("Arial",20));
        AnchorPane.setTopAnchor(playerOne, 275.0);
        AnchorPane.setLeftAnchor(playerOne, 375.0);
        AnchorPane.setTopAnchor(enemy, 275.0);
        AnchorPane.setLeftAnchor(enemy, 375.0);
        MarkMovement.getPoints().addAll(20.0, 15.0,
                0.0, 10.0,
                0.0, 20.0);
        MarkMovement.setFill(Color.TRANSPARENT);
        anchorPaneMain.getChildren().add(MarkMovement);
        enemy.setTranslateX(playerOne.getTranslateX() + 1035);
        enemy.setTranslateY(playerOne.getTranslateY() + 1450);
        Pointer1.setTranslateX(enemy.getTranslateX()+400);
        Pointer1.setTranslateY(enemy.getTranslateY()+350);

        Image backgroundImage = new Image(getClass().getResource("/mapaAmericaSur.jpg").toExternalForm());
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
            if (CanMove){
                RotateBoat();
            }
            camera.setTranslateX(playerOne.getTranslateX());
            camera.setTranslateY(playerOne.getTranslateY());
            healthBar.setTranslateX(camera.getTranslateX());
            healthBar.setTranslateY(camera.getTranslateY()+460);
            healthBar.setText(playerOne.getHealth().toString());

            KeyController();
            ShootUpdate();
            EnemyAttack(AreaToDamage1 ,Chile1);
            EnemyAttack(AreaToDamage2 ,Chile2);
            EnemyAttack(AreaToDamage3 ,Chile3);
            CheckShoot(Chile1);
            CheckShoot(Chile2);
            CheckShoot(Chile3);

        }
    }));

    public void initialize()
    {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setRate(1); //1 is normal speed. 2 is double, etc. -1 is reverse.
        timeline.play();
    }

    private void RotateBoat(){

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
        thetaE = theta;
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
        if (playerOne.getTranslateY()<-300){
            playerOne.setTranslateY(playerOne.getTranslateY()+5);
        }else if(playerOne.getTranslateY()>4500){
            playerOne.setTranslateY(playerOne.getTranslateY()-5);
        }
    }

    private void Shoot(KeyCode keyCode){
            if (keyCode == KeyCode.Z && canShoot){
                bullet = new Circle(5, Color.BLACK);
                bullet.setTranslateX(playerOne.getTranslateX()+390);
                bullet.setTranslateY(playerOne.getTranslateY()+290);
                thetaEstatic = thetaE;
                anchorPaneMain.getChildren().add(bullet);
                canShoot = false;
                LeftShoot = true;
                HitEnemyOneTime = false;
            }else if(keyCode == KeyCode.X && canShoot){
                bullet = new Circle(5, Color.BLACK);
                bullet.setTranslateX(playerOne.getTranslateX()+390);
                bullet.setTranslateY(playerOne.getTranslateY()+290);
                thetaEstatic = thetaE;
                anchorPaneMain.getChildren().add(bullet);
                canShoot = false;
                LeftShoot = false;
                HitEnemyOneTime = false;
            }
    }
    private void ShootUpdate(){
        if (!canShoot){
            if (speedB <= 20 && speedB > 0){speedB = speedB-0.40;}
            else if(speedB <= 0){
                anchorPaneMain.getChildren().remove(bullet);
                speedB = 20;
                canShoot = true;
                ;}
            if (LeftShoot){
                xVelocityB = speedB * Math.cos(thetaEstatic-(Math.PI/2));
                yVelocityB = speedB * Math.sin(thetaEstatic-(Math.PI/2));
            }else {
                xVelocityB = speedB * Math.cos(thetaEstatic+(Math.PI/2));
                yVelocityB = speedB * Math.sin(thetaEstatic+(Math.PI/2));
            }
            bullet.setTranslateX(bullet.getTranslateX()+ xVelocityB);
            bullet.setTranslateY(bullet.getTranslateY()+ yVelocityB);
        }
    }
    private void CheckShoot(PlayerBoat enemY){
        if (!canShoot){
            if(bullet.getBoundsInParent().intersects(enemY.getBoundsInParent()) && !enemY.isDead() && !HitEnemyOneTime){
                enemY.reduceHealth(25.0);
                anchorPaneMain.getChildren().remove(bullet);
                HitEnemyOneTime = true;
                if (enemY.isDead()){
                    anchorPaneMain.getChildren().remove(enemY);
                }
            }
        }
    }
    private void EnemyAttack(Circle shootArea,PlayerBoat enemyBoat){
        if (shootArea.getBoundsInParent().intersects(playerOne.getBoundsInParent()) && enemyShoot && !enemyBoat.isDead()){
            shot = new Circle(5, Color.BLACK);
            shot.setTranslateX(enemyBoat.getTranslateX()+10);
            shot.setTranslateY(enemyBoat.getTranslateY()+10);
            anchorPaneMain.getChildren().add(shot);
            double MBxN = playerOne.getTranslateX()+385-enemyBoat.getTranslateX();
            double MByN = playerOne.getTranslateY()+300-enemyBoat.getTranslateY();
            if (MBxN < 0){
                if (MByN > 0){
                    //3Quadrant 180-theta
                    thetaNewEnemy =  Math.PI-Math.atan(-1*MByN/MBxN);
                }else {
                    //2Quadrant 180+theta
                    thetaNewEnemy =  Math.PI+Math.atan(MByN/MBxN);
                }
            }else {
                if (MByN > 0){
                    //4Quadrant 360-theta
                    thetaNewEnemy =  Math.PI*2-Math.atan(-1*MByN/MBxN);

                }else {
                    //1Quadrant theta
                    thetaNewEnemy = Math.atan(MByN/MBxN);
                }
            }
            double thetaDNew = Math.toDegrees(thetaNewEnemy);
            enemyBoat.rotateProperty().set(thetaDNew+90);
            enemyShoot = false;
            HitPlayerOneTime = false;
        }
        if (!enemyShoot && shootArea.getBoundsInParent().intersects(playerOne.getBoundsInParent())&& !enemyBoat.isDead()){
            if (SpeedBullet <= 20 && SpeedBullet > 0){SpeedBullet = SpeedBullet-0.001;}
            else if(SpeedBullet <= 0){
                anchorPaneMain.getChildren().remove(shot);
                SpeedBullet = 0.10;
                enemyShoot = true;
                }
            playerOne.setFill(Color.BLACK);
            velocityX = speedB * Math.cos(thetaNewEnemy);
            velocityY = speedB * Math.sin(thetaNewEnemy);
            shot.setTranslateX(shot.getTranslateX()+ velocityX);
            shot.setTranslateY(shot.getTranslateY()+ velocityY);
            if (shot.getBoundsInParent().intersects(playerOne.getBoundsInParent()) && !HitPlayerOneTime){
                playerOne.reduceHealth(25.0);
                HitPlayerOneTime = true;
                playerOne.setFill(Color.YELLOW);
            }
            if (playerOne.isDead()){
                CanMove = false;
                Text DeathText = new Text("You are dead");
                DeathText.setFont(Font.font(50));
                DeathText.setTranslateX(camera.getTranslateX());
                DeathText.setTranslateY(camera.getTranslateY());
                anchorPaneMain.getChildren().add(DeathText);
            }
        }
    }

    private  void CheckBox(Rectangle box, String message, KeyCode keyCo){
        if(playerOne.getBoundsInParent().intersects(box.getBoundsInParent()) && keyCo == KeyCode.F && !ChatUp){
            chat.setText(message);
            anchorPaneMain.getChildren().addAll(chatbox,chat);
            chat.setTranslateX(playerOne.getTranslateX()-70);
            chat.setTranslateY(playerOne.getTranslateY()+170);
            chatbox.setTranslateX(chat.getTranslateX()-15);
            chatbox.setTranslateY(chat.getTranslateY()-15);
            ChatUp = true;
            CanMove = false;
        }else if (keyCo == KeyCode.F && ChatUp && playerOne.getBoundsInParent().intersects(box.getBoundsInParent())){
            anchorPaneMain.getChildren().removeAll(chatbox,chat);
            ChatUp = false;
            CanMove = true;
        }
    }

    private void KeyController(){
        scene.setOnKeyPressed(events -> {
            KeyCode keyCode = events.getCode();
            Shoot(keyCode);
            CheckBox(enemy, LimaMes, keyCode);
            CheckBox(SantiagoCh, SantiagoMes, keyCode);
            CheckBox(Arica, AricaMes, keyCode);
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}