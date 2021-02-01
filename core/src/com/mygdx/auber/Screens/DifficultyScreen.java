package com.mygdx.auber.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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


public class DifficultyScreen implements Screen {

    private Viewport viewport;
    Stage stage;
    TextButton easyButton, mediumButton, hardButton, backButton;
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;
    Texture title;
    Image titleCard;
    Texture background;
    private Auber game;

    public DifficultyScreen (final Auber game){
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
        easyButton = new TextButton("EASY", textButtonStyle);
        mediumButton = new TextButton("MEDIUM", textButtonStyle);
        hardButton = new TextButton("HARD", textButtonStyle);
        backButton = new TextButton("BACK", textButtonStyle);
        titleCard = new Image(title);
        easyButton.setSize(200, 190);



        easyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //System.out.println("Clicked");

                game.setScreen(new PlayScreen(game, false,6,15,5,false));

            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //System.out.println("Hovered");
                easyButton.setChecked(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                //System.out.println("Exited");
                easyButton.setChecked(false);
            }
        });
        mediumButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //System.out.println("Clicked");
                game.setScreen(new PlayScreen(game, false,8,25,3,false));
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //System.out.println("Hovered");
                mediumButton.setChecked(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                //System.out.println("Exited");
                mediumButton.setChecked(false);
            }
        });
        hardButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen(game, false,8,40,3,false));
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //System.out.println("Hovered");
                hardButton.setChecked(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                //System.out.println("Exited");
                hardButton.setChecked(false);
            }
        });
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //System.out.println("Clicked");
                game.setScreen(new MainMenuScreen(game));
            }


            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //System.out.println("Hovered");
                backButton.setChecked(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                //System.out.println("Exited");
                backButton.setChecked(false);
            }
        });


        Table menuTable = new Table();
        menuTable.setTouchable(Touchable.enabled);
        menuTable.setFillParent(true);
        menuTable.add(titleCard).padBottom(10);
        menuTable.row();
        menuTable.add(easyButton).padBottom(20);
        menuTable.row();
        menuTable.add(mediumButton).padBottom(20);
        menuTable.row();
        menuTable.add(hardButton).padBottom(20);
        menuTable.row();
        menuTable.add(backButton).padBottom(20);


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
