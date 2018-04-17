package com.tytm.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 必应壁纸Entity
 * 注：@Data 可以细分@Getter,@Setter--（详解）https://projectlombok.org/features/
 */
@SuppressWarnings("all")
@Entity
@Table(name = "wallpaper_info")
public class WallpaperInfo implements Serializable {
    private static final long serializableVersion = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //壁纸标题
    @Column(name = "title")
    private String title;

    //壁纸简介
    @Column(name = "introduction")
    private String introduction;

    //壁纸更新时间
    @Column(name = "updateTime")
    private Date updateTime;

    //壁纸所属地
    @Column(name = "position")
    private String position;

    //壁纸查看数量
    @Column(name = "checkNumber")
    private Long checkNumber;

    //壁纸喜欢数量
    @Column(name = "likeNumber")
    private Long likeNumber;

    //壁纸下载数量
    @Column(name = "downLoadNumber")
    private Long downLoadNumber;

    //壁纸适用分辨率
    @Column(name = "resolution")
    private String resolution;

    //壁纸真实url
    @Column(name = "realUrl")
    private String realUrl;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(Long checkNumber) {
        this.checkNumber = checkNumber;
    }

    public Long getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(Long likeNumber) {
        this.likeNumber = likeNumber;
    }

    public Long getDownLoadNumber() {
        return downLoadNumber;
    }

    public void setDownLoadNumber(Long downLoadNumber) {
        this.downLoadNumber = downLoadNumber;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getRealUrl() {
        return realUrl;
    }

    public void setRealUrl(String realUrl) {
        this.realUrl = realUrl;
    }

}
