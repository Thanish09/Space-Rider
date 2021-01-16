package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Project1;

public class GameOver extends State {

    private Texture background;
    private Texture gameOver;
    private PlayState play;
    private String score;
    BitmapFont font;
    //public Sound alienCrash;
    //public Sound Crash;

    public GameOver(GameStateManager gsm, String YourScoreName) {
        super(gsm);
        cam.setToOrtho(false, Project1.WIDTH, Project1.HEIGHT );
        background = new Texture("bg.jpg");
        gameOver = new Texture("gameOver.png");
        font = new BitmapFont();
        score = YourScoreName;
      //  alienCrash= Gdx.audio.newSound(Gdx.files.internal("Alien Death.mp3"));
        //Crash=Gdx.audio.newSound(Gdx.files.internal("UFO crash.mp3"));
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
        sb.draw(gameOver,(Project1.WIDTH / 2)-((gameOver.getWidth())/2), (Project1.WIDTH / 2)-(gameOver.getHeight()-300/2), Project1.WIDTH/2, Project1.HEIGHT/2);
        font.setColor(1.0f,1.0f,1.0f,1.0f);
        font.getData().setScale(3,3);
        font.draw(sb,score,400,250);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        gameOver.dispose();
        System.out.println("Menu state disposed");
    }

}
