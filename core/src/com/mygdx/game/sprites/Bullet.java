package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet
{
    public static final int BULLET_SPEED = 800;
    private static Texture bullet_texture;
    float bullet_x, bullet_y;
    public boolean remove_bullet = false; // checks if the object should be removed from the list

    // the x value is static while the y value is not since the rider moves up & down only
    public Bullet (float bullet_y, float bullet_x) // constructor
    {
        this.bullet_y = bullet_y; // this y value is where the rider wil be
        this.bullet_x = bullet_x ;
        if(bullet_texture == null)
        {
            bullet_texture = new Texture("shoot3.png"); // setting the image for the bullet
        }
    }

    public void update_bullet (float bullet_dt) // it will update the position of thr bullet according to the game frame rate
    {
        bullet_x += BULLET_SPEED * bullet_dt; // incrementing x value, so tht the bullet will travel horizontally across the screen
    /*    if(bullet_x > Gdx.graphics.getWidth()) // to destroy the bullet if it moves out of the screen
        {
            remove_bullet = true;
            System.out.println("the bullet has left the screen");
        }

     */
    }

    public void render_bullet (SpriteBatch batch) // this method will draw the bullet on the screen
    {
        batch.draw(bullet_texture, bullet_x,bullet_y);
    }




}
