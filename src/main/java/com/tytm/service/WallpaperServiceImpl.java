package com.tytm.service;

import com.tytm.dao.impl.WallpaperDao;
import com.tytm.entity.WallpaperInfo;
import com.tytm.service.impl.WallpaperService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("all")
@Service
public class WallpaperServiceImpl implements WallpaperService {
    @Autowired
    private WallpaperDao wallpaperDao;


    /**
     * 获取html源码
     */
    @Override
    public String getListWallpaperSourceCode(String sourceUrl, String defaultCharset) {
        String content = null;
        //创建client实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建httpGet实例
        HttpGet httpGet = new HttpGet(sourceUrl);
        try {
            //执行get请求
            CloseableHttpResponse execute = httpClient.execute(httpGet);
            //返回实体
            HttpEntity entity = execute.getEntity();
            //获取网页内容，指定编码UTF-8
            content = EntityUtils.toString(entity, defaultCharset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 获取html源码，提取壁纸以及其他信息
     */
    @Override
    public List<Document> getWallpaperInfoSourceCode(List<String> wallpaperUrls, String sourceUrl, String defaultCharset) {
        String content = null;
        List<Document> wallpaperUrl = new ArrayList<>();
        //创建client实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        for (int i = 0; i < wallpaperUrls.size(); i++) {
            //创建httpGet实例
            HttpGet httpGet = new HttpGet(sourceUrl + wallpaperUrls.get(i));
            try {
                //执行get请求
                CloseableHttpResponse execute = httpClient.execute(httpGet);
                //返回实体
                HttpEntity entity = execute.getEntity();
                //获取网页内容，指定编码UTF-8
                content = EntityUtils.toString(entity, defaultCharset);
                Document htmlToDom = this.getHtmlToDom(content);
                wallpaperUrl.add(htmlToDom);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  wallpaperUrl;
    }

    /**
     * 将字符串解析（分析）转DOM元素
     */
    @Override
    public Document getHtmlToDom(String sourceCode) {
        Document parse = Jsoup.parse(sourceCode);
        return parse;
    }

    /**
     * 获取DOM图片真实地址
     *
     * @param document
     */
    @Override
    public List<String> getRegularMatchingWallpaperUrl(Document document) {
        List<String> wallpaperUrl = new ArrayList<>();
        Matcher matcher = this.compileRegular("/photo.*home_1", document.toString());
        while (matcher.find()) {
            String group = matcher.group();
            wallpaperUrl.add(group);
        }
        return wallpaperUrl;
    }

    /**
     * 编译正则调用
     *
     * @param regEx
     * @param content
     * @return
     */
    @Override
    public Matcher compileRegular(String regEx, String content) {
        Pattern compile = Pattern.compile(regEx);
        Matcher matcher = compile.matcher(content);
        return matcher;
    }

    /**
     * 下载壁纸
     *
     * @param wallpaperUrls
     * @param sourceUrl
     */
    @Override
    public void getExtractAndSave(List<Document> wallpaperUrls,String sourceUrl) {
        if (wallpaperUrls.size() > 0) {
            Matcher matcher = null;
            WallpaperInfo wallpaperInfo = null;
            for (int i = 0; i < wallpaperUrls.size(); i++) {
                wallpaperInfo = new WallpaperInfo();
                String dom = wallpaperUrls.get(i).toString();
                matcher = this.compileRegular("<p class=\"title\">.*</p>", dom);
                matcher.group();//壁纸标题
                matcher = this.compileRegular("<p class=\"sub\">.*</p>",dom);
                matcher.group();//壁纸简介
                matcher = this.compileRegular("<em class=\"t\">.*-.*</em>",dom);
                matcher.group();//壁纸更新时间
                matcher = this.compileRegular("<em class=\"t\">.*,.*</em>",dom);
                matcher.group();//壁纸所属地
                matcher = this.compileRegular("<i class=\"icon icon-eye\">(|\\s)</i><em class=\"t\">.*\\d.*</em>",dom);
                matcher.group();//壁纸查看数量
                matcher = this.compileRegular("<i class=\"icon icon-heart\">(|\\s)</i><em class=\"t\">.*\\d.*</em>",dom);
                matcher.group();//壁纸喜欢数量
                matcher = this.compileRegular("<i class=\"icon icon-download\">(|\\s)</i><em class=\"t\">.*\\d.*</em>",dom);
                matcher.group();//壁纸下载数量
                matcher = this.compileRegular("<i class=\"icon icon-download\">(|\\s)</i><em class=\"t\">.*\\d.*</em>",dom);
                matcher.group();//壁纸适用分辨率
                matcher = this.compileRegular("/photo.*force=download", dom);
                matcher.group();//图片真是链接
                
            }
        }
    }

    /**
     * 保存
     *
     * @param wallpaperInfo
     */
    @Override
    public void saveWallpaperInfo(WallpaperInfo wallpaperInfo) {
        wallpaperDao.save(wallpaperInfo);
    }
}
