package org.maze.formatter;

public class NeighbourDto {


    private String direction;
    private int circleIndex;
    private int sectorNumber;

    public NeighbourDto(String direction, int circleIndex, int sectorNumber) {

        this.direction = direction;
        this.circleIndex = circleIndex;
        this.sectorNumber = sectorNumber;
    }

    public String getDirection() {
        return direction;
    }

    public int getCircleIndex() {
        return circleIndex;
    }

    public int getSectorNumber() {
        return sectorNumber;
    }
}
