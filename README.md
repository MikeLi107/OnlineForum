# 📝 Online Forum System (Spring Boot Refactor Version)

> 一个基于 Spring Boot 3.x 重构的简易在线问答/论坛平台。
> A simple online forum platform refactored with Spring Boot 3.x.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.x-green)
![Thymeleaf](https://img.shields.io/badge/Template-Thymeleaf-blue)
![License](https://img.shields.io/badge/license-MIT-grey)

## 📖 项目简介 (Introduction)

本项目是基于传统 JavaWeb (Servlet + JSP) 项目的**Spring Boot 重构版本**。
项目旨在演示如何从传统的开发模式迁移到现代的 **IOC (控制反转)** 和 **MVC** 架构。

**核心特点：**

*   **零数据库依赖**：使用内存结构 (`CopyOnWriteArrayList`) 模拟持久化存储，无需配置 MySQL。
*   **纯原生实现**：登录验证码功能不依赖第三方库，完全使用 Java AWT `Graphics` 手绘生成。
*   **安全机制**：使用 Spring Interceptor (拦截器) 实现登录鉴权。
*   **轻量级部署**：使用 Thymeleaf 替代 JSP，支持打成 Jar 包独立运行。

---

## ✨ 功能特性 (Features)

### 1. 用户模块

*   **安全登录**：支持用户名/密码校验。
*   **图形验证码**：后端动态生成 4 位随机数字图片，包含干扰背景，防止机器暴力破解。
*   **会话管理**：基于 Session 的用户状态维持，支持注销。

### 2. 论坛模块

*   **话题列表**：查看所有用户发布的主题讨论。
*   **话题详情**：点击主题查看完整内容及所有相关的回复。
*   **发布/回复**：登录用户可以发布新话题，或对已有话题进行回复。
*   **多行文本**：支持保留文本格式（换行符）的显示。

### 3. 系统架构

*   **MVC 设计**：Model (数据模型), View (Thymeleaf), Controller (业务分发) 分离。
*   **IOC 容器**：使用 `@Service` 单例 Bean 管理全局数据。
*   **拦截器**：自动拦截非登录用户的非法访问。

---

## 🛠️ 技术栈 (Tech Stack)

*   **后端框架**: Spring Boot 3.2.5 (Spring MVC)
*   **编程语言**: Java 17
*   **模板引擎**: Thymeleaf
*   **前端样式**: Native CSS3 (Flexbox 布局)
*   **构建工具**: Maven
*   **工具库**: Lombok (简化 Java Bean 代码)

---

## 📂 项目结构 (Structure)

```text
src/main/java/com/example/forum
├── config           // Web 配置 (拦截器注册)
├── controller       // 控制层 (处理 URL 请求)
│   ├── ForumController.java
│   └── LoginController.java
├── interceptor      // 拦截器 (登录检查)
├── model            // 实体类 (User, Message)
├── service          // 业务逻辑层 (模拟数据库数据)
└── ForumApplication.java // 启动类

src/main/resources
├── static/css       // 静态资源 (样式表)
└── templates        // Thymeleaf 页面 (HTML)
    ├── login.html
    ├── list.html
    ├── detail.html
    └── post.html

## 🚀 快速开始 (Getting Started)

### 前置要求

*   JDK 17 或更高版本
*   Maven 3.6+

### 运行步骤

1.  **克隆项目**
    ```bash
    git clone https://github.com/YourUsername/OnlineForum.git
    cd OnlineForum
    ```

2.  **打包项目**
    ```bash
    mvn clean package
    ```

3.  **启动服务**
    
    进入 target 目录，运行生成的 jar 包：
    ```bash
    java -jar OnlineForum-0.0.1-SNAPSHOT.jar
    ```
    (或者直接在 IDEA 中运行 `ForumApplication.java` 的 main 方法)

4.  **访问项目**
    
    打开浏览器访问：[http://localhost:8080](http://localhost:8080)

### 默认测试账号

| 用户名 | 密码 | 角色 |
| :--- | :--- | :--- |
| admin | 123 | 管理员/测试用户 |
| student | 123 | 普通用户 |

## 📸 运行截图 (Screenshots)


| 登录页 (Login) | 话题列表 (List) |
| :--- | :--- |
| ![alt text](https://via.placeholder.com/400x300?text=Login+Page) | ![alt text](https://via.placeholder.com/400x300?text=Forum+List) |

| 详情与回复 (Detail) | 发布话题 (Post) |
| :--- | :--- |
| ![alt text](https://via.placeholder.com/400x300?text=Detail+Page) | ![alt text](https://via.placeholder.com/400x300?text=New+Post) |

## 📝 学习笔记 (Refactoring Notes)

本项目完成从 Servlet 到 Spring Boot 的重构过程：

1.  **去 XML 配置**：使用 Java Config (WebConfig) 替代 web.xml。
2.  **View 层升级**：从 .jsp 迁移至 .html (Thymeleaf)，实现前后端解耦和 Jar 包部署。
3.  **数据管理**：使用 Spring 的单例 Bean (DataService) 替代 ServletContext 全局域对象。

## 📄 License

MIT License
