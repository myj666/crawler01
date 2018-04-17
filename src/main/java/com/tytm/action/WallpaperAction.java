package com.tytm.action;

import com.tytm.service.impl.WallpaperService;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("all")
@Controller
@RequestMapping(value = "wallpaperAction")
public class WallpaperAction {
    private static final String SUCCESS = "success";
    private static final String sourceUrl = "https://bing.ioliu.cn";
    private static final String defaultCharset = "UTF-8";

    @Autowired
    private WallpaperService wallpaperService;

    /**
     * 爬虫入口
     *
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public String init() {
        //获取壁纸列表页html源码
        String sourceCode = wallpaperService.getListWallpaperSourceCode(sourceUrl, defaultCharset);
        //列表页html转DOM
        Document htmlToDom = wallpaperService.getHtmlToDom(sourceCode);
        //列表页html使用正则匹配壁纸url
        List<String> wallpaperRealAddress = wallpaperService.getRegularMatchingWallpaperUrl(htmlToDom);
        //获取壁纸信息html源码（转DOM）
        List<Document> realSourceCode = wallpaperService.getWallpaperInfoSourceCode(wallpaperRealAddress, sourceUrl, defaultCharset);
        //保存壁纸以及其他信息
        wallpaperService.getExtractAndSave(realSourceCode,sourceUrl);
        return SUCCESS;
    }
}
