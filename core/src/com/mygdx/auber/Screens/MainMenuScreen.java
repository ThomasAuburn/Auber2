package com.mygdx.auber.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.auber.Auber;
import com.mygdx.auber.ScrollingBackground;


public class MainMenuScreen implements Screen {

    private Viewport viewport;
    Stage stage;
    TextButton playButton, exitButton, demoButton;
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;
    Texture title;
    Image titleCard;
    private Auber game;
    private ScrollingBackground scrollingBackground;

    public MainMenuScreen(final Auber game){
        this.game = game;

        viewport = new ExtendViewport(Auber.VirtualWidth, Auber.VirtualHeight, new OrthographicCamera());
        stage = new Stage(viewport, ((Auber) game).batch);
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont();
        skin = new Skin();
        title = new Texture("TitleCard.png");
        buttonAtlas = new TextureAtlas("buttonAtlas.atlas");
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("up-button");
        textButtonStyle.down = skin.getDrawable("down-button");
        textButtonStyle.checked = skin.getDrawable("checked-button");
        playButton = new TextButton("PLAY", textButtonStyle);
        demoButton = new TextButton("DEMO", textButtonStyle);
        exitButton = new TextButton("EXIT", textButtonStyle);
        titleCard = new Image(title);
        playButton.setSize(200, 190);

        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked");
                game.setScreen(new PlayScreen(game));
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                System.out.println("Hovered");
                playButton.setChecked(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                System.out.println("Exited");
                playButton.setChecked(false);
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                System.out.println("Hovered");
                exitButton.setChecked(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                System.out.println("Exited");
                exitButton.setChecked(false);
            }
        });
        demoButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked");
                game.setScreen(new PlayScreen(game));
            }


            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                System.out.println("Hovered");
                demoButton.setChecked(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                System.out.println("Exited");
                demoButton.setChecked(false);
            }
        });

        Table menuTable = new Table();
        menuTable.setTouchable(Touchable.enabled);
        menuTable.setFillParent(true);
        menuTable.add(titleCard).padBottom(20);
        menuTable.row();
        menuTable.add(playButton).padBottom(20);
        menuTable.row();
        menuTable.add(demoButton).padBottom(20);
        menuTable.row();
        menuTable.add(exitButton);
        //menuTable.debug();

        stage.addActor(menuTable);
    }

    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
