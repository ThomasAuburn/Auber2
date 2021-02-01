package com.mygdx.auber.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.google.gson.JsonElement;
import com.mygdx.auber.*;
import com.mygdx.auber.Pathfinding.GraphCreator;
import com.mygdx.auber.Pathfinding.MapGraph;
import com.mygdx.auber.Scenes.Hud;
import com.mygdx.auber.entities.*;
import com.google.gson.Gson;

public class PlayScreen implements Screen {
    private final Auber game;
    private final Viewport viewport;
    private final Hud hud;
    private final TmxMapLoader mapLoader;
    public final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final GraphCreator graphCreator;
    private final ScrollingBackground scrollingBackground;
    private final KeySystemManager keySystemManager;
    private Prisoners prisoners;
    private final ShapeRenderer shapeRenderer;
    public static OrthographicCamera camera;
    public static Player player;

    public static int numberOfInfiltrators;
    public static int numberOfCrew;
    public static int numberOfPowerups;
    public static int maxIncorrectArrests;

    private static boolean demo;

    public PlayScreen(Auber game, boolean demo,int setNumberOfInfiltrators,int setNumberOfCrew,int setMaxIncorrectArrests,boolean loadingGame){
        this.game = game;
        this.demo = demo;

        numberOfInfiltrators = setNumberOfInfiltrators;
        numberOfCrew = setNumberOfCrew;

        maxIncorrectArrests = setMaxIncorrectArrests;
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(Auber.VirtualWidth, Auber.VirtualHeight, camera);
        hud = new Hud(game.batch);
        shapeRenderer = new ShapeRenderer();
        scrollingBackground = new ScrollingBackground(); //Creating a new camera, viewport, hud and scrolling background, setting the viewport to camera and virtual height/width

        mapLoader = new TmxMapLoader();

        map = mapLoader.load("AuberMap.tmx"); //Creates a new map loader and loads the map into map

        Infiltrator.createInfiltratorSprites();
        CrewMembers.createCrewSprites();
        Powerup.createPowerupSprites(); //Generates the infiltrator,crewmember and powerup sprites

        graphCreator = new GraphCreator((TiledMapTileLayer)map.getLayers().get("Tile Layer 1")); //Generates all the nodes and paths for the given map layer
        keySystemManager = new KeySystemManager((TiledMapTileLayer)map.getLayers().get("Systems")); //Generates key systems
        prisoners = new Prisoners((TiledMapTileLayer)map.getLayers().get("OutsideWalls+Lining"));
        if(demo)
        {
            NPCCreator.createCrew(new Sprite(new Texture("AuberStand.png")), MapGraph.closest(1700,3000), graphCreator.mapGraph, (double) 0,(float) 1,(float) 1);
        }
        if(! loadingGame) {//if not loading a game then make everything fresh
            for (int i = 0; i < numberOfInfiltrators; i++) {
                //System.out.println("Infiltrator created!");
                double chance = Math.random() * 20;
                if (i == numberOfInfiltrators - 1) {
                    NPCCreator.createInfiltrator(Infiltrator.selectSprite(chance), MapGraph.getRandomNode(), graphCreator.mapGraph,(double) 0,(float) 1,(float) 1,false);
                    break;
                }
                NPCCreator.createInfiltrator(Infiltrator.selectSprite(chance), MapGraph.getRandomNode(), graphCreator.mapGraph,(double) 0,(float) 1,(float) 1,false);
            } //Creates numberOfInfiltrators infiltrators, gives them a random hard or easy sprite

            for (int i = 0; i < numberOfCrew; i++) { //creates the crewmates
                double chance = Math.random() * 20;
                NPCCreator.createCrew(CrewMembers.selectSprite(chance), MapGraph.getRandomNode(), graphCreator.mapGraph,chance,(float) 1,(float) 1);
            } //Creates numberOfCrew crewmembers, gives them a random sprite

            /**
             * creates power-ups
             */

            for (int i = 0; i < 5; i++) {
                double chance = Math.random() * 5;
                GeneratePowerups.createPowerups(Powerup.selectSprite(chance), MapGraph.getRandomNode(), chance);
            } //creates 5 random powerups in random locations


        }
        else{
            Gson gson = new Gson();//reloads old infiltrators
            String infSave = Gdx.app.getPreferences("Saved Game").getString("infInfo");
            INFInfo infInfo = gson.fromJson(infSave, INFInfo.class);
            for(CrewModel inf:infInfo.data){
                NPCCreator.createInfiltrator(Infiltrator.selectSprite(inf.chance), MapGraph.closest(inf.x,inf.y), graphCreator.mapGraph,inf.chance,inf.goalX,inf.goalY,inf.destoying);
            }

            Gson gsoon = new Gson();//reloads old crewmates
            String npcSave = Gdx.app.getPreferences("Saved Game").getString("npcInfo");
            NPCInfo npcInfo = gsoon.fromJson(npcSave, NPCInfo.class);


            for(CrewModel crew:npcInfo.data){
                NPCCreator.createCrew(CrewMembers.selectSprite(crew.chance), MapGraph.closest(crew.x,crew.y), graphCreator.mapGraph,crew.chance,crew.goalX,crew.goalY);
            }
            Gson gsooon = new Gson();//reloads old prisoners
            String prisSave = Gdx.app.getPreferences("Saved Game").getString("prisInfo");
            PrisonerInfo prisInfo = gsooon.fromJson(prisSave, PrisonerInfo.class);
            for (PrisonerModel pris:prisInfo.data){
                if (! pris.side){
                    Prisoners.addPrisoner(pris.chance,pris.side);
                    Hud.CrewmateCount += 1;
                }
                else{
                    Prisoners.addPrisoner(pris.chance,pris.side);
                    Hud.ImposterCount += 1;
                }
            }

            Gson gsoooon = new Gson();//reloads old powerups
            String pwrUpSave = Gdx.app.getPreferences("Saved Game").getString("powerupInfo");
            PowerupInfo pwrInfo = gsoooon.fromJson(pwrUpSave, PowerupInfo.class);

            for(PowerupModel powerup:pwrInfo.data){
                GeneratePowerups.createPowerups(Powerup.selectSprite(powerup.chance),MapGraph.closest(powerup.x,powerup.y), powerup.chance);
            }
        }



//            Gson gson = new Gson();
//            String npcSave = Gdx.app.getPreferences("Saved Game").getString("npcInfo");
//            NPCInfo npcInfo = gson.fromJson(npcSave, NPCInfo.class);
//            System.out.println(npcInfo);
            //System.out.println(playerInfo.x);
            //NPCCreator.crew = npcInfo.crew;

        Array<TiledMapTileLayer> playerCollisionLayers = new Array<>();
        playerCollisionLayers.add((TiledMapTileLayer) map.getLayers().get("Tile Layer 1")); playerCollisionLayers.add((TiledMapTileLayer) map.getLayers().get(2)); //The layers on which the player will collide

        player = new Player(new Sprite(new Texture("AuberStand.png")), playerCollisionLayers, demo);
        //System.out.println(player);
        if(! loadingGame) {
            player.setPosition(1700, 3000); //Creates a player and sets him to the given position
        }
        else{
            Gson gson = new Gson();
            String playerSave = Gdx.app.getPreferences("Saved Game").getString("playerInfo");
            PlayerInfo playerInfo = gson.fromJson(playerSave, PlayerInfo.class);
            //System.out.println(playerInfo.x);
            player.setPosition(playerInfo.x, playerInfo.y);
            player.health = playerInfo.health;
        }



        player.findHealers((TiledMapTileLayer) map.getLayers().get("Systems")); //Finds infirmary
        player.teleporters = player.getTeleporterLocations((TiledMapTileLayer) map.getLayers().get("Systems")); //Finds the teleporters

        renderer = new OrthogonalTiledMapRenderer(map); //Creates a new renderer with the given map

        camera.position.set(player.getX(),player.getY(),0); //Sets the camera position to the player

        Gdx.input.setInputProcessor(player); //Sets the input to be handled by the player class
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(player);

    }

