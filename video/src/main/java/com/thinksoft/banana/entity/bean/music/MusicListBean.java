package com.thinksoft.banana.entity.bean.music;

import com.thinksoft.banana.entity.BaseBean;
import com.thinksoft.banana.entity.bean.circle.CircleBean;

import java.util.List;

/**
 * @author txf
 * @create 2019/3/22 0022
 * @
 */
public class MusicListBean extends BaseBean {
    List<MusicDataBean> musicList;

    public void setMusicList(List<MusicDataBean> musicList) {
        this.musicList = musicList;
    }

    public List<MusicDataBean> getMusicList() {
        return musicList;
    }
}
