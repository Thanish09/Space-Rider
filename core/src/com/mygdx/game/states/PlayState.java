package com.mygdx.game.states;


        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.Input;
        import com.badlogic.gdx.Preferences;
        import com.badlogic.gdx.audio.Music;
        import com.badlogic.gdx.audio.Sound;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.BitmapFont;
        import com.badlogic.gdx.graphics.g2d.SpriteBatch;
        import com.badlogic.gdx.math.Vector2;
        import com.badlogic.gdx.utils.Array;
        import com.badlogic.gdx.utils.Null;
        import com.mygdx.game.Project1;
        import com.mygdx.game.sprites.Coins;
        import com.mygdx.game.sprites.Alien;
        import com.mygdx.game.sprites.Obstacles;
        import com.mygdx.game.sprites.Rider;
        import com.mygdx.game.sprites.FlyingUfo;
        import com.mygdx.game.sprites.Bullet;
        import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

        import java.util.Random;
        import java.util.ArrayList;


public class PlayState extends State {
    private static final int coins_spacing = 5;
    private static final int fluctuation = 250;
    private static final int coins_counts = 15;
    private static final int GROUND_Y_OFFSET = -110;
    private static final int OBSTACLES_SPACING = 500;
    private static final int OBSTACLES_SPACING2 = 700;
    private static final int OBSTACLE_SPACING3 = 500;
    private static final int OBSTACLES_COUNT = 7;

    private Rider rider;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos0, groundPos1, groundPos2, groundPos3,groundPos4;
    public Music alienCrash;
    public Music Crash;
    public Music duit;
    private Array<Obstacles> ob;
    private Array<Alien> alien;
    private Array<Coins> coin;
    private Array<FlyingUfo> fly;
    private GameOver over;
    private Random rand;
    private int count = 0;
    private double multiplier = 0;
    private int multi;
    private int gro =1;
    private int score;
    private String Score;
    private static Preferences prefs;
    private int high;

    BitmapFont font;
    int speed = 0;
    // for bullets, the dynamic list is created. we do not know how many bullet will be shoot
    ArrayList<Bullet> bullets; // the type of obj stored = Bullet and the name of this list = bullets
    ArrayList<Bullet> bullets_to_remove;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        score = 0;
        Score = "score: 0";
       font = new BitmapFont();

        prefs = Gdx.app.getPreferences("Space Rider");
        if(!prefs.contains("highscore")){
            prefs.putInteger("highscore",0);
        }
        rider = new Rider(-90, 25);
        cam.setToOrtho(false, Project1.WIDTH / 2, Project1.HEIGHT / 2);
        bg = new Texture("bg1.jpg");

        // bullet code starts
        bullets = new ArrayList<Bullet>(); // officially created the bullets list
        // bullet code ends

