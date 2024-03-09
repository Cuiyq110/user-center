package com.cuiyq.model.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version V1.0
 * @Title:
 * @Description: 用户注册请求体
 * @Copyright 2024 Cuiyq
 * @author: Cuiyq
 * @date: 2024/3/9$ 21:29$
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 42L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
