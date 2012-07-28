package org.maze.domain;

import java.util.ArrayList;
import java.util.List;

public enum WallDirectionEnum {
    Up,
    Down,
    Left,
    Right;

    public static List<WallDirectionEnum> getValues() {
        List<WallDirectionEnum> wallDirectionEnumList = new ArrayList<WallDirectionEnum>();
        for (WallDirectionEnum wallDirection : values())
            wallDirectionEnumList.add(wallDirection);
        return wallDirectionEnumList;
    }


    }
