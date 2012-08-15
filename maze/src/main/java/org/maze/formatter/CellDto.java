package org.maze.formatter;

import java.util.List;

public class CellDto {
    private int circleIndex;
    private int sectorNumber;
    private List<NeighbourDto> openNeighbourDtos;

    public CellDto(int circleIndex, int sectorNumber, List<NeighbourDto> openNeighbourDtos) {

        this.circleIndex = circleIndex;
        this.sectorNumber = sectorNumber;
        this.openNeighbourDtos = openNeighbourDtos;
    }

    public int getCircleIndex() {
        return circleIndex;
    }

    public int getSectorNumber() {
        return sectorNumber;
    }

    public List<NeighbourDto> getOpenNeighbourDtos() {
        return openNeighbourDtos;
    }
}
