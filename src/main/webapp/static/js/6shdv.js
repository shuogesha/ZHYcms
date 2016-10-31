/*-- NTV
====================================================== --*/

var ntv = ntv || {};
ntv.stb = ntv.stb || function(){};

/**
 * SHDV中间件问题汇总
 * 1. 返回键不透传给页面的document.onkeydown
 * 2. 页面的window.onbeforeunload时所有实例已销毁,所以释放资源时无法判断是否为空
 */

/*--STB SHDV
====================================================== */
ntv.stb.shdv = function(){};

ntv.stb.shdv.init = function(){
	ntv.stb.shdv.key.control_key_back(true);
};

/*--Tools
====================================================== */
ntv.stb.shdv.get_version = function(){
	return jShow.browserGetVersion();
};

ntv.stb.shdv.get_MAC = function(){
	return jShow.systemInfo().mac;
};

/*--Key
====================================================== */
ntv.stb.shdv.key = function(){};
ntv.stb.shdv.key.control_key_back = function(enabled){
	if(enabled)
		jShow.browserSetControlKeycode(this._change_key_mapping("VK_BACK_SPACE", "KEY_F"));
	else
		jShow.browserSetControlKeycode(this._change_key_mapping("VK_BACK_SPACE", "VK_BACK_SPACE"));
};

ntv.stb.shdv.key._change_key_mapping = function(source_key, new_key){
	var objKey = new Object();
	objKey.remoteCode = source_key;
	objKey.newKeyCode = new_key;
	objKey.ctrlMode = 0;
	objKey.shiftMode = 0;
	objKey.altMode = 0;
	return objKey;
};

ntv.stb.shdv.key.move_back = function(){
	ntv.log.console("ntv.stb.shdv.key.move_back()");
	if(ntv.stb.key.move_back_url)
	{
		ntv.page.openurl(ntv.stb.key.move_back_url);
		ntv.stb.shdv.key.control_key_back(false);

		ntv.stb.shdv.onpageunload();
	}
};

/*--MediaPlayer
====================================================== */
ntv.stb.shdv.mediaplayer = function(){};
ntv.stb.shdv.mediaplayer.mp = undefined;
ntv.stb.shdv.mediaplayer.msg = {}; // 由msg.js 重写
ntv.stb.shdv.mediaplayer.play = function(type, url){
	var log = "ntv.stb.shdv.mediaplayer.play()";
		log +=", param: type = " + type + ", url = " + url;

	if(!this.mp)
	{
		if(type == "AUDIO")
		{
			if(this._hip.play(url))
				this.mp = "hip";
		}
		else if(type == "9DAYEPG")
		{
			if(this._9dayepg.play())
				this.mp = "9dayepg";
		}

	}else
		log += ", " + this.msg.currentMediaPlayer + ntv.msg.common.exist;

	ntv.log.console(log);
};

ntv.stb.shdv.mediaplayer.pause = function(){
	var log = "ntv.stb.shdv.mediaplayer.pause()";

	if(this.mp)
	{
		if(this.mp == "hip")
			this._hip.pause();	
		//else if(this.mp == "9dayepg")
			// 待补充;

	}else
		log += ", " +　this.msg.currentMediaPlayer + ntv.msg.common.isnull;

	ntv.log.console(log);
};

ntv.stb.shdv.mediaplayer.resume = function(){};

ntv.stb.shdv.mediaplayer.stop = function(){
	var log = "ntv.stb.shdv.mediaplayer.stop()";

	if(this.mp)
	{
		if(this.mp == "hip")
			this._hip.stop();	
		//else if(this.mp == "9dayepg")
			// 待补充;

		this.mp = undefined;
	}else
		log += ", " +　this.msg.currentMediaPlayer + ntv.msg.common.isnull;

	ntv.log.console(log);
};

