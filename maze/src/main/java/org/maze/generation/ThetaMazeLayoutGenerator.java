package org.maze.generation;

import org.maze.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThetaMazeLayoutGenerator {

    private int numberOfConcentricCircles;
    private int numberOfSectors;

    public ThetaMazeLayoutGenerator(int numberOfConcentricCircles, int numberOfSectors) {

        this.numberOfConcentricCircles = numberOfConcentricCircles;
        this.numberOfSectors = numberOfSectors;
    }


    public ThetaMaze generateLayout() {
        List<List<Cell>> circleDictionary = new ArrayList<List<Cell>>();
        for (int circleNumber = 0; circleNumber < numberOfConcentricCircles - 1; circleNumber++) {
            List<Cell> cellsInTheOuterCircle = circleNumber == 0 ? null : circleDictionary.get(circleNumber - 1);
            List<Cell> cellsInACircle = populateCellsInACircle(circleNumber, cellsInTheOuterCircle);
            circleDictionary.add(cellsInACircle);

        }
        List<Cell> cellsInTheLastButOneCircle = circleDictionary.get(numberOfConcentricCircles - 2);
        Cell cellInTheLastCircle = new Cell(numberOfConcentricCircles - 1, 0);
        circleDictionary.add(Arrays.asList(cellInTheLastCircle));
        for (Cell cell : cellsInTheLastButOneCircle) {
            cell.addWall(WallDirectionEnum.Down, new Wall(WallStatusEnum.Closed, cell, cellInTheLastCircle));
        }

        return new ThetaMaze(circleDictionary);


    }


    private List<Cell> populateCellsInACircle(int circleNumber, List<Cell> cellsInTheOuterCircle) {
        ArrayList<Cell> cellsInACircle = new ArrayList<Cell>();
        for (int sectorNumber = 0; sectorNumber < numberOfSectors; sectorNumber++) {
            cellsInACircle.add(new Cell(circleNumber, sectorNumber));
        }
        for (int sectorNumber = 0; sectorNumber < numberOfSectors; sectorNumber++) {
            createWallBetweenLeftAndRightCells(cellsInACircle, sectorNumber);
            if (cellsInTheOuterCircle != null)
                createWallBetweenAboveAndBelowCells(cellsInTheOuterCircle, cellsInACircle, sectorNumber);

        }
        return cellsInACircle;


    }

    private void createWallBetweenAboveAndBelowCells(List<Cell> cellsInTheOuterCircle, ArrayList<Cell> cellsInACircle, int sectorNumber) {
        Cell cellBelow = cellsInACircle.get(sectorNumber);
        Cell cellAbove = cellsInTheOuterCircle.get(sectorNumber);
        Wall wall = new Wall(WallStatusEnum.Closed, cellBelow, cellAbove);
        cellBelow.addWall(WallDirectionEnum.Up, wall);
        cellAbove.addWall(WallDirectionEnum.Down, wall);
    }

    private void createWallBetweenLeftAndRightCells(ArrayList<Cell> cellsInACircle, int sectorNumber) {
        Cell rightCell = cellsInACircle.get(sectorNumber);
        Cell leftCell = cellsInACircle.get((sectorNumber + (numberOfSectors - 1)) % numberOfSectors);
        Wall wall = new Wall(WallStatusEnum.Closed, rightCell, leftCell);
        rightCell.addWall(WallDirectionEnum.Left, wall);
        leftCell.addWall(WallDirectionEnum.Right, wall);
    }
}