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
import com.mygdx.auber.LoadingGame;
import com.mygdx.auber.SavingGame;
import com.mygdx.auber.entities.Player;


public class PauseScreen implements Screen {

    private Viewport viewport;
    Stage stage;
    TextButton resumeButton, exitButton, demoButton, saveButton,loadButton;
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;
    Texture title;
    Image titleCard;
    Texture background;
    private Auber game;

    public PauseScreen(final Auber game, final PlayScreen currentScreen){
        this.game = game;

        viewport = new ExtendViewport(Auber.VirtualWidth, Auber.VirtualHeight, new OrthographicCamera());
        stage = new Stage(viewport, ((Auber) game).batch);
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont();
        skin = new Skin();
        title = new Texture("TitleCard.png");
        buttonAtlas = new TextureAtlas("buttonAtlas.atlas");
        background = new Texture("background.png");
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("up-button");
        textButtonStyle.down = skin.getDrawable("down-button");
        textButtonStyle.checked = skin.getDrawable("checked-button");
        resumeButton = new TextButton("RESUME", textButtonStyle);
        demoButton = new TextButton("DEMO", textButtonStyle);
        loadButton = new TextButton("LOAD", textButtonStyle);
        exitButton = new TextButton("EXIT", textButtonStyle);
        saveButton = new TextButton("SAVE", textButtonStyle);
        titleCard = new Image(title);
        resumeButton.setSize(200, 190);



        loadButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //System.out.println("Clicked");
                currentScreen.fakeHide();
                new LoadingGame().loadGame(game);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //System.out.println("Hovered");
                loadButton.setChecked(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                //System.out.println("Exited");
                loadButton.setChecked(false);
            }
        });
        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //System.out.println("Clicked");
                game.setScreen(currentScreen);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //System.out.println("Hovered");
                resumeButton.setChecked(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                //System.out.println("Exited");
                resumeButton.setChecked(false);
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentScreen.fakeHide();
                game.setScreen(new MainMenuScreen(game));
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //System.out.println("Hovered");
                exitButton.setChecked(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                //System.out.println("Exited");
                exitButton.setChecked(false);
            }
        });
         saveButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    new SavingGame().playerSave();
                }
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    //System.out.println("Hovered");
                    exitButton.setChecked(true);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    //System.out.println("Exited");
                    exitButton.setChecked(false);
                }
        });


        Table menuTable = new Table();
        menuTable.setTouchable(Touchable.enabled);
        menuTable.setFillParent(true);
        menuTable.add(titleCard).padBottom(10);
        menuTable.row();
        menuTable.add(resumeButton).padBottom(20);
        menuTable.row();
        menuTable.add(loadButton).padBottom(20);
        menuTable.row();
        menuTable.add(saveButton).padBottom(20);
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
        stage.getBatch().begin();
        stage.getBatch().draw(background, -100f, 0f);
        stage.getBatch().end();
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
        stage.dispose();
        font.dispose();
        skin.dispose();
        buttonAtlas.dispose();
        background.dispose();
    }
}
