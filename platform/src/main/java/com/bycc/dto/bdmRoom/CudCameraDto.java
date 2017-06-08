package com.bycc.dto.bdmRoom;

import com.bycc.dto.bdmRoom.BdmCameraDto;

import java.util.List;

/**
 * Created by wanghaidong on 2017/4/21.
 */
public class CudCameraDto {
    private List<BdmCameraDto> news;
    private List<BdmCameraDto> updates;
    private List<String> deletes;

    public List<BdmCameraDto> getNews() {
        return news;
    }

    public void setNews(List<BdmCameraDto> news) {
        this.news = news;
    }

    public List<BdmCameraDto> getUpdates() {
        return updates;
    }

    public void setUpdates(List<BdmCameraDto> updates) {
        this.updates = updates;
    }

    public List<String> getDeletes() {
        return deletes;
    }

    public void setDeletes(List<String> deletes) {
        this.deletes = deletes;
    }
}
