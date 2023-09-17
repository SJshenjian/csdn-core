package com.itmei.csdnscore.model;

import lombok.Data;

import java.util.List;

/**
 * @Author itmei
 * @Date 2023/9/17 15:07
 * @description: 响应体
 * @Title: ArticleResponse
 * @Package com.itmei.csdnscore.model
 */
@Data
public class ArticleResponse {
    private Integer code;
    private String message;
    private String traceId;
    private DataDto data;
    @Data
    public class DataDto {
        /**
         * 文章集合
         */
        List<ArticleDetails> list;
        /**
         * 总条数
         */
        private  Long total;
    }
}
