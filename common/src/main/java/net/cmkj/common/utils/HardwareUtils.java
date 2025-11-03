package net.cmkj.common.utils;

import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 硬件信息工具类
 */
public class HardwareUtils {
    // 获取本机MAC地址（取第一个非虚拟网卡的MAC）
    public static String getMacAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                // 过滤虚拟网卡和禁用的网卡
                if (ni.isVirtual() || !ni.isUp()) continue;

                byte[] mac = ni.getHardwareAddress();
                if (mac != null) {
                    StringBuilder sb = new StringBuilder();
                    for (byte b : mac) {
                        sb.append(String.format("%02X:", b));
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
