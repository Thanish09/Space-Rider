package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class RiderAnimation {
    private Array<TextureRegion> Rframes;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;

    public RiderAnimation(TextureRegion region, int frameCount, float cycle) {
        Rframes = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;
        for (int i = 0; i < frameCount; i++) {
            Rframes.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycle / frameCount;
        frame = 0;
    }

    public void update(float dt) {

        currentFrameTime += dt;
        if (currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime = 0;
        }
        if (frame >= frameCount) {
            frame = 0;
        }
    }

    public TextureRegion getFrame()
    {
        return Rframes.get(frame);
    }

}