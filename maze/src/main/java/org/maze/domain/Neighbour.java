package org.maze.domain;

public class Neighbour {
    
    private Cell cell;
    private WallDirectionEnum direction;

    public Neighbour(Cell cell, WallDirectionEnum direction) {
        this.cell = cell;
        this.direction = direction;
    }

    public Cell getCell() {
        return cell;
    }

    public WallDirectionEnum getDirection() {
        return direction;
    }
}
