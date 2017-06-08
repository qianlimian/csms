package com.bycc.dto.caseRegister.jasper;

import java.util.List;

public class HandlingAreaRegister {

    private String policeStationName;
    //涉案人
    private SubPeople subPeopleMap;
    //物品
    private List<SubBelongs> subBelongsList;
    //人身检查&&信息采集
    private SubInspect subInspectMap;
    //轨迹
    private List<SubTrace> subTraceList;
    //物品返还
    private SubReturn subReturnMap;

    public String getPoliceStationName() {
        return policeStationName;
    }

    public void setPoliceStationName(String policeStationName) {
        this.policeStationName = policeStationName;
    }

    public SubPeople getSubPeopleMap() {
        return subPeopleMap;
    }

    public void setSubPeopleMap(SubPeople subPeopleMap) {
        this.subPeopleMap = subPeopleMap;
    }

    public List<SubBelongs> getSubBelongsList() {
        return subBelongsList;
    }

    public void setSubBelongsList(List<SubBelongs> subBelongsList) {
        this.subBelongsList = subBelongsList;
    }

    public SubInspect getSubInspectMap() {
        return subInspectMap;
    }

    public void setSubInspectMap(SubInspect subInspectMap) {
        this.subInspectMap = subInspectMap;
    }

    public List<SubTrace> getSubTraceList() {
        return subTraceList;
    }

    public void setSubTraceList(List<SubTrace> subTraceList) {
        this.subTraceList = subTraceList;
    }

    public SubReturn getSubReturnMap() {
        return subReturnMap;
    }

    public void setSubReturnMap(SubReturn subReturnMap) {
        this.subReturnMap = subReturnMap;
    }
}