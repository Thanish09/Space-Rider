package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import sun.font.TextRecord;

public class Obstacles {

    public static final int OBSTACLES_WIDTH=52;
  //  private static final int FLUCTUATION = 130;
   // private static final int TUBE_GAP =100;
   // private static final int LOWEST_OPENING = 120
 //   private static final int FLUCTUATION =130;
    private Texture ufo;
    private Vector2 posUfo;
    private Rectangle boundsUfo;
    private Random rand;

    public Obstacles(float x){
        ufo = new Texture("ufo.png");
        rand =new Random();

        posUfo= new Vector2(x, 20);
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
        posUfo.set(x, 20);
        boundsUfo.setPosition(posUfo.x,posUfo.y);
    }

    public boolean collide(Rectangle player)
    {
        return player.overlaps(boundsUfo);
    }

    public void dispose()
    {
        ufo.dispose();
    }
}
