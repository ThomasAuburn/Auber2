package com.mygdx.auber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScrollingBackground {

    Texture image;
    float y1, y2;
    public static int SPEED = 30; //In pixels per second
    float imageScale;

    public ScrollingBackground()
    {
        image = new Texture("Background.png");

        y1 = 0;
        y2 = image.getHeight();
        imageScale = 0;
    }

    public void updateRender(float delta, SpriteBatch batch)
    {
        y1 -= SPEED * delta;
        y2 -= SPEED * delta;

        if(y1 + image.getHeight() * imageScale <= 0)
        {
            y1 = y2 + image.getHeight() * imageScale;
        }
        if(y2 + image.getHeight() * imageScale <= 0)
        {
            y2 = y1 + image.getHeight() * imageScale;
        }

        batch.draw(image, 0, y1, Gdx.graphics.getWidth(), image.getHeight() * imageScale);
        batch.draw(image, 0, y2, Gdx.graphics.getWidth(), image.getHeight() * imageScale);
    }

    public void resize(int width, int height)
    {
        imageScale = width / image.getWidth();
    }
}
