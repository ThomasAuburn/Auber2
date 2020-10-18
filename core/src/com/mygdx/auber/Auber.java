package com.mygdx.auber;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Auber extends ApplicationAdapter {
	SpriteBatch batch;
	Texture texture;
	Sprite sprite;
	Sprite character;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		texture = new Texture("Room 1.png");
		character = new Sprite(new Texture("Construction worker.png"));
		sprite = new Sprite(texture);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			character.translate(-5, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			character.translate(5, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			character.translate(0, 5);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			character.translate(0, -5);
		}

		batch.begin();
		sprite.draw(batch);
		character.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		texture.dispose();
	}
}
