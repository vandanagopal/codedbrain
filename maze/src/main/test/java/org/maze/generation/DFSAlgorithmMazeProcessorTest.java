package org.maze.generation;

import org.junit.Test;
import org.maze.domain.Cell;
import org.maze.domain.ThetaMaze;

import java.util.List;

public class DFSAlgorithmMazeProcessorTest {

    @Test
    public void shouldCreateAPassageBetweenAThreeCellMaze(){
        ThetaMaze thetaMaze = new ThetaMazeLayoutGenerator(2, 2).generateLayout();
        thetaMaze.setInitialStartCell(thetaMaze.getCircleCellDictionary().get(0).get(0));
        new DFSAlgorithmMazeProcessor().process(thetaMaze);
        List<List<Cell>> circleCellDictionary = thetaMaze.getCircleCellDictionary();
//        Cell initialStartCell = thetaMaze.getInitialStartCell();
//        HashMap<WallDirectionEnum,Cell> openNeighbours = initialStartCell.getOpenNeighbours();
//        if(initialStartCell.getSectorNumber()==0)
//            assertNotNull(openNeighbours.get(WallDirectionEnum.Right));
//        else
//            assertNotNull(openNeighbours.get(WallDirectionEnum.Left));
//        List<Cell> cellsInTheOutermostCircle = circleCellDictionary.get(0);
//        List<Cell> cellsWithPassageToTheInnermostCell = filter(having(on(Cell.class).getWallPassageMap().get(WallDirectionEnum.Down), Matchers.notNullValue()), cellsInTheOutermostCircle);
//        assertEquals(1,cellsWithPassageToTheInnermostCell);
//

    }

}
