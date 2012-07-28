package org.maze.generation;

import ch.lambdaj.function.matcher.Predicate;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.maze.domain.Cell;
import org.maze.domain.ThetaMaze;
import org.maze.domain.WallDirectionEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ch.lambdaj.Lambda.*;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class DFSAlgorithmMazeProcessorTest {

    @Test
    public void shouldCreateAPassageBetweenAThreeCellMaze(){
        ThetaMaze thetaMaze = new ThetaMazeLayoutGenerator(2, 2).generateLayout();
        thetaMaze.setInitialStartCell(thetaMaze.getCircleCellDictionary().get(0).get(0));
        new DFSAlgorithmMazeProcessor().process(thetaMaze);
        List<List<Cell>> circleCellDictionary = thetaMaze.getCircleCellDictionary();
        Cell initialStartCell = thetaMaze.getInitialStartCell();
        HashMap<WallDirectionEnum,Cell> openNeighbours = initialStartCell.getOpenNeighbours();
        if(initialStartCell.getSectorNumber()==0)
            assertNotNull(openNeighbours.get(WallDirectionEnum.Right));
        else
            assertNotNull(openNeighbours.get(WallDirectionEnum.Left));
        List<Cell> cellsInTheOutermostCircle = circleCellDictionary.get(0);
        List<Cell> cellsWithPassageToTheInnermostCell = filter(having(on(Cell.class).getWallPassageMap().get(WallDirectionEnum.Down), Matchers.notNullValue()), cellsInTheOutermostCircle);
        assertEquals(1,cellsWithPassageToTheInnermostCell);


    }
    
    @Test
    public void foo(){
        HashMap<WallDirectionEnum, Cell> map = new HashMap<WallDirectionEnum, Cell>();
        Cell cell = new Cell(2, 2);
        cell.setHasBeenVisited(true);
        map.put(WallDirectionEnum.Down, cell);
        map.put(WallDirectionEnum.Up, new Cell(1,1));
        map.put(WallDirectionEnum.Left, new Cell(0,0));
        
        for(WallDirectionEnum wallEnum : map.keySet()){

        }

        Matcher<Map.Entry<WallDirectionEnum,Cell>> odd = new Predicate<Map.Entry<WallDirectionEnum,Cell>>() {


            @Override
            public boolean apply(Map.Entry<WallDirectionEnum, Cell> wallDirectionEnumCellHashMap) {

                return !wallDirectionEnumCellHashMap.getValue().getHasBeenVisited();
            }
        };

        List<Map.Entry<WallDirectionEnum,Cell>> filter = filter(odd, map.entrySet());
//        assertTrue(unvisitedNeighbours.size()>0);


    }
}
