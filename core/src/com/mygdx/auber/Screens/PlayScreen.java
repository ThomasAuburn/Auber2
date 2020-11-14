package com.mygdx.auber.Screens;

import com.badlogic.gdx.Gdx;
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
    private int numberOfInfiltrators = 25;
    private Infiltrator[] infiltrators = new Infiltrator[numberOfInfiltrators];

    public PlayScreen(Auber game){
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(Auber.VirtualWidth, Auber.VirtualHeight, camera);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("testmap2.tmx");
        player = new Player(new Sprite(new Texture("AuberStand.png")),(TiledMapTileLayer)map.getLayers().get(0));
        player.setPosition(player.getX() + 150, player.getY() + 100);

        graphCreator = new GraphCreator((TiledMapTileLayer)map.getLayers().get(0));
        for (int i = 0; i < numberOfInfiltrators; i++) {
            infiltrators[i] = new Infiltrator(new Sprite(new Texture("HumanInfiltratorStand.png")),(TiledMapTileLayer)map.getLayers().get(0), MapGraph.getRandomNode(), graphCreator.mapGraph);
        }


        renderer = new OrthogonalTiledMapRenderer(map);
        camera.position.set(player.getX(),player.getY(),0);

        Gdx.input.setInputProcessor(player);
        //camera.position.set(viewport.getScreenWidth() / 2, viewport.getScreenHeight() / 2, 0);
    }

    @Override
    public void show() {


    }

    public boolean gameOver() {
        return Player.health <= 0;
    }

    public void handleInput(float time){

    }

    public void update(float time){
        handleInput(time);

        for (Infiltrator infiltrator:
                infiltrators) {
            infiltrator.step();
        }

        camera.update();
        renderer.setView(camera);
    }

    @Override
    public void render(float delta) {
        /** Clears the screen and sets it to the colour light blue or whatever colour it is */
        Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(player.getX() + player.getWidth()/2,player.getY() + player.getHeight()/2,0); //Sets camera to centre of player position
        game.batch.setProjectionMatrix(camera.combined); //Ensures everything is rendered properly, only renders things in viewport
        renderer.getBatch().begin();  //Start the sprite batch
        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get(0)); //Renders the bottom layer of the map

        player.draw(renderer.getBatch());
        for (Infiltrator infiltrator:
             infiltrators) {
            infiltrator.draw(renderer.getBatch());
        } //Renders the player and all infiltrators

        update(delta); //Updates the game camera and NPCs
        hud.stage.draw(); //Draws the HUD on the game

        graphCreator.shapeRenderer.setProjectionMatrix(camera.combined); //Ensures nodes are rendered properly
        graphCreator.render(); //Debugging shows nodes and paths

        renderer.getBatch().end(); //Finishes the sprite batch

        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        } //If game over, show game over screen and dispose of all assets

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.viewportWidth = width/2f;
        camera.viewportHeight = height/2f;
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
        game.dispose();
        map.dispose();
        renderer.dispose();
        graphCreator.dispose();
    }
}