/**
 * 此方法就算页面没有创建媒体实例, 也会主动去调用媒体资源销毁.
 * 为防止在使用返回键或window.location.href离开页面时, 媒体无法停止释放资源
 * 所有需要手动调用释放资源的对象, 都应在此方法内调用一次
 */
ntv.stb.shdv.mediaplayer._stop_every = function(){
	var log = "ntv.stb.ipanel.mediaplayer._stop_every()";

	// hIp接口
	jShow.hIpMediaStop()

	// 9dayepg接口
	// 待补充

	ntv.log.console(log);
};

/*--MediaPlayer Private hIp(IP点播)
====================================================== */
ntv.stb.shdv.mediaplayer._hip = function(){};
ntv.stb.shdv.mediaplayer._hip.msg = {}; // 由msg.js 重写
ntv.stb.shdv.mediaplayer._hip.play = function(url){
	var log = "ntv.stb.shdv.mediaplayer._hip.play()";
		log += ", param: url = " + url + ". ";

	var rs = jShow.hIpMediaPlay(url, 0, 1);

	if(rs == 0)
  		log += this.msg.hIpMediaPlay + ntv.msg.common.success;
  	else
  		log += this.msg.hIpMediaPlay + ntv.msg.common.failure;

	ntv.log.console(log);

	return rs == 0 ? true : false;
};

ntv.stb.shdv.mediaplayer._hip.pause = function(){
	var log = "ntv.stb.shdv.mediaplayer._hip.pause().";

	if(jShow.hIpMediaPause() == 0)
  		log += this.msg.hIpMediaPause + ntv.msg.common.success;
  	else
  		log += this.msg.hIpMediaPause + ntv.msg.common.failure;

	ntv.log.console(log);
};

ntv.stb.shdv.mediaplayer._hip.stop = function(){
	var log = "ntv.stb.shdv.mediaplayer._hip.stop().";

	if(jShow.hIpMediaStop() == 0)
		log += this.msg.hIpMediaStop + ntv.msg.common.success;
	else
		log += this.msg.hIpMediaStop + ntv.msg.common.failure;

	ntv.log.console(log);
};

ntv.stb.shdv.mediaplayer._hip.set_volume = function(value){
	var log = "ntv.stb.shdv.mediaplayer._hip.set_volume()";
		log += ", param: value = " + value + ". ";

	jShow.hIpMediaSetVolume(value)
	log += this.msg.hIpMediaSetVolume + ntv.msg.common.success;

	ntv.log.console(log);
};


/*--MediaPlayer Private 9dayepg(9天EPG)
====================================================== */
ntv.stb.shdv.mediaplayer._9dayepg = function(){};
ntv.stb.shdv.mediaplayer._9dayepg.msg = {}; // 由msg.js 重写
ntv.stb.shdv.mediaplayer._9dayepg.play = function(url){
	var log = "ntv.stb.shdv.mediaplayer._9dayepg.play()";
		log += ", param: url = " + url + ". ";

	var rs = jShow.play9dayEpgEvent(url);
	if(rs == 0)
  		log += this.msg.play9dayEpgEvent + ntv.msg.common.success;
  	else
  		log += this.msg.play9dayEpgEvent + ntv.msg.common.failure;

	ntv.log.console(log);

	return rs == 0 ? true : false;
};


/*--System Event
====================================================== */
ntv.stb.shdv.systemevent = function(event_code){
	ntv.log.console("ntv.stb.shdv.systemevent(), event_code:" + event_code);
};


/*--Destory beforeunload
====================================================== */
ntv.stb.shdv.onpageunload = function(){
	ntv.log.console("ntv.stb.shdv.onpageunload()");
	
	if(ntv.stb.shdv.mediaplayer.mp) // 返回键控制时
		ntv.stb.shdv.mediaplayer.mp = undefined;

	// 由于页面每次加载时都调用, 故使用_stop_every函数
	ntv.stb.shdv.mediaplayer._stop_every();

	// 注销系统消息处理
	jShow.signal.disconnect(ntv.key.systemevent);
};