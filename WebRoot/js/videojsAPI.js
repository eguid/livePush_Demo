/*
* 根据videoJS官方文档编写的播放器常用操作  
*/

	//获取当前类型
	function getCurrentType(idnex) {
		return idnex.currentType();
	}
	//获取当前播放地址
	function getCurrentAddr(index) {
		return index.currentSrc();
	}
	//获取当前播放时间
	function getCurrentTime(index) {
		return index.currentTime();
	}
	//获取当前网络状态
	function networkState(index) {
		return index.networkState();
	}
	//修改播放地址
	function setsrc(index, url, type) {
		index.src({
			type : type,
			src : url
		});
	}
	//重载播放器
	function reset(index) {
		index.reset();
		index.load();
	}
	//播放
	function play(index) {
		index.play();
	}
	//暂停
	function pause(index) {
		index.pause();
	}