package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;



public class Alien {

    public static final int ALIEN_WIDTH=150;

    public static int getAlienWidth() {
        return ALIEN_WIDTH;
    }

    private Texture  alien;
    private Vector2 posAlien;
    private Rectangle boundsAlien;
    private Random rand;

    public Alien(float x){

        alien = new Texture("alien.png");
        rand =new Random();

        posAlien = new Vector2(x,20);
        boundsAlien = new Rectangle(posAlien.x,posAlien.y,alien.getWidth(),alien.getHeight());
    }

    public Texture getAlien() {
        return alien;
    }

    public Vector2 getPosAlien() {
        return posAlien;
    }

    public Rectangle getBoundsAlien() {
        return boundsAlien;
    }

    public void reposition(float x)
    {
        posAlien.set(x,10);
        boundsAlien.setPosition(posAlien.x,posAlien.y);
    }

    public boolean collide(Rectangle player)
    {
        return player.overlaps(boundsAlien);
    }

    public void dispose()
    {
        alien.dispose();
    }
}