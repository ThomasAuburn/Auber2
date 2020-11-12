package com.mygdx.auber.Pathfinding;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Node {
    float x,y;

    int index;

    public Node(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public void render(ShapeRenderer shapeRenderer, SpriteBatch batch, BitmapFont font){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(.8f, .88f, .95f, 1);
        shapeRenderer.circle(x, y, 10);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.circle(x, y, 10);
        shapeRenderer.end();

        batch.begin();
        font.setColor(0, 0, 0, 255);
        font.draw(batch, Integer.toString(index), x-5, y+5);
        batch.end();
    }
}
