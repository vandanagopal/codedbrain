package org.maze.generation;

import org.maze.domain.*;
import org.maze.utils.Random;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class DFSAlgorithmMazeProcessor {

    public void process(ThetaMaze thetaMaze) {
        Stack dfsStack = new Stack();
        Cell currentCell = getInitialCell(thetaMaze);
        currentCell.setHasBeenVisited(true);
        dfsStack.push(currentCell);
        while (true) {

            Neighbour unvisitedNeighbour = getUnvisitedNeighbourOfCurrentCell(currentCell);
            if (unvisitedNeighbour == null)
                try {
                    currentCell = (Cell) dfsStack.pop();
                } catch (EmptyStackException e) {
                    break;
                }
            else {
                openTheWall(currentCell, unvisitedNeighbour);

                currentCell = unvisitedNeighbour.getCell();
                dfsStack.push(currentCell);
            }

        }

    }

    private void openTheWall(Cell currentCell, Neighbour unvisitedNeighbour) {
        Cell neighbourCell = unvisitedNeighbour.getCell();
        Wall wall = new Wall(WallStatusEnum.Open, currentCell, neighbourCell);
        WallDirectionEnum directionRelativeToCurrentCell = unvisitedNeighbour.getDirection();
        currentCell.addWall(directionRelativeToCurrentCell, wall);
        neighbourCell.addWall(directionRelativeToCurrentCell.getOppositeDirection(), wall);
    }


    private Cell getInitialCell(ThetaMaze thetaMaze) {
        List<List<Cell>> circleCellDictionary = thetaMaze.getCircleCellDictionary();
        int numberOfCircles = circleCellDictionary.size();
        int randomCircleNumber = Random.getRandomNumber(numberOfCircles - 1);
        int numberOfSectors = circleCellDictionary.get(0).size();
        int randomSectorNumber = Random.getRandomNumber(numberOfSectors);
        return circleCellDictionary.get(randomCircleNumber).get(randomSectorNumber);

    }

    private Neighbour getUnvisitedNeighbourOfCurrentCell(Cell currentCell) {
        List<Neighbour> unvisitedNeighbours = currentCell.getUnvisitedNeighbours();
        if (unvisitedNeighbours.size() == 0) return null;
        int randomIndex = Random.getRandomNumber(unvisitedNeighbours.size());

        return unvisitedNeighbours.get(randomIndex);

    }
}
