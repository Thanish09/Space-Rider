package com.mygdx.game.states;


        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.Input;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.SpriteBatch;
        import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.utils.Array;
        import com.mygdx.game.Project1;
        import com.mygdx.game.sprites.Coins;
        import com.mygdx.game.sprites.Obstacles;
        import com.mygdx.game.sprites.Rider;

        import java.util.Random;


public class PlayState extends State {
    private static final int coins_spacing = 5;
    private static final int fluctuation = 250;
    private static final int coins_counts = 8;
    private static final int GROUND_Y_OFFSET = -85;
    private static final int OBSTACLES_SPACING = 400;
    private static final int OBSTACLES_COUNT = 2;

    private Rider rider;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos0, groundPos1, groundPos2, groundPos3;
    private Array<Obstacles> ob;
    private Array<Coins> coin;
    private Random rand;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        rider = new Rider(10, 25);
        cam.setToOrtho(false, Project1.WIDTH / 2, Project1.HEIGHT / 2);
        bg = new Texture("bg.jpg");

        ground = new Texture("ground.png");
        groundPos0 = new Vector2(-200, GROUND_Y_OFFSET);
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);
        groundPos3 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth() + ground.getWidth(), GROUND_Y_OFFSET);

        rand = new Random();
        int xy = 30+ rand.nextInt(fluctuation);
        int coinss = 300 + rand.nextInt(500);
        coin = new Array<Coins>();
        for(int i=1; i <= coins_counts; i++){
            coin.add(new Coins((i * (coins_spacing + Coins.coins_width)) + coinss, xy));
        }

        ob = new Array<Obstacles>();
        for (int i = 1; i <= OBSTACLES_COUNT; i++) {
            ob.add(new Obstacles(i * (OBSTACLES_SPACING + Obstacles.OBSTACLES_WIDTH)));
        }

    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            rider.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        updateCoin(dt);

        rider.update(dt);
        cam.position.x = rider.getPosition().x + 80;

        /*for(Coins co : coin){
            if(cam.position.x - (cam.viewportWidth / 2) > co.getPosCoins().x + co.getCoins().getWidth()){
                co.reposition(co.getPosCoins().x + ((Coins.coins_width + coins_spacing)* coins_counts),rand.nextInt(fluctuation));
            }
        }*/

        for (int i = 0; i < ob.size; i++) {
            Obstacles obs = ob.get(i);
            if (cam.position.x - (cam.viewportWidth / 2) > obs.getPosUfo().x + obs.getUfo().getWidth()) {
                obs.reposition(obs.getPosUfo().x + ((obs.OBSTACLES_WIDTH + OBSTACLES_SPACING) * OBSTACLES_COUNT));
            }

            if (obs.collide(rider.getBounds())) {
                gsm.set(new PlayState(gsm));
            }
        }
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(rider.getTexture(), rider.getPosition().x, rider.getPosition().y);

        for(Coins co : coin){
            sb.draw(co.getCoins(), co.getPosCoins().x, co.getPosCoins().y);
        }

        for (Obstacles obs : ob) {
            sb.draw(obs.getUfo(), obs.getPosUfo().x, obs.getPosUfo().y);
        }
        sb.draw(ground, groundPos0.x, groundPos0.y);
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.draw(ground, groundPos3.x, groundPos3.y);
        sb.end();
    }

    public void dispose() {
        bg.dispose();
        rider.dispose();
        for (Obstacles obs : ob) {
            obs.dispose();
        }
        for (Coins co: coin){
            co.dispose();
        }
    }

    private void updateGround() {
        if (cam.position.x - (cam.viewportWidth /1) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 3, 0);
            /*int yui = 30 + rand.nextInt(fluctuation);
            for(Coins co : coin){
                co.reposition(co.getPosCoins().x + groundPos1.x + ((Coins.coins_width + coins_spacing)* coins_counts),yui);
            }*/
        }
        if (cam.position.x - (cam.viewportWidth / 1) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 3, 0);
            /*int yui = 30 + rand.nextInt(fluctuation);
            for(Coins co : coin){
                co.reposition(co.getPosCoins().x + groundPos2.x + ((Coins.coins_width + coins_spacing)* coins_counts),yui);
            }*/
        }
        if (cam.position.x - (cam.viewportWidth / 1) > groundPos3.x + ground.getWidth()) {
            groundPos3.add(ground.getWidth() * 3, 0);
            /*int yui = 30 + rand.nextInt(fluctuation);
            for(Coins co : coin){
                co.reposition(co.getPosCoins().x + groundPos3.x + ((Coins.coins_width + coins_spacing)* coins_counts),yui);
            }*/
        }
    }
    private void updateCoin(float dt){
        //if(cam.position.x - (cam.viewportWidth / 2) > co.getPosCoins().x + co.getCoins().getWidth())
        //if(cam.position.x - (cam.viewportWidth) > (Coins.coins_width + coins_spacing) * coins_counts){
            int yui = 30 + rand.nextInt(fluctuation);
            int iuy = 100 + rand.nextInt(400);

            //for(int i = 0; i < 1; i++){
                Coins co = coin.get(0);
                if(cam.position.x - cam.viewportWidth - (cam.viewportWidth/2) > co.getPosCoins().x + co.getCoins().getRegionWidth()){
                    //co.reposition(co.getPosCoins().x + ((Coins.coins_width + coins_spacing)* coins_counts),yui);
                    for(int j = 0; j < coin.size; j++){
                        Coins coi = coin.get(j);
                        coi.reposition(iuy + coi.getPosCoins().x + ((Coins.coins_width + coins_spacing)* coins_counts),yui,dt);
                    }
                }
            //}
        //}
    }

}