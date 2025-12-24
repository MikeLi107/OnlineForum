package com.Service;

import com.Model.*;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service // 这是一个Spring管理的Bean，默认单例，相当于Application Scope
public class DataService {

    // 线程安全的List，模拟数据库
    private final List<User> users = new CopyOnWriteArrayList<>();
    private final List<Message> messages = new CopyOnWriteArrayList<>();
    private final AtomicInteger messageIdGenerator = new AtomicInteger(1);

    public DataService() {
        // 这里的顺序要和 User 类里的字段定义顺序完全一致！
        // 假设 User 类里第一个字段是 username，第二个是 password
        users.add(new User("admin", "123", "1001", 30));
        users.add(new User("student", "123", "1002", 20));
    }

    public User login(String username, String password) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public void addMessage(Message msg) {
        messages.add(msg);
    }

    public int getNextMessageId() {
        return messageIdGenerator.getAndIncrement();
    }

    // 获取所有主贴 (replyId 为 null 或 0)
    public List<Message> getAllMainThreads() {
        return messages.stream()
                .filter(m -> m.getReplyId() == null || m.getReplyId() == 0)
                .collect(Collectors.toList());
    }

    // 获取某个主贴的详情
    public Message getMessageById(int id) {
        return messages.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
    }

    // 获取某个主贴的所有回复
    public List<Message> getReplies(int mainThreadId) {
        return messages.stream()
                .filter(m -> m.getReplyId() != null && m.getReplyId() == mainThreadId)
                .collect(Collectors.toList());
    }
}