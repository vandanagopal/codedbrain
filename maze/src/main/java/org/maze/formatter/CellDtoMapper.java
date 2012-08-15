package org.maze.formatter;

import org.maze.domain.Cell;
import org.maze.domain.Neighbour;

import java.util.ArrayList;
import java.util.List;

public class CellDtoMapper {

    public static CellDto map(Cell cell) {
        List<Neighbour> openWallNeighbours = cell.getOpenWallNeighbours();
        List<NeighbourDto> openNeighbourDtos = new ArrayList<NeighbourDto>();
        for (Neighbour neighbour : openWallNeighbours)
           openNeighbourDtos.add(NeighbourDtoMapper.map(neighbour));

            return new CellDto(cell.getCircleIndex(), cell.getSectorNumber(), openNeighbourDtos);
    }

}
