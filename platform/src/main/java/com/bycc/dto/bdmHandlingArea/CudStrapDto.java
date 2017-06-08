package com.bycc.dto.bdmHandlingArea;

import java.util.List;

/**
 * Created by wanghaidong on 2017/4/20.
 */
public class CudStrapDto {
    private List<BdmStrapDto> news;
    private List<BdmStrapDto> updates;
    private List<String> deletes;

    public List<BdmStrapDto> getNews() {
        return news;
    }

    public void setNews(List<BdmStrapDto> news) {
        this.news = news;
    }

    public List<BdmStrapDto> getUpdates() {
        return updates;
    }

    public void setUpdates(List<BdmStrapDto> updates) {
        this.updates = updates;
    }

    public List<String> getDeletes() {
        return deletes;
    }

    public void setDeletes(List<String> deletes) {
        this.deletes = deletes;
    }
}
