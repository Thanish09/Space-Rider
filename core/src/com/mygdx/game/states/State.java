package com.mygdx.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {

    protected OrthographicCamera cam;//screen that will be displayed to the user
    protected Vector3 mouse;//x y z coordinate
    protected GameStateManager gsm; //manages the states of the game

  //  constructor
    protected State(GameStateManager gsm) {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }

  //  handle any input that send by user
    public abstract void handleInput();


    public abstract void update(float dt);  //dt means diff between one frame render to next frame render

  //used to draw things on the screen
  //SpriteBatch is a container
  //for anything that we need to render on our screen
    public abstract void render(SpriteBatch sb); //
//
    //dispose textures to avoid memory leaks
    public abstract void dispose();

}

