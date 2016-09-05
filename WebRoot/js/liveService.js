/*
 * 页面操作（发布，播放，删除，查看发布列表，查看发布信息）
 * 编写：wangliang
 */
//封装JsonAjax方法(请求类型，url，参数，成功方法)
function JsonAjax(urltype,urlLink,paramData,sucessMethod){
	$.ajax({
		type:urltype,
		url:urlLink,
		data:paramData,
		dataType:"json",
		success:function(data){
			sucessMethod(data);
		},
		error:function(){
			alert("服务器未开启或页面错误，请联系管理员");
		}
		});
	}
//增强型ajax方法：增加一个回调参数方便回调方法调用；通过回调参数，回调方法就可以在调用方法后续无法处理的业务的时候处理后续动作，比如生成列表
function EchoJsonAjax(urltype,urlLink,paramData,sucessMethod,async,index){
	$.ajax({
		type:urltype,
		url:urlLink,
		async:async,
		data:paramData,
		dataType:"json",
		contentType:"application/json,;charset=UTF-8",
		success:function(data){
			//把回调参数给回调方法
			sucessMethod(data,index);
		},
		error:function(){
			alert("服务器未开启或页面错误，请联系管理员");
		}
		});
	}
/*
 * --------------------发布应用事件-----------------------
 */
/**
 * 发布一个应用方法
 */
function pushApp(newMethod){
	//自定义成功方法
	var appName=$("#p-appName").val();
	var input=$("#p-input").val();
	var output=$("#p-output").val();
	var fmt=$("#p-fmt").val();
	var fps=$("#p-fps").val();
	var rs=$("#p-rs").val();
	var disableAudio=$("#p-disableAudio").val();
	if(isNull(appName)||isNull(input)||isNull(output)||isNull(fmt)||isNull(fps)||isNull(rs)||isNull(disableAudio)){
		alert("发布失败，必须填写全部参数！");
		return ;
		}
	var param=$("#appForm").serializeArray();
	if(window.confirm("确定发布名称为：’"+appName+" ‘的实时应用？（提示：发布后该实时流（在不关闭的情况下）会一直保持推送状态）")){
		if(newMethod){
			JsonAjax("POST","live/push/"+appName,param,newMethod);
		}else{
			JsonAjax("POST","live/push/"+appName,param,sucessPushApp);
		}
		}
}
//是否为空
function isNull(element){
	return element==null||new String(element).trim()=="";
}
/**
 * 成功返回方法：成功发布应用后更新应用列表
 * @param element
 */
function sucessPushApp(resultData){
	if(resultData){
	if(resultData.status==0){
		//显示列表
		viewAll();
		}
	//不管是否发布成功都要显示结果信息
	alert(resultData.msg);
	}else{
		alert("服务器抽风了，请稍后再试！");
	}
}
/*
 * -----------------关闭应用事件---------------
 */
/**
 * 通过应用名关闭应用
 * @param element
 */
function closeApp(element){
	var appName=$(element).parent().parent().data("appName");
	if(window.confirm("停止这个实时应用？（提示：实时应用无法暂停，停止即删除）")){
		EchoJsonAjax("DELETE","live/close/"+appName,"",sucessCloseApp,true,element);
	}
}
/**
 * 成功返回方法：成功关闭应用事件
 * @param resultData
 * @param index
 */
function sucessCloseApp(resultData,index){
	if(resultData){
		if(resultData.status==0){
			//删除应用后刷新列表
			viewAll();
		}
		alert(resultData.msg);
	}else{
		alert("服务器抽风了 - -!");
	}
	}
/*
 * ----------------------查看应用详细事件-------------------
 */
/**
 * 查看应用详细信息
 * @param element：点击事件的按钮元素
 * @param sucessMethod：成功返回的方法
 * @param index：appName，该参数如果可用，element参数将自动失效
 * 
 */
function view(element,sucessMethod,index){
	var appName=index?index:$(element).parent().parent().data("appName");
	JsonAjax("GET","live/view/"+appName,"",sucessMethod);
}
//成功获取应用详细（暂时不用）
function sucessvViewApp(resultData,element){
	
}
/*
 * --------------列表查询事件------------
 */
/**
 * 查看当前全部应用方法
 */
function viewAll(){
	JsonAjax("GET","live/viewAll","",sucessViewAllAppList);
}
/**
 * 成功返回方法：成功获取参数后把所有应用形成列表
 * @param resultData
 */
function sucessViewAllAppList(resultData){
	if(resultData||resultData.status==0){
		//每次生成要先把原数据删除再生成
		$("#appList").empty();
		var listData=resultData.data;
		if(listData&&listData.length>0){
			for(var i=0;i<listData.length;i++){
				var elementHTML='<tr class="appManager"><td class="list-appName">'+
				listData[i].appName+'</td><td class="list-input">'+
				listData[i].input+'</td><td class="list-output">'+
				listData[i].output+'</td><td class="list-fmt">'+
				listData[i].fmt+'</td><td class="list-fps">'+
				listData[i].fps+'</td><td class="list-rs">'+
				listData[i].rs+'</td><td class="list-disableAudio">'+
				listData[i].disableAudio+'</td><td class="playVideoOnTable">'+
				'<input class="playVideo" type="button" value="播放" onclick="playFromList(this);"/></td><td class="closeVideoOnTable">'+
				'<input class="closeVideo" type="button" value="关闭" onclick="closeApp(this);"/></td></tr>';
				$(elementHTML).appendTo($("#appList")).data("appName",listData[i].appName);
			}
			}else{
				alert(resultData.msg);
				//查询列表失败清空列表
				$("#appList").empty();
			}
		}else{
			alert("服务器抽风了 - -!");
			//查询列表失败清空列表
			$("#appList").empty();
		}
}
/*
 * -----------------发布并播放事件----------------
 */
/**
 * 发布并播放方法
 */
function pushAndPlay(){
	pushApp(sucesspushAndPlay);
	}
/**
 * 成功返回方法：发布后播放器播放发布的应用
 */
function sucesspushAndPlay(resultData)
{
	if(resultData){
		if(resultData.status==0){
			//显示列表
			viewAll();
			var appName=resultData.data.appName;
			view(null,sucessPlayFromView,appName);
			}
		//不管是否发布成功都要显示结果信息
		alert(resultData.msg);
		}else{
			alert("服务器抽风了，请稍后再试！");
		}
}
/*
 * ----------------播放事件----------------
 */
/**
 * 播放应用
 * @param element
 */
function playFromList(element){
	if(window.confirm("播放这个实时应用？（提示：这将会关闭正在播放的应用）")){
	view(element,sucessPlayFromView);
	}
}
/**
 * 成功获取应用播放地址并播放
 * @param resultData
 */
function sucessPlayFromView(resultData){
	if(resultData){
		if(resultData.status==0){
		var playUrl=resultData.data.output+resultData.data.appName;
		//修改播放地址并播放
		writeAddressAndPlay(player,playUrl)
		}else{
		alert(resultData.msg);
		}
	}else{
		alert("服务器抽风了 - -!");
	}
}
