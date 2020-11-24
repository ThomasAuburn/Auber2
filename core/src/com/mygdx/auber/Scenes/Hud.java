package com.mygdx.auber.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.auber.Auber;
import com.mygdx.auber.Screens.PlayScreen;
import com.mygdx.auber.entities.*;

public class Hud {
    public Stage stage;//2D scene graph, handles viewport and distributes input events.
    private Viewport viewport;
    private Table hudTable;

    public static Integer ImposterCount;
    public static Integer CrewmateCount;

    Label imposterCountLabel;
    Label crewmateCountLabel;
    Label keySystemsCountLabel;
    Label playerHealthLabel;

    public Hud(SpriteBatch spritebatch){
        ImposterCount = 0;
        CrewmateCount = 0;

        viewport = new FitViewport(Auber.VirtualWidth, Auber.VirtualHeight, new OrthographicCamera());
        stage = new Stage(viewport, spritebatch);

        hudTable = new Table();
        hudTable.top();
        hudTable.setFillParent(true);

        imposterCountLabel = new Label(String.format("Imposter Arrests: %02d / %02d", ImposterCount, PlayScreen.numberOfInfiltrators), new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        crewmateCountLabel = new Label(String.format("Incorrect Arrests: %02d / %02d", CrewmateCount, PlayScreen.maxIncorrectArrests), new Label.LabelStyle(new BitmapFont(), Color.RED));
        playerHealthLabel = new Label(String.format("Health: %02d", (int) Player.health), new Label.LabelStyle(new BitmapFont(), Color.RED));
        keySystemsCountLabel = new Label(String.format("Key systems destroyed: %02d / %02d", 1, 1), new Label.LabelStyle(new BitmapFont(), Color.RED));

        hudTable.add(imposterCountLabel).expandX().left().padLeft(10);
        hudTable.row();
        hudTable.add(crewmateCountLabel).expandX().left().padLeft(10);
        hudTable.row();
        hudTable.add(keySystemsCountLabel).expandX().left().padLeft(10);
        hudTable.row().bottom().expandY();
        hudTable.add(playerHealthLabel).expandX().left().padLeft(10);


        stage.addActor(hudTable);
    }

    public void update() {
        imposterCountLabel.setText(String.format("Imposter Arrests: %02d / %02d", ImposterCount, PlayScreen.numberOfInfiltrators));
        crewmateCountLabel.setText(String.format("Incorrect Arrests: %02d / %02d", CrewmateCount, PlayScreen.maxIncorrectArrests));
        playerHealthLabel.setText(String.format("Health: %02d",(int) Player.health));
        keySystemsCountLabel.setText(String.format("Safe key systems: %02d / %02d", KeySystemManager.safeKeySystemsCount(), KeySystemManager.keySystemsCount()));
    }

}
