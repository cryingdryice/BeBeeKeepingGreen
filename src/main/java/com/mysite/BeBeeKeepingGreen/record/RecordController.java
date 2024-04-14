package com.mysite.BeBeeKeepingGreen.record;

import com.mysite.BeBeeKeepingGreen.user.SiteUser;
import com.mysite.BeBeeKeepingGreen.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/record")
public class RecordController {
    private final RecordService recordService;
    private final UserService userService;

    @GetMapping("")
    public String record(){
        return "redirect:/record/list";
    }

    @PreAuthorize("isAuthenticated()") // 로그인해야만 접속가능
    @GetMapping("/list")
    public String list(Model model, RecordForm recordForm){
        List<HiveRecord> hiveRecordList = this.recordService.getList();
        model.addAttribute("hiveRecordList", hiveRecordList);

        return "RecordingArea";
    }

    @PreAuthorize("isAuthenticated()") // 로그인해야만 접속가능
    @PostMapping("/list")
    public String recordCreate(@Valid RecordForm recordForm, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            return "RecordingArea";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.recordService.create(recordForm.getContent(), siteUser);
        return "redirect:/record/list";
    }
}
