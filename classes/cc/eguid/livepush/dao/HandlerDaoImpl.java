/**
 * 文件名：HandlerDaoImpl.java 描述：命令行执行处理器缓存的简单实现 修改人：eguid 修改时间：2016年6月27日 修改内容：
 */
package cc.eguid.livepush.dao;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * 处理器的缓存简单实现
 * @author eguid
 * @version 2016年6月27日
 * @see HandlerDaoImpl
 * @since jdk1.7
 */

public class HandlerDaoImpl implements HandlerDao
{
    /**
     * 存放process
     */
    private static ConcurrentMap<String, ConcurrentMap<String, Object>> handlerMap = new ConcurrentHashMap<String, ConcurrentMap<String, Object>>(20);

    @Override
    public ConcurrentMap<String,Object> get(String pushId)
    {
        if(handlerMap.containsKey(pushId))
        {
            return handlerMap.get(pushId);
        }
        return null;
    }

    @Override
    public ConcurrentMap<String, ConcurrentMap<String, Object>> getAll()
    {
        return handlerMap;
    }

    @Override
    public void delete(String pushId)
    {
        if (pushId != null)
        {
            handlerMap.remove(pushId);
        }
    }

    @Override
    public void set(String key,  ConcurrentMap<String, Object> map)
    {
        if (key != null)
        {
            handlerMap.put(key, map);
        }
    }

    @Override
    public boolean isHave(String pushId)
    {
        return  handlerMap.containsKey(pushId);
    }

    @Override
    public Set<String> getAllAppName()
    {
        return handlerMap.keySet();
       
    }

}