        ground = new Texture("Land.png");
        groundPos0 = new Vector2(-200, GROUND_Y_OFFSET);
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);
        groundPos3 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth() + ground.getWidth(), GROUND_Y_OFFSET);
        groundPos4 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth() + ground.getWidth() + ground.getWidth(), GROUND_Y_OFFSET);

        rand = new Random();
        int xy = 30+ rand.nextInt(fluctuation);
        int coinss = 300 + rand.nextInt(500);
        coin = new Array<Coins>();

        for(int i=1; i <= coins_counts; i++){
            coin.add(new Coins((i * (coins_spacing + (Coins.coins_width))) + coinss, xy));
        }

        fly = new Array<FlyingUfo>();
        ob = new Array<Obstacles>();
        alien = new Array<Alien>();
        ob.add(new Obstacles(1*(250)));
        for (int i = 2; i <= OBSTACLES_COUNT; i++) {
            ob.add(new Obstacles(i * (OBSTACLES_SPACING + Obstacles.UFO_WIDTH)));
        }
        for (int i = 1; i < OBSTACLES_COUNT; i++) {
            fly.add(new FlyingUfo(i * (rand.nextInt(300) +OBSTACLES_SPACING2 + FlyingUfo.OBSTACLES_WIDTH)));
        }
        alien.add(new Alien(1*(300)));
        for (int i = 2; i < OBSTACLES_COUNT; i++) {
            alien.add(new Alien(i * (OBSTACLE_SPACING3+ Alien.ALIEN_WIDTH)));
        }
    }

    public static void setHighScore(int val){
        prefs.putInteger("highscore",val);
        prefs.flush();
    }

    public static int getHighScore(){
        return prefs.getInteger("highscore");
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
        rider.update(dt);
        Obstacles obs;
        FlyingUfo flyUfo;
        Alien aliens;
        cam.position.x = rider.getPosition().x + 80;
        multiplier += 0.2;
        multi = (int)multiplier;
        Score = "score: " + (score + multi);
        int sco = score + multi;
        speed++;
        if(speed == 5000){
            rider.getspeed();
            speed = 0;
            if(gro == 0) {
                bg = new Texture("bg1.jpg");
                ground = new Texture("Land 1.png");
            }
            else if(gro == 1) {
                bg = new Texture("bg2.jpg");
                ground = new Texture("Land 2.png");
            }
            else if(gro == 2) {
                bg = new Texture("bg3.jpg");
                ground = new Texture("Land3.png");
            }
            else if(gro == 3) {
                bg = new Texture("bg4.jpg");
                ground = new Texture("Land 4.png");
            }

            if(gro == 3)
                gro = 0;
            else
                gro++;
        }
        int xy = 30+ rand.nextInt(fluctuation); //random position of coins y position
        int coinss = 500 + rand.nextInt(500); //random position of coins x position

        for (int i = 0; i < coin.size; i++) {
            Coins co = coin.get(i);
            co.update(dt);  //animation

            if (co.collide(rider.getBounds())) {
                co.setRemove(true); //when collide it becomes true
                if(co.isRemove()){  //becomes true
                    coin.removeIndex(i);//remove that particular coin in array
                    duit= Gdx.audio.newMusic(Gdx.files.internal("coin up.mp3"));
                    duit.setVolume(1F);
                    duit.play();
                    count = count + 10;
                    score += count;
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
            if(bullet.dropBullet(cam.position.x + (cam.viewportWidth / 2)))
            {
                bullets_to_remove.add(bullet);
            }
        }
        if(ob.size == 0 )
        {
            for (int j = 1; j <= OBSTACLES_COUNT; j++)
            {
                ob.add(new Obstacles(j * (OBSTACLES_SPACING + Obstacles.UFO_WIDTH)));
            }
        }
        if(alien.size == 0 )
        {
            for (int j = 1; j <= OBSTACLES_COUNT; j++)
            {
                alien.add(new Alien(j * (OBSTACLE_SPACING3+ Alien.ALIEN_WIDTH)));
            }
        }
        if(fly.size == 0 )
        {
            for (int j = 1; j <= OBSTACLES_COUNT; j++)
            {
                fly.add(new FlyingUfo(j * (rand.nextInt(300) +OBSTACLES_SPACING2 + FlyingUfo.OBSTACLES_WIDTH)));
            }
        }
        // bullet code ends

        for (int i = 0; i < ob.size; i++) {
            obs = ob.get(i);
            if (cam.position.x - (cam.viewportWidth / 2) > obs.getPosUfo().x + obs.getUfo().getWidth()) {
                obs.reposition(obs.getPosUfo().x + ((obs.UFO_WIDTH + OBSTACLES_SPACING) * OBSTACLES_COUNT));
            }
            if (obs.collide(rider.getBounds())) {
                Crash=Gdx.audio.newMusic(Gdx.files.internal("UFO crash.mp3"));
               Crash.setVolume(1F);
                Crash.play();
                if(sco>getHighScore()){
                    setHighScore(sco);
                }
                high = getHighScore();
                gsm.set(new GameOver(gsm,Score,high));
            }
        }
        for (int i = 0; i < fly.size; i++) {
            flyUfo= fly.get(i);
            if (cam.position.x - (cam.viewportWidth / 2) > flyUfo.getPosFly().x + flyUfo.getFlyingUfo().getWidth()) {
                flyUfo.repositionFly(flyUfo.getPosFly().x + ((flyUfo.OBSTACLES_WIDTH + OBSTACLES_SPACING) * OBSTACLES_COUNT));
            }
            if (flyUfo.collideFly(rider.getBounds())) {
                Crash=Gdx.audio.newMusic(Gdx.files.internal("UFO crash.mp3"));
                Crash.setVolume(1F);
                Crash.play();
                if(sco>getHighScore()){
                    setHighScore(sco);
                }
                high = getHighScore();
                gsm.set(new GameOver(gsm,Score,high));
            }
        }
        for (int i = 0; i < alien.size; i++) {
            aliens = alien.get(i);

            if (cam.position.x - (cam.viewportWidth / 2) > aliens.getPosAlien().x + aliens.getAlien().getWidth()) {
                aliens.reposition(aliens.getPosAlien().x + ((aliens.ALIEN_WIDTH + OBSTACLES_SPACING) * OBSTACLES_COUNT));
            }

            if (aliens.collide(rider.getBounds())) {
                alienCrash= Gdx.audio.newMusic(Gdx.files.internal("Alien Death.mp3"));
                alienCrash.setVolume(1F);
                alienCrash.play();
                if(sco>getHighScore()){
                    setHighScore(sco);
                }
                high = getHighScore();
                gsm.set(new GameOver(gsm,Score,high));
            }
        }
        for (Bullet bullet : bullets) // when th bullet hit a target, the target disappears
        {
            for(int i = 0; i < ob.size; i++)
            {
                obs = ob.get(i);
                if((bullet.bulletHitUfo(obs.getBoundsUfo())) && (!bullet.dropBullet(cam.position.x + (cam.viewportWidth / 2))) )
                {
                    bullets_to_remove.add(bullet);
                    ob.removeIndex(i);
                    count = count + 20;
                    score += count;
                }
            }
            for(int k = 0; k < alien.size; k++)
            {
                aliens = alien.get(k);
                if((bullet.bulletHitAlien(aliens.getBoundsAlien())) && (!bullet.dropBullet(cam.position.x + (cam.viewportWidth / 2))) )
                {
                    bullets_to_remove.add(bullet);
                    alien.removeIndex(k);
                    count = count + 20;
                    score += count;
                }
            }
            for(int p = 0; p < fly.size; p++)
            {
                flyUfo = fly.get(p);
                if((bullet.bulletHitFly(flyUfo.getBoundsFly())) && (!bullet.dropBullet(cam.position.x + (cam.viewportWidth / 2))) )
                {
                    bullets_to_remove.add(bullet);
                    fly.removeIndex(p);
                    count = count + 20;
                    score += count;
                }
            }
        }
        bullets.removeAll(bullets_to_remove); // will remove all the bullets added into this list
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(rider.getTexture(), rider.getPosition().x, rider.getPosition().y);

        font.setColor(1.0f,1.0f,1.0f,1.0f);
        font.draw(sb, Score, rider.getPosition().x + 200, 250);

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
        }
        for (FlyingUfo flyUfo: fly) {
            sb.draw(flyUfo.getFlyingUfo(), flyUfo.getPosFly().x, flyUfo.getPosFly().y);
        }
        for (Alien aliens:alien) {
            sb.draw(aliens.getAlien(), aliens.getPosAlien().x, aliens.getPosAlien().y);
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
        for (Obstacles obs : ob) {
            obs.dispose();
        }
        for (FlyingUfo flyUfo : fly) {
            flyUfo.dispose();
        }
        for (Alien aliens: alien){
            aliens.dispose();
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