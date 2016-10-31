/*-- NTV
====================================================== --*/

var ntv = ntv || {};
ntv.stb = ntv.stb || function(){};


/*--PCBrowser
====================================================== */
ntv.stb.pcbrowser = function(){};

/*--Tools
====================================================== */
ntv.stb.pcbrowser.get_version = function(){
	return "12";
};

ntv.stb.pcbrowser.get_MAC = function(){
	return "98bc5705f8a0";
};

/*--Key
====================================================== */
ntv.stb.pcbrowser.key = function(){};
ntv.stb.pcbrowser.key.move_back = function(){
	ntv.log.console("ntv.stb.pcbrowser.key.move_back()");
	if(ntv.stb.key.move_back_url)
	{
		ntv.page.openurl(ntv.stb.key.move_back_url);
	}
};

/*--MediaPlayer
====================================================== */
ntv.stb.pcbrowser.mediaplayer = function(){};
ntv.stb.pcbrowser.mediaplayer.mp = undefined;
ntv.stb.pcbrowser.mediaplayer.msg = {}; // 由msg.js 重写

ntv.stb.pcbrowser.mediaplayer.play = function(type, url){
	var log = "ntv.stb.pcbrowser.mediaplayer.play()";
		log += ", param : type = " + type + ", url = " + url + ". ";

	if(!this.mp)
	{
		log += this.msg.play + ntv.msg.common.success;
		this.mp = 1;
	}
	else
		log += this.msg.currentMediaPlayer + ntv.msg.common.exist;

	ntv.log.console(log);
};

ntv.stb.pcbrowser.mediaplayer.pause = function(){
	var log = "ntv.stb.pcbrowser.mediaplayer.pause(), ";

	if(this.mp)
		log += this.msg.pause + ntv.msg.common.success;
	else
		log += this.msg.currentMediaPlayer + ntv.msg.common.isnull;

	ntv.log.console(log);
};

ntv.stb.pcbrowser.mediaplayer.resume = function(){
	var log = "ntv.stb.pcbrowser.mediaplayer.resume(), ";

	if(this.mp)
		log += this.msg.resume + ntv.msg.common.success;
	else
		log += this.msg.currentMediaPlayer + ntv.msg.common.isnull;

	ntv.log.console(log);
};

ntv.stb.pcbrowser.mediaplayer.stop = function(){
	var log = "ntv.stb.pcbrowser.mediaplayer.stop(), ";

	if(this.mp)
	{
		log += this.msg.stop + ntv.msg.common.success;
		this.mp = undefined;
	}
	else
		log += this.msg.currentMediaPlayer + ntv.msg.common.isnull;

	ntv.log.console(log);
};