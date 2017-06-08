package com.bycc.service.Live;

import java.io.IOException;

/**
 * Created by wanghaidong on 2017/5/3.
 */
public interface LiveService {
    /**
     * 开始直播
     * @param
     * @return
     */
    String startLive(String cIp,String cUsername,String cPassword) throws IOException, InterruptedException;

    /**
     * 结束直播
     * @param
     * @return
     */
    Boolean endLive(String cIp);
}
