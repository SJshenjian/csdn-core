package com.itmei.csdnscore.service;

import com.itmei.csdnscore.model.ArticleDetails;
import com.itmei.csdnscore.model.Score;

import java.util.List;
import java.util.Map;

/**
 * @Author itmei
 * @Date 2023/9/17 14:56
 * @description:
 * @Title: CsdnScoreService
 * @Package com.itmei.csdnscore.service
 */
public interface CsdnScoreService {

    /**
     * 得到所有文章
     *
     * @param username     博主名称
     * @param businessType 类型
     * @return
     */
    public List<ArticleDetails> getAllTheArticles(String username, String businessType);

    /**
     * 得到文章的分数
     *
     * @param url
     * @return
     */
    public Score getArticlesScore(String url);

    /**
     * 导出excel
     *
     * @param filePath
     * @param rows
     */
    void exportExcel(String filePath, List<Map<String, Object>> rows);

}
