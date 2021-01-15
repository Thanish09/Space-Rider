package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import sun.font.TextRecord;

public class Obstacles {

    //public static final int OBSTACLES_WIDTH=52;
    public static final int UFO_WIDTH=52;
    public static final int ALIEN_WIDTH=52;
  //  private static final int FLUCTUATION = 130;
   // private static final int TUBE_GAP =100;
   // private static final int LOWEST_OPENING = 120
 //   private static final int FLUCTUATION =130;
    private Texture ufo, alien;
    private Vector2 posUfo, posAlien;
    private Rectangle boundsUfo, boundsAlien;
    private Random rand;

    public Obstacles(float x){
        ufo = new Texture("ufo.png");
        alien = new Texture("alien.png");
        rand =new Random();

        posUfo= new Vector2(300+x, 20);
        posAlien = new Vector2(x,10);
        boundsUfo= new Rectangle(posUfo.x, posUfo.y,ufo.getWidth(),ufo.getHeight());
        boundsAlien = new Rectangle(posAlien.x,posAlien.y,alien.getWidth(),alien.getHeight());
    }

    public Texture getAlien() {
        return alien;
    }

    public Texture getUfo() {
        return ufo;
    }

    public Vector2 getPosUfo() {
        return posUfo;
    }

    public Vector2 getPosAlien() {
        return posAlien;
    }

    public Rectangle getBoundsAlien() {
        return boundsAlien;
    }

    public void reposition(float x)
    {
        posUfo.set(300+x, 20);
        posAlien.set(x,10);
        boundsUfo.setPosition(posUfo.x,posUfo.y);
        boundsAlien.setPosition(posAlien.x,posAlien.y);
    }

    public boolean collide(Rectangle player)
    {
        return player.overlaps(boundsUfo);//||player.overlaps(boundsAlien);
    }

    public boolean collide1(Rectangle player)
    {
        return player.overlaps(boundsAlien);
    }

    public void dispose()
    {
        ufo.dispose();
        alien.dispose();
    }
}
