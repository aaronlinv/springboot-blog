package com.al.blog.web.admin;

import com.al.blog.po.User;
import com.al.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;


    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if (user != null) {
            // 清除密码
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        }else{
            // 不要直接return "/admin" 这样再次提交会有问题
            attributes.addFlashAttribute("message","用户名或密码错误");
            // 如果放到Model中 重定向拿不到数据
            return "redirect:/admin";
        }
        
    }
    // 注销 清空session
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
