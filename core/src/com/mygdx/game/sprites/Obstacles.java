package com.mygdx.game.sprites;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;


public class Obstacles {

    public static final int UFO_WIDTH=52;
    private Texture ufo;
    private Vector2 posUfo;
    private Rectangle boundsUfo;
    private Random rand;

    public Obstacles(float x){
        ufo = new Texture("ufo.png");
        rand =new Random();

        posUfo= new Vector2(300+x, 20);
        boundsUfo= new Rectangle(posUfo.x, posUfo.y,ufo.getWidth(),ufo.getHeight());
    }

    public Texture getUfo() {
        return ufo;
    }

    public Vector2 getPosUfo() {
        return posUfo;
    }

    public void reposition(float x)
    {
        posUfo.set(300+x, 20);
        boundsUfo.setPosition(posUfo.x,posUfo.y);
    }

    public boolean collide(Rectangle player)
    {
        return player.overlaps(boundsUfo);//||player.overlaps(boundsAlien);
    }

    public Rectangle getBoundsUfo()
    {
        return boundsUfo;
    }

    public void dispose()
    {
        ufo.dispose();
    }
}
