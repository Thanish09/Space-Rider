package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;


import jdk.nashorn.internal.parser.Scanner;

public class Coins {
    public static final int coins_width = 32;
    //private Texture coins;
    public Vector2 posCoins;
    private Animation coinAni;
    private Texture texture;

    public Coins(float x, float y){
        //coins = new Texture("co.png");
        posCoins = new Vector2(x, y);
        texture = new Texture("coinsheet.png");
        coinAni = new Animation(new TextureRegion(texture), 6, 5f);
    }

    public TextureRegion getCoins() {
        return coinAni.getFrame();
    }

    public Vector2 getPosCoins() {
        return posCoins;
    }

    public void reposition(float x,float y, float dt){
        posCoins.set(x+200,y);coinAni.update(dt);
    }

    public void dispose(){texture.dispose();}
}
