package com.bycc.dto.bdmRoom;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.smartframework.utils.helper.JsonHelper;

import java.io.IOException;

public class RoomCondition {
    
    private Integer handlingAreaId;

    public RoomCondition() {// jackson调用该构造方法构造新的bean对象
    }

    public RoomCondition(String json) {// @RequestParam注解调用该构造函数将json字符串参数传入
    }

    // 名称固定的静态方法，转换json字符串为对象
    public static RoomCondition valueOf(String json) throws JsonParseException, JsonMappingException, IOException {
        RoomCondition RoomCondition = JsonHelper.getBean(json, RoomCondition.class);
        return RoomCondition;
    }

    public Integer getHandlingAreaId() {
        return handlingAreaId;
    }

    public void setHandlingAreaId(Integer handlingAreaId) {
        this.handlingAreaId = handlingAreaId;
    }
}
