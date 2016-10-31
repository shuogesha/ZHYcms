/*-- NTV
====================================================== --*/

var ntv = ntv || {};

/*--依赖 ngb_h.js, ipanel.js, shdv.js, pc.js 之后引用
====================================================== */

/*--STB
====================================================== */
ntv.stb = ntv.stb || function(){};

ntv.stb.init = function(){
	ntv.log.console("当前网页运行的浏览器版本为: " + this.get_version());

	ntv.stb.load.init();
	ntv.stb.key.init();
	ntv.stb.mediaplayer.init();
	ntv.stb.systemevent.init();
	ntv.stb.unload.init();
};

/*--Load
====================================================== */
ntv.stb.load = function(){};
ntv.stb.load.init = function(){
	ntv.log.console("ntv.stb.load.init()");

	var browser = ntv.profile.browser;
	
	if(browser == "NGB-H")
		ntv.stb.ngb_h.init();
	else if(browser == "iPanel")
		ntv.stb.ipanel.init();
	else if(browser == "SHDV")
		ntv.stb.shdv.init();
};

/*--Tools
====================================================== */
ntv.stb.get_version = function(){
	var version = "未获取到浏览器版本!";

	var browser = ntv.profile.browser;

	if(browser == "iPanel")
		version = ntv.stb.ipanel.get_version()[0];
	else if(browser == "SHDV")
		version = ntv.stb.shdv.get_version();
	else if(browser == "NGB-H")
		version = ntv.stb.ngb_h.get_version();
	else if(browser == "PCBrowser")
		version = ntv.stb.pcbrowser.get_version();

	return version;
};

ntv.stb.get_MAC = function(){
	var mac = "未获取到机顶盒MAC地址!";

    var browser = ntv.profile.browser;

	if(browser == "iPanel")
		mac = ntv.stb.ipanel.get_MAC();
	else if(browser == "SHDV")
		mac = ntv.stb.shdv.get_MAC();
	else if(browser == "NGB-H")
		mac = ntv.stb.ngb_h.get_MAC();
	else if(browser == "PCBrowser")
		mac = ntv.stb.pcbrowser.get_MAC();

	ntv.log.console("ntv.stb.get_MAC(), mac: "+ mac);
	return mac;
};

/*--Key
====================================================== */
ntv.stb.key = function(){};
ntv.stb.key.move_back_url = "";
ntv.stb.key.init = function(){
	ntv.log.console("ntv.stb.key.init()");

	var browser = ntv.profile.browser;
	
	if(browser == "NGB-H")
		ntv.navigation.move_back = ntv.stb.ngb_h.key.move_back;
	else if(browser == "iPanel")
		ntv.navigation.move_back = ntv.stb.ipanel.key.move_back;
	else if(browser == "SHDV")
		ntv.navigation.move_back = ntv.stb.shdv.key.move_back;
	else if(browser == "PCBrowser")
		ntv.navigation.move_back = ntv.stb.pcbrowser.key.move_back;
};

ntv.stb.key.enable_manual_control_back_event = function(){
	ntv.key.keypress = ntv.key.keypress_for_manual_control_back_event;
};

/*--MediaPlayer
====================================================== */
ntv.stb.mediaplayer = function(){};
ntv.stb.mediaplayer.init = function(){
	ntv.log.console("ntv.stb.mediaplayer.init()");

	var browser = ntv.profile.browser;
	
	if(browser == "NGB-H")
		ntv.stb.mediaplayer = ntv.stb.ngb_h.mediaplayer;
	else if(browser == "iPanel")
		ntv.stb.mediaplayer = ntv.stb.ipanel.mediaplayer;
	else if(browser == "SHDV")
		ntv.stb.mediaplayer = ntv.stb.shdv.mediaplayer;
	else if(browser == "PCBrowser")
		ntv.stb.mediaplayer = ntv.stb.pcbrowser.mediaplayer;
};

/*--SystemEvent
====================================================== */
ntv.stb.systemevent = function(){};
ntv.stb.systemevent.init = function(){
	ntv.log.console("ntv.stb.systemevent.init()");

	var browser = ntv.profile.browser;
	
	if(browser == "NGB-H")
		ntv.key.systemevent_handle = ntv.stb.ngb_h.systemevent;
	else if(browser == "iPanel")
		ntv.key.systemevent_handle = ntv.stb.ipanel.systemevent;
	else if(browser == "SHDV")
		ntv.key.systemevent_handle = ntv.stb.shdv.systemevent;
};

/*--unload
====================================================== */
ntv.stb.unload = function(){};
ntv.stb.unload.init = function(){
	ntv.log.console("ntv.stb.unload.init()");

	var browser = ntv.profile.browser;
	
	if(browser == "NGB-H") // NGB-H在离开页面时会自动释放媒体资源
		ntv.page.onbeforeunload_stb = ntv.stb.ngb_h.onpageunload;
	else if(browser == "iPanel")
		ntv.stb.ipanel.onpageunload();
	else if(browser == "SHDV")
		ntv.stb.shdv.onpageunload();
};


/*--stb对象初始化
====================================================== */
(function(){
	ntv.stb.init();
})();