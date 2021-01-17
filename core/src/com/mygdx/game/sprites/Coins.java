package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Coins {
    public static final int coins_width = 32;
    private Texture coins;
    public Vector2 posCoins;
    private Animation coinAni;
    private Texture texture;
    private Rectangle boundsCoins;
    protected boolean hit, remove;

    public Coins(float x, float y){
        posCoins = new Vector2(x, y);
        texture = new Texture("coinsheet.png");
        coinAni = new Animation(new TextureRegion(texture), 6, 0.5f);
        boundsCoins= new Rectangle(posCoins.x, posCoins.y,texture.getWidth()/6,texture.getHeight());
        hit = false;
        remove = false;
    }

    public TextureRegion getCoins() {
        return coinAni.getFrame();
    }

    public Vector2 getPosCoins() {
        return posCoins;
    }

    public void reposition(float x,float y){
        posCoins.set(x+200,y);
        boundsCoins.setPosition(posCoins.x,posCoins.y);
    }

    public boolean isHit() {
        return hit;
    }

    public boolean isRemove() {
        return remove;
    }

    public void update(float dt)
    {
        coinAni.update(dt);
    }

    public Rectangle getBounds(){
        return boundsCoins;
    }

    public boolean collide(Rectangle player){
        return player.overlaps(boundsCoins);
    }

    public void setRemove(boolean disappear){
        remove = disappear;
    }

    public void dispose(){texture.dispose();}
}
