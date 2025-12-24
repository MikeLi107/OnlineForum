package com.Controller;

import com.Model.Message;
import com.Model.User;
import com.Service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/forum")
public class ForumController {

    @Autowired
    private DataService dataService;

    // --- 以前的 checkLogin 方法删掉 ---

    @GetMapping("/list")
    public String list(Model model) {
        // --- 以前的 if (!checkLogin) 删掉 ---
        // 只要能进到这个方法，说明拦截器已经放行了，肯定是登录过的

        model.addAttribute("threads", dataService.getAllMainThreads());
        return "list";
    }

    @GetMapping("/post")
    public String postPage() {
        return "post";
    }

    @PostMapping("/post")
    public String doPost(@RequestParam String title,
                         @RequestParam String content,
                         HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        Message msg = new Message(dataService.getNextMessageId(), title, content, 0, user);
        dataService.addMessage(msg);
        return "redirect:/forum/list";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam int id, Model model) {
        Message mainMsg = dataService.getMessageById(id);
        if (mainMsg == null) return "redirect:/forum/list";

        model.addAttribute("mainMsg", mainMsg);
        model.addAttribute("replies", dataService.getReplies(id));
        return "detail";
    }

    @PostMapping("/reply")
    public String reply(@RequestParam int threadId,
                        @RequestParam String content,
                        HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        Message reply = new Message(dataService.getNextMessageId(),
                "RE: " + threadId,
                content,
                threadId,
                user);
        dataService.addMessage(reply);
        return "redirect:/forum/detail?id=" + threadId;
    }
}