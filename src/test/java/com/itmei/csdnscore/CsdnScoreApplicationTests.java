package com.itmei.csdnscore;

import cn.hutool.core.date.DateUtil;
import com.itmei.csdnscore.model.ArticleDetails;
import com.itmei.csdnscore.model.Score;
import com.itmei.csdnscore.service.CsdnScoreService;
import com.itmei.csdnscore.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class CsdnScoreApplicationTests {
    @Resource
    private CsdnScoreService scoreService;

    @Test
    void articleDetailsScore() {
        List<Map<String, Object>> rows = new ArrayList<>();

        List<ArticleDetails> allTheArticles = scoreService.getAllTheArticles("SJshenjian", "blog");
        for (ArticleDetails articleDetails : allTheArticles) {

            Score articlesScore = scoreService.getArticlesScore(articleDetails.getUrl());
            System.out.println("-------文章质量分------");
            System.out.println("文章名称:" + articleDetails.getTitle());
            System.out.println("文章分数:" + articlesScore.getScore());
            System.out.println("文章建议:" + articlesScore.getMessage());
            System.out.println("-------   结束  ------");
            Map<String, Object> row = new HashMap<>();
            row.put("文章名称", articleDetails.getTitle());
            row.put("文章阅读数", articleDetails.getViewCount());
            row.put("文章分数", articlesScore.getScore());
            row.put("文章建议", articlesScore.getMessage());
            rows.add(row);
        }
        String absolutePath =  "/home/shenjian/数据/CSDN文章分数" + DateUtil.currentSeconds() + ".xlsx";
        scoreService.exportExcel(absolutePath, rows);
    }

}
