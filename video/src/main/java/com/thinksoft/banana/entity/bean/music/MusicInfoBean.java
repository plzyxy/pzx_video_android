package com.thinksoft.banana.entity.bean.music;

import com.thinksoft.banana.entity.BaseBean;

/**
 * @author txf
 * @create 2019/3/22 0022
 * @
 */
public class MusicInfoBean extends BaseBean {

    MusicInfoDataBean musicInfo;

    public void setMusicInfo(MusicInfoDataBean musicInfo) {
        this.musicInfo = musicInfo;
    }

    public MusicInfoDataBean getMusicInfo() {
        return musicInfo;
    }
}
