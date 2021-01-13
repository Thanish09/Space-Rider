package com.mygdx.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

// it pushes the new state on the top of the stack
// after the new state removed ...it will do the state at the top
//GameStateManager will be used to manage our states and
//hence we can put a state over a state , pause a state ,resume state etc
public class GameStateManager {

    private Stack<State> states;
    public GameStateManager()
    {
        states = new Stack<State>();
    }

    public void push(State state)
    {
        states.push(state);
    }

    public void pop()
    {
        states.pop().dispose();
    }

    //when we pop a state
    //we instantly push the other state
    public void set(State state)
    {
       states.pop().dispose();
        states.push(state);
    }

    public void update(float dt)
    {
        states.peek().update(dt);
    }   //peek is return the value of top without removing it from stack

  //  It look at the top of the stack using peek method and
    //then it renders it on the screen
    public void render(SpriteBatch sb)
    {
        states.peek().render(sb);
    }
}
