package com.itmei.csdnscore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author itmei
 * @Date 2023/9/17 16:05
 * @description: 分数
 * @Title: Score
 * @Package com.itmei.csdnscore.model
 */
@Data
public class Score {
    @JsonProperty("articleId")
    private Integer articleId;
    /**
     * 分数
     */
    @JsonProperty("score")
    private Integer score;
    /**
     * 建议
     */
    @JsonProperty("message")
    private String message;
    /**
     * 发布时间
     */
    @JsonProperty("postTime")
    private String postTime;
}
