package com.itmei.csdnscore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author itmei
 * @Date 2023/9/17 15:02
 * @description:
 * @Title: ArticleDetails
 * @Package com.itmei.csdnscore.model
 */
@NoArgsConstructor
@Data
public class ArticleDetails {
    @JsonProperty("articleId")
    private Integer articleId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("url")
    private String url;
    @JsonProperty("type")
    private Integer type;
    @JsonProperty("top")
    private Boolean top;
    @JsonProperty("forcePlan")
    private Boolean forcePlan;
    @JsonProperty("viewCount")
    private Integer viewCount;
    @JsonProperty("commentCount")
    private Integer commentCount;
    @JsonProperty("editUrl")
    private String editUrl;
    @JsonProperty("postTime")
    private String postTime;
    @JsonProperty("diggCount")
    private Integer diggCount;
    @JsonProperty("formatTime")
    private String formatTime;
    @JsonProperty("picList")
    private List<String> picList;
    @JsonProperty("collectCount")
    private Integer collectCount;
}
