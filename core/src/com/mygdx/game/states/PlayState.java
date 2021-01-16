package com.mygdx.game.states;


        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.Input;
        import com.badlogic.gdx.audio.Sound;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.SpriteBatch;
        import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.utils.Array;
        import com.badlogic.gdx.utils.Null;
        import com.mygdx.game.Project1;
        import com.mygdx.game.sprites.Coins;
        import com.mygdx.game.sprites.Obstacles;
        import com.mygdx.game.sprites.Rider;
        import com.mygdx.game.sprites.FlyingUfo;
        import com.mygdx.game.sprites.Bullet;

        import java.util.Random;
        import java.util.ArrayList;


public class PlayState extends State {
    private static final int coins_spacing = 5;
    private static final int fluctuation = 250;
    private static final int coins_counts = 15;
    private static final int GROUND_Y_OFFSET = -85;
    private static final int OBSTACLES_SPACING = 400;
    private static final int OBSTACLES_SPACING2 = 700;
    private static final int OBSTACLES_COUNT = 3;

    private Rider rider;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos0, groundPos1, groundPos2, groundPos3,groundPos4;
    public Sound alienCrash;
    public Sound Crash;
    private Array<Obstacles> ob;
    private Array<Coins> coin;
    private Array<FlyingUfo> fly;
    private GameOver over;
    private Random rand;
    int count = 0;
    // for bullets, the dynamic list is created. we do not know how many bullet will be shoot
    ArrayList<Bullet> bullets; // the type of obj stored = Bullet and the name of this list = bullets
    ArrayList<Bullet> bullets_to_remove;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        rider = new Rider(10, 25);
        cam.setToOrtho(false, Project1.WIDTH / 2, Project1.HEIGHT / 2);
        bg = new Texture("bg.jpg");

        // bullet code starts
        bullets = new ArrayList<Bullet>(); // officially created the bullets list
        // bullet code ends

