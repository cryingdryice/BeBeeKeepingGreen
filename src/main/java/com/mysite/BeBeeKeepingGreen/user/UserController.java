package user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/register")
    public String register(UserCreateForm userCreateForm){
        return "RegisterForm";
    }

    @PostMapping("/register")
    public String register(@Valid UserCreateForm userCreateForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "RegisterForm";
        }
        // 비밀번호가 비밀번호 확인과 같지않다면 다시 시도
        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())){
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");

            return "RegisterForm";
        }

        // 올바르게 입력했다면 생성
        userService.create(userCreateForm.getUsername(), userCreateForm.getLocation(), userCreateForm.getPassword1());
        return "redirect:/";
    }
}