    /**
     * If any of the game over conditions are true, returns true
     * @return Boolean if the game is over or not
     */
    public boolean gameOver() {
        return Player.health <= 0 || Hud.CrewmateCount >= PlayScreen.maxIncorrectArrests || KeySystemManager.destroyedKeySystemsCount() >= 15 ;
    }

    /**
     * If any of the win conditions are true, returns true
     * @return Boolean If the game is won or not
     */
    public boolean gameWin()
    {
        return NPCCreator.infiltrators.isEmpty();
    }

    /**
     * Called every frame, call update methods in here
     * @param time Time between last frame and this frame
     */
    public void update(float time){
        NPC.updateNPC(time);
        player.update(time);
        Powerup.updatePowerup();
        hud.update();
        camera.update(); //Updating everything that needs to be updated

        //debugText();

        renderer.setView(camera); //Needed for some reason

        if(gameOver()){
            System.out.println("Win");
            this.fakeHide();
            game.setScreen(new GameOverScreen(game, false));
            return;
        } //If game over, show game over screen and dispose of all assets
        if(gameWin())
        {
            System.out.println("Lose");
            this.fakeHide();
            game.setScreen(new GameOverScreen(game, true));
            return;
        } //If game won, show game win screen and dispose of all assets
    }

    /**
     * Called every frame, call render methods in here
     * @param delta Time between last frame and this frame
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.09f, 0.09f, 0.09f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);// Clears the screen and sets it to the colour light blue or whatever colour it is

        if(!demo)
        {
            camera.position.set(player.getX() + player.getWidth()/2,player.getY() + player.getHeight()/2,0); //Sets camera to centre of player position
        }
        else{
            CrewMembers crew = NPCCreator.crew.get(0);
            camera.position.set(crew.getX() + crew.getWidth()/2, crew.getY() + crew.getHeight()/2, 0);
        }

        game.batch.setProjectionMatrix(camera.combined); //Ensures everything is rendered properly, only renders things in viewport
        shapeRenderer.setProjectionMatrix(camera.combined); //Ensures the shape renderer renders thing properly

        renderer.getBatch().begin();  //Start the sprite batch
        /* Render sprites/textures below this line */

