/**
 * 文件名：PushManager.java
 * 描述：用于处理push相关操作的接口
 * 修改人：eguid
 * 修改时间：2016年6月24日
 * 修改内容：
 */
package cc.eguid.livepush;

import java.util.Map;
import java.util.Set;

/**
 * 用于提供push操作的增删查服务
 * @author eguid  
 * @version 2016年6月24日  
 * @see PushManager  
 * @since  jdk1.7
 */

public interface PushManager
{
/**
 *  发布一个流到服务器
 * @param map
 * @return pushId(当前发布流的标识，方便操作该push)
 */
public String push(Map<String,String>map);
/**
 *  通过应用名删除某个push
 * @param pushId
 */
public boolean closePush(String appName);
/**
 *  查看全部当前正在运行的应用名称
 * @param pushId
 */
public Set<String> viewAppName();
/**
 * 应用是否已经存在
 * @param appName
 * @return true:存在；false:不存在
 */
public boolean isHave(String appName);
}
