package org.maze.generation;

import org.junit.Assert;
import org.junit.Test;
import org.maze.domain.Cell;
import org.maze.domain.ThetaMaze;
import org.maze.domain.WallDirectionEnum;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class ThetaMazeLayoutGeneratorTest {

    private final int numberOfConcentricCircles = 3;
    private final int numberOfSectors = 8;

    @Test
    public void shouldGenerateThreeCellsWhenNumberOfCirclesIsTwoAndNumberOfSectorsIsTwo() {
        ThetaMazeLayoutGenerator thetaLayoutGenerator = new ThetaMazeLayoutGenerator(2, 2);
        ThetaMaze thetaMaze = thetaLayoutGenerator.generateLayout();
        List<List<Cell>> circleCellDictionary = thetaMaze.getCircleCellDictionary();
        List<Cell> cellsInCircleZero = circleCellDictionary.get(0);
        List<Cell> cellsInCircleOne = circleCellDictionary.get(1);

        Assert.assertEquals(2, cellsInCircleZero.size());
        Assert.assertEquals(1, cellsInCircleOne.size());


        Cell cellInCircle0Sector0 = cellsInCircleZero.get(0);
        Cell cellInCircle0Sector1 = cellsInCircleZero.get(1);

        assertEquals(cellInCircle0Sector1, cellInCircle0Sector0.getNeighbour(WallDirectionEnum.Right));
        assertEquals(cellInCircle0Sector1, cellInCircle0Sector0.getNeighbour(WallDirectionEnum.Left));
        assertEquals(cellsInCircleOne.get(0), cellInCircle0Sector0.getNeighbour(WallDirectionEnum.Down));
        Assert.assertNull(cellInCircle0Sector0.getNeighbour(WallDirectionEnum.Up));


    }

    @Test
    public void shouldGenerateSevenCellsWhenNumberOfCirclesIsThreeAndNumberOfSectorsIsThree() {
        ThetaMazeLayoutGenerator thetaLayoutGenerator = new ThetaMazeLayoutGenerator(3, 3);
        ThetaMaze thetaMaze = thetaLayoutGenerator.generateLayout();
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



        assertEquals(sector1Cell, sector0Cell.getNeighbour(WallDirectionEnum.Right));
        assertEquals(sector2Cell, sector1Cell.getNeighbour(WallDirectionEnum.Right));
        assertEquals(sector0Cell, sector2Cell.getNeighbour(WallDirectionEnum.Right));

        assertEquals(sector2Cell, sector0Cell.getNeighbour(WallDirectionEnum.Left));
        assertEquals(sector0Cell, sector1Cell.getNeighbour(WallDirectionEnum.Left));
        assertEquals(sector1Cell, sector2Cell.getNeighbour(WallDirectionEnum.Left));

        assertEquals(cellsInCircle1.get(0), sector0Cell.getNeighbour(WallDirectionEnum.Down));
        assertEquals(cellsInCircle1.get(1), sector1Cell.getNeighbour(WallDirectionEnum.Down));
        assertEquals(cellsInCircle1.get(2), sector2Cell.getNeighbour(WallDirectionEnum.Down));
        Assert.assertNull(sector0Cell.getNeighbour(WallDirectionEnum.Up));
        Assert.assertNull(sector1Cell.getNeighbour(WallDirectionEnum.Up));
        Assert.assertNull(sector2Cell.getNeighbour(WallDirectionEnum.Up));
    }

    private void assertCellLayoutOfCircle1(List<Cell> cellsInCircle1, List<Cell> cellsInCircle0, List<Cell> cellsInCircle2) {
        Cell sector0Cell = cellsInCircle1.get(0);
        Cell sector1Cell = cellsInCircle1.get(1);
        Cell sector2Cell = cellsInCircle1.get(2);



        assertEquals(sector1Cell, sector0Cell.getNeighbour(WallDirectionEnum.Right));
        assertEquals(sector2Cell, sector1Cell.getNeighbour(WallDirectionEnum.Right));
        assertEquals(sector0Cell, sector2Cell.getNeighbour(WallDirectionEnum.Right));

        assertEquals(sector2Cell, sector0Cell.getNeighbour(WallDirectionEnum.Left));
        assertEquals(sector0Cell, sector1Cell.getNeighbour(WallDirectionEnum.Left));
        assertEquals(sector1Cell, sector2Cell.getNeighbour(WallDirectionEnum.Left));

        assertEquals(cellsInCircle2.get(0), sector0Cell.getNeighbour(WallDirectionEnum.Down));

        assertEquals(cellsInCircle0.get(0),sector0Cell.getNeighbour(WallDirectionEnum.Up));
        assertEquals(cellsInCircle0.get(1),sector1Cell.getNeighbour(WallDirectionEnum.Up));
        assertEquals(cellsInCircle0.get(2),sector2Cell.getNeighbour(WallDirectionEnum.Up));
    }


}

