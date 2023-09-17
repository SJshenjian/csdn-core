package com.itmei.csdnscore.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.itmei.csdnscore.model.ArticleDetails;
import com.itmei.csdnscore.model.ArticleResponse;
import com.itmei.csdnscore.model.Score;
import com.itmei.csdnscore.model.ScoreResponse;
import com.itmei.csdnscore.service.CsdnScoreService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author itmei
 * @Date 2023/9/17 14:50
 * @description: csdn质量分
 * @Title: CsdnScoreServiceImpl
 * @Package com.itmei.csdnscore.service
 */
@Service
public class CsdnScoreServiceImpl implements CsdnScoreService {
    private final String getArticleUrl = "https://blog.csdn.net/community/home-api/v1/get-business-list";
    private final String getArticlesScoreUrl = "https://bizapi.csdn.net/trends/api/v1/get-article-score";

    @Override
    public List<ArticleDetails> getAllTheArticles(String username, String businessType) {
        List<ArticleDetails> articleDetails = new ArrayList<>();
        Map<String, Object> param = new HashMap<>(4);
        param.put("size", 20);
        param.put("businessType", businessType);
        param.put("username", username);
        int index = 0;
        while (true) {
            index++;
            param.put("page", index);
            String msg = HttpUtil.get(getArticleUrl, param);
            if (ObjectUtil.isEmpty(msg)) {
                break;
            }
            JSONObject data = JSONUtil.parseObj(msg);
            ArticleResponse articleResponse = JSONUtil.toBean(data, ArticleResponse.class);
            if (ObjectUtil.isNotEmpty(articleResponse)
                    && ObjectUtil.isNotEmpty(articleResponse.getData())
                    && ObjectUtil.isNotEmpty(articleResponse.getData().getList())
            ) {
                articleDetails.addAll(articleResponse.getData().getList());
            } else {
                break;
            }
        }
        return articleDetails;
    }

    @Override
    public Score getArticlesScore(String url) {
        Map<String, String> headers = new HashMap<>(6);
        headers.put("X-Ca-Key", "203930474");
        headers.put("X-Ca-Signature", "+fkC/Z91B8FRai2qZutPI0OyQCX7IsfVFcS7rPZk+YM=");
        headers.put("X-Ca-Nonce", "86970a2f-f385-4427-a40b-c90cb17c00b9");
        headers.put("X-Ca-Signature-Headers", "x-ca-key,x-ca-nonce");
        headers.put("X-Ca-Signed-Content-Type", "multipart/form-data");
        headers.put("Accept", "application/json, text/plain, */*");

        String body = HttpUtil.createPost(getArticlesScoreUrl).headerMap(headers, true)
                .body("url=" + url).execute().body();
        if (ObjectUtil.isNotEmpty(body)) {
            ScoreResponse scoreResponse = JSONUtil.toBean(body, ScoreResponse.class);
            if (ObjectUtil.isNotEmpty(scoreResponse)) {
                return scoreResponse.getData();
            }
        }
        return null;
    }

    @Override
    public void exportExcel(String filePath, List<Map<String, Object>> rows) {
        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter(filePath);
        // 默认的，未添加alias的属性也会写出，如果想只写出加了别名的字段，可以调用此方法排除之
        writer.setOnlyAlias(true);
        // 合并单元格后的标题行，使用默认标题样式
        Integer columnTotal = rows.get(0).size() - 1;
        writer.merge(columnTotal, "CSDN文章质量分");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows, true);
        // 设置第一列的自动调整列宽
        writer.autoSizeColumn(0, true);
        // 关闭writer，释放内存
        writer.close();
    }
}





