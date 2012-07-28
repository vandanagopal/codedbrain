package org.maze.generation;

import org.maze.domain.Cell;
import org.maze.domain.ThetaMaze;
import org.maze.utils.Random;

import java.util.List;

public class MazeGenerator {

    public void generateMaze(ThetaMaze maze) {

        Cell initialStartCell = getInitialStartCell(maze);
        maze.setInitialStartCell(initialStartCell);
        applyModifiedAlgorithm(maze);
        assignStartAndFinishPoints();
    }

    private void assignStartAndFinishPoints() {

    }

    private void applyModifiedAlgorithm(ThetaMaze maze) {

    }

    private Cell getInitialStartCell(ThetaMaze maze) {
        List<List<Cell>> circleCellDictionary = maze.getCircleCellDictionary();
        int numberOfCircles = circleCellDictionary.size();
        int randomCircleNumber = Random.getRandomNumber(numberOfCircles - 1);
        int numberOfSectors = circleCellDictionary.get(0).size();
        int randomSectorNumber = Random.getRandomNumber(numberOfSectors);

        return circleCellDictionary.get(randomCircleNumber).get(randomSectorNumber);

    }


}
