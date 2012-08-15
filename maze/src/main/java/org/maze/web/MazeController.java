package org.maze.web;

import org.maze.domain.ThetaMaze;
import org.maze.formatter.MazeFormatter;
import org.maze.generation.DFSAlgorithmMazeProcessor;
import org.maze.generation.ThetaMazeLayoutGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class MazeController {

    @RequestMapping(value="/maze",method = RequestMethod.GET)
    public void getMaze(HttpServletResponse httpServletResponse) throws IOException {
        ThetaMaze thetaMaze = new ThetaMazeLayoutGenerator(4, 6).generateLayout();
        new DFSAlgorithmMazeProcessor().process(thetaMaze);
        httpServletResponse.getOutputStream().print(MazeFormatter.convertToJson(thetaMaze));

    }
}
