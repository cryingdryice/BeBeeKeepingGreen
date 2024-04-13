package com.mysite.BeBeeKeepingGreen.record;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/record")
public class RecordController {
    private final RecordService recordService;

    @GetMapping("")
    public String record(){
        return "redirect:/record/list";
    }

    @GetMapping("/list")
    public String list(Model model, RecordForm recordForm){
        List<HiveRecord> hiveRecordList = this.recordService.getList();
        model.addAttribute("hiveRecordList", hiveRecordList);

        return "RecordingArea";
    }

    @PostMapping("/list")
    public String recordCreate(@Valid RecordForm recordForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "RecordingArea";
        }
        this.recordService.create(recordForm.getContent());
        return "redirect:/record/list";
    }
}
