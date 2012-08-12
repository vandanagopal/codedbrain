package org.maze.utils;

import org.maze.domain.Cell;
import org.maze.domain.Wall;
import org.maze.domain.WallDirectionEnum;
import org.maze.domain.WallStatusEnum;

public class WallUtils {
    
    public static void addWall(WallStatusEnum wallStatus,Cell cell1, Cell cell2, WallDirectionEnum directionRelativeToFirstCell){
        Wall wall = new Wall(wallStatus, cell1, cell2);
        cell1.addWall(directionRelativeToFirstCell,wall);
        cell2.addWall(directionRelativeToFirstCell.getOppositeDirection(),wall);
    }

}
