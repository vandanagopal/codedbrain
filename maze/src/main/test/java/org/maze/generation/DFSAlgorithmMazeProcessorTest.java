package org.maze.generation;

import org.junit.Before;
import org.junit.Test;
import org.maze.domain.Cell;
import org.maze.domain.Neighbour;
import org.maze.domain.ThetaMaze;
import org.maze.domain.WallDirectionEnum;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class DFSAlgorithmMazeProcessorTest {

    private DFSAlgorithmMazeProcessor dfsAlgorithmMazeProcessor;

    @Before
    public void setUp() throws Exception {
        dfsAlgorithmMazeProcessor = new DFSAlgorithmMazeProcessor();
    }

    @Test
    public void shouldCreateAMazeBetweenThreeCells(){
        ThetaMaze thetaMaze = new ThetaMazeLayoutGenerator(2, 2).generateLayout();
        List<Cell> outerCircleCells = thetaMaze.getCircleCellDictionary().get(0);
        Cell innermostCell = thetaMaze.getCircleCellDictionary().get(1).get(0);
        Cell circle0Sector0Cell = outerCircleCells.get(0);
        Cell circle0Sector1Cell = outerCircleCells.get(1);
        List<Cell> neighbourCellsOfCellCircle0Sector0=new ArrayList<Cell>();
        for(Neighbour neighbour : circle0Sector0Cell.getOpenWallNeighbours())
            neighbourCellsOfCellCircle0Sector0.add(neighbour.getCell());
        assertTrue(neighbourCellsOfCellCircle0Sector0.contains(circle0Sector1Cell));
        assertTrue(circle0Sector0Cell.getNeighbour(WallDirectionEnum.Down).equals(innermostCell) ||
                circle0Sector1Cell.getNeighbour(WallDirectionEnum.Down).equals(innermostCell));

    }



}
