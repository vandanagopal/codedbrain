package org.maze.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertEquals;

public class CellTest {

    @Test
    public void shouldReturnNeighboursOfCell(){
        Cell cell = new Cell(2, 3);
        Cell cellAbove = new Cell(2, 2);
        Cell cellBelow = new Cell(4, 4);
        cell.addWall(WallDirectionEnum.Up, new Wall(WallStatusEnum.Closed, cell, cellAbove));
        cell.addWall(WallDirectionEnum.Down, new Wall(WallStatusEnum.Closed, cell, cellBelow));
        HashMap<WallDirectionEnum,Cell> neighbours = cell.getNeighbours();
        Assert.assertEquals(cellAbove,neighbours.get(WallDirectionEnum.Up));
        Assert.assertEquals(cellBelow,neighbours.get(WallDirectionEnum.Down));
        Assert.assertNull(neighbours.get(WallDirectionEnum.Right));
        Assert.assertNull(neighbours.get(WallDirectionEnum.Left));

    }

    @Test
    public void shouldReturnOpenNeighboursOfCell(){
        Cell cell = new Cell(2, 3);
        Cell cellAbove = new Cell(2, 2);
        Cell cellBelow = new Cell(4, 4);
        Cell cellLeft = new Cell(5, 5);
        Cell cellRight = new Cell(6, 6);
        cell.addWall(WallDirectionEnum.Up, new Wall(WallStatusEnum.Closed, cell, cellAbove));
        cell.addWall(WallDirectionEnum.Down, new Wall(WallStatusEnum.Open, cell, cellBelow));
        cell.addWall(WallDirectionEnum.Right, new Wall(WallStatusEnum.Open, cell, cellRight));
        cell.addWall(WallDirectionEnum.Left, new Wall(WallStatusEnum.Closed, cell, cellLeft));
        HashMap<WallDirectionEnum,Cell> neighbours = cell.getOpenNeighbours();
        Assert.assertEquals(cellBelow, neighbours.get(WallDirectionEnum.Down));
        Assert.assertEquals(cellRight,neighbours.get(WallDirectionEnum.Right));
        Assert.assertNull(neighbours.get(WallDirectionEnum.Up));
        Assert.assertNull(neighbours.get(WallDirectionEnum.Left));

    }
}
