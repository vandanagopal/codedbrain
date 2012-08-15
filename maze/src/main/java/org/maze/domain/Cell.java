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
        Wall wall = getWall(wallDirection);
        return wall == null ? null : wall.getOtherCell(this);
    }

    private Wall getWall(WallDirectionEnum wallDirection) {
        return getWallPassageMap().get(wallDirection);
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

    public List<Neighbour> getOpenWallNeighbours() {
        List<Neighbour> openWallNeighbours=new ArrayList<Neighbour>();
        for(WallDirectionEnum wallDirection : wallPassageMap.keySet()) {
            Wall wall = getWall(wallDirection);
            if(wall.getWallStatus()==WallStatusEnum.Open)
                openWallNeighbours.add(new Neighbour(wall.getOtherCell(this),wallDirection));
        }
        return openWallNeighbours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (circleIndex != cell.circleIndex) return false;
        if (hasBeenVisited != cell.hasBeenVisited) return false;
        if (sectorNumber != cell.sectorNumber) return false;
        if (wallPassageMap != null ? !wallPassageMap.equals(cell.wallPassageMap) : cell.wallPassageMap != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = circleIndex;
        result = 31 * result + sectorNumber;
        result = 31 * result + (wallPassageMap != null ? wallPassageMap.hashCode() : 0);
        result = 31 * result + (hasBeenVisited ? 1 : 0);
        return result;
    }
}
