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
        param.put("noMore", false);
        int index = 0;
        Map<String, String> headers = new HashMap<>(6);
        headers.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");
        headers.put("Host", "blog.csdn.net");
        headers.put("Cookie", "uuid_tt_dd=10_8463471280-1718794507759-204350; FCNEC=%5B%5B%22AKsRol88-fqTmGBYN7YDi9U4ygbkj1JCa7LGB_Eh5oqBMYbxPawawYP1R9HQMitznfEzHTdvP9Hq03iYunTjc6fz30rEUuagbA7rMA4utrG6MGIAyOONuiP8vf-cK8ohqxRwGbzFu1tQjTY70B_-6QJ_4lXJEycdOA%3D%3D%22%5D%5D; loginbox_strategy=%7B%22taskId%22%3A349%2C%22abCheckTime%22%3A1718794512762%2C%22version%22%3A%22exp11%22%2C%22blog-threeH-dialog-exp11tipShowTimes%22%3A1%2C%22blog-threeH-dialog-exp11%22%3A1718794512763%7D; fpv=9660f121e827956d613e4a2657299e01; UserName=SJshenjian; UserInfo=01a1f47bd59046a493a9edaec4a94fa2; UserToken=01a1f47bd59046a493a9edaec4a94fa2; UserNick=%E6%B2%88%E5%81%A5_%E7%AE%97%E6%B3%95%E5%B0%8F%E7%94%9F; AU=7C8; UN=SJshenjian; BT=1718794604114; p_uid=U010000; management_ques=1718794735632; dc_session_id=10_1719017672988.334311; c_first_ref=cn.bing.com; c_segment=14; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1718794511,1719017674; https_waf_cookie=36c96ef2-eb7d-4e1c43281137a82485add8b511c3873a1823; dc_sid=32f7e92e29922c6bb7f590ee5319d105; creative_btn_mp=3; _clck=1mb890v%7C2%7Cfmu%7C0%7C1631; __gads=ID=97831c72e8a5d544:T=1718794504:RT=1719017718:S=ALNI_Mage4T_L7_PIttVEUlqKDn0abcd5w; __gpi=UID=00000e54e5f01fb5:T=1718794504:RT=1719017718:S=ALNI_MavHywZwa13TawErwQWLQ9HE9HRrg; __eoi=ID=6830a1239ddbaed9:T=1718794504:RT=1719017718:S=AA-AfjZp5nJb-rONHn2kDzA4InqR; yd_captcha_token=ycvu6kdAqCo7T6q8n1U4eqGfUPYDwODSOQZOk3NhHGK92gwCahTTz1bXx3O7an8OGojofgLc9HzheEv565VgOQ%3D%3D; c_first_page=https%3A//blog.csdn.net/wtyuong/article/details/136683702; c_dsid=11_1719018073483.620679; creativeSetApiNew=%7B%22toolbarImg%22%3A%22https%3A//img-home.csdnimg.cn/images/20230921102607.png%22%2C%22publishSuccessImg%22%3A%22https%3A//img-home.csdnimg.cn/images/20240229024608.png%22%2C%22articleNum%22%3A320%2C%22type%22%3A2%2C%22oldUser%22%3Atrue%2C%22useSeven%22%3Afalse%2C%22oldFullVersion%22%3Atrue%2C%22userName%22%3A%22SJshenjian%22%7D; c_pref=https%3A//blog.csdn.net/wtyuong/article/details/136683702; c_ref=https%3A//mp.csdn.net/mp_blog/manage/article%3Fspm%3D1001.2101.3001.5448; c_page_id=default; log_Id_pv=179; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1719018084; log_Id_view=9359; waf_captcha_marker=dc58a87b3ede410a37684476ff6946f2a1c1a6c1a952b5f088d359f3bb04f73b; log_Id_click=214; _clsk=1hosmqe%7C1719018094985%7C4%7C0%7Cx.clarity.ms%2Fcollect; dc_tos=sfgivk");
        while (true) {
            index++;
            param.put("page", index);

            String msg = HttpUtil.createGet(getArticleUrl).headerMap(headers, true).body("size=20&page=" + index + "&businessType="+businessType
            +"&username="+username+"&noMore=false").execute().body();
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





