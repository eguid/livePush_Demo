/**
 * 文件名：PushHandler.java
 * 描述：push操作处理器接口
 * 修改人：eguid
 * 修改时间：2016年6月24日
 * 修改内容：
 */
package cc.eguid.livepush.handler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * 用于提供处理push操作的服务接口
 * @author eguid  
 * @version 2016年6月24日  
 * @see PushHandler  
 * @since  jdk1.7
 */

public interface PushHandler
{
/**
 *  处理push操作（包含一个主进程和两个输出线程）
 * @param map 
 * 格式：
 * name:应用名；input:接收地址；output:推送地址；fmt:视频格式；fps:视频帧率；rs:视频分辨率；disableAudio:是否开启音频
 * @return map（进程，消息（info，error））
 * @throws IOException
 * 
 */
public ConcurrentMap<String,Object> push(Map<String,String>map)throws Exception;
}
