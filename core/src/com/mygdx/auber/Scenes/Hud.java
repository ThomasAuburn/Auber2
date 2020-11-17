package com.mygdx.auber.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.auber.Auber;
import com.mygdx.auber.entities.CrewMembers;


public class Hud {
    public Stage stage;//2D scene graph, handles viewport and distributes input events.
    private Viewport viewport;

    public static Integer ImposterCount;
    public static Integer CrewmateCount;

    Label imposterCountLabel;
    Label crewmateCountLabel;

    public Hud(SpriteBatch spritebatch){
        ImposterCount = 0;
        CrewmateCount = 0;

        viewport = new FitViewport(Auber.VirtualWidth, Auber.VirtualHeight, new OrthographicCamera());
        stage = new Stage(viewport, spritebatch);

        Table hudTable = new Table();
        hudTable.top();
        hudTable.setFillParent(true);

        imposterCountLabel = new Label(String.format("Imposter Arrests: %02d", ImposterCount), new Label.LabelStyle(new BitmapFont(), Color.RED));
        crewmateCountLabel = new Label(String.format("Crewmate Arrests: %02d", CrewmateCount), new Label.LabelStyle(new BitmapFont(), Color.RED));

        hudTable.add(imposterCountLabel).expandX().left().padLeft(10);
        hudTable.add(crewmateCountLabel).expandX().right().padRight(10);

        stage.addActor(hudTable);
    }

    public void update()
    {
        imposterCountLabel.setText(String.format("Imposter Arrests: %02d", ImposterCount));
        crewmateCountLabel.setText(String.format("Crewmate Arrests: %02d", CrewmateCount));
    }


}
