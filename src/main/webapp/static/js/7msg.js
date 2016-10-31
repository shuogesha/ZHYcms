 /*-- NTV
====================================================== --*/

 var ntv = ntv || {};


/*--Msg
====================================================== */
ntv.msg = function(){};
ntv.msg.common = {
	success:           "SUCCESS.",
	failure:           "FAILURE.",
	error:             "ERROR.",
	exist:          　 "IS EXIST.",
	isnull:            "IS NULL."
};


/*--STB NGB-H
====================================================== */
ntv.msg.ngb_h = function(){};
ntv.msg.ngb_h.init = function(){
	ntv.stb.ngb_h.mediaplayer.msg = this.dev.mediaplayer;
};

ntv.msg.ngb_h.dev = function(){};
ntv.msg.ngb_h.dev.mediaplayer = {
	// MediaPlayer对象
	getPlayerInstanceID: "获得接收终端本地可用的播放器实例ID: ",
	bindPlayerInstance:  "MediaPlayer对象与播放器实例绑定: ",
	unbindPlayerInstance: "MediaPlayer对象和当前播放器实例解除绑定: ",
	setMediaSource:       "异步方法，设置待播放媒体的URL地址: ",
	play:                 "异步方法，从媒体起始点开始播放: ",
	seek:                 "异步方法，从当前媒体的某个时间点开始播放: ",
	setPace:              "异步方法，设置播放步长: ",
	pause:                "异步方法，暂停播放: ",
	resume:               "异步方法，恢复播放: ",
	stop:                 "异步方法，停止播放: ",
	refresh:              "刷新视频窗口: ",
	enableTrickMode:      "设置MediaPlayer对象当前所绑定的播放器实例在其生命周期内是否允许特技操作: ",
	getTrickModeFlag:     "获取特技模式操作标志: ",
	setVideoDisplayMode:  "设置MediaPlayer对象对应的视频窗口的显示模式: ",
	getVideoDisplayMode:  "获取MediaPlayer对象对应的视频窗口的显示模式: ",
	setVideoDisplayArea:  "设置窗口显示区域: ",
	getVideoDisplayArea:  "获取视频窗口显示区域的位置信息: ",
	setVolume:            "设置当前音量: ",
	getVolume:            "获取当前音量: ",
	getMediaDuration:     "获取当前播放媒体的总时长: ",
	getCurrentPlayTime:   "获取媒体播放的当前时间点: ",
	getPlaybackMode:      "获取播放器当前的播放模式: ",
	// 自定义
	currentMediaPlayer:   "当前MediaPlayer实例: "
};

/*--STB iPanel
====================================================== */
ntv.msg.ipanel = function(){};
ntv.msg.ipanel.init = function(){
	ntv.stb.ipanel.mediaplayer.msg = this.dev.mediaplayer;
	ntv.stb.ipanel.mediaplayer._mp3.msg = this.dev.mediaplayer;
	ntv.stb.ipanel.mediaplayer._media.av.msg = this.dev.mediaplayer;
};

ntv.msg.ipanel.dev = function(){};
ntv.msg.ipanel.dev.mediaplayer = {
	// media.AV接口
	media_AV_open:         "media.AV接口，异步方法，打开指定媒体源进入待命状态: ",
	media_AV_play:         "media.AV接口，异步方法，开始正常播放当前所打开的视频: ",
	media_AV_pause:        "media.AV接口，暂时停止正在播放的视频: ",
	media_AV_stop:         "media.AV接口，将正在播放的、暂停的视频停止播放，进入待命状态: ",
	media_AV_close:        "media.AV接口，释放该媒体源信息及其相关信息: ",
	// MP3接口（已过时），为了兼容低版本。新版本请使用，media.AV接口
	MP3_setProperty:       "MP3接口（已过时），设置媒体属性: ",
	MP3_open:              "MP3接口（已过时），打开媒体源: ",
	MP3_play:              "MP3接口（已过时），播放媒体: ",
	MP3_pause:             "MP3接口（已过时），暂停播放: ",
	MP3_stop:              "MP3接口（已过时），停止媒体播放: ",
	MP3_close:             "MP3接口（已过时），释放媒体资源: ",
	MP3_resume:            "MP3接口（已过时），恢复已暂停的播放: ",
	// 自定义
	currentMediaPlayer:    "当前MediaPlayer实例: "
};

/*--STB SHDV
====================================================== */
ntv.msg.shdv = function(){};
ntv.msg.shdv.init = function(){
	ntv.stb.shdv.mediaplayer.msg = this.dev.mediaplayer;
	ntv.stb.shdv.mediaplayer._hip.msg = this.dev.mediaplayer;
	ntv.stb.shdv.mediaplayer._9dayepg.msg = this.dev.mediaplayer;
};

ntv.msg.shdv.dev = function(){};
ntv.msg.shdv.dev.mediaplayer = {
	// jShow IP点播接口
	hIpMediaPlay:          "IP点播接口，Ip方式播放文件: ",
	hIpMediaPause:         "IP点播接口，暂停正在通过ip方式播放的文件: ",
	hIpMediaStop:          "IP点播接口，停止正在通过ip方式播放的文件: ",
	hIpMediaSetVolume:     "IP点播接口，播放背景视频的同时设置背景视频音量: ",
	// 自定义
	currentMediaPlayer:    "当前MediaPlayer实例: "

};

/*--STB PCBrowser
====================================================== */
ntv.msg.pcbrowser = function(){};
ntv.msg.pcbrowser.init = function(){
	ntv.stb.pcbrowser.mediaplayer.msg = this.dev.mediaplayer;
};

ntv.msg.pcbrowser.dev = function(){};
ntv.msg.pcbrowser.dev.mediaplayer = {
	play:                  "PC浏览器，播放视频: ",
	pause:                 "PC浏览器，暂停视频播放: ",
	resume:                "PC浏览器，恢复暂停的视频: ",
	stop:                  "PC浏览器，停止视频播放: ",
	// 自定义
	currentMediaPlayer:    "当前MediaPlayer实例: "

};



/*--Msg对象初始化
====================================================== */
(function(){
	ntv.msg.ngb_h.init();
	ntv.msg.ipanel.init();
	ntv.msg.shdv.init();
	ntv.msg.pcbrowser.init();
})();