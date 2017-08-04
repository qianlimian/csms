package com.bycc.utils;


import java.util.ArrayList;
import java.util.List;

import com.bycc.dto.RFParams;

public class RFHelper {
	
	public static List<RFParams> getRFParams(byte[] contents) {
		List<RFParams> result = new ArrayList<RFParams>();
		Integer dataLength = Integer.parseInt(bytesToHexString(copyOfRange(contents, 10, 10)), 16);
		Integer start = 0;
		for (int i = 0; i < dataLength; i++) {
			RFParams rfParams = new RFParams();
			rfParams.setDevId(Integer.parseInt(bytesToHexString(copyOfRange(contents, 6, 7)), 16));
			if (i == 0) {
				start = 11;
			} else {
				start = start + 14;
			}
			rfParams.setTagId(Integer.parseInt(bytesToHexString(copyOfRange(contents, start, start + 2)), 16));
			
			byte statusByte = copyOfRange(contents, start + 3, start + 3)[0];
			String statusString = getBit(statusByte);
			if (statusString.length() == 8) {
				if ("1".equals(statusString.substring(0, 1))) {
					rfParams.setActivate(true);
				} else {
					rfParams.setActivate(false);
				}
				if (!rfParams.isActivate()) {
					continue;
				}
				if ("1".equals(statusString.substring(1, 2))) {
					rfParams.setVoltage(true);
				} else {
					rfParams.setVoltage(false);
				}
				if ("1".equals(statusString.substring(2, 3))) {
					rfParams.setTamper(true);
				} else {
					rfParams.setTamper(false);
				}
				if ("1".equals(statusString.substring(3, 4))) {
					rfParams.setButton1(true);
				} else {
					rfParams.setButton1(false);
				}
				if ("1".equals(statusString.substring(4, 5))) {
					rfParams.setButton2(true);
				} else {
					rfParams.setButton2(false);
				}
				if ("00".equals(statusString.substring(5, 7))) {
					rfParams.setGain(0);
				} else if ("01".equals(statusString.substring(5, 7))) {
					rfParams.setGain(1);
				} else if ("10".equals(statusString.substring(5, 7))) {
					rfParams.setGain(2);
				} else if ("11".equals(statusString.substring(5, 7))) {
					rfParams.setGain(3);
				}
				if ("1".equals(statusString.substring(7, 8))) {
					rfParams.setTraverse(true);
				} else {
					rfParams.setTraverse(false);
				}
			}
			rfParams.setAntenna1ID(Integer.parseInt(bytesToHexString(copyOfRange(contents, start + 4, start + 5)), 16));
			rfParams.setRssi1(Integer.parseInt(bytesToHexString(copyOfRange(contents, start + 6, start + 6)), 16));
			rfParams.setAntenna2ID(Integer.parseInt(bytesToHexString(copyOfRange(contents, start + 7, start + 8)), 16));
			rfParams.setRssi2(Integer.parseInt(bytesToHexString(copyOfRange(contents, start + 9, start + 9)), 16));
			rfParams.setActivatetimes(Integer.parseInt(bytesToHexString(copyOfRange(contents, start + 10, start + 10)), 16));
			rfParams.setSendtimes(Integer.parseInt(bytesToHexString(copyOfRange(contents, start + 11, start + 11)), 16));
			rfParams.setMasterRSSI(Integer.parseInt(bytesToHexString(copyOfRange(contents, start + 12, start + 12)), 16));
			rfParams.setSlaveRSSI(Integer.parseInt(bytesToHexString(copyOfRange(contents, start + 13, start + 13)), 16));
			result.add(rfParams);
		}
		return result;
	}
	
	
	/**
	* 从指定数组的copy一个子数组并返回
	*
	* @param original of type byte[] 原数组
	* @param from 起始点
	* @param to 结束点
	* @return 返回copy的数组
	*/
	public static byte[] copyOfRange(byte[] original, int from, int to) {
		int newLength = to - from + 1;
		if (newLength < 0)
			throw new IllegalArgumentException(from + ">" + to);
		byte[] copy = new byte[newLength];
		System.arraycopy(original, from, copy, 0, newLength);
	    return copy;

	}
	
    /**
     * byte[]转换为16进制字符串
     * @param src byte[]数组
     * @return String
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if ((src == null) || (src.length <= 0)) {
            return null;
        }
        for (int i = 0; i < src.length; ++i) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();

    }

	/**
	 * byte转bit
	 */
	public static String getBit(byte by){
		StringBuffer sb = new StringBuffer();
		sb.append((by>>7)&0x1)
		  .append((by>>6)&0x1)
		  .append((by>>5)&0x1)
		  .append((by>>4)&0x1)
		  .append((by>>3)&0x1)
		  .append((by>>2)&0x1)
		  .append((by>>1)&0x1)
		  .append((by>>0)&0x1);
		return sb.toString();
	}

}

