package com.mygdx.game.states;

        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.SpriteBatch;
        import com.mygdx.game.Project1;

public class MenuStates extends State {

    private Texture background;            //New Texture explained here
    private Texture playBtn;

    public MenuStates(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg.jpg");
        playBtn = new Texture("play2.png");

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched())
        {
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
        sb.begin();
        sb.draw(background, 0, 0, Project1.WIDTH, Project1.HEIGHT);
        sb.draw(playBtn,(Project1.WIDTH/2) - (playBtn.getWidth() / 2),Project1.HEIGHT/2);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menu state disposed");
    }

}
