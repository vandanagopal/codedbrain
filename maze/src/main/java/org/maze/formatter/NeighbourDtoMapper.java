package org.maze.formatter;

import org.maze.domain.Neighbour;

public class NeighbourDtoMapper {

    public static NeighbourDto map(Neighbour neighbour){
        return new NeighbourDto(neighbour.getDirection().toString(),neighbour.getCell().getCircleIndex(),neighbour.getCell().getSectorNumber());
    }
}
