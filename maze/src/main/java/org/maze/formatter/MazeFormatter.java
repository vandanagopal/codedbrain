package org.maze.formatter;

import org.codehaus.jackson.map.ObjectMapper;
import org.maze.domain.Cell;
import org.maze.domain.ThetaMaze;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class MazeFormatter {
    

    public static String convertToJson(ThetaMaze maze){
        List<CellDto> cellDtos = new ArrayList<CellDto>();

        for(List<Cell> cellsInACircle: maze.getCircleCellDictionary()){
            for(Cell cell : cellsInACircle)
                cellDtos.add(CellDtoMapper.map(cell));
                
        }
        return toJson(cellDtos);

    }

    private static String toJson(Object objectToSerialize) {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();
        try {
            mapper.writeValue(stringWriter, objectToSerialize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }
}
