package com.mysite.BeBeeKeepingGreen.map;

import com.mysite.BeBeeKeepingGreen.record.HiveRecord;
import com.mysite.BeBeeKeepingGreen.record.RecordForm;
import com.mysite.BeBeeKeepingGreen.user.SiteUser;
import com.mysite.BeBeeKeepingGreen.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/map")
public class MapController {
    private final MapService mapService;
    @GetMapping("")
    public String map(){
        return "redirect:/map/show";
    }

    @PreAuthorize("isAuthenticated()") // 로그인해야만 접속가능
    @GetMapping("/show")
    public String show(Model model, Principal principal){
        List<BeePlant> beePlantList = this.mapService.getConfirmedList();
        model.addAttribute("plant_list", beePlantList);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            // 현재 사용자가 admin 권한을 가지고 있다면 특정한 페이지를 보여줍니다.
            model.addAttribute("isAdmin", 1);

            // 어드민이면 신청리스트를 보여줌
            List<BeePlant> waitingList = this.mapService.getWaitingList();
            model.addAttribute("waiting_list", waitingList);
        }

        return "MapScreen";
    }

    @PreAuthorize("isAuthenticated()") // 로그인해야만 접속가능
    @PostMapping("/show")
    public String requirePlant(@Valid PlantForm plantForm, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            return "MapScreen";
        }
        this.mapService.require(plantForm.getPlantLocation());

        return "redirect:/map/show";
    }

    @PreAuthorize("isAuthenticated()") // 로그인해야만 접속가능
    @PostMapping("/show/ack")
    public String ackPlant(@Valid PlantForm plantForm, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            return "MapScreen";
        }
        BeePlant beePlant = this.mapService.getPlant(plantForm.getPlantLocation());
        if(beePlant==null) System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n\naaaaaaaa\n\n");
        else System.out.println(beePlant.getPlantLocation());

        this.mapService.ackPlant(beePlant);

        return "redirect:/map/show";
    }
}
