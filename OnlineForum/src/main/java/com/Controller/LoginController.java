package com.Controller;

import com.Model.User;
import com.Service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Controller
public class LoginController {

    @Autowired
    private DataService dataService;

    // 1. 专门处理 GET 请求，展示登录页
    // 这样访问 http://localhost:8080/login 就不会报 405 了
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // 原有的首页也跳转到 login
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    // 生成验证码图片
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response, HttpSession session) throws IOException {
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        // 背景
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, width, height);

        // 生成随机数 (0-9的4位数字)
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));

        for (int i = 0; i < 4; i++) {
            int num = r.nextInt(10);
            sb.append(num);
            g.drawString(String.valueOf(num), 10 + (i * 15), 22);
        }

        // 保存验证码到Session用于校验
        session.setAttribute("captcha_key", sb.toString());

        // 设置响应头
        response.setContentType("image/jpeg");
        // 不缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 输出图片
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String captcha,
                        HttpSession session,
                        Model model) {

        // 1. 校验验证码
        String sessionCaptcha = (String) session.getAttribute("captcha_key");
        if (sessionCaptcha == null || !sessionCaptcha.equals(captcha)) {
            model.addAttribute("error", "验证码错误");
            return "login";
        }

        // 2. 校验用户
        User user = dataService.login(username, password);
        if (user != null) {
            session.setAttribute("currentUser", user);
            return "redirect:/forum/list"; // 登录成功，重定向到列表页
        } else {
            model.addAttribute("error", "用户名或密码错误");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}