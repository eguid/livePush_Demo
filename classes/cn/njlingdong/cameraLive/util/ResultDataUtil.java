/**
 * 文件名：ResultDataUtil.java
 * 描述：提供方便操作的工具方法
 * 修改人：eguid
 * 修改时间：2016年6月30日
 * 修改内容：
 */
package cn.njlingdong.cameraLive.util;

import cn.njlingdong.cameraLive.entity.ResultData;

/**
 * 方便操作的工具类
 * @author eguid  
 * @version 2016年6月30日  
 * @see ResultDataUtil  
 * @since  jdk1.7
 */

public class ResultDataUtil
{
public static void setData(ResultData result,String status,String msg,Object data)
{
    if(status!=null)
    result.setStatus(status);
    if(msg!=null)
    result.setMsg(msg);
    if(data!=null)
    result.setData(data);
    }
/**
 * 检查字符串不能包含中文字符
 * @return true：是；false：否
 */
public static boolean checkStringNoCN(String element)
{
    String regex="[a-zA-Z0-9]+";
    return element.matches(regex);
}
/**
 * 检查字符串是否是链接地址
 * @param element
 * @return true：是 ；false：否
 */
public static boolean checkURLNoCN(String element)
{
    String regex="[a-zA-Z0-9/@:.?%&=]+";
    return element.matches(regex);
}
/**
 * 去除字符串两端空格
 * @param str
 */
public static void trim(String ...str)
{
    for(String s:str)
    {
        s=s.trim();
    }
   
}
/**
 * 测试
 * @param args
 */
public static void main(String [] args)
{
    System.err.println(checkURLNoCN("rtsp://admin:admin@192.168.2.236:37779/cam/realmonitor?channel=1&subtype=0"));
    }
}
