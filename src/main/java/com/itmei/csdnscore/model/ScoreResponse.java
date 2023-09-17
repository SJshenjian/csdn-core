package com.itmei.csdnscore.model;

import lombok.Data;

/**
 * @Author itmei
 * @Date 2023/9/17 15:07
 * @description: 质量分响应
 * @Title: ScoreResponse
 * @Package com.itmei.csdnscore.model
 */
@Data
public class ScoreResponse {
    private Integer code;
    private String message;
    private Score data;
}
