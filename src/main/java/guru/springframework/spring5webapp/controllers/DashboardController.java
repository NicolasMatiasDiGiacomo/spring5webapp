package guru.springframework.spring5webapp.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping(value = {"/", "/dashboard"})
    public String getDashboard(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user",user);

        return "/home/dashboard";
    }
}
