package com.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data; // 引入 Lombok
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private int id;
    private String title;
    private String content;
    private String datetime;
    private Integer replyId; // 如果是主贴则为null或0，如果是回帖则是主贴ID
    private User sender;

    public Message(int id, String title, String content, Integer replyId, User sender) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.replyId = replyId;
        this.sender = sender;
        // 自动生成时间
        this.datetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}