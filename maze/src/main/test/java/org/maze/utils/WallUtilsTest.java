package org.maze.utils;

import org.junit.Test;
import org.maze.domain.WallDirectionEnum;

import static junit.framework.Assert.assertEquals;

public class WallUtilsTest {

    @Test
    public void shouldGetOppositeDirection(){
        assertEquals(WallDirectionEnum.Down,WallDirectionEnum.Up.getOppositeDirection());
        assertEquals(WallDirectionEnum.Left,WallDirectionEnum.Right.getOppositeDirection());
        assertEquals(WallDirectionEnum.Up,WallDirectionEnum.Down.getOppositeDirection());
        assertEquals(WallDirectionEnum.Right,WallDirectionEnum.Left.getOppositeDirection());
    }
}
