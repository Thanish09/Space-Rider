package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet
{
    public static final int BULLET_SPEED = 800;
    private static Texture bullet_texture;
    //float bullet_x, bullet_y;
    private Vector2 bulletPos;
    private Rectangle boundsBullet;
    public boolean remove_bullet = false; // checks if the object should be removed from the list

    // the x value is static while the y value is not since the rider moves up & down only
    public Bullet (float bullet_y, float bullet_x) // constructor
    {
        //this.bullet_y = bullet_y; // this y value is where the rider wil be
        //this.bullet_x = bullet_x ;
        if(bullet_texture == null)
        {
            bullet_texture = new Texture("shoot1.png"); // setting the image for the bullet
        }
        bulletPos = new Vector2(bullet_x,bullet_y);
        boundsBullet = new Rectangle(bulletPos.x, bulletPos.y, bullet_texture.getWidth(), bullet_texture.getHeight());
    }

    public void update_bullet (float bullet_dt) // it will update the position of thr bullet according to the game frame rate
    { bulletPos.x += BULLET_SPEED * bullet_dt; // incrementing x value, so tht the bullet will travel horizontally across the screen
        boundsBullet.setPosition(bulletPos.x, bulletPos.y);
    }

    public void render_bullet (SpriteBatch batch) // this method will draw the bullet on the screen
    {
        batch.draw(bullet_texture, bulletPos.x, bulletPos.y);
    }

    public boolean bulletHitUfo(Rectangle boundsUfo)
    {
        return boundsUfo.overlaps(boundsBullet); // check if overlap occurs
    }

    public boolean bulletHitAlien(Rectangle boundsAlien)
    {
        return boundsAlien.overlaps(boundsBullet);
    }

    public boolean bulletHitFly(Rectangle boundsFly)
    {
        return boundsFly.overlaps(boundsBullet);
    }

    public boolean dropBullet(float screen_width)
    {
        if(bulletPos.x > screen_width)
            return true;
        else
            return false;
    }


}
