package org.maze.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cell {
    private int circleIndex;
    private int sectorNumber;
    private HashMap<WallDirectionEnum, Wall> wallPassageMap = new HashMap<WallDirectionEnum, Wall>();
    private boolean hasBeenVisited;

    public Cell(int circleIndex, int sectorNumber) {

        this.circleIndex = circleIndex;
        this.sectorNumber = sectorNumber;
    }

    public int getSectorNumber() {
        return sectorNumber;
    }

    public int getCircleIndex() {
        return circleIndex;
    }


    public void addWall(WallDirectionEnum wallDirection, Wall wall) {
        wallPassageMap.put(wallDirection, wall);
    }

    public HashMap<WallDirectionEnum, Wall> getWallPassageMap() {
        return wallPassageMap;
    }


    public Cell getNeighbour(WallDirectionEnum wallDirection) {
        Wall wall = getWallPassageMap().get(wallDirection);
        return wall == null ? null : wall.getOtherCell(this);
    }

    public void setHasBeenVisited(boolean hasBeenVisited) {
        this.hasBeenVisited = hasBeenVisited;
    }

    public List<Neighbour> getUnvisitedNeighbours() {
        List<Neighbour> unvisitedNeighbours=new ArrayList<Neighbour>();
        for(WallDirectionEnum wallDirection : wallPassageMap.keySet()) {
            Cell neighbour = getNeighbour(wallDirection);
            if (!neighbour.hasBeenVisited)
                unvisitedNeighbours.add(new Neighbour(neighbour,wallDirection));
        }
        return unvisitedNeighbours;
    }


}
