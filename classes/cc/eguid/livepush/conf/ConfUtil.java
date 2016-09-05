/**
 * 文件名：ConfUtil.java 描述：读取配置文件属性 修改人：eguid 修改时间：2016年7月8日 修改内容：
 */
package cc.eguid.livepush.conf;

import java.io.File;

/**
 * 读取配置文件
 * 
 * @author eguid
 * @version 2016年7月8日
 * @see ConfUtil
 * @since jdk1.7
 */

public class ConfUtil
{
    private volatile static boolean isHave=false;
    private volatile static String ffmpegPath=null;
    public ConfUtil()
    {
        super();
        initConfInfo();
    }

    /**
     * 从配置文件中初始化参数
     */
    private  void initConfInfo()
    {
        System.out.print("预加载配置：");
        String path = getClass().getResource("../").getPath() + "ffmpeg/ffmpeg.exe";
        File ffmpeg =new File(path);
        ffmpegPath=ffmpeg.getPath();
        if (isHave=ffmpeg.isFile())
        {
            System.out.println("加载ffmpeg成功！");
        }
        else
        {
            System.out.println("加载ffmpeg失败！");
        }
    }

    /**
     *判断ffmpeg环境配置
     * @return true：配置成功；false：配置失败
     */
    public  boolean isHave()
    {
        return isHave;
    }
    /**
     * 获取ffmpeg环境调用地址
     *  添加方法功能描述
     * @return
     */
    public String getPath()
    {
        return ffmpegPath;
    }
}
