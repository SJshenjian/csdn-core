package com.itmei.csdnscore;

import com.itmei.csdnscore.model.ArticleDetails;
import com.itmei.csdnscore.model.Score;
import com.itmei.csdnscore.service.CsdnScoreService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class CsdnScoreApplicationTests {
    @Resource
    private CsdnScoreService scoreService;

    @Test
    void articleDetailsScore() {
        List<ArticleDetails> allTheArticles = scoreService.getAllTheArticles("qq_45502336", "blog");
        ArticleDetails articleDetails = allTheArticles.get(0);
        Score articlesScore = scoreService.getArticlesScore(articleDetails.getUrl());
        System.out.println("-------文章质量分------");
        System.out.println("文章名称:"+articleDetails.getTitle());
        System.out.println("文章分数:"+articlesScore.getScore());
        System.out.println("文章建议:"+articlesScore.getMessage());
        System.out.println("-------   结束  ------");
    }

}
