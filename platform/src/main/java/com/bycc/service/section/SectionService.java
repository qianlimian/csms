package com.bycc.service.section;

import java.io.IOException;

/**
 * Created by wanghaidong on 2017/5/3.
 */
public interface SectionService {
    /**
     * 开始截取
     * @param
     * @return
     */
    Boolean startSection(String cIp,String cUsername,String cPassword,String caseName,String pName) throws IOException, InterruptedException;

    /**
     * 停止截取
     * @param
     * @return
     */
    Boolean endSection(String cIp);
}
