/**
 * 文件名：CamaraLiveService.java 
 * 描述：提供直播流管理的接口化服务 
 * 修改人：eguid 
 * 修改时间：2016年6月30日 
 * 修改内容：
 */
package cn.njlingdong.cameraLive.service;

import cn.njlingdong.cameraLive.entity.LiveInfoEntity;
import cn.njlingdong.cameraLive.entity.ResultData;

/**
 * 管理直播流查看、发布和关闭
 * 
 * @author eguid
 * @version 2016年6月30日
 * @see CamaraLiveService
 * @since jdk1.7
 */

public interface CameraLiveService
{
    /**
     * 创建一个直播流应用
     * @param liveInfo
     * @return appName（当前应用名）
     */
    public ResultData add(LiveInfoEntity liveInfo);

    /**
     * 关闭直播流应用
     * @param appName
     */
    public ResultData remove(String appName);

    /**
     * 查看当前所有正在运行的直播流应用
     * @return appName列表
     */
    public ResultData viewAll();
    /**
     * 查看应用详细
     * @return appName列表
     */
    public ResultData view(String appName);
}
