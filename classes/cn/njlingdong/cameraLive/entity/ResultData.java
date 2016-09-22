/**
 * 文件名：ResultData.java
 * 描述：响应JSON结果集
 * 修改人：eguid
 * 修改时间：2016年6月30日
 * 修改内容：
 */
package cn.njlingdong.cameraLive.entity;

import java.io.Serializable;

/**
 * 响应JSON结果集
 * 
 * @author eguid
 * @version 2016年6月30日
 * @see ResultData
 * @since
 */

public class ResultData implements Serializable {
	private static final long serialVersionUID = 7219235230800863548L;
	private String status;
	private String msg;
	private Object data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResultData [status=" + status + ", msg=" + msg + ", data=" + data + "]";
	}

}
