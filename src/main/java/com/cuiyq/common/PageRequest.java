package com.cuiyq.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @version V1.0
 * @Title:
 * @Description:
 * @Copyright 2024 Cuiyq
 * @author: Cuiyq
 * @date: 2024/6/8 15:49
 */
@Data
public class PageRequest implements Serializable {

    private static final long serialVersionUID = -2944133209931364836L;

    /**
     * 每页显示条数
     */
    protected int pageSize = 10;

    /**
     * 当前第几页
     */
    protected int pageNum = 1;
}
