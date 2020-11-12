package com.mygdx.auber.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.auber.Auber;
import com.mygdx.auber.Pathfinding.GraphCreator;
import com.mygdx.auber.Pathfinding.MapGraph;
import com.mygdx.auber.Scenes.Hud;
import com.mygdx.auber.entities.Infiltrator;
import com.mygdx.auber.entities.Player;

public class PlayScreen implements Screen {
    private Auber game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private GraphCreator graphCreator;

    private Player player;
    private Infiltrator infiltrator;

    public PlayScreen(Auber game){
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(Auber.VirtualWidth, Auber.VirtualHeight, camera);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("AuberMap1.0.tmx");
        player = new Player(new Sprite(new Texture("SpriteTest.png")),(TiledMapTileLayer)map.getLayers().get(0));
        player.setPosition(player.getX() + 150, player.getY() + 100);

        infiltrator = new Infiltrator(new Sprite(new Texture("SpriteTest.png")),(TiledMapTileLayer)map.getLayers().get(0),player.getX(), player.getY());

        renderer = new OrthogonalTiledMapRenderer(map);
        camera.position.set(player.getX(),player.getY(),0);

        Gdx.input.setInputProcessor(player);
        //camera.position.set(viewport.getScreenWidth() / 2, viewport.getScreenHeight() / 2, 0);

        graphCreator = new GraphCreator((TiledMapTileLayer)map.getLayers().get(0));
    }

    @Override
    public void show() {


    }

    public boolean gameOver(){
        if(hud.health <= 0){
            return true;
        }
        return false;
    }

    public void handleInput(float time){
        /*if(Gdx.input.isKeyPressed(Input.Keys.W))
            camera.position.y += 100 * time;
        if(Gdx.input.isKeyPressed(Input.Keys.D))
            camera.position.x += 100 * time;
        if(Gdx.input.isKeyPressed(Input.Keys.S))
            camera.position.y -= 100 * time;
        if(Gdx.input.isKeyPressed(Input.Keys.A))
            camera.position.x -= 100 * time;
        */


    }

    public void update(float time){
        handleInput(time);

        camera.update();
        renderer.setView(camera);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(player.getX() + player.getWidth()/2,player.getY() + player.getHeight()/2,0);

        renderer.getBatch().begin();

        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get(0));
        player.draw(renderer.getBatch());
        infiltrator.draw(renderer.getBatch());
        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get(1));
        graphCreator.render();
        update(delta);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);//Tells the game batch to only render what is in the game camera
        hud.stage.draw();
        renderer.getBatch().end();

        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.viewportWidth = width/2;
        camera.viewportHeight = height/2;
        camera.update();
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
