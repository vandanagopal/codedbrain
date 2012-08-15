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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Neighbour neighbour = (Neighbour) o;

        if (cell != null ? !cell.equals(neighbour.cell) : neighbour.cell != null) return false;
        if (!direction.equals(neighbour.direction)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cell != null ? cell.hashCode() : 0;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }
}
