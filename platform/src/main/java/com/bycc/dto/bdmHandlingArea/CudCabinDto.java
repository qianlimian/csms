package com.bycc.dto.bdmHandlingArea;

import java.util.List;

/**
 * Created by wanghaidong on 2017/4/20.
 */
public class CudCabinDto {
    private List<BdmCabinetDto> news;
    private List<BdmCabinetDto> updates;
    private List<String> deletes;

    public List<BdmCabinetDto> getNews() {
        return news;
    }

    public void setNews(List<BdmCabinetDto> news) {
        this.news = news;
    }

    public List<BdmCabinetDto> getUpdates() {
        return updates;
    }

    public void setUpdates(List<BdmCabinetDto> updates) {
        this.updates = updates;
    }

    public List<String> getDeletes() {
        return deletes;
    }

    public void setDeletes(List<String> deletes) {
        this.deletes = deletes;
    }
}
