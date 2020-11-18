package com.mygdx.auber;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.auber.Screens.MainMenuScreen;
import com.mygdx.auber.Screens.PlayScreen;

import java.util.HashMap;

public class Auber extends Game {
	public static final int VirtualWidth = 800;
	public static final int VirtualHeight = 480;//Virtual dimensions for the game

	public SpriteBatch batch;
	Texture img;

	final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>(); //Hashmap to store sprites inside of to reduce memory consumption of creating multiple sprite instances

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();//delegates render method to current active screen
	}
}


