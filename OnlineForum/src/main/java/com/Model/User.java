package com.Model;

import lombok.Data; // 引入 Lombok
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private String username;
    private String password; // 模拟登录用
    private String id;
    private int age;
}