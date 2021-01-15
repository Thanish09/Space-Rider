package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Project1;

public class GameOver extends State {

    private Texture background;
    private Texture gameOver;

    public GameOver(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Project1.WIDTH / 2, Project1.HEIGHT / 2);
        background = new Texture("bg.jpg");
        gameOver = new Texture("gameOver.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched())
        {
            dispose();
            gsm.set(new PlayState(gsm));
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
        //   states.dispose();
        sb.begin();
        sb.draw(background, cam.position.x - (cam.viewportWidth / 2), 0, Project1.WIDTH, Project1.HEIGHT);
        sb.draw(gameOver,cam.position.x - (cam.viewportWidth / 2)+100, 100, Project1.WIDTH/6, Project1.HEIGHT/6);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        gameOver.dispose();
        System.out.println("Menu state disposed");
    }

}
