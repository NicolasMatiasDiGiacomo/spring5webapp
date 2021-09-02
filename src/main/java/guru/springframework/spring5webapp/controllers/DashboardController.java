package guru.springframework.spring5webapp.controllers;

import guru.springframework.spring5webapp.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    @Autowired
    JwtService jwtService;

    @GetMapping(value = {"/", "/dashboard"})
    public String getDashboard(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user",user);
        List<String> roleList = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        System.out.println("Token: " + jwtService.createToken(user.getUsername(), roleList));

        return "/home/dashboard";
    }
}
