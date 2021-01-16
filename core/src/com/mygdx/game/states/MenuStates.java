package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Project1;

public class MenuStates extends State {

    private Texture background;            //New Texture explained here

    private Texture playBtn;
    private Texture closeBtn;
    //Vinishaa edited
    private Sprite playSprite;
    private Sprite exitSprite;


    public MenuStates(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Project1.WIDTH, Project1.HEIGHT);
        background = new Texture("background.png");
        playBtn = new Texture("Play.png");
        closeBtn = new Texture("Quit.png");
        playSprite = new Sprite(playBtn);
        playSprite.setPosition((Project1.WIDTH/2) - (playBtn.getWidth() / 2),Project1.HEIGHT/2);
        exitSprite = new Sprite(closeBtn);
        exitSprite.setPosition(200,150);
    }

    @Override
    public void handleInput() //We use override command to change the default handleInput method to the one we will define now
    {
        if (Gdx.input.isTouched()) {
            if((Gdx.input.getX()<(Gdx.graphics.getWidth())/2)  && (Gdx.input.getY()>Gdx.graphics.getHeight()/2)){
                gsm.set(new PlayState(gsm));
                dispose();
            }

            if(Gdx.input.getX()>Gdx.graphics.getWidth()/2  && Gdx.input.getY()>Gdx.graphics.getHeight()/2){
                Gdx.app.exit();
                dispose();
            }
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    //SpriteBatch sb- imagine as box
    //open box...store inside box
    //close box
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0, Project1.WIDTH, Project1.HEIGHT);
        sb.draw(playSprite,((Project1.WIDTH/2) - ((playBtn.getWidth()-200) / 2)),((Project1.HEIGHT-360)/2),150,50);
        sb.draw(exitSprite, ((Project1.WIDTH/2)-((closeBtn.getWidth()-200) /2))+200,((Project1.HEIGHT-360)/2),150,50);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        closeBtn.dispose();
        System.out.println("Menu state disposed");
    }

}
