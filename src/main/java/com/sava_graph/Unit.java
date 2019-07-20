package com.sava_graph;

import java.util.ArrayList;

public class Unit {
    int id;
    ArrayList<Unit> neighbours = new ArrayList<>();

    public Unit(int id) {
        this.id = id;
    }

    public void addNeighbour(Unit u) {
        neighbours.add(u);
    }

    public void markNeighbours() {

    }
}
