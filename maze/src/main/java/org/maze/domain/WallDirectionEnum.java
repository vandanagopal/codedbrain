package org.maze.domain;

import java.util.ArrayList;
import java.util.List;

public enum WallDirectionEnum {
    Up(1),
    Down(-1),
    Left(2),
    Right(-2);
    private int directionKey;

    WallDirectionEnum(int directionKey) {

        this.directionKey = directionKey;
    }

    public WallDirectionEnum getOppositeDirection() {
        for (WallDirectionEnum wallDirection : values())
            if (wallDirection.directionKey == directionKey*-1)
                return wallDirection;
        return null;

    }


    public static List<WallDirectionEnum> getValues() {
        List<WallDirectionEnum> wallDirectionEnumList = new ArrayList<WallDirectionEnum>();
        for (WallDirectionEnum wallDirection : values())
            wallDirectionEnumList.add(wallDirection);
        return wallDirectionEnumList;
    }


}
