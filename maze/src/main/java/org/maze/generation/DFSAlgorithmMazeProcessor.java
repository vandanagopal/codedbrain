package org.maze.generation;

import org.hamcrest.Matchers;
import org.maze.domain.Cell;
import org.maze.domain.ThetaMaze;
import org.maze.domain.WallDirectionEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import static ch.lambdaj.Lambda.*;

public class DFSAlgorithmMazeProcessor {

    public void process(ThetaMaze thetaMaze) {
        Stack dfsStack = new Stack();
        Cell currentCell = thetaMaze.getInitialStartCell();
        while (true) {
            currentCell.setHasBeenVisited(true);
            dfsStack.push(currentCell);
            getUnvisitedRandomOpenNeighbourOfCurrentCell(currentCell);
        }

    }

    private Cell getUnvisitedRandomOpenNeighbourOfCurrentCell(Cell currentCell) {
        HashMap<WallDirectionEnum, Cell> currentCellNeighbours = currentCell.getNeighbours();
        List<HashMap<WallDirectionEnum,Cell>> unvisitedNeighbours = filter(having(on(Cell.class).getHasBeenVisited(), Matchers.is(false)), currentCellNeighbours);
        return null;

    }
}
