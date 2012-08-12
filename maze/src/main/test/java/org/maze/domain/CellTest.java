package org.maze.domain;

import org.junit.Test;

import java.util.List;

import static ch.lambdaj.Lambda.*;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.IsEqual.equalTo;

public class CellTest {

    @Test
    public void shouldReturnAllUnvisitedNeighboursOfACell(){
        Cell cell = new Cell(2, 3);
        Cell cellAbove = new Cell(2, 2);
        Cell cellBelow = new Cell(4, 4);
        Cell cellLeft = new Cell(3, 3);

        cellAbove.setHasBeenVisited(true);

        cell.addWall(WallDirectionEnum.Up, new Wall(WallStatusEnum.Closed, cell, cellAbove));
        cell.addWall(WallDirectionEnum.Down, new Wall(WallStatusEnum.Closed, cell, cellBelow));
        cell.addWall(WallDirectionEnum.Left, new Wall(WallStatusEnum.Closed, cell, cellLeft));

        List<Neighbour> unvisitedNeighbours = cell.getUnvisitedNeighbours();
        assertEquals(2,unvisitedNeighbours.size());
        assertEquals(cellBelow, getNeighbour(unvisitedNeighbours, WallDirectionEnum.Down));
        assertEquals(cellLeft, getNeighbour(unvisitedNeighbours, WallDirectionEnum.Left));
    }

    private Cell getNeighbour(List<Neighbour> neighbours, WallDirectionEnum direction) {
        List<Neighbour> neighbourList = filter(having(on(Neighbour.class).getDirection(), equalTo(direction)), neighbours);
        return neighbourList == null || neighbourList.size() == 0 ? null : neighbourList.get(0).getCell();
    }

}
