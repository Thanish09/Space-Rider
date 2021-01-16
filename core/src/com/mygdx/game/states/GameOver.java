package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Project1;

public class GameOver extends State {

    private Texture background;
    private Texture restartBtn;
    private Texture closeBtn;
    private Texture gameOver;
    private PlayState play;
    private String score;
    private Sprite restartSprite;
    private Sprite exitSprite;
    BitmapFont font;
    //public Sound alienCrash;
    //public Sound Crash;

    public GameOver(GameStateManager gsm, String YourScoreName) {
        super(gsm);
        cam.setToOrtho(false, Project1.WIDTH, Project1.HEIGHT );
        background = new Texture("GameOver.png");
        restartBtn = new Texture("NewGame.png");
        closeBtn = new Texture("Quit.png");
        restartSprite = new Sprite(restartBtn);
        restartSprite.setPosition((Project1.WIDTH/2) - (restartBtn.getWidth() / 2),Project1.HEIGHT/2);
        exitSprite = new Sprite(closeBtn);
        exitSprite.setPosition((Project1.WIDTH/2) - (closeBtn.getWidth() / 2),(Project1.HEIGHT/2)-150);
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
        if (Gdx.input.isTouched()) {
            if ((Gdx.input.getX()<(Gdx.graphics.getWidth())/2) && (Gdx.input.getY()>Gdx.graphics.getHeight()/2 )) {
                gsm.set(new PlayState(gsm));
                dispose();
            }
            if (Gdx.input.getX() > Gdx.graphics.getWidth()/2 && Gdx.input.getY()>Gdx.graphics.getHeight()/2 ) {
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
        //   states.dispose();
        sb.begin();
        sb.draw(background, cam.position.x - (cam.viewportWidth / 2), 0, Project1.WIDTH, Project1.HEIGHT);
        sb.draw(restartSprite,((Project1.WIDTH / 2)-((restartBtn.getWidth()-200)/2)), ((Project1.WIDTH-500) / 2), 150, 50);
        sb.draw(exitSprite,((Project1.WIDTH / 2)-((closeBtn.getWidth()-200)/2))+200, ((Project1.WIDTH-500) / 2), 150, 50);
        font.setColor(1.0f,1.0f,1.0f,1.0f);
        font.getData().setScale(3,3);
        font.draw(sb,score,390,180);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        restartBtn.dispose();
        closeBtn.dispose();
        System.out.println("Menu state disposed");
    }

}
