package com.cuiyq.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version V1.0
 * @Title:
 * @Description: 用户登录请求体
 * @Copyright 2024 Cuiyq
 * @author: Cuiyq
 * @date: 2024/3/9$ 23:20$
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest implements Serializable {


    private static final long serialVersionUID = -4063614549540352718L;
    private String userAccount;
    private String userPassword;
}
