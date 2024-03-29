package com.example.demo;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class PlayerBoat extends Polygon {

    private String playerName;
    private Double health;

    private Circle bullet;

    public PlayerBoat(String name, Double p1x, Double p1y, Double p2x, Double p2y, Double p3x, Double p3y){
        this.playerName = name;
        this.getPoints().addAll(p1x, p1y,
                p2x, p2y,
                p3x, p3y);
        health = 100.0;
    }

    public String getPlayerName(){
        return playerName;
    }
    public Double getHealth(){return health;}
    public boolean isDead(){
        if (health <= 0){
            return true;
        }else {
            return false;
        }
    }

    public void reduceHealth(Double damagedone){
        health -= damagedone;
        if(isDead()){
            health = 0.0;
            destroyobject();
        }
    }

    public void destroyobject(){

    }

}
