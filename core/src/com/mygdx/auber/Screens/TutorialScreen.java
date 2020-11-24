package com.mygdx.auber.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.auber.Auber;

public class TutorialScreen implements Screen{

    private Viewport viewport;
    Stage stage;
    BitmapFont font;
    Skin skin;
    Array<Image> images = new Array<>();

    private Auber game;

    public TutorialScreen(Auber game)
    {
        this.game = game;

        viewport = new ExtendViewport(Auber.VirtualWidth, Auber.VirtualHeight, new OrthographicCamera());
        stage = new Stage(viewport, ((Auber) game).batch);
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont();
        skin = new Skin();

        images.add(new Image(new Texture("Tutorial1.png")));
        images.add(new Image(new Texture("Tutorial2.png")));
        images.add(new Image(new Texture("Tutorial3.png")));
        images.add(new Image(new Texture("Tutorial4.png")));
        images.add(new Image(new Texture("Tutorial5.png")));


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
