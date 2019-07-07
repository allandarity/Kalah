package uk.co.erlski.kalah.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("games")
public class GameController {

    @RequestMapping("test")
    @ResponseBody
    public String index() {
        return "hello";
    }



}
