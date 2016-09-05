/**
 * 文件名：HandlerDao.java
 * 描述：管理所有命令行处理器的缓存
 * 修改人：eguid
 * 修改时间：2016年6月27日
 * 修改内容：
 */
package cc.eguid.livepush.dao;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * 命令行执行处理器缓存，方便管理处理器的开启和关闭
 * @author eguid 
 * @version 2016年6月27日  
 * @see HandlerDao  
 * @since  jdk1.7
 */

public interface HandlerDao
{
    /**
     *  获取某个处理器
     * @param pushId
     * @return
     */
    public ConcurrentMap<String, Object> get(String pushId);
    /**
     * 存放一个处理器
     * @param handlerMap
     */
    public void set(String key, ConcurrentMap<String, Object> resultMap);
    /**
     * 获取全部处理器
     * @return
     */
    public ConcurrentMap<String, ConcurrentMap<String, Object>> getAll();
    /**
     * 获取全部处理器名称
     * @return
     */
    public Set<String> getAllAppName();
    /**
     * 删除某个处理器
     * @param pushId
     */
    public void delete(String appName);
    /**
     * 是否存在key
     */
    public boolean isHave(String appName);
}