        ground = new Texture("ground.png");
        groundPos0 = new Vector2(-200, GROUND_Y_OFFSET);
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);
        groundPos3 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth() + ground.getWidth(), GROUND_Y_OFFSET);
        groundPos4 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth() + ground.getWidth() + ground.getWidth(), GROUND_Y_OFFSET);

        //alienCrash= Gdx.audio.newSound(Gdx.files.internal("Alien Death.mp3"));
        //Crash=Gdx.audio.newSound(Gdx.files.internal("UFO crash.mp3"));
        rand = new Random();
        int xy = 30+ rand.nextInt(fluctuation);
        int coinss = 300 + rand.nextInt(500);
        coin = new Array<Coins>();

        for(int i=1; i <= coins_counts; i++){
            coin.add(new Coins((i * (coins_spacing + (Coins.coins_width))) + coinss, xy));
        }

        fly = new Array<FlyingUfo>();
        ob = new Array<Obstacles>();
        for (int i = 1; i <= OBSTACLES_COUNT; i++) {
            ob.add(new Obstacles(i * (OBSTACLES_SPACING + Obstacles.UFO_WIDTH)));
            ob.add(new Obstacles(i * (OBSTACLES_SPACING + Obstacles.ALIEN_WIDTH)));
        }
        for (int i = 1; i < OBSTACLES_COUNT; i++) {
            fly.add(new FlyingUfo(i * (rand.nextInt(300) +OBSTACLES_SPACING2 + FlyingUfo.OBSTACLES_WIDTH)));
        }
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
                rider.jump();
            }
        }
    }

    public Rider getRider() {
        return rider;
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        //updateCoin();
        rider.update(dt);
        cam.position.x = rider.getPosition().x + 80;
        count++;
        if(count == 5000){
            rider.getspeed();
            count = 0;
        }
        System.out.println("Count" + count);
        int xy = 30+ rand.nextInt(fluctuation); //random position of coins y position
        int coinss = 500 + rand.nextInt(500); //random position of coins x position

        for (int i = 0; i < coin.size; i++) {
            Coins co = coin.get(i);
            co.update(dt);  //animation

            if (co.collide(rider.getBounds())) {
                co.setRemove(true); //when collide it becomes true
                if(co.isRemove()){  //becomes true
                    coin.removeIndex(i);//remove that particular coin in array
                }
            }
        }

        if(coin.size == 0){ //no coins in array, so we add again the coins in array
            for (int j = 1; j <= coins_counts; j++) {
                coin.add(new Coins((j * (coins_spacing + (Coins.coins_width))) + coinss, xy));
            }
        }
        else
            updateCoin(); //reposition coins at front of frame
        //  bullet codes
        bullets_to_remove = new ArrayList<Bullet>();
        for (Bullet bullet : bullets) // will loop through each bullet
        {
            bullet.update_bullet(dt);
            if(bullet.remove_bullet)
            {
                bullets_to_remove.add(bullet);
            }
        }
        bullets.removeAll(bullets_to_remove); // will remove all the bullets added into this list
        // bullet code ends

        for (int i = 0; i < ob.size; i++) {
            Obstacles obs = ob.get(i);
            if (cam.position.x - (cam.viewportWidth / 2) > obs.getPosUfo().x + obs.getUfo().getWidth()) {
                obs.reposition(obs.getPosUfo().x + ((obs.UFO_WIDTH + OBSTACLES_SPACING) * OBSTACLES_COUNT));
            }

            if (obs.collide1(rider.getBounds())) {
                alienCrash= Gdx.audio.newSound(Gdx.files.internal("Alien Death.mp3"));
                alienCrash.play(1f);
                Crash=Gdx.audio.newSound(Gdx.files.internal("UFO crash.mp3"));
                Crash.play(0f);
                gsm.set(new GameOver(gsm));
            }
            if (obs.collide(rider.getBounds())) {
                Crash=Gdx.audio.newSound(Gdx.files.internal("UFO crash.mp3"));
                Crash.play(1f);
                alienCrash= Gdx.audio.newSound(Gdx.files.internal("Alien Death.mp3"));
                alienCrash.play(0f);
                gsm.set(new GameOver(gsm));
            }
        }
        for (int i = 0; i < fly.size; i++) {
            FlyingUfo flyUfo= fly.get(i);
            if (cam.position.x - (cam.viewportWidth / 2) > flyUfo.getPosFly().x + flyUfo.getFlyingUfo().getWidth()) {
                flyUfo.repositionFly(flyUfo.getPosFly().x + ((flyUfo.OBSTACLES_WIDTH + OBSTACLES_SPACING) * OBSTACLES_COUNT));
            }
            if (flyUfo.collideFly(rider.getBounds())) {
                Crash=Gdx.audio.newSound(Gdx.files.internal("UFO crash.mp3"));
                Crash.play(0f);
                alienCrash= Gdx.audio.newSound(Gdx.files.internal("Alien Death.mp3"));
                alienCrash.play(0f);
                gsm.set(new GameOver(gsm));
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

        // bullet code..taking in input from the user
        if(Gdx.input.justTouched()){
            if(Gdx.input.getX() < Gdx.graphics.getWidth()/2)
            {
                bullets.add(new Bullet(rider.getPosition().y,rider.getPosition().x));  // this the place for you to add the bullets to the motorbike, the display part
            }
        }

        for(Bullet bullet : bullets)
        {
            bullet.render_bullet(sb); // passing sb to it , sb will enters bullet.java
        }
        // bullet code ends

        for(Coins co : coin){
            sb.draw(co.getCoins(), co.getPosCoins().x, co.getPosCoins().y);
        }

        for (Obstacles obs : ob) {
            sb.draw(obs.getUfo(), obs.getPosUfo().x, obs.getPosUfo().y);
            sb.draw(obs.getAlien(), obs.getPosAlien().x, obs.getPosAlien().y);
        }
        for (FlyingUfo flyUfo: fly) {
            sb.draw(flyUfo.getFlyingUfo(), flyUfo.getPosFly().x, flyUfo.getPosFly().y);
        }
        sb.draw(ground, groundPos0.x, groundPos0.y);
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.draw(ground, groundPos3.x, groundPos3.y);
        sb.draw(ground, groundPos4.x, groundPos4.y);
        sb.end();
    }

    public void dispose() {
        bg.dispose();
        rider.dispose();
        ground.dispose();
        Crash.dispose();
        alienCrash.dispose();
        for (Obstacles obs : ob) {
            obs.dispose();
        }
        for (FlyingUfo flyUfo : fly) {
            flyUfo.dispose();
        }
        for (Coins co: coin){
            co.dispose();
        }
    }

    private void updateGround() {
        if (cam.position.x - (cam.viewportWidth /1) > groundPos1.x + ground.getWidth()) {
            groundPos1.add(ground.getWidth() * 4, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 1) > groundPos2.x + ground.getWidth()) {
            groundPos2.add(ground.getWidth() * 4, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 1) > groundPos3.x + ground.getWidth()) {
            groundPos3.add(ground.getWidth() * 4, 0);
        }
        if (cam.position.x - (cam.viewportWidth / 1) > groundPos4.x + ground.getWidth()) {
            groundPos4.add(ground.getWidth() * 4, 0);
        }
    }
    private void updateCoin(){

        int yui = 30 + rand.nextInt(fluctuation);
        int iuy = 900 + rand.nextInt(1000);
        Coins co = coin.get(0);
        if(cam.position.x - cam.viewportWidth - (cam.viewportWidth/2) > co.getPosCoins().x + co.getCoins().getRegionX()){
            for(int j = 0; j < coin.size; j++){
                Coins coi = coin.get(j);
                coi.reposition(iuy + coi.getPosCoins().x + ((Coins.coins_width + coins_spacing)* coins_counts),yui);
            }
        }
    }

}