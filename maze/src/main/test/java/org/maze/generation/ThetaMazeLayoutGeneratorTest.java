package org.maze.generation;

import org.junit.Assert;
import org.junit.Test;
import org.maze.domain.Cell;
import org.maze.domain.ThetaMaze;
import org.maze.domain.WallDirectionEnum;

import java.util.HashMap;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class ThetaMazeLayoutGeneratorTest {

    private final int numberOfConcentricCircles = 3;
    private final int numberOfSectors = 8;

    @Test
    public void shouldGenerateThreeCellsWhenNumberOfCirclesIsTwoAndNumberOfSectorsIsTwo() {
        ThetaMazeLayoutGenerator thetaMazeLayoutGenerator = new ThetaMazeLayoutGenerator(2, 2);
        ThetaMaze thetaMaze = thetaMazeLayoutGenerator.generateLayout();
        List<List<Cell>> circleCellDictionary = thetaMaze.getCircleCellDictionary();
        List<Cell> cellsInCircleZero = circleCellDictionary.get(0);
        List<Cell> cellsInCircleOne = circleCellDictionary.get(1);

        Assert.assertEquals(2, cellsInCircleZero.size());
        Assert.assertEquals(1, cellsInCircleOne.size());


        Cell cellInCircle0Sector0 = cellsInCircleZero.get(0);
        Cell cellInCircle0Sector1 = cellsInCircleZero.get(1);
        HashMap<WallDirectionEnum, Cell> neighboursOfSector0Circle0 = cellInCircle0Sector0.getNeighbours();

        assertEquals(cellInCircle0Sector1, neighboursOfSector0Circle0.get(WallDirectionEnum.Right));
        assertEquals(cellInCircle0Sector1, neighboursOfSector0Circle0.get(WallDirectionEnum.Left));
        assertEquals(cellsInCircleOne.get(0), neighboursOfSector0Circle0.get(WallDirectionEnum.Down));
        Assert.assertNull(neighboursOfSector0Circle0.get(WallDirectionEnum.Up));


    }

    @Test
    public void shouldGenerateSevenCellsWhenNumberOfCirclesIsThreeAndNumberOfSectorsIsThree() {
        ThetaMazeLayoutGenerator thetaMazeLayoutGenerator = new ThetaMazeLayoutGenerator(3, 3);
        ThetaMaze thetaMaze = thetaMazeLayoutGenerator.generateLayout();
        List<List<Cell>> circleCellDictionary = thetaMaze.getCircleCellDictionary();
        List<Cell> cellsInCircleZero = circleCellDictionary.get(0);
        List<Cell> cellsInCircleOne = circleCellDictionary.get(1);
        List<Cell> cellsInCircleTwo = circleCellDictionary.get(2);

        Assert.assertEquals(3, cellsInCircleZero.size());
        Assert.assertEquals(3, cellsInCircleOne.size());
        Assert.assertEquals(1, cellsInCircleTwo.size());

        assertCellLayoutOfCircle0(cellsInCircleZero, cellsInCircleOne);
        assertCellLayoutOfCircle1(cellsInCircleOne,cellsInCircleZero,cellsInCircleTwo);



    }

    private void assertCellLayoutOfCircle0(List<Cell> cellsInCircle0, List<Cell> cellsInCircle1) {
        Cell sector0Cell = cellsInCircle0.get(0);
        Cell sector1Cell = cellsInCircle0.get(1);
        Cell sector2Cell = cellsInCircle0.get(2);

        HashMap<WallDirectionEnum, Cell> neighboursOfCircle0Sector0 = sector0Cell.getNeighbours();
        HashMap<WallDirectionEnum, Cell> neighboursOfCircle0Sector1 = sector1Cell.getNeighbours();
        HashMap<WallDirectionEnum, Cell> neighboursOfCircle0Sector2 = sector2Cell.getNeighbours();


        assertEquals(sector1Cell, neighboursOfCircle0Sector0.get(WallDirectionEnum.Right));
        assertEquals(sector2Cell, neighboursOfCircle0Sector1.get(WallDirectionEnum.Right));
        assertEquals(sector0Cell, neighboursOfCircle0Sector2.get(WallDirectionEnum.Right));

        assertEquals(sector2Cell, neighboursOfCircle0Sector0.get(WallDirectionEnum.Left));
        assertEquals(sector0Cell, neighboursOfCircle0Sector1.get(WallDirectionEnum.Left));
        assertEquals(sector1Cell, neighboursOfCircle0Sector2.get(WallDirectionEnum.Left));

        assertEquals(cellsInCircle1.get(0), neighboursOfCircle0Sector0.get(WallDirectionEnum.Down));
        assertEquals(cellsInCircle1.get(1), neighboursOfCircle0Sector1.get(WallDirectionEnum.Down));
        assertEquals(cellsInCircle1.get(2), neighboursOfCircle0Sector2.get(WallDirectionEnum.Down));
        Assert.assertNull(neighboursOfCircle0Sector0.get(WallDirectionEnum.Up));
        Assert.assertNull(neighboursOfCircle0Sector1.get(WallDirectionEnum.Up));
        Assert.assertNull(neighboursOfCircle0Sector2.get(WallDirectionEnum.Up));
    }

    private void assertCellLayoutOfCircle1(List<Cell> cellsInCircle1, List<Cell> cellsInCircle0, List<Cell> cellsInCircle2) {
        Cell sector0Cell = cellsInCircle1.get(0);
        Cell sector1Cell = cellsInCircle1.get(1);
        Cell sector2Cell = cellsInCircle1.get(2);

        HashMap<WallDirectionEnum, Cell> neighboursOfCircle1Sector0 = sector0Cell.getNeighbours();
        HashMap<WallDirectionEnum, Cell> neighboursOfCircle1Sector1 = sector1Cell.getNeighbours();
        HashMap<WallDirectionEnum, Cell> neighboursOfCircle1Sector2 = sector2Cell.getNeighbours();


        assertEquals(sector1Cell, neighboursOfCircle1Sector0.get(WallDirectionEnum.Right));
        assertEquals(sector2Cell, neighboursOfCircle1Sector1.get(WallDirectionEnum.Right));
        assertEquals(sector0Cell, neighboursOfCircle1Sector2.get(WallDirectionEnum.Right));

        assertEquals(sector2Cell, neighboursOfCircle1Sector0.get(WallDirectionEnum.Left));
        assertEquals(sector0Cell, neighboursOfCircle1Sector1.get(WallDirectionEnum.Left));
        assertEquals(sector1Cell, neighboursOfCircle1Sector2.get(WallDirectionEnum.Left));

        assertEquals(cellsInCircle2.get(0), neighboursOfCircle1Sector0.get(WallDirectionEnum.Down));

        assertEquals(cellsInCircle0.get(0),neighboursOfCircle1Sector0.get(WallDirectionEnum.Up));
        assertEquals(cellsInCircle0.get(1),neighboursOfCircle1Sector1.get(WallDirectionEnum.Up));
        assertEquals(cellsInCircle0.get(2),neighboursOfCircle1Sector2.get(WallDirectionEnum.Up));
    }


}

