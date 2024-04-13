package com.mysite.BeBeeKeepingGreen;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/home")
    public String home() {
        return "HomeScreen";
    }

    @GetMapping("/")
    public String root(){
        return "redirect:/home";
    }

//    @GetMapping("/RecordingArea.html")
//    public String record() {
//        return "RecordingArea";
//    }

    @GetMapping("/MapScreen.html")
    public String map() {
        return "MapScreen";
    }
}
