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
        background = new Texture("bg.jpg");
        playBtn = new Texture("play2.png");
        closeBtn = new Texture("close.png");
        playSprite = new Sprite(playBtn);
        playSprite.setPosition((Project1.WIDTH/2) - (playBtn.getWidth() / 2),Project1.HEIGHT/2);
        exitSprite = new Sprite(closeBtn);
        exitSprite.setPosition(200,150);
    }

    @Override
    public void handleInput() //We use override command to change the default handleInput method to the one we will define now
    {
        if (Gdx.input.isTouched()) {
            if (playSprite.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY())) {
                gsm.set(new PlayState(gsm));
                dispose();
            }

            if (exitSprite.getBoundingRectangle().contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY())) {
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
        sb.begin();
        sb.draw(background, 0, 0, Project1.WIDTH, Project1.HEIGHT);
        sb.draw(playSprite,(Project1.WIDTH/2) - (playBtn.getWidth() / 2),Project1.HEIGHT/2);
        sb.draw(exitSprite, 200,150);
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
