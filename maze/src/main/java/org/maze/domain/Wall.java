package org.maze.domain;

public class Wall {
    
    private WallStatusEnum wallStatus;
    private Cell cell1;
    private Cell cell2;

    public Wall(WallStatusEnum wallStatus,Cell cell1,Cell cell2) {
        this.wallStatus = wallStatus;
        this.cell1 = cell1;
        this.cell2 = cell2;
    }

    public WallStatusEnum getWallStatus() {
        return wallStatus;
    }

    public Cell getCell1() {
        return cell1;
    }

    public Cell getCell2() {
        return cell2;
    }
    
    public Cell getOtherCell(Cell cell){
        if(cell==cell1)
            return cell2;
        return cell == cell2 ? cell1 : null;

    }
}
