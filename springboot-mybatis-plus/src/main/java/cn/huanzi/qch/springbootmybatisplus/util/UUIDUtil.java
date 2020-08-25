package cn.huanzi.qch.springbootmybatisplus.util;


import java.util.UUID;

/**
 * UUID工具类
 */
public class UUIDUtil {

	/** 
     * 生成32位UUID编码
     */
    public static String getUUID(){
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }
}
