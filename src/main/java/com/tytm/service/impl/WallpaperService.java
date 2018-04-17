package com.tytm.service.impl;


import com.tytm.entity.WallpaperInfo;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.regex.Matcher;

@SuppressWarnings("all")
public interface WallpaperService {

    /**
     * 获取html源码
     *
     * @param sourceUrl
     * @param defaultCharset
     * @return
     */
    String getListWallpaperSourceCode(String sourceUrl, String defaultCharset);

    /**
     * 获取html源码，提取壁纸以及其他信息
     * @param wallpaperUrls
     * @param sourceUrl
     * @param defaultCharset
     */
    List<Document> getWallpaperInfoSourceCode(List<String> wallpaperUrls, String sourceUrl, String defaultCharset);

    /**
     * 将字符串解析（分析）转DOM元素
     *
     * @param sourceCode
     * @return
     */
    Document getHtmlToDom(String sourceCode);

    /**
     * 正则提取壁纸url
     *
     * @param document
     * @return
     */
    List<String> getRegularMatchingWallpaperUrl(Document document);

    /**
     * 下载壁纸
     *
     * @param wallpaperUrls
     * @param sourceUrl
     */
    void getExtractAndSave(List<Document> realSourceCode,String sourceUrl);

    /**
     * 编译正则调用
     *
     * @param regEx
     * @param content
     * @return
     */
    Matcher compileRegular(String regEx, String content);

    /**
     * 保存
     *
     * @param wallpaperInfo
     */
    void saveWallpaperInfo(WallpaperInfo wallpaperInfo);
}
