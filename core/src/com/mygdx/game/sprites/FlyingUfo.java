package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class FlyingUfo {
    public static final int OBSTACLES_WIDTH=80;

    private Texture FlyingUfo;
    private Vector2 posFly;
    private Rectangle boundsFly;

    private Random rand;

    public FlyingUfo(float x){
        FlyingUfo = new Texture("FlyingUfo2.png");
        rand =new Random();

        posFly = new Vector2(x,200);
        boundsFly = new Rectangle(posFly.x, posFly.y,FlyingUfo.getWidth(),FlyingUfo.getHeight());
    }

    public Texture getFlyingUfo()
    {
        return FlyingUfo;
    }
    public Vector2 getPosFly() {

        return posFly;
    }
    public void repositionFly(float x)
    {
        posFly.set(x, rand.nextInt(100)+100);
        boundsFly.setPosition(posFly.x,posFly.y);
    }
    public boolean collideFly(Rectangle player)
    {
        return player.overlaps(boundsFly);
    }


    public void dispose()
    {
        FlyingUfo.dispose();
    }
}

