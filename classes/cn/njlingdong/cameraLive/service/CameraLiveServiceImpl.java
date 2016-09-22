/**
 * 文件名：CameraLiveServiceImpl.java 
 * 描述：实现监控实时视频发布 
 * 修改人：eguid 
 * 修改时间：2016年6月30日 
 * 修改内容：
 */
package cn.njlingdong.cameraLive.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Service;

import cc.eguid.livepush.PushManager;
import cc.eguid.livepush.PushManagerImpl;
import cn.njlingdong.cameraLive.entity.LiveInfoEntity;
import cn.njlingdong.cameraLive.entity.ResultData;
import cn.njlingdong.cameraLive.util.ResultDataUtil;

/**
 * 实现实时监控视频发布
 * 
 * @author eguid
 * @version 2016年6月30日
 * @see CameraLiveServiceImpl
 * @since jdk1.7
 */
@Service("cameraLiveService")
public class CameraLiveServiceImpl implements CameraLiveService
{
    /**
     * 引用push管理器，用于push直播流到rtmp服务器
     */
    private static PushManager pusher = new PushManagerImpl();

    // 简单存放发布视频的信息
    private static ConcurrentMap<String, LiveInfoEntity> AllLiveInfo = new ConcurrentHashMap<String, LiveInfoEntity>();

    public static void setPusher(PushManager pusher)
    {
        CameraLiveServiceImpl.pusher = pusher;
    }

    @Override
    public ResultData add(LiveInfoEntity liveInfo)
    {
        ResultData result = new ResultData();
        String appName = null;
        if (liveInfo != null && liveInfo.getAppName() != null && liveInfo.getInput() != null
            && liveInfo.getOutput() != null)
        {
            if (ResultDataUtil.checkStringNoCN(liveInfo.getAppName())
                && ResultDataUtil.checkURLNoCN(liveInfo.getInput())
                && ResultDataUtil.checkURLNoCN(liveInfo.getOutput()))
            {
                if (AllLiveInfo.containsKey(liveInfo.getAppName()))
                {
                    ResultDataUtil.setData(result, "2", "发布应用失败：该应用已存在", appName);
                }
                else
                {
                    Map<String, Object> map = getMap4LiveInfo(liveInfo);
                    //开启推送处理器
                    appName = pusher.push(map);
                    // 存放信息
                    AllLiveInfo.put(appName, liveInfo);
                    ResultDataUtil.setData(result, "0", "成功发布应用", map);
                }
            }
            else
            {
                ResultDataUtil.setData(result, "3", "发布应用失败：应用非地址禁止使用特殊符号，空格，中文字符；地址可以包含/:@.?&=特殊字符", appName);
            }
        }
        else
        {
            ResultDataUtil.setData(result, "1", "发布应用失败", appName);
        }
        return result;
    }

    /**
     * 解析参数
     * 
     * @param liveInfo
     * @return
     */
    private Map<String, Object> getMap4LiveInfo(LiveInfoEntity liveInfo)
    {
        String appName = liveInfo.getAppName();
        String input = liveInfo.getInput();
        String output = liveInfo.getOutput();
        String fmt = liveInfo.getFmt();
        String fps = liveInfo.getFps();
        String rs = liveInfo.getRs();
        String disableAudio = liveInfo.getDisableAudio();

        Map<String, Object> map = new HashMap<String,Object>(20);
        map.put("appName", appName);
        // 输入输出暂时固定
        map.put("input", input);
        map.put("output", output);
        if (fmt != null)
        {
            map.put("fmt", fmt);
        }
        if (fps != null)
        {
            map.put("fps", fps);
        }
        if (rs != null)
        {
            map.put("rs", rs);
        }
        if (disableAudio != null)
        {
            map.put("disableAudio", disableAudio);
        }
        return map;
    }

    @Override
    public ResultData remove(String appName)
    {
        ResultData result = new ResultData();
        if (appName != null && AllLiveInfo.containsKey(appName))
        {
            pusher.closePush(appName);
            AllLiveInfo.remove(appName);
            ResultDataUtil.setData(result, "0", "删除成功", appName);
        }
        else
        {
            ResultDataUtil.setData(result, "2", "非法操作或删除失败", appName);
        }
        return result;
    }

    @Override
    public ResultData viewAll()
    {
        ResultData result = new ResultData();
        if (AllLiveInfo != null && !AllLiveInfo.isEmpty())
        {
            ResultDataUtil.setData(result, "0", "获取全部信息成功", getSet());
        }
        else
        {
            ResultDataUtil.setData(result, "1", "当前没有发布新的应用", null);
        }
        return result;
    }

    private List<LiveInfoEntity> getSet()
    {
        List<LiveInfoEntity> list = new ArrayList<LiveInfoEntity>();
        if (!AllLiveInfo.isEmpty())
        {
            for (String key : AllLiveInfo.keySet())
            {
                list.add(AllLiveInfo.get(key));
            }
        }
        return list;

    }

    @Override
    public ResultData view(String appName)
    {
        ResultData result = new ResultData();
        if (appName != null && AllLiveInfo.containsKey(appName))
        {
            LiveInfoEntity liveInfo=AllLiveInfo.get(appName); 
            ResultDataUtil.setData(result, "0", "获取详细信息成功",liveInfo);
        }
        else
        {
            ResultDataUtil.setData(result, "1", "获取失败", appName);
        }
        return result;
    }
}
