package org.maze.generation;

import ch.lambdaj.function.matcher.HasArgumentWithValue;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.maze.domain.Cell;
import org.maze.domain.ThetaMaze;
import org.maze.domain.Wall;
import org.maze.domain.WallDirectionEnum;

import java.util.List;

import static ch.lambdaj.Lambda.*;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MazeGeneratorTest {

    private MazeGenerator mazeGenerator;

    @Before
    public void setUp() {
        mazeGenerator = new MazeGenerator();

    }

    @Test
    public void shouldRandomlySetAnInitialCellForTheMaze(){
        ThetaMaze maze = new ThetaMazeLayoutGenerator(2, 2).generateLayout();
        mazeGenerator.generateMaze(maze);
        Cell initialStartCell = maze.getInitialStartCell();
        assertNotNull(initialStartCell);
        assertEquals(0,initialStartCell.getCircleIndex());


    }

    @Test
    public void shouldCreateAPathFromOutermostToInnermostCircleForAMazeWithTwoCirclesAndTwoSectors(){
        ThetaMaze maze = new ThetaMazeLayoutGenerator(2, 2).generateLayout();
        mazeGenerator.generateMaze(maze);
        List<List<Cell>> circleCellDictionary = maze.getCircleCellDictionary();
        List<Cell> cellsInTheOuterMostCircle = circleCellDictionary.get(0);
        Assert.assertEquals(2, cellsInTheOuterMostCircle.size());
        List<Cell> outerCellListHavingPassageToInnerCircle = filter(getCellsHavingAPassageToInnerCircle(), cellsInTheOuterMostCircle);
        assertEquals(1,outerCellListHavingPassageToInnerCircle.size());


    }

    private HasArgumentWithValue<Object, Wall> getCellsHavingAPassageToInnerCircle() {
        return having(on(Cell.class).getWallPassageMap().get(WallDirectionEnum.Down), Matchers.equalTo(1));
    }

//    @Test
//    public void shouldCreateASinglePathFromTheCenterOfTheMazeToTheExit() {
//        int numberOfConcentricCircles = 4;
//        ThetaMazeLayoutGenerator layoutGenerator = new ThetaMazeLayoutGenerator(numberOfConcentricCircles, 4);
//        ThetaMaze maze = layoutGenerator.generateLayout();
//        braidAlgorithmMazeGenerator.generateMaze(maze);
//        List<List<Cell>> circleCellDictionary = maze.getCircleCellDictionary();
//        List<Cell> visitedCells = new ArrayList<Cell>();
//        Cell cellInTheLastCircle = circleCellDictionary.get(circleCellDictionary.size() - 1).get(0);
//        Cell currentCell = cellInTheLastCircle;
//        while (true) {
//            HashMap<WallDirectionEnum, Cell> openNeighbours = currentCell.getOpenNeighbours();
//            if (hasOuterMostCircleBeenReached(numberOfConcentricCircles, currentCell))
//                return;
//            for (Cell cell : openNeighbours.values()) {
//                if (!visitedCells.contains(cell)) {
//                    currentCell = cell;
//                    visitedCells.add(currentCell);
//                    break;
//                }
//            }
//        }
//
//
//    }
//
//    private boolean hasOuterMostCircleBeenReached(int numberOfConcentricCircles, Cell currentCell) {
//        return currentCell.getCircleIndex() == numberOfConcentricCircles - 1;
//    }
}
