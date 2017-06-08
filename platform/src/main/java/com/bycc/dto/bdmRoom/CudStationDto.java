package com.bycc.dto.bdmRoom;

import com.bycc.dto.bdmRoom.BdmStationDto;

import java.util.List;

/**
 * Created by wanghaidong on 2017/4/21.
 */
public class CudStationDto {
    private List<BdmStationDto> news;
    private List<BdmStationDto> updates;
    private List<String> deletes;

    public List<BdmStationDto> getNews() {
        return news;
    }

    public void setNews(List<BdmStationDto> news) {
        this.news = news;
    }

    public List<BdmStationDto> getUpdates() {
        return updates;
    }

    public void setUpdates(List<BdmStationDto> updates) {
        this.updates = updates;
    }

    public List<String> getDeletes() {
        return deletes;
    }

    public void setDeletes(List<String> deletes) {
        this.deletes = deletes;
    }
}
