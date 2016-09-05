/**
 * 文件名：PushHandlerImpl.java 描述： 修改人：eguid 修改时间：2016年6月24日 修改内容：
 */
package cc.eguid.livepush.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 提供解析参数生成ffmpeg命令并处理push操作
 * 
 * @author eguid
 * @version 2016年6月24日
 * @see PushHandlerImpl
 * @since jdk1.7
 */

public class PushHandlerImpl implements PushHandler
{
    /*
     * "ffmpeg -i "+ "rtsp://admin:admin@192.168.2.236:37779/cam/realmonitor?channel=1&subtype=0 "+
     * " -f flv -r 25 -s 640x360 -an" + " rtmp://192.168.30.21/live/test" 推送流格式：
     * name:应用名；input:接收地址；output:推送地址；fmt:视频格式；fps:视频帧率；rs:视频分辨率；disableAudio:是否开启音频
     */
    @Override
    public ConcurrentMap<String, Object> push(Map<String, String> paramMap)
        throws Exception
    {
        // 从map里面取数据，组装成命令
        String comm = getComm4Map(paramMap);
        ConcurrentMap<String, Object> resultMap = null;
        // 执行命令行
        System.out.println("执行命令----start commond：" + comm);
        final Process proc = Runtime.getRuntime().exec(comm);
        OutHandler errorGobbler = new OutHandler(proc.getErrorStream(), paramMap.get("appName"));
        // OutHandler outputGobbler = new OutHandler(proc.getInputStream(), "Info");

        errorGobbler.start();
        // outputGobbler.start();
        // 返回参数
        resultMap = new ConcurrentHashMap<String, Object>();
        // resultMap.put("info", outputGobbler);
        resultMap.put("error", errorGobbler);
        resultMap.put("process", proc);
        return resultMap;
    }

    /**
     * 通过解析参数生成可执行的命令行字符串；
     * name:应用名；input:接收地址；output:推送地址；fmt:视频格式；fps:视频帧率；rs:视频分辨率；disableAudio:是否开启音频
     * 
     * @param paramMap
     * @throws Exception
     * @return 命令行字符串
     */
    protected String getComm4Map(Map<String, String> paramMap) throws Exception
    {

        if (paramMap.containsKey("ffmpegPath"))
        {
            StringBuilder comm = new StringBuilder(paramMap.get("ffmpegPath") + " -i ");
            // -i：输入流地址或者文件绝对地址

            // 是否有必输项：输入地址，输出地址，应用名
            if (paramMap.containsKey("input") && paramMap.containsKey("output")
                && paramMap.containsKey("appName"))
            {
                comm.append(paramMap.get("input")).append(" ");
                // -f ：转换格式，默认flv
                comm.append(" -f ").append(paramMap.containsKey("fmt") ? paramMap.get("fmt") : "flv").append(" ");
                // -r :帧率，默认25
                comm.append("-r ").append(paramMap.containsKey("fps") ? paramMap.get("fps") : "30").append(" ");
                // -s 分辨率 默认是原分辨率
                comm.append("-s ").append(paramMap.containsKey("rs") ? paramMap.get("rs") : "").append(" ");
                // -an 禁用音频
                comm.append("-an ").append(paramMap.containsKey("disableAudio") && ("true".equals(paramMap.get("disableAudio"))) ? "-an" : "").append(" ");
                // 输出地址
                comm.append(paramMap.get("output"));
                // 发布的应用名
                comm.append(paramMap.get("appName"));
                // 一个视频源，可以有多个输出，第二个输出为拷贝源视频输出，不改变视频的各项参数并且命名为应用名+HD
                comm.append(" ").append(" -vcodec copy  -f flv -an ").append(paramMap.get("output")).append(paramMap.get("appName")).append("HD");
                return comm.toString();
            }
        }
        else
        {
            throw new Exception("重大错误：必输项不能为空!");
        }
        return null;
    }
}
