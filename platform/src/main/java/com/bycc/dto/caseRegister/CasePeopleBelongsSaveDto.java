package com.bycc.dto.caseRegister;

import org.smartframework.platform.dto.annotation.DtoClass;

import java.util.List;

/**
 * Description:
 * User: yumingzhe
 * Time: 2017-5-8 16:01
 */
@DtoClass
public class CasePeopleBelongsSaveDto {

	private Integer casePeopleId;

    private List<CasePeopleBelongsDto> news;

	private List<CasePeopleBelongsDto> updates;

    private List<Integer> deletes;

    public Integer getCasePeopleId() {
        return casePeopleId;
    }

    public void setCasePeopleId(Integer casePeopleId) {
        this.casePeopleId = casePeopleId;
    }

    public List<CasePeopleBelongsDto> getNews() {
        return news;
    }

    public void setNews(List<CasePeopleBelongsDto> news) {
        this.news = news;
    }

    public List<CasePeopleBelongsDto> getUpdates() {
        return updates;
    }

    public void setUpdates(List<CasePeopleBelongsDto> updates) {
        this.updates = updates;
    }

    public List<Integer> getDeletes() {
        return deletes;
    }

    public void setDeletes(List<Integer> deletes) {
        this.deletes = deletes;
    }
}
