package com.empress.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 邮件
 *
 * @author Hystar
 * @date 2018/10/8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

    /**
     * 主题
     */
    private String subject;

    /**
     * 发送的信息
     */
    private String message;

    /**
     * 收件人
     */
    private Set<String> receivers;
}
