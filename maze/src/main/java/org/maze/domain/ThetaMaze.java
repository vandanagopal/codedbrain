package org.maze.domain;

import java.util.List;

public class ThetaMaze {

    private List<List<Cell>> circleCellDictionary;
    private Cell initialStartCell;

    public ThetaMaze(List<List<Cell>> circleCellDictionary) {
        this.circleCellDictionary = circleCellDictionary;
    }

    public List<List<Cell>> getCircleCellDictionary() {
        return circleCellDictionary;
    }

    public void setInitialStartCell(Cell initialStartCell) {

        this.initialStartCell = initialStartCell;
    }

    public Cell getInitialStartCell() {
        return initialStartCell;
    }

}
