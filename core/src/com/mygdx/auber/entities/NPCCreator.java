package com.mygdx.auber.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.auber.Pathfinding.MapGraph;
import com.mygdx.auber.Pathfinding.Node;

public class NPCCreator {

    private static int lastInfiltratorIndex = 0;
    private static int lastCrewIndex = 0;

    public static void createInfiltrator(Sprite sprite, TiledMapTileLayer layer, Node start, MapGraph graph)
    {
        Infiltrator infiltrator = new Infiltrator(sprite, layer, start, graph);
        NPC.infiltrators.add(infiltrator);
        infiltrator.setIndex(lastInfiltratorIndex);
        lastInfiltratorIndex++;
    }

    public static void createCrew(Sprite sprite, TiledMapTileLayer layer, Node start, MapGraph graph)
    {
        CrewMembers crewMember = new CrewMembers(sprite, layer, start, graph);
        NPC.crew.add(crewMember);
        crewMember.setIndex(lastCrewIndex);
        lastCrewIndex++;
    }
}
