package org.maze.domain;

import ch.lambdaj.function.matcher.Predicate;
import org.hamcrest.Matcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ch.lambdaj.Lambda.filter;
import static ch.lambdaj.Lambda.forEach;

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


    private Cell getNeighbour(WallDirectionEnum wallDirection) {
        Wall wall = getWallPassageMap().get(wallDirection);
        return wall == null ? null : wall.getOtherCell(this);
    }

    private Cell getOpenNeighbour(WallDirectionEnum wallDirection) {
        Wall wall = getWallPassageMap().get(wallDirection);
        if (wall == null || wall.getWallStatus() == WallStatusEnum.Closed)
            return null;
        return wall.getOtherCell(this);
    }

    public HashMap<WallDirectionEnum, Cell> getNeighbours() {
        HashMap<WallDirectionEnum, Cell> map = new HashMap<WallDirectionEnum, Cell>();
        for (WallDirectionEnum wallDirection : WallDirectionEnum.getValues())
            map.put(wallDirection, getNeighbour(wallDirection));
        return map;
    }

    public HashMap<WallDirectionEnum, Cell> getOpenNeighbours() {
        HashMap<WallDirectionEnum, Cell> map = new HashMap<WallDirectionEnum, Cell>();
        for (WallDirectionEnum wallDirection : WallDirectionEnum.getValues())
            map.put(wallDirection, getOpenNeighbour(wallDirection));
        return map;
    }

    public void setHasBeenVisited(boolean hasBeenVisited) {
        this.hasBeenVisited = hasBeenVisited;
    }

    public boolean getHasBeenVisited() {
        return hasBeenVisited;
    }

    public List<Map.Entry<WallDirectionEnum,Cell>> getUnvisitedNeighbours(){
        List<Map.Entry<WallDirectionEnum, Cell>> filter = filter(notVisitedNeighboursPredicate, getNeighbours().entrySet());
        forEach(filter,)
        return filter;
    }

    Matcher<Map.Entry<WallDirectionEnum, Cell>> notVisitedNeighboursPredicate = new Predicate<Map.Entry<WallDirectionEnum, Cell>>() {
        @Override
        public boolean apply(Map.Entry<WallDirectionEnum, Cell> cellMap) {
            return !cellMap.getValue().getHasBeenVisited();
        }
    };

}
