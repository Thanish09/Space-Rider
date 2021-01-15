package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.states.PlayState;

public class Rider {
    private static final int GRAVITY =-15;
    private static final int MOVEMENT=250;
    private Vector3 position;
//    private Vector3 shoot1Pos;
//    private Vector3 shoot2Pos;
//    private Vector3 shoot3Pos;
//    private Vector3 shoot4Pos;
//    private Vector3 shoot5Pos;
    public Vector3 velocity;
    private Rectangle bounds;
//    public int toShoot=0;
//    private int shootCounter=1;
//    private PlayState play;
    private RiderAnimation riderAni;
    private Texture rider;
//    private Texture shoot1;
//    private Texture shoot2;
//    private Texture shoot3;
//    private Texture shoot4;
//    private Texture shoot5;
    private boolean colliding;
    public Rider(int x, int y)
    {
        position=new Vector3(x,y,0);
//        shoot1Pos = new Vector3(x+30,y+10,0);
//        shoot2Pos = new Vector3(x+25,y+10,0);
//        shoot3Pos = new Vector3(x+15,y+10,0);
//        shoot4Pos = new Vector3(x+10,y+10,0);
//        shoot5Pos = new Vector3(x+5,y+10,0);
        velocity=new Vector3(0,0,0);
        rider= new Texture("RiderAni.png");
        bounds= new Rectangle(x,y,rider.getWidth()/3,rider.getHeight());
        colliding=false;
//        shoot1=new Texture("shoot1.png");
//        shoot2=new Texture("shoot2.png");
//        shoot3=new Texture("shoot3.png");
//        shoot4=new Texture("shoot4.png");
//        shoot5=new Texture("shoot5.png");

    }
    public void update(float dt)
    {
        riderAni.update(dt);
        if(position.y>25) {
            velocity.add(0, GRAVITY, 0);
        }
        if(position.y>200){
            position.y=200;
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
    }
}
