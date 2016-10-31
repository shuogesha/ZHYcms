/*-- NTV
====================================================== --*/

var ntv = ntv || {};
ntv.stb = ntv.stb || function(){};

/**
 * NGB-H中间件问题汇总
 * 1. mp.setVideoDisplayMode(255);无效
 */

/*--STB NGB-H
====================================================== */
ntv.stb.ngb_h = function(){};

ntv.stb.ngb_h.init = function(){
	
};

/*--Tools
====================================================== */
ntv.stb.ngb_h.get_version = function(){
	return "待补充";
};

ntv.stb.ngb_h.get_MAC = function(){
	var ethernets = Broadband.getAllEthernets();
	if (ethernets.length > 0) {
		var ethernet = ethernets[0];
		mac = ethernet.MACAddress.replace(/-/g, "");
	} else {
		mac = ethernet.MACAddress.replace(/-/g, "");
	}

	return mac;
};

/*--Key
====================================================== */
ntv.stb.ngb_h.key = function(){};
ntv.stb.ngb_h.key.move_back = function(){
	ntv.log.console("ntv.stb.ngb_h.key.move_back()");
	if(ntv.stb.key.move_back_url)
	{
		ntv.page.openurl(ntv.stb.key.move_back_url);
		
		ntv.stb.ngb_h.onpageunload();
	}
};

/*--MediaPlayer
====================================================== */
ntv.stb.ngb_h.mediaplayer = function(){};
ntv.stb.ngb_h.mediaplayer.mp = undefined;
ntv.stb.ngb_h.mediaplayer.msg = {}; // 由msg.js 重写
/**
 * 媒体统一播放函数
 * param type String "AUDIO", "VOD", "DVB"
 * param url  String "http://mp3", "rtsp://ts", "dvb:xxx"
 */
ntv.stb.ngb_h.mediaplayer.play = function(type, url){

	var log = "ntv.stb.ngb_h.mediaplayer.play(), ";

	if(!this.mp)
	{
		this._set_mp_instance();

		var set_source_rs = this.mp.setMediaSource(url);
		if(set_source_rs == 0)
		{
			if(type == "AUDIO")
				this._set_audio_mode(this.mp);
			else if(type == "VOD")
				this._set_vod_mode(this.mp);
			else if(type == "DVB")
				this._set_dvb_mode(this.mp);

			this.mp.play();
			log += this.msg.play + ntv.msg.common.success;
		}
		else
			log += this.msg.setMediaSource + ntv.msg.common.failure;
	}
	else
		log += this.msg.currentMediaPlayer + ntv.msg.common.exist;

	ntv.log.console(log);		
};

ntv.stb.ngb_h.mediaplayer.pause = function(){
	this.mediaplayer.mp.pause();
};
ntv.stb.ngb_h.mediaplayer.resume = function(){
	this.mediaplayer.mp.resume();
};

/**
 * 临时使用函数,中间件bug.待bug解决后请使用_stop()
 * 龙晶提供临时解决方法，将媒体地址指向错误地址
 */
ntv.stb.ngb_h.mediaplayer.stop = function(){
	var log = "ntv.stb.ngb_h.mediaplayer.stop()";

	if(this.mp)
	{
		this.mp.setMediaSource("http://127.0.0.1/audio.mp3");
		this.mp.refresh();
		this.mp.play();
		this.mp = undefined;
	}else
		log += this.msg.stop + ntv.msg.common.isnull;

	ntv.log.console(log);
};

ntv.stb.ngb_h.mediaplayer._stop = function(){
	var log = "ntv.stb.ngb_h.mediaplayer.stop(), ";

	if(this.mp)
	{
		var mp_stop_rs = this.mp.stop();

		if(mp_stop_rs == 0)
		{
			var unbind_instance_rs = this.mp.unbindPlayerInstance();
			if(unbind_instance_rs == 0)
			{
				this.mp = undefined;
				log += this.msg.stop + ntv.msg.common.success;
			}
			else
				log += this.msg.unbindPlayerInstance + ntv.msg.common.failure;
		}else
			log += this.msg.stop + ntv.msg.common.failure;
	}else
		log += this.msg.stop + ntv.msg.common.isnull;

	ntv.log.console(log);
};

/*--MediaPlayer Private Function
====================================================== */
ntv.stb.ngb_h.mediaplayer._set_mp_instance = function(){
	var log = "ntv.stb.ngb_h.mediaplayer._set_mp_instance(), ";

	var mp = new MediaPlayer();
	var get_instance_id = mp.getPlayerInstanceID();
	if(get_instance_id != -1)
	{
		var bind_instance_rs = mp.bindPlayerInstance(get_instance_id);
		if(bind_instance_rs != -1)
		{
			this.mp = mp;
			log += this.msg.bindPlayerInstance + ntv.msg.common.success;
		}
		else
			log += this.msg.bindPlayerInstance + ntv.msg.common.failure;
	}
	else
		log += this.msg.getPlayerInstanceID + ntv.msg.common.failure;

	ntv.log.console(log);
};

ntv.stb.ngb_h.mediaplayer._set_audio_mode = function(mp){
	var log = "ntv.stb.ngb_h.mediaplayer._set_audio_mode(), ";

	var rect = this._set_rectangle(1280, 720, 0, 0);
	var set_display_area_rs = mp.setVideoDisplayArea(rect);
	if(set_display_area_rs == 0)
	{
		var mp_refresh_rs = mp.refresh();
		if(mp_refresh_rs == 0)
		{
			log += this.msg.setVideoDisplayArea + ntv.msg.common.success;	
		}else
			log += this.msg.refresh + ntv.msg.common.failure;
		
	}else
		log += this.msg.setVideoDisplayArea + ntv.msg.common.failure;


	ntv.log.console(log);
};
ntv.stb.ngb_h.mediaplayer._set_vod_mode = function(mp){
	var log = "ntv.stb.ngb_h.mediaplayer._set_vod_mode(), ";

	ntv.log.console(log);
};
ntv.stb.ngb_h.mediaplayer._set_dvb_mode = function(mp){
	var log = "ntv.stb.ngb_h.mediaplayer._set_dvb_mode(), ";

	ntv.log.console(log);
};

ntv.stb.ngb_h.mediaplayer._set_rectangle = function(left, top, width, height){
	var rect = new Rectangle();
	rect.left = left;
	rect.top = top;
	rect.width = width;
	rect.height = height;

	return rect;
};


/*--System Event
====================================================== */
ntv.stb.ngb_h.systemevent = function(event_code){
	ntv.log.console("ntv.stb.ngb_h.systemevent(), event_code:" + event_code);
};


/*--Destory beforeunload
====================================================== */
ntv.stb.ngb_h.onpageunload = function(){
	ntv.log.console("ntv.stb.ngb_h.onpageunload()");
	// MediaPlayer资源释放,如果不为空
	if(ntv.stb.ngb_h.mediaplayer.mp)
		ntv.stb.ngb_h.mediaplayer.stop();
};