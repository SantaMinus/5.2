package com.sava_graph;

import java.util.ArrayList;
import java.util.List;

public class Unit {
    private int id;
    private List<Unit> neighbours;

    Unit(int id) {
        this.id = id;
        this.neighbours = new ArrayList<>();
    }

    void addNeighbour(Unit u) {
        neighbours.add(u);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Unit> getNeighbours() {
        return neighbours;
    }
}
