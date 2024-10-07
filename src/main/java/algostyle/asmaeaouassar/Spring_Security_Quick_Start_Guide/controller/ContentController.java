package algostyle.asmaeaouassar.Spring_Security_Quick_Start_Guide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {

    @GetMapping("/home")
    public String handleHomePage(){
        return "home";
    }

    @GetMapping("/user/home")
    public String handleUserHomePage(){
        return "user_home";
    }

    @GetMapping("/admin/home")
    public String handlAdminHomePage(){
        return "admin_home";
    }

    @GetMapping("/login")
    public String handleCustomLoginPage(){
        return "custom_login_page";
    }
}