        scrollingBackground.updateRender(delta, (SpriteBatch) renderer.getBatch());//Renders the background
        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get(0)); //Renders the bottom layer of the map
        Prisoners.render(renderer.getBatch());
        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get(1));
        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get(2));
        renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get(3));

        NPC.render(renderer.getBatch()); //Renders all NPCs
        Powerup.render(renderer.getBatch()); //Renders all power-ups
        if(!demo)
        {
            player.draw(renderer.getBatch()); //Renders the player
            player.drawArrow(renderer.getBatch()); //Renders arrows towards key systems
        }

        update(delta); //Updates the game camera and NPCs
        hud.stage.draw(); //Draws the HUD on the game

        /* Render sprites/textures above this line */
        renderer.getBatch().end(); //Finishes the sprite batch
        /* Render shapes below this line*/

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); //Allows for alpha changes in shapes

        if(!demo)
        {
            player.drawCircle(shapeRenderer);
        }
        //graphCreator.shapeRenderer.setProjectionMatrix(camera.combined); //Ensures shapes are rendered properly
        //graphCreator.render(); //Debugging shows nodes and paths

        /* Render shapes above this line */
        Gdx.gl.glDisable(GL20.GL_BLEND);
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            if (! demo){
                game.setScreen(new PauseScreen(game,this));
            }
            else{
                this.fakeHide();
                game.setScreen(new MainMenuScreen(game));
            }
        }
    }
    public void fakeHide(){
        graphCreator.dispose();
        NPC.disposeNPC();
        KeySystemManager.dispose();
        player.dispose();
        Powerup.disposePowerup();
        GeneratePowerups.dispose();
    }

    /**
     * Called upon window being resized, and at the beginning
     * @param width Width of the window
     * @param height Height of the window
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.viewportWidth = width/2f;
        camera.viewportHeight = height/2f;
        camera.update();
        scrollingBackground.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
//        graphCreator.dispose();
//        NPC.disposeNPC();
//        KeySystemManager.dispose();
//        player.dispose();
    }

    /**
     * Called when the screen is closed, need to call dispose methods of classes to ensure no memory leaks
     */
    @Override
    public void dispose() {
        graphCreator.dispose();
        NPC.disposeNPC();
        KeySystemManager.dispose();
        player.dispose();
        graphCreator.dispose();
        NPC.disposeNPC();
        Powerup.disposePowerup();
        GeneratePowerups.dispose();
        KeySystemManager.dispose();
        player.dispose();
        map.dispose();
        game.dispose();
        renderer.dispose();
    }

    public void debugText()
    {
        System.out.println("KeySystems:");
        System.out.format(" Safe: %d\n", KeySystemManager.safeKeySystemsCount());
        System.out.format(" BeingDestroyed: %d\n", KeySystemManager.beingDestroyedKeySystemsCount());
        System.out.format(" Destroyed: %d\n", KeySystemManager.destroyedKeySystemsCount());
        System.out.println();
    }

}
