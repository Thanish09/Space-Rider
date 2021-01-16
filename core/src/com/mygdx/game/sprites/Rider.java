package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.states.PlayState;

public class Rider {
    private static final int GRAVITY =-15;
    private int MOVEMENT=100;
    private Vector3 position;
    public Vector3 velocity;
    private Rectangle bounds;
    private RiderAnimation riderAni;
    private Texture rider;
    private boolean colliding;
    private Sound rideSound;

    public Rider(int x, int y)
    {
        position=new Vector3(x,y,0);
        velocity=new Vector3(0,0,0);
        rider= new Texture("RiderAni.png");
        riderAni=new RiderAnimation(new TextureRegion(rider),2,0.1f);
        bounds= new Rectangle(x,y,rider.getWidth()/3,rider.getHeight());
        colliding=false;
        rideSound= Gdx.audio.newSound(Gdx.files.internal("motor riding.mp3"));
        rideSound.play(1.0f);
    }
    public void update(float dt)
    {
        riderAni.update(dt);
        if(position.y>25) {
            velocity.add(0, GRAVITY, 0);
        }
        if(position.y>250){
            position.y=250;
        }
        velocity.scl(dt);
        position.add(MOVEMENT*dt, velocity.y, 0);
        if(position.y<25)
        {
            position.y=25;
        }
        velocity.scl(1/dt);
        bounds.setPosition(position.x,position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return riderAni.getFrame();
    }

    public void jump(){
            velocity.y=400;
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public void dispose()
    {
        rider.dispose();
        rideSound.dispose();
    }

    public void getspeed(){
        MOVEMENT += 50;
        if(MOVEMENT > 350){
            MOVEMENT = 350;
        }
    }
}
