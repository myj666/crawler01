package com.tytm.dao;

import com.tytm.dao.impl.WallpaperDao;
import com.tytm.entity.WallpaperInfo;
import frame.tytm.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class WallpaperDaoImpl extends BaseDaoImpl<WallpaperInfo, Long> implements WallpaperDao {

}
