
//播放器实例
var player = videojs('videoPlayer');

//播放器初始化操作面板清晰度菜单
	function playerInitVideo() {
		$videoPanelMenu = $(".vjs-fullscreen-control");
		$videoPanelMenu.before('<div class="vjs-subtitles-button vjs-menu-button vjs-menu-button-popup vjs-control vjs-button" tabindex="0" role="menuitem" aria-live="polite" aria-expanded="false" aria-haspopup="true">'
						+ '<div class="vjs-menu" role="presentation">'
						+ '<ul class="vjs-menu-content" role="menu">'
						+ '<li class="vjs-menu-item" tabindex="-1" role="menuitemcheckbox"  onclick="changeUrl(this)">高清</li>'
						+ '<li class="vjs-menu-item vjs-selected" tabindex="-1" role="menuitemcheckbox"  onclick="changeUrl(this)">标清 </li>'
						+ '</ul></div><span class="vjs-control-text">清晰度</span></div>');
		}
	//加载页面进行播放器初始化
	player.ready(function() {
		playerInitVideo();
		//player.play();
		//setsrc(player,"rtmp://192.168.30.21/live/test3","rtmp/flv");
	});
	
	//通过id获取DOM
	function get(index) {
		return document.getElementById(index);
	}
	//修改播放地址并播放
	function writeAddressAndPlay(index,url,type) {
		//播放器操作
		setsrc(index, url, type?type:"rtmp/flv");
		play(index);
	}
	//高清标清切换就是应用名加减HD
	function changeUrl(video) {
		var index = $(video).text();
		//获取当前播放的url
		var CurrentUrl = getCurrentAddr(player);
		$(".vjs-menu-item").removeClass("vjs-selected");
			$(video).addClass("vjs-selected");
		if (index == "高清") {
			if (CurrentUrl.indexOf("HD") == -1) {
				CurrentUrl = CurrentUrl + "HD";
			} else {
				return;
			}
		} else {
			if (CurrentUrl.indexOf("HD") != -1) {
				CurrentUrl = CurrentUrl.replace("HD", "");
			} else {
				return;
			}
		}
		//修改地址并播放
		writeAddressAndPlay(player, CurrentUrl);
	}
	