package org.maze.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MazeController {

    @Autowired
    foof foofy;

    @RequestMapping(value="/hi",method = RequestMethod.GET)
    public String getPoo(){
        foofy.hey();
        return "hello";
    }
}
